package com.example.a01_wskpolice.View.session2.wanted

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a01_wskpolice.Model.Data.session2.wanted.Wanted
import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
import com.example.a01_wskpolice.R
import com.example.a01_wskpolice.View.recyclerview.session2.WantedAdapter
import com.example.a01_wskpolice.View.session1.main.MainSignActivity
import com.example.a01_wskpolice.databinding.WantedActivityBinding
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WantedActivity: AppCompatActivity() {
    private lateinit var binding: WantedActivityBinding
    private lateinit var adapter: WantedAdapter
    private var isCheckboxVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WantedActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loadingBar.visibility = View.VISIBLE
        var isTextVisible = false


        adapter = WantedAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        if (!isNetworkAvailable()) {
            showNoInternetDialog()
            return
        }
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mad2019.hakta.pro/").client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val wantedApi = retrofit.create(ApiService::class.java)

        lifecycleScope.launch {
            val list = wantedApi.getAllWanted()
            runOnUiThread {
                binding.apply {
                    adapter.submitList(list.data)
                    binding.loadingBar.visibility = View.GONE
                }
            }
        }
        binding.backIMG.setOnClickListener {
            startActivity(Intent(this, MainSignActivity::class.java))
        }
        binding.binIMG.setOnClickListener {
            isCheckboxVisible = !isCheckboxVisible
            adapter.showCheckBoxes(isCheckboxVisible)
            val checkBox = findViewById<CheckBox>(R.id.choseCB)
            if (isTextVisible){
                binding.view.visibility = View.GONE
                checkBox.visibility = View.GONE
                isTextVisible = false
            }else {
                binding.view.visibility = View.VISIBLE

                checkBox.visibility = View.VISIBLE
                isTextVisible = true
            }
        }
        binding.deleteBT.setOnClickListener {
            val selectedItems = adapter.getSelectedItems()
            adapter.removeItems(selectedItems)
            isCheckboxVisible = !isCheckboxVisible
            adapter.showCheckBoxes(isCheckboxVisible)
            val checkBox = findViewById<CheckBox>(R.id.choseCB)
            if (isTextVisible){
                binding.view.visibility = View.VISIBLE
                checkBox.visibility = View.VISIBLE
                isTextVisible = false
            }else {
                binding.view.visibility = View.GONE

                checkBox.visibility = View.GONE
                isTextVisible = true
            }
            binding.view.visibility = View.GONE
            adapter.notifyDataSetChanged()
        }
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
    private fun showNoInternetDialog(){

        AlertDialog.Builder(this)
            .setMessage("No internet")
            .setPositiveButton("Go Back") { dialog, _ ->
                onBackPressed()
            }
            .setNegativeButton("Exit app") { dialog, _ ->
                finishAffinity()
            }
            .setNeutralButton("Try again") { _, _ ->
                fun isInternetAvailable(): Boolean {
                    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val networkInfo = connectivityManager.activeNetworkInfo
                    return networkInfo != null && networkInfo.isConnected
                }
                if (!isInternetAvailable()) {
                    showNoInternetDialog()
                }
            }
            .setCancelable(false)
            .show()
    }
}