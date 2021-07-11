package com.android.mlgear.barcode

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.mlgear.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.layout_barcode_detection.*


class BarcodeDetectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_barcode_detection)

        click_photoBCR.setOnClickListener {
            cameraViewBCR.captureImage { image ->
                setBarcodeData(image.bitmap)
            }
        }

    }

    private fun setBarcodeData(bitmap: Bitmap) {
        val barcodeDetectorOptions = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(
                FirebaseVisionBarcode.FORMAT_ALL_FORMATS
            )
            .build()

        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(barcodeDetectorOptions)
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        detector.detectInImage(image)
            .addOnSuccessListener { barcodes ->
                infoBCR.visibility = View.VISIBLE
                for(b in barcodes){
                    formatBCR.text = b.format.toString()
                    valueBCR.text = b.rawValue.toString()
                }
            }
            .addOnFailureListener {exception ->
                exception.printStackTrace()
                Toast.makeText(baseContext, exception.message, Toast.LENGTH_SHORT)
                    .show()
            }
    }

    override fun onResume() {
        super.onResume()
        cameraViewBCR.start()
    }

    override fun onPause() {
        cameraViewBCR.stop()
        super.onPause()
    }
}