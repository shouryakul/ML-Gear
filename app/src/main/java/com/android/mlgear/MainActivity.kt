package com.android.mlgear

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.mlgear.barcode.BarcodeDetectionActivity
import com.android.mlgear.face.FaceDetectionActivity
import com.android.mlgear.image.ObjectDetectionActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnimage.setOnClickListener {
            startActivity(Intent(this, ObjectDetectionActivity::class.java))
        }

        btnface.setOnClickListener {
            startActivity(Intent(this, FaceDetectionActivity::class.java))
        }

        btnbarcode.setOnClickListener {
            startActivity(Intent(this, BarcodeDetectionActivity::class.java))
        }

    }

}