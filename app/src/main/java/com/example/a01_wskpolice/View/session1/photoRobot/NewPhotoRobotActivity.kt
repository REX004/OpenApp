package com.example.a01_wskpolice.View.session1.photoRobot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a01_wskpolice.View.recyclerview.session1.ImageAdapter
import com.example.a01_wskpolice.databinding.NewPhotoRobotActivityBinding

class NewPhotoRobotActivity : AppCompatActivity() {
    private lateinit var binding: NewPhotoRobotActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewPhotoRobotActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backIMG.setOnClickListener {
            startActivity(Intent(this, PhotoRobotsActivity::class.java))
        }
        setupRecyclerViews()
    }

    private fun setupRecyclerViews() {
        val folderName = "photorobot"

        binding.recyclerView1.apply {
            layoutManager = LinearLayoutManager(this@NewPhotoRobotActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ImageAdapter(this@NewPhotoRobotActivity, folderName)
        }

        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(this@NewPhotoRobotActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ImageAdapter(this@NewPhotoRobotActivity, folderName)
        }

        binding.recyclerView3.apply {
            layoutManager = LinearLayoutManager(this@NewPhotoRobotActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ImageAdapter(this@NewPhotoRobotActivity, folderName)
        }
    }
}
