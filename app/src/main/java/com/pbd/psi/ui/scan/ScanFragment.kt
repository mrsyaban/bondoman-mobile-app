package com.pbd.psi.ui.scan

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.fragment.app.Fragment
import com.pbd.psi.databinding.FragmentScanBinding

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private var imageCapture: ImageCapture? = null

    private var previewFrozen: Boolean = false
    private var cameraProvider: ProcessCameraProvider? = null

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

        binding.scanButton?.setOnClickListener {
            previewMask()
        }

        binding.uploadImage?.setOnClickListener{
            openFileExplorer()
        }

        return root
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({

            cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                    mPreview ->
                if (!previewFrozen) {
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
        binding.scanButton?.isEnabled = false
        previewFrozen = !previewFrozen
        startCamera()

        Handler(Looper.getMainLooper()).postDelayed({
            // TO DO: Add  modal
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder
                .setMessage("Do you want to retake the picture?")
                .setPositiveButton("Yes") { dialog, which ->
                    dialog.dismiss()
                    binding.scanButton?.isEnabled = true
                    previewFrozen = !previewFrozen
                    startCamera()
                }
                .setNegativeButton("No") { dialog, which ->
                    // TO DO : send a froze preview to the server as img form data
                    dialog.dismiss()
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }, 1000)
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
                    binding.scanView?.background = drawable
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}