package com.example.a01_wskpolice.View.session1.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.View.Session3.criminalCase.CriminalCasesActivity
import com.example.a01_wskpolice.View.session1.AboutUsActivity
import com.example.a01_wskpolice.View.session1.photoRobot.PhotoRobotsActivity
import com.example.a01_wskpolice.View.session2.departments.DepartmentsActivity
import com.example.a01_wskpolice.View.session2.wanted.WantedActivity
import com.example.a01_wskpolice.databinding.MainSignActivityBinding

class MainSignActivity : AppCompatActivity() {
    private lateinit var binding: MainSignActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainSignActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.aboutus.setOnClickListener {
            startActivity(Intent(this, AboutUsActivity::class.java))
        }
        binding.departments.setOnClickListener {
            startActivity(Intent(this, DepartmentsActivity::class.java))
        }
        binding.photorobot.setOnClickListener {
            startActivity(Intent(this, PhotoRobotsActivity::class.java))
        }
        binding.wanted.setOnClickListener {
            startActivity(Intent(this, WantedActivity::class.java))
        }
        binding.criminalCases.setOnClickListener {
            startActivity(Intent(this, CriminalCasesActivity::class.java))
        }
        binding.logOut.setOnClickListener {
            startActivity(Intent(this, SignActivity::class.java))
            finish()
        }

    }
}