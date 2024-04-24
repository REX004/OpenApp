package com.example.a01_wskpolice.View.Session3.evidence

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
import com.example.a01_wskpolice.View.recyclerview.session3.EvidenceAdapter
import com.example.a01_wskpolice.databinding.EvidenceActivityBinding
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EvidenceActivity: AppCompatActivity() {
    private lateinit var binding: EvidenceActivityBinding
    private lateinit var adapter: EvidenceAdapter
    private val token = "1f1dfe69-69c0-bf2b-d505-53e550f36fe1"
    private lateinit var apiService: ApiService

    private val userId = "a2f6ecb0-88e1-b4f2-09ea-ec1cfd678d54"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EvidenceActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = EvidenceAdapter(this)
        binding.evidenceRV.layoutManager = LinearLayoutManager(this)
        binding.evidenceRV.adapter = adapter

        if (!internetIsAvailable()) {
            showDialog("Internet error")
            return
        }
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader("user_id", userId)
                    .addHeader("token", token)
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mad2019.hakta.pro/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        binding.newIMG.setOnClickListener {
            startActivity(Intent(this, AddEvidenceActivity::class.java))
        }

        lifecycleScope.launch {


            val list = apiService.getAllEvidence()
            runOnUiThread {
                try {
                       binding.apply {
                            adapter.submitList(list.data)

                        showDialog("Internet error")
                    }

                } catch (e: Exception) {
                    Log.e("EvidenceActivity", "Error")
                }


            }

        }
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("Try again") { dialog, _ ->
                fun isInternetAvailable(): Boolean {
                    val connectivityManager =
                        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val networkInfo = connectivityManager.activeNetworkInfo
                    return networkInfo != null && networkInfo.isConnected
                }
                if (!isInternetAvailable()) {
                    showDialog("Internet error")
                }
            }
            .setNegativeButton("Go Back") { dialog, _ ->
                onBackPressed()
            }
            .setNeutralButton("Exit app") { dialog, _ ->
                finishAffinity()
            }
            .show()
    }
    private fun internetIsAvailable(): Boolean {
        val connectActivity = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectActivity.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable
    }
}


