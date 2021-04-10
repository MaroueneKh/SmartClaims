package com.marouenekhadhraoui.smartclaims.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.ActivityCameraBinding
import com.marouenekhadhraoui.smartclaims.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject


@AndroidEntryPoint
class CameraActivity : AppCompatActivity() {
    private var imageCapture: ImageCapture? = null
    private val cameraViewModel: CameraViewModel by viewModels()

    private lateinit var binding: ActivityCameraBinding
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var preview: Preview
    private lateinit var cameraProvider: ProcessCameraProvider
    private var photoTaken: Boolean = false
    private var torchenabled: Boolean = false

    private lateinit var photoFile: File
    private lateinit var camera: Camera

    @Inject
    lateinit var logger: Logger
    lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        bindViewModel()
        setupButtons()

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Set up the listener for take photo button


        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

    }

    private fun takePhoto() {
        photoTaken = true
        cameraProvider.unbind(preview)
        cameraExecutor.shutdown()
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        photoFile = File(
                outputDirectory,
                "ddddd".format(System.currentTimeMillis()) + ".jpg")

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
                outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {

                val savedUri = Uri.fromFile(photoFile)
                uri = savedUri


            }
        })
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
                baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults:
            IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                finish()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            cameraProvider = cameraProviderFuture.get()

            // Preview
            preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(viewFinder.createSurfaceProvider())
                    }

            imageCapture = ImageCapture.Builder()
                    .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                camera = cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture)
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture)

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun IntentToFragment() {


        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("ActivityResult", uri)
        setResult(RESULT_OK, intent)
        finish()
    }


    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)
        binding.lifecycleOwner = this
    }

    fun bindViewModel() {
        binding.viewModel = cameraViewModel
    }


    fun setbuttonsOnpressBack() {
        button_ok.isClickable = false
        button_ok.visibility = View.GONE
        button_backtopreview.isClickable = false
        button_backtopreview.visibility = View.GONE
        button_cancel.isClickable = true
        button_cancel.visibility = View.VISIBLE
        button_flash.isClickable = true
        button_flash.visibility = View.VISIBLE
    }

    fun setbuttonsOnpressTakepic() {
        button_ok.isClickable = true
        button_ok.visibility = View.VISIBLE
        button_cancel.isClickable = false
        button_cancel.visibility = View.GONE
        button_backtopreview.isClickable = true
        button_backtopreview.visibility = View.VISIBLE
        button_flash.isClickable = false
        button_flash.visibility = View.GONE

    }

    private fun setupButtons() {


        button_ok.isClickable = false
        button_ok.visibility = View.GONE
        button_backtopreview.isClickable = false
        button_backtopreview.visibility = View.GONE
        cameraViewModel.pressBtnTakePicEvent.observe(
                this,
                androidx.lifecycle.Observer {
                    it?.let {
                        takePhoto()
                        setbuttonsOnpressTakepic()
                    }

                })
        cameraViewModel.pressBtnConfirmEvent.observe(
                this,
                androidx.lifecycle.Observer {
                    it?.let {
                        IntentToFragment()
                    }

                })
        cameraViewModel.pressBtnSetFlashEvent.observe(
                this,
                androidx.lifecycle.Observer {
                    it?.let {

                        when (torchenabled) {
                            true -> {
                                setTorchOff()
                            }
                            false -> {
                                setTorchOn()
                            }
                        }


                    }

                })

        cameraViewModel.pressBtnBackEvent.observe(
                this,
                androidx.lifecycle.Observer {
                    it?.let {
                        startCamera()
                        setbuttonsOnpressBack()
                    }

                })


        cameraViewModel.pressBtnCancelEvent.observe(
                this,
                androidx.lifecycle.Observer {
                    it?.let {
                        logger.log("in cancel")
                        if (photoTaken)
                            photoFile.delete()
                        finish()


                    }

                })
    }

    private fun setTorchOn() {
        torchenabled = true
        camera.cameraControl.enableTorch(true)
    }

    private fun setTorchOff() {
        torchenabled = false
        camera.cameraControl.enableTorch(false)

    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}