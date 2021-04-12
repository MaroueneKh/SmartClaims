package com.marouenekhadhraoui.smartclaims.ui.videoSinistre

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.VideoCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.marouenekhadhraoui.smartclaims.Logger
import com.marouenekhadhraoui.smartclaims.R
import com.marouenekhadhraoui.smartclaims.databinding.ActivityCaptureVideoBinding
import com.marouenekhadhraoui.smartclaims.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_camera.viewFinder
import kotlinx.android.synthetic.main.activity_capture_video.*
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject


@AndroidEntryPoint
class CaptureVideoActivity : AppCompatActivity() {

    private var videoCapture: VideoCapture? = null
    private val cameraViewModel: CaptureVideoModel by viewModels()

    private lateinit var binding: ActivityCaptureVideoBinding
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var preview: Preview
    private lateinit var cameraProvider: ProcessCameraProvider
    private var photoTaken: Boolean = false
    private var torchenabled: Boolean = false

    private lateinit var videoFile: File
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

    @SuppressLint("RestrictedApi")
    private fun takeVideo() {


        // Get a stable reference of the modifiable image capture use case


        // Create time-stamped output file to hold the image
        videoFile = File(
                outputDirectory,
                "ddddd".format(System.currentTimeMillis()) + ".mp4")


        videoCapture?.startRecording(videoFile, cameraExecutor, object : VideoCapture.OnVideoSavedCallback {


            override fun onVideoSaved(file: File) {
                val savedUri = Uri.fromFile(videoFile)
                uri = savedUri
            }

            override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {

            }
        })


        // Set up image capture listener, which is triggered after photo has
        // been taken

    }

    fun stopVideo() {
        photoTaken = true
        cameraProvider.unbind(preview)
        cameraExecutor.shutdown()

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
        logger.log("camera started")
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

            videoCapture = VideoCapture.Builder()
                    .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding


                camera = cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, videoCapture)
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, videoCapture)

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_capture_video)
        binding.lifecycleOwner = this
    }

    fun bindViewModel() {
        binding.viewModel = cameraViewModel
    }


    fun setbuttonsOnpressBack() {
        button_ok2.isClickable = false
        button_ok2.visibility = View.GONE
        button_backtopreview2.isClickable = false
        button_backtopreview2.visibility = View.GONE
        button_cancel2.isClickable = true
        button_cancel2.visibility = View.VISIBLE
        button_flash2.isClickable = true
        button_flash2.visibility = View.VISIBLE
    }

    fun setbuttonsOnpressTakepic() {
        button_ok2.isClickable = true
        button_ok2.visibility = View.VISIBLE
        button_cancel2.isClickable = false
        button_cancel2.visibility = View.GONE
        button_backtopreview2.isClickable = true
        button_backtopreview2.visibility = View.VISIBLE
        button_flash2.isClickable = false
        button_flash2.visibility = View.GONE

    }

    private fun setupButtons() {
        stopcamera_capture_button2.isClickable = false
        stopcamera_capture_button2.visibility = View.GONE


        button_ok2.isClickable = false
        button_ok2.visibility = View.GONE
        button_backtopreview2.isClickable = false
        button_backtopreview2.visibility = View.GONE
        cameraViewModel.pressBtnTakePicEvent.observe(
                this,
                androidx.lifecycle.Observer {
                    it?.let {
                        logger.log("hneee")

                        camera_capture_button2.visibility = View.GONE
                        camera_capture_button2.isClickable = false
                        stopcamera_capture_button2.isClickable = true
                        stopcamera_capture_button2.visibility = View.VISIBLE
                        //   takeVideo()
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
        cameraViewModel.pressBtnStopVideoEvent.observe(
                this,
                androidx.lifecycle.Observer {
                    it?.let {

                        stopVideo()


                    }

                })

        cameraViewModel.pressBtnCancelEvent.observe(
                this,
                androidx.lifecycle.Observer {
                    it?.let {
                        logger.log("in cancel")
                        if (photoTaken)
                            videoFile.delete()
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