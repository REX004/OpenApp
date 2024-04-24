package com.example.a01_wskpolice.View.session1.main

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.databinding.SignActivityBinding
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class SignActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    private lateinit var binding: SignActivityBinding
    private var failedAttempts = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInBT.setOnClickListener {
            binding.apply {
                signInBT.visibility = View.GONE
                guestBT.visibility = View.GONE
                captchaImage.visibility = View.VISIBLE
                view.visibility = View.VISIBLE
                okBT.visibility = View.VISIBLE
                textView.visibility = View.VISIBLE
                captcha.visibility = View.VISIBLE
                capchaET.visibility = View.VISIBLE
            }

        }
        var captcha = generateCaptcha()

        val randomRotate = (-50..50).random().toFloat()

        binding.captcha.apply {
            text = captcha
            rotation = randomRotate
            scaleX = (1..2).random().toFloat()
            scaleY = (1..2).random().toFloat()
        }

        binding.okBT.setOnClickListener {
            val enteredCaptcha = binding.capchaET.text.toString()
            if (enteredCaptcha == captcha) { // Проверяем введенную капчу с текущей капчей
                val login = binding.loginET.text.toString()
                val password = binding.passwordET.text.toString()
                if (isInternetAvailable()) {
                    authorizeUser(login, password)
                } else {
                    showDialog("No internet connection")
                }
            } else {
                val randomRotater = (-50..50).random().toFloat()
                showDialog("Invalid captcha or no login found")
                // Генерируем новую капчу и обновляем отображение
                captcha = generateCaptcha()
                binding.captcha.apply {
                    text = captcha
                    rotation = randomRotater
                    scaleX = (1..2).random().toFloat()
                    scaleY = (1..2).random().toFloat()
                }
            }
        }

        binding.guestBT.setOnClickListener {
            startActivity(Intent(this@SignActivity, MainGuestActivity::class.java))
            Toast.makeText(this@SignActivity, "You authorizated by gost", Toast.LENGTH_SHORT).show()
        }
    }
    private fun showDialog(message: String) {
        AlertDialog.Builder(this@SignActivity)
            .setMessage(message)
            .setPositiveButton("Try again") { dialog, _ ->

                binding.capchaET.text.clear()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
    private fun generateCaptcha(): String {
        val charset = "qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        return (1..3)
            .map { charset.random() }
            .joinToString("")
    }

    private fun authorizeUser(login: String, password: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://mad2019.hakta.pro/api/login/?login=$login&password=$password")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Failed to make request: ${e.message}")
                Toast.makeText(this@SignActivity, "Failed to make request", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                response.body?.close()

                responseData?.let {
                    runCatching {
                        val jsonObject = JSONObject(it)
                        val success = jsonObject.getBoolean("success")
                        val message = if (success) "Authorization successful" else "Authorization failed"
                        val token = jsonObject.getJSONObject("data").getString("token")
                        Log.d(TAG, "Authorization: $message. Token: $token")
                        runOnUiThread {
                            Toast.makeText(this@SignActivity, message, Toast.LENGTH_SHORT).show()
                        }
                        if (success) {
                            startActivity(Intent(this@SignActivity, MainSignActivity::class.java))
                            finish()
                        }
                    }.onFailure {
                        Log.e(TAG, "Failed to parse response: ${it.message}")
                        runOnUiThread {
                            Toast.makeText(this@SignActivity, "Failed to parse response ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
