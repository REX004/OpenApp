package com.example.a01_wskpolice.View.session2.wanted

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.databinding.AddNewWantedActivityBinding

class AddNewWanted : AppCompatActivity() {
    private lateinit var binding: AddNewWantedActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddNewWantedActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}