package com.android.mlgear.face

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.mlgear.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import kotlinx.android.synthetic.main.layout_face_detection.*

class FaceDetectionActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_face_detection)

        click_photoFD.setOnClickListener {
            cameraViewFD.captureImage {image ->
                setFaceData(image.bitmap)
            }

        }
    }

    private fun setFaceData(bitmap: Bitmap) {

        val faceDetectorOptions = FirebaseVisionFaceDetectorOptions.Builder()
            .setLandmarkType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
            .setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
            .setModeType(FirebaseVisionFaceDetectorOptions.FAST_MODE)
            .build()

        val image: FirebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().getVisionFaceDetector(faceDetectorOptions)

        detector.detectInImage(image)
            .addOnSuccessListener {faces ->
                if(faces.size > 0) {
                    smileFD.text = (faces[0].smilingProbability*100).toString()+"%"
                    righteyeFD.text = (faces[0].rightEyeOpenProbability*100).toString()+"%"
                    lefteyeFD.text = (faces[0].leftEyeOpenProbability*100).toString()+"%"
                    infoFD.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
    }

    override fun onResume() {
        super.onResume()
        cameraViewFD!!.start()
    }

    override fun onPause() {
        cameraViewFD!!.stop()
        super.onPause()
    }
}