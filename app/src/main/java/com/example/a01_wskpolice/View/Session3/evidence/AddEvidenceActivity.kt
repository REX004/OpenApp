package com.example.a01_wskpolice.View.Session3.evidence

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.Model.Data.session3.evidence.NewEvidence
import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
import com.example.a01_wskpolice.databinding.AddEvidenceActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddEvidenceActivity: AppCompatActivity() {
    private lateinit var binding: AddEvidenceActivityBinding
    private lateinit var apiService: ApiService
    private val token = "1f1dfe69-69c0-bf2b-d505-53e550f36fe1"
    private val userId = "a2f6ecb0-88e1-b4f2-09ea-ec1cfd678d54"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddEvidenceActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor{ chain ->
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


        binding.sumbitButton.setOnClickListener {
            val requestBody = NewEvidence(
                name = binding.evidenceName.text.toString(),
                criminal_case_id = "",
                description = binding.evidenceDescription.text.toString()
            )

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.postEvidence(requestBody)
                }catch (e: Exception){
                    Log.e("NewCriminalCaseActivity", "Error sending data: ${e.message}", e)
                }
            }
            startActivity(Intent(this, EvidenceActivity::class.java))
        }
    }
    private fun showError(message: String, activity: Activity) {
        AlertDialog.Builder(activity)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("Exit") { dialog, _ ->
                dialog.dismiss()
                activity.finish()
            }
            .show()
    }
}