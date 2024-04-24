package com.example.a01_wskpolice.View.session1.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.View.session1.AboutUsActivity
import com.example.a01_wskpolice.View.session1.paint.PaintActivity
import com.example.a01_wskpolice.View.session1.photoRobot.PhotoRobotsActivity
import com.example.a01_wskpolice.View.session2.departments.DepartmentsActivity
import com.example.a01_wskpolice.View.session2.wanted.WantedActivity
import com.example.a01_wskpolice.databinding.MainActivityBinding

class MainGuestActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.aboutus.setOnClickListener {
            startActivity(Intent(this@MainGuestActivity, AboutUsActivity::class.java))
        }
        binding.departments.setOnClickListener {
            startActivity(Intent(this, DepartmentsActivity::class.java))
        }
        binding.paint.setOnClickListener {
            startActivity(Intent(this, PaintActivity::class.java))
        }
        binding.photorobot.setOnClickListener {
            startActivity(Intent(this, PhotoRobotsActivity::class.java))
        }
        binding.wanted.setOnClickListener {
            startActivity(Intent(this, WantedActivity::class.java))
        }
        binding.backIMG.setOnClickListener {
            startActivity(Intent(this, SignActivity::class.java))
        }
    }
}