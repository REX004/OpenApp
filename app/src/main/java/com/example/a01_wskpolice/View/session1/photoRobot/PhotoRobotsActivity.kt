package com.example.a01_wskpolice.View.session1.photoRobot

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.databinding.PhotoRobotsActivityBinding
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class PhotoRobotsActivity : AppCompatActivity() {
private lateinit var binding: PhotoRobotsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PhotoRobotsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.newIMG.setOnClickListener {
            startActivity(Intent(this, NewPhotoRobotActivity::class.java))
        }
    }
}