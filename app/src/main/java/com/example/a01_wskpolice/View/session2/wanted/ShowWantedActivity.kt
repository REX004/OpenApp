package com.example.a01_wskpolice.View.session2.wanted

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.View.recyclerview.session2.WantedAdapter
import com.example.a01_wskpolice.View.session2.departments.DepartmentsActivity
import com.example.a01_wskpolice.databinding.ShowWantedBinding

class ShowWantedActivity: AppCompatActivity() {

    private lateinit var adapter: WantedAdapter
    private lateinit var binding: ShowWantedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShowWantedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backIMG.setOnClickListener {
            startActivity(Intent(this, WantedActivity::class.java))
        }
        adapter = WantedAdapter(this)

        val departmentId = intent.getStringExtra("first_name")
        val departmentAddress = intent.getStringExtra("last_name")
        val departmentBoss = intent.getStringExtra("description")
        val departmentName = intent.getStringExtra("last_location")
        val departmentPhone = intent.getStringExtra("status")
        val departmentEmail = intent.getStringExtra("nicknames")

        binding.apply {
            lastLocation.text = departmentName
            lastName.text = departmentAddress
            description.text = departmentBoss
            status.text = departmentPhone
            nicknames.text = departmentEmail
        }

    }
}