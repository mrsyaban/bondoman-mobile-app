package com.pbd.psi.ui.twibbon

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.pbd.psi.R
import com.pbd.psi.databinding.FragmentTwibbonBinding

class TwibbonFragment : Fragment() {

    private var _binding: FragmentTwibbonBinding? = null
    private val binding get() = _binding!!

    private var imageCapture: ImageCapture? = null

    private var previewFrozen: Boolean = false
    private var cameraProvider: ProcessCameraProvider? = null

    private val twibbonDrawable = arrayOf(
        R.drawable.twibbon1,
        R.drawable.twibbon2,
        R.drawable.twibbon3
    )

    private var currentTwibbonIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTwibbonBinding.inflate(inflater, container, false)
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

        binding.cameraButton?.setOnClickListener {
            previewMask()
        }

        binding.changeButton?.setOnClickListener{
            changeTwibbon()
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
                    mPreview.setSurfaceProvider(binding.previewView?.surfaceProvider)
                } else {
                    mPreview.setSurfaceProvider(null)
                }
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

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
            binding.cameraButton?.setBackgroundResource(R.drawable.capture_button)
        } else {
            binding.cameraButton?.setBackgroundResource(R.drawable.retake_button)
        }
        startCamera()
    }

    private fun changeTwibbon(){
        currentTwibbonIndex = (currentTwibbonIndex + 1) % twibbonDrawable.size
        binding.imageView3.setImageResource(twibbonDrawable[currentTwibbonIndex])
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}