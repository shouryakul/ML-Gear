package com.android.mlgear.image

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.mlgear.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_object_detection.*

class ObjectDetectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_detection)

        click_photo.setOnClickListener {
            cameraView.captureImage { cameraKitImage -> setImageData(cameraKitImage.bitmap) }
        }
    }

    private fun setImageData(bitmap: Bitmap) {

        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector =
            FirebaseVision.getInstance().visionLabelDetector

        detector.detectInImage(image)
            .addOnSuccessListener { labels ->
                label.text = labels[0].label
                confidence.text = (labels[0].confidence*100).toString()+"%"
                info.visibility = View.VISIBLE
            }
    }

    override fun onResume() {
        super.onResume()
        cameraView!!.start()
    }

    override fun onPause() {
        cameraView!!.stop()
        super.onPause()
    }

}