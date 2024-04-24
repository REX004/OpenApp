package com.example.a01_wskpolice.View.Session3.criminalCase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.Model.Data.session3.criminalCase.NewCriminalCase
import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
import com.example.a01_wskpolice.databinding.NewCriminalCasesActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewCriminalCasesActivity: AppCompatActivity() {
    private lateinit var binding: NewCriminalCasesActivityBinding
    private lateinit var apiService: ApiService
    private val token = "1f1dfe69-69c0-bf2b-d505-53e550f36fe1"
    private val userId = "a2f6ecb0-88e1-b4f2-09ea-ec1cfd678d54"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewCriminalCasesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


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

        var textHide = true

        binding.hideIMG.setOnClickListener {
            if (textHide){
                binding.descriptionET.visibility = View.GONE
                binding.descriptionTitle.visibility = View.GONE
                binding.descriptionView.visibility = View.GONE
                textHide = false
            } else{
                binding.descriptionET.visibility = View.VISIBLE
                binding.descriptionTitle.visibility = View.VISIBLE
                binding.descriptionView.visibility = View.VISIBLE
                textHide = true
            }
        }
        binding.descriptionET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val textLength = s?.length ?: 0
                binding.counter.text = "$textLength / 255"
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

            binding.sumbitButton.setOnClickListener {
                binding.progressHorizontal.visibility = View.VISIBLE
                val requestBody = NewCriminalCase(
                    number = binding.numberET.text.toString(),
                    create_date = binding.dateET.text.toString(),
                    detective = binding.detectiveET.text.toString(),
                    client = binding.clientET.text.toString(),
                    category = binding.categpryET.text.toString(),
                    description = binding.descriptionET.text.toString())

                CoroutineScope(Dispatchers.IO).launch {
                    try {

                        val response = apiService.postData(requestBody)
                        Toast.makeText(this@NewCriminalCasesActivity, "Data saved", Toast.LENGTH_SHORT).show()

                    } catch (e: Exception) {
                        Log.e("NewCriminalCaseActivity", "Error sending data: ${e.message}", e)
                    }
                }
                if (!isInternetAvailable()) {
                showNoInternetDialog()
                    binding.progressHorizontal.visibility = View.GONE

                }else{
                startActivity(Intent(this, CriminalCasesActivity::class.java))
                }


            }


    }
    private fun isInternetAvailable(): Boolean{
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeInternetInfo = connectivityManager.activeNetworkInfo
        return activeInternetInfo != null && activeInternetInfo.isAvailable
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