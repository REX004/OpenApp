package com.example.a01_wskpolice.View.Session3.criminalCase

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.View.Session3.evidence.EvidenceActivity
import com.example.a01_wskpolice.databinding.CriminalCaseShowActivityBinding

class CriminalCaseShowActivity: AppCompatActivity() {
    private lateinit var binding: CriminalCaseShowActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CriminalCaseShowActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.evidenceLL.setOnClickListener {
            startActivity(Intent(this, EvidenceActivity::class.java))
        }
        var textVisable = true
        binding.swap.setOnClickListener {
            if (textVisable){
                binding.textCriminalCase.visibility = View.VISIBLE
                textVisable = false
            }else{
                binding.textCriminalCase.visibility = View.GONE
                textVisable = true
            }

        }

        val criminalCategory = intent.getStringExtra("criminalCategory")
        val criminalDetective = intent.getStringExtra("criminalDetective")
        val criminalClient = intent.getStringExtra("criminalClient")
        val criminalNumber = intent.getStringExtra("criminalNumber")
        val criminalDescription = intent.getStringExtra("criminalDescription")
        val criminalData = intent.getStringExtra("criminalData")

        binding.apply {
            categoryCriminalCase.text = criminalCategory
            detectiveCriminalCase.text = criminalDetective
            clientCriminalCaase.text = criminalClient
            numberCriminalCase.text = criminalNumber
            textCriminalCase.text = criminalDescription
            dateCriminalCase.text = criminalData
        }



    }
}