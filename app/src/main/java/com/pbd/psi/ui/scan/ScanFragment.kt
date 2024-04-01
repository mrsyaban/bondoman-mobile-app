package com.pbd.psi.ui.scan

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.pbd.psi.LoginActivity
import com.pbd.psi.api.ApiConfig
import com.pbd.psi.databinding.FragmentScanBinding
import com.pbd.psi.models.UploadRes
import com.pbd.psi.repository.ScanRepository
import com.pbd.psi.room.AppDatabase
import com.pbd.psi.room.TransactionEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private var imageCapture: ImageCapture? = null

    private var previewFrozen: Boolean = false
    private var cameraProvider: ProcessCameraProvider? = null

    private lateinit var viewModel: ScanViewModel

    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri = result.data?.data
            selectedImageUri?.let { uri ->
                loadSelectedImage(uri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                startCamera()
            }
        }

        permissionLauncher.launch(Manifest.permission.CAMERA)

        imageCapture = ImageCapture.Builder().build()
        binding.sendButton?.isVisible = false
        binding.retakeButton?.isVisible = false

        binding.scanButton?.setOnClickListener {
            previewMask()
        }

        binding.uploadImage?.setOnClickListener{
            openFileExplorer()
        }

        binding.retakeButton?.setOnClickListener{
            previewMask()
        }

        binding.sendButton?.setOnClickListener{
            uploadImage()
        }

        val appDatabase = AppDatabase.getDatabase(requireContext())
        val repository = ScanRepository(appDatabase)
        val viewModel = ScanViewModel(repository)

        return root
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({

            cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                    mPreview ->
                if (!previewFrozen) {
                    binding.scanView?.foreground = null
                    mPreview.setSurfaceProvider(binding.scanView?.surfaceProvider)
                } else {
                    mPreview.setSurfaceProvider(null)
                }
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(this, cameraSelector, preview!!)
            } catch (e: Exception) {
                Log.d("CameraX", "startCamera Failed:", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun previewMask() {
        previewFrozen = !previewFrozen
        if (!previewFrozen) {
            binding.scanButton?.isVisible= true
            binding.uploadImage?.isVisible = true
            binding.sendButton?.isVisible  = false
            binding.retakeButton?.isVisible = false
        } else {
            binding.scanButton?.isVisible= false
            binding.uploadImage?.isVisible = false
            binding.sendButton?.isVisible  = true
            binding.retakeButton?.isVisible = true
        }
        startCamera()
    }

    private fun openFileExplorer() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        filePickerLauncher.launch(intent)
    }

    private fun loadSelectedImage(uri: Uri) {
        try {
            requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                if (bitmap != null) {
                    val drawable = BitmapDrawable(resources, bitmap)
                    binding.scanView?.foreground = drawable
                    previewMask()
                } else {
                    Toast.makeText(requireContext(), "Failed to decode image", Toast.LENGTH_SHORT).show()
                    Log.e("ScanFragment", "Failed to decode image")
                }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show()
            Log.e("ScanFragment", "Failed to load image", e)
        }
    }

    private fun uploadImage() {
        var bitmap = (binding.scanView?.foreground as? BitmapDrawable)?.bitmap
        if (bitmap == null) {
            bitmap = (binding.scanView?.surfaceProvider as? BitmapDrawable)?.bitmap
        }
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())

        val token = requireActivity().getSharedPreferences(LoginActivity.SHARED_PREFS, Context.MODE_PRIVATE)
            .getString(LoginActivity.TOKEN, "")

        if (token.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            return
        }

        val imagePart = MultipartBody.Part.createFormData("file", "image.jpg", requestBody)

        val call = ApiConfig.api.uploadImage("Bearer $token", imagePart)

        call.enqueue(object : Callback<UploadRes> {
            override fun onResponse(call: Call<UploadRes>, response: Response<UploadRes>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val responseString = responseBody.toString()
                        Log.d("ResponseString", "Response: $responseString")
                        Toast.makeText(requireContext(), "Image uploaded successfully! Response: $responseString", Toast.LENGTH_LONG).show()
                        try {
                            val scanData = parseScanData(responseString)
                            for (item in scanData?.items?.items ?: emptyList()) {
                                val transactionEntity = TransactionEntity(
                                    name = item.name,
                                    price = item.price.toInt() * item.qty,
                                    category = 0,
                                    longitude = 0.0,
                                    latitude = 0.0,
                                )
                                viewModel.addTransaction(transactionEntity)
                            }
                        } catch (e: Exception) {
                            Log.e("UploadError", "Error parsing response JSON", e)
                            Toast.makeText(requireContext(), "Error parsing response JSON", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show()
                    }
                    previewMask()
                } else {
                    Toast.makeText(requireContext(), "Failed to upload image. Error code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UploadRes>, t: Throwable) {
                Log.e("UploadError", "Failed to upload image", t)
                Toast.makeText(requireContext(), "Network error occurred. Please try again later.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun parseScanData(responseString: String): UploadRes? {
        return try {
            val jsonObject = JSONObject(responseString)
            val gson = Gson()
            gson.fromJson(jsonObject.toString(), UploadRes::class.java)
        } catch (e: Exception) {
            null
        }
    }

}