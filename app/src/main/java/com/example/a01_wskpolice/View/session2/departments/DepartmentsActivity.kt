package com.example.a01_wskpolice.View.session2.departments

import com.example.a01_wskpolice.View.recyclerview.session2.DepartmentsAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
import com.example.a01_wskpolice.databinding.DepartmentsActivityBinding
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DepartmentsActivity : AppCompatActivity() {

    private lateinit var adapter: DepartmentsAdapter
    private lateinit var binding: DepartmentsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DepartmentsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = DepartmentsAdapter(this)
        binding.departRV.layoutManager = LinearLayoutManager(this)
        binding.departRV.adapter = adapter

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mad2019.hakta.pro/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val departmentApi = retrofit.create(ApiService::class.java)

        lifecycleScope.launch {
            val list = departmentApi.getAllDepartments()
            runOnUiThread{
                binding.apply {
                    adapter.submitList(list.data)
                }
            }
        }
    }
}