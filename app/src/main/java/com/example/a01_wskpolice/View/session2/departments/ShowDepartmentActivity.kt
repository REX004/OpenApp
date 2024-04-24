package com.example.a01_wskpolice.View.session2.departments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.databinding.ShowDepartmentActivityBinding

class ShowDepartmentActivity: AppCompatActivity() {
    private lateinit var binding: ShowDepartmentActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShowDepartmentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var isTextVisible = true

        val departmentId = intent.getStringExtra("departmentId")
        val departmentAddress = intent.getStringExtra("departmentAddress")
        val departmentBoss = intent.getStringExtra("departmentBoss")
        val departmentName = intent.getStringExtra("departmentName")
        val departmentPhone = intent.getStringExtra("departmentPhone")
        val departmentEmail = intent.getStringExtra("departmentEmail")
        val departmentDescription = intent.getStringExtra("departmentDescription")
        val departmentCoords1 = intent.getDoubleExtra("departmentLatitude", 0.0)
        val departmentCoords2 = intent.getDoubleExtra("departmentLongitude", 0.0)



        binding.apply {
            namedepTV.text = departmentName
            addressdepTV.text = departmentAddress
            bossdepTV.text = departmentBoss
            phonedepTV.text = departmentPhone
            emaildepTV.text = departmentEmail
            descriptiondevTV.text = departmentDescription
        }

        binding.swap.setOnClickListener {
            if (isTextVisible){
                binding.descriptiondevTV.visibility = View.GONE
                isTextVisible = false
            }else {
                binding.descriptiondevTV.visibility = View.VISIBLE
                isTextVisible = true
            }
        }

        binding.showBT.setOnClickListener {
            val intent = Intent(this, ShowDepartmentActivity::class.java)
            intent.putExtra("departmentLatitude", departmentCoords1)
            intent.putExtra("departmentLongitude", departmentCoords2)

            startActivity(Intent(this@ShowDepartmentActivity, ShowDepMap::class.java))
            }


    }

}