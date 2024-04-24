package com.example.a01_wskpolice.View.session1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.View.session1.main.MainSignActivity
import com.example.a01_wskpolice.databinding.AboutUsActivityBinding

class AboutUsActivity: AppCompatActivity() {
    private lateinit var binding: AboutUsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AboutUsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backIMG.setOnClickListener {
            startActivity(Intent(this@AboutUsActivity, MainSignActivity::class.java))
            finish()
        }


    }
}