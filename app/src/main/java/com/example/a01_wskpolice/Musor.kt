package com.example.a01_wskpolice

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a01_wskpolice.Model.Data.session2.department.Department
import com.example.a01_wskpolice.Model.Data.session3.criminalCase.NewCriminalCase
import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
import com.example.a01_wskpolice.View.Session3.criminalCase.CriminalCasesActivity
import com.example.a01_wskpolice.View.recyclerview.session2.DepartmentsAdapter
import com.example.a01_wskpolice.View.recyclerview.session3.CriminalCasesAdapter
import com.example.a01_wskpolice.View.session1.main.MainGuestActivity
import com.example.a01_wskpolice.View.session1.main.MainSignActivity
import com.example.a01_wskpolice.View.session2.departments.ShowDepartmentActivity
import com.example.a01_wskpolice.databinding.DepartmentsActivityBinding
import com.example.a01_wskpolice.databinding.DepartmentsItemBinding
import com.example.a01_wskpolice.databinding.NewCriminalCasesActivityBinding
import com.example.a01_wskpolice.databinding.SignActivityBinding
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException
import kotlin.math.log

class Musor(val context: Context) : ListAdapter<Department, Musor.Holder>(Comparator()){

    class Comparator : DiffUtil.ItemCallback<Department>(){
        override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
            return oldItem == newItem
        }

    }
    inner class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = DepartmentsItemBinding.bind(view)

        fun bind(department: Department) = with(binding){
            name.text = department.name
            address.text = department.address

            card.setOnClickListener {
                val intent = Intent(context, ShowDepartmentActivity::class.java).apply {
                    putExtra("", department.id)
                }
                context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.departments_item, parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}

//class Musor(val context: Context) : ListAdapter<Department, Musor.Holder>(Comparator()){
//    class Comparator : DiffUtil.ItemCallback<Department>(){
//        override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//    inner class Holder(view: View) : RecyclerView.ViewHolder(view){
//        private val binding = DepartmentsItemBinding.bind(view)
//
//        fun bind(department: Department) = with(binding){
//
//            name.text = department.name
//            address.text = department.address
//
//            card.setOnClickListener {
//                val intent = Intent(context, ShowDepartmentActivity::class.java).apply {
//                    putExtra("departmentId", department.id)
//                    putExtra("departmentAddress", department.address)
//                    putExtra("departmentBoss", department.boss)
//                    putExtra("departmentName", department.name)
//                    putExtra("departmentPhone", department.phone)
//                    putExtra("departmentEmail", department.email)
//                    putExtra("departmentDescription", department.description)
//                }
//                context.startActivity(intent)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//         val view = LayoutInflater.from(parent.context)
//             .inflate(R.layout.departments_item, parent, false)
//        return Holder(view)
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.bind(getItem(position))
//    }
//}
//class Musor : AppCompatActivity(){
//    private lateinit var binding: NewCriminalCasesActivityBinding
//    private lateinit var apiService: ApiService
//    private val token = ""
//    private val user_id = ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = NewCriminalCasesActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .addInterceptor{ chain ->
//                val request : Request = chain.request().newBuilder()
//                    .addHeader("user_id", user_id)
//                    .addHeader("token", token)
//                    .build()
//                chain.proceed(request)
//            }
//            .build()
//        val retrofit = Retrofit.Builder()
//            .baseUrl("")
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        apiService = retrofit.create(ApiService::class.java)
//
//        var textHide = true
//
//        binding.hideIMG.setOnClickListener {
//            if (textHide){
//                binding.descriptionET.visibility = View.GONE
//                binding.descriptionView.visibility = View.GONE
//                binding.descriptionTitle.visibility = View.GONE
//                textHide = false
//            }else{
//                binding.descriptionET.visibility = View.VISIBLE
//                binding.descriptionView.visibility = View.VISIBLE
//                binding.descriptionTitle.visibility = View.VISIBLE
//                textHide = true
//            }
//        }
//
//        binding.descriptionET.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val textLength = s?.length ?: 0
//                binding.counter.text = "$textLength / 255"
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })
//
//        binding.sumbitButton.setOnClickListener {
//            binding.progressHorizontal.visibility = View.VISIBLE
//            val requestBody = NewCriminalCase(
//                number = binding.numberET.text.toString(),
//                create_date = binding.dateET.text.toString(),
//                detective = binding.detectiveET.text.toString(),
//                client = binding.clientET.text.toString(),
//                category = binding.categpryET.text.toString(),
//                description = binding.descriptionET.text.toString())
//            lifecycleScope.launch {
//                try {
//                    val response = apiService.postData(requestBody)
//                }catch (e: Exception){
//                    Log.e("NewCriminalCaseActivity", "Error sending data: ${e.message}", e)
//                }
//            }
//            startActivity(Intent(this, CriminalCasesActivity::class.java))
//            binding.progressHorizontal.visibility = View.GONE
//        }
//    }
//}

//class Musor : AppCompatActivity(){
//    private lateinit var binding: DepartmentsActivityBinding
//    private lateinit var adapter: DepartmentsAdapter
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState,)
//        binding = DepartmentsActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        adapter = DepartmentsAdapter(this)
//        binding.departRV.adapter = adapter
//        binding.departRV.layoutManager = LinearLayoutManager(this)
//
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("")
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val departmentApi = retrofit.create(ApiService::class.java)
//
//        lifecycleScope.launch {
//            val list = departmentApi.getAllDepartments()
//            runOnUiThread {
//                binding.apply {
//                    adapter.submitList(list.data)
//                }
//            }
//        }
//
//    }
//}
//class Musor : AppCompatActivity(){
//    private lateinit var binding: SignActivityBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = SignActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.signInBT.setOnClickListener {
//            binding.apply {
//                signInBT.visibility = View.GONE
//                guestBT.visibility = View.GONE
//
//                captchaImage.visibility = View.VISIBLE
//                capchaET.visibility = View.VISIBLE
//                captcha.visibility = View.VISIBLE
//                textView.visibility = View.VISIBLE
//                okBT.visibility = View.VISIBLE
//                view.visibility = View.VISIBLE
//            }
//        }
//        var captcha = generateCaptcha()
//
//        val randomRotation = (-50..50).random().toFloat()
//
//        binding.captcha.apply {
//            text = captcha
//            rotation = randomRotation
//            scaleX = (1..3).random().toFloat()
//            scaleY = (1..3).random().toFloat()
//        }
//
//        binding.guestBT.setOnClickListener {
//            startActivity(Intent(this@Musor, MainGuestActivity::class.java))
//            Toast.makeText(this@Musor, "You authorizated bu guest", Toast.LENGTH_SHORT).show()
//        }
//        binding.okBT.setOnClickListener {
//            val enteredCaptcha = binding.capchaET.text.toString()
//            if (enteredCaptcha == captcha) {
//                val login = binding.loginET.text.toString()
//                val password = binding.passwordET.text.toString()
//                if (isInternetAvailable()){
//                    authorizeUser(login, password)
//                } else {
//                    showDialog("No internet connection")
//                }
//            } else {
//                val randomRotater = (-50..50).random().toFloat()
//                showDialog("Invalid captcha")
//
//                captcha = generateCaptcha()
//                binding.captcha.apply {
//                    text = captcha
//                    rotation = randomRotater
//                    scaleX = (1..2).random().toFloat()
//                    scaleY = (1..2).random().toFloat()
//                }
//            }
//        }
//    }
//    private fun authorizeUser(login: String, password: String){
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url("")
//            .build()
//
//        client.newCall(request).enqueue(object: Callback{
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("SignActivity", "Failed to make request: ${e.message}")
//                Toast.makeText(this@Musor, "Failed to make request: ", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body?.string()
//                response.body?.close()
//
//                responseData?.let {
//                    runCatching {
//                        val jsonObject = JSONObject(it)
//                        val success = jsonObject.getBoolean("success")
//                        val message = if (success) "Authorization successful" else "Authorization failed"
//                        val token = jsonObject.getJSONObject("data").getString("token")
//                        Log.d("SignActivity", "Failed to make request: $message")
//                        runOnUiThread {
//                            Toast.makeText(this@Musor, message, Toast.LENGTH_SHORT).show()
//                        }
//                        if (success) {
//                            startActivity(Intent(this@Musor, MainSignActivity::class.java))
//                            finish()
//                        }
//
//                    }.onFailure {
//                        Log.e("Sign Activity", "Failed to parse response: ${it.message}")
//                        runOnUiThread {
//                            Toast.makeText(this@Musor, "Failed to parse response", Toast.LENGTH_SHORT).show()
//                        }
//
//                    }
//                }
//            }
//
//        })
//    }
//    private fun showDialog(message: String){
//        AlertDialog.Builder(this)
//            .setMessage(message)
//            .setPositiveButton("Try again") { dialog, _ ->
//                binding.capchaET.text.clear()
//                dialog.dismiss()
//            }
//            .setCancelable(false)
//            .show()
//    }
//    private fun isInternetAvailable(): Boolean{
//        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetworkInfo = connectivityManager.activeNetworkInfo
//        return activeNetworkInfo != null && activeNetworkInfo.isAvailable
//    }
//    private fun generateCaptcha(): String{
//        val charest = "QWERTYUIOPASDFGHJKZXCVBNM1234567890"
//        return (1..3)
//            .map { charest.random() }
//            .joinToString("")
//    }
//}




//import android.content.Intent
//import android.net.ConnectivityManager
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import com.example.a01_wskpolice.View.session1.main.MainSignActivity
//import com.example.a01_wskpolice.databinding.SignActivityBinding
//import okhttp3.Call
//import okhttp3.Callback
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.Response
//import org.json.JSONObject
//import java.io.IOException
//
//class Musor : AppCompatActivity(){
//    private lateinit var binding: SignActivityBinding
//    private val TAG = "SignActivity"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = SignActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.okBT.setOnClickListener {
//            val login = binding.loginET.text.toString()
//            val password = binding.passwordET.text.toString()
//            if (isInternetAvailable()){
//                authorizeUser(login, password)
//            }else {
//                showDialog("Internet error")
//            }
//        }
//    }
//
//    private fun showDialog(message: String) {
//        AlertDialog.Builder(this)
//            .setMessage(message)
//            .setPositiveButton("Try again") { dialog, _ ->
//
//                binding.capchaET.text.clear()
//                dialog.dismiss()
//            }
//            .setCancelable(false)
//            .show()
//    }
//    private fun authorizeUser(login : String, password : String){
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url("http://mad2019.hakta.pro/api/login/?login=$login&password=$password")
//            .build()
//
//        client.newCall(request).enqueue(object: Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e(TAG, "Failed to make request: ${e.message}")
//                Toast.makeText(this@Musor, "Failed to make request", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body?.string()
//                response.body?.close()
//
//            responseData?.let {
//                runCatching {
//                    val jsonObject = JSONObject()
//                    val success = jsonObject.getBoolean("success")
//                    val message = if (success) "Authorization success" else "Authorization failed"
//                    val token = jsonObject.getJSONObject("data").getString("token")
//                    Log.e(TAG, "Authorization: $message. Token: 4$token")
//                    runOnUiThread {
//                        Toast.makeText(this@Musor, message, Toast.LENGTH_SHORT).show()
//                    }
//                    if (success) {
//                        startActivity(Intent(this@Musor, MainSignActivity::class.java))
//                        finish()
//                    }
//                }.onFailure {
//                    Log.e(TAG, "Failed to parse response: ${it.message}")
//                    runOnUiThread {
//                        Toast.makeText(this@Musor, "Failed to parse response: ${it.message}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//            }
//
//        })
//    }
//    private fun isInternetAvailable(): Boolean{
//        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetworkInfo = connectivityManager.activeNetworkInfo
//        return activeNetworkInfo != null && activeNetworkInfo.isAvailable
//    }
//}
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import com.example.a01_wskpolice.View.session1.main.MainSignActivity
//import com.example.a01_wskpolice.databinding.SignActivityBinding
//import okhttp3.Call
//import okhttp3.Callback
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.Response
//import org.json.JSONObject
//import java.io.IOException
//
//class Musor : AppCompatActivity(){
//    private lateinit var binding: SignActivityBinding
//    private var textVisibility = true
//    private val TAG = "Sign Activity"
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = SignActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.signInBT.setOnClickListener {
//            binding.apply {
//                signInBT.visibility = View.GONE
//                guestBT.visibility = View.GONE
//
//                captcha.visibility = View.VISIBLE
//                captchaImage.visibility = View.VISIBLE
//                capchaET.visibility = View.VISIBLE
//                textView.visibility = View.VISIBLE
//                view.visibility = View.VISIBLE
//            }
//        }
//        var captcha = generateCaptcha()
//        var randomRotation = (-50..50).random().toFloat()
//        binding.captcha.apply {
//            text = captcha
//            rotation = randomRotation
//            scaleY = (1..2).random().toFloat()
//            scaleX = (1..2).random().toFloat()
//        }
//        binding.okBT.setOnClickListener {
//            val enteredCaptcha = binding.capchaET.text.toString()
//            if (enteredCaptcha == captcha){
//                val login = binding.loginET.text.toString()
//                val password = binding.passwordET.text.toString()
//                authorizeUser(login, password)
//            } else {
//                var randomRotationer = (-50..50).random().toFloat()
//                showDialog("Invalid captcha")
//                captcha = generateCaptcha()
//                binding.captcha.apply {
//                    text = captcha
//                    rotation = randomRotationer
//                    scaleY = (1..2).random().toFloat()
//                    scaleX = (1..2).random().toFloat()
//                }
//            }
//        }
//    }
//    private fun generateCaptcha() : String{
//        val charest = "QWERTYUIOPSDFGHJKLZXCVBNM1234567890"
//        return (1..3)
//            .map { charest.random() }
//            .joinToString("")
//
//    }
//
//    private fun showDialog(message: String){
//        AlertDialog.Builder(this)
//            .setMessage(message)
//            .setPositiveButton("Try again") { dialog, _ ->
//                binding.capchaET.text.clear()
//                dialog.dismiss()
//            }
//            .setCancelable(false)
//            .show()
//    }
//
//    private fun authorizeUser(login: String, password: String){
//
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url("")
//            .build()
//
//        client.newCall(request).enqueue(object : Callback{
//
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e(TAG, "Failed to make requst: ${e.message}")
//                Toast.makeText(this@Musor, "Failed to make request", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body?.string()
//                response.body?.close()
//
//                responseData?.let {
//                    runCatching {
//                        val jsonObject = JSONObject()
//                        val success = jsonObject.getBoolean("success")
//                        val message = if (success) "Authorization successful" else "Authorization failed"
//                        val token = jsonObject.getJSONObject("data").getString("token")
//                        Log.d(TAG, "Authorization: $message. Token: $token")
//                        runOnUiThread {
//                            Toast.makeText(this@Musor, message, Toast.LENGTH_SHORT).show()
//                        }
//                        if (success){
//                            startActivity(Intent(this@Musor, MainSignActivity::class.java))
//                            finish()
//                        }
//                    }.onFailure {
//                        Log.e(TAG, "Failed to parse response: ${it.message}")
//                        runOnUiThread {
//                            Toast.makeText(this@Musor, "Failed to parse response", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//        })
//    }
//}


//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import com.example.a01_wskpolice.Model.Data.session3.evidence.NewEvidence
//import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
//import com.example.a01_wskpolice.View.Session3.evidence.AddEvidenceActivity
//import com.example.a01_wskpolice.View.Session3.evidence.EvidenceActivity
//import com.example.a01_wskpolice.databinding.AddEvidenceActivityBinding
//import kotlinx.coroutines.launch
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.create
//
//class Musor : AppCompatActivity(){
//    private lateinit var binding: AddEvidenceActivityBinding
//    private lateinit var apiService: ApiService
//    private val token = ""
//    private val userId = ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = AddEvidenceActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .addInterceptor { chain ->
//                val request : Request = chain.request().newBuilder()
//                    .addHeader("user_id", userId)
//                    .addHeader("token", token)
//                    .build()
//                chain.proceed(request)
//            }
//            .build()
//        val retrofit = Retrofit.Builder()
//            .baseUrl("")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//
//        apiService = retrofit.create(ApiService::class.java)
//
//        binding.sumbitButton.setOnClickListener {
//            val requstBody = NewEvidence(
//                name = binding.evidenceName.text.toString(),
//                criminal_case_id = "",
//                description = binding.evidenceDescription.text.toString()
//            )
//
//            lifecycleScope.launch {
//                try {
//                    val response = apiService.postEvidence(requstBody)
//                }catch (e: Exception){
//                    Log.e("NewCriminalCaseActivity", "Error sending data: ${e.message}", e)
//                }
//            }
//            startActivity(Intent(this, EvidenceActivity::class.java))
//        }
//    }
//}
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
//import com.example.a01_wskpolice.View.Session3.evidence.AddEvidenceActivity
//import com.example.a01_wskpolice.View.recyclerview.session3.EvidenceAdapter
//import com.example.a01_wskpolice.databinding.EvidenceActivityBinding
//import kotlinx.coroutines.launch
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.create
//
//class Musor : AppCompatActivity() {
//    private lateinit var binding: EvidenceActivityBinding
//    private lateinit var adapter: EvidenceAdapter
//    private val token = "1f1dfe69-69c0-bf2b-d505-53e550f36fe1"
//    private lateinit var apiService: ApiService
//
//    private val userId = "a2f6ecb0-88e1-b4f2-09ea-ec1cfd678d54"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = EvidenceActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        adapter = EvidenceAdapter(this)
//        binding.evidenceRV.layoutManager = LinearLayoutManager(this)
//        binding.evidenceRV.adapter = adapter
//
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .addInterceptor{ chain ->
//                val requst: Request = chain.request().newBuilder()
//                    .addHeader("user_id", userId)
//                    .addHeader("token", token)
//                    .build()
//                chain.proceed(requst)
//            }
//            .build()
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://mad2019.hakta.pro/")
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        apiService = retrofit.create(ApiService::class.java)
//
//        binding.newIMG.setOnClickListener {
//            startActivity(Intent(this, AddEvidenceActivity::class.java))
//        }
//
//        lifecycleScope.launch {
//
//            val list = apiService.getAllEvidence()
//            runOnUiThread {
//                try {
//                    binding.apply {
//                        adapter.submitList(list.data)
//                    }
//                }catch (e: Exception){
//                    Log.e("EvidenceActivity", "Error")
//                }
//            }
//        }
//    }
//}
//class Musor: View() {
//
//}
//    private val TAG = "LoginActivity"
//    private lateinit var binding: SignActivityBinding
//    private var failedAttempts = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = SignActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.signInBT.setOnClickListener {
//            binding.apply {
//                signInBT.visibility = View.GONE
//                guestBT.visibility = View.GONE
//                captchaImage.visibility = View.VISIBLE
//                view.visibility = View.VISIBLE
//                okBT.visibility = View.VISIBLE
//                textView.visibility = View.VISIBLE
//                captcha.visibility = View.VISIBLE
//                capchaET.visibility = View.VISIBLE
//            }
//        }
//        var captcha = generateCaptcha()
//
//        val randomRotate = (-50..50).random().toFloat()
//
//        binding.captcha.apply {
//            text = captcha
//            scaleY = (1..3).random().toFloat()
//            scaleX =  (1..3).random().toFloat()
//            rotation = randomRotate
//        }
//
//        binding.okBT.setOnClickListener {
//            val enteredCaptcha = binding.capchaET.text.toString()
//            if (enteredCaptcha == captcha){
//                val login = binding.loginET.text.toString()
//                val password = binding.passwordET.text.toString()
//                authorizeUser(login, password)
//            }else{
//                val randomRotater = (-50..50).random().toFloat()
//                showDialog("Invalid captcha")
//                captcha = generateCaptcha()
//                binding.captcha.apply {
//                    text = captcha
//                    scaleY = (1..3).random().toFloat()
//                    scaleX =  (1..3).random().toFloat()
//                    rotation = randomRotater
//                }
//            }
//        }
//        binding.guestBT.setOnClickListener {
//            startActivity(Intent(this, MainGuestActivity::class.java))
//            Toast.makeText(this@Musor, "Tou have authorizated bu guest", Toast.LENGTH_SHORT).show()
//        }
//    }
//    private fun showDialog(message: String){
//        AlertDialog.Builder(this)
//            .setMessage(message)
//            .setPositiveButton("Try again") { dialog, _ ->
//
//                binding.capchaET.text.clear()
//                dialog.dismiss()
//            }
//            .setCancelable(false)
//            .show()
//    }
//    private fun generateCaptcha() : String{
//        val charest = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890"
//            return (1..3)
//                .map { charest.random() }
//                .joinToString("")
//    }
//    private fun authorizeUser(login: String, password : String){
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url("")
//            .build()
//
//        client.newCall(request).enqueue(object : Callback{
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e(TAG, "Faoled to make request: ${e.message}")
//                Toast.makeText(this@Musor, "Failed to make request", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body?.string()
//                response.body?.close()
//
//                responseData?.let {
//                    runCatching {
//                        val jsonObject = JSONObject()
//                        val success = jsonObject.getBoolean("success")
//                        val message = if (success) "Authorization successful" else "Authorization failed"
//                        val token = jsonObject.getJSONObject("data").getString("token")
//                        Log.e(TAG, "Authorization: $message. Token: $token")
//                        runOnUiThread {
//                            Toast.makeText(this@Musor, message, Toast.LENGTH_SHORT).show()
//                        }
//                        if (success){
//                            startActivity(Intent(this@Musor, MainSignActivity::class.java))
//                            finish()
//                        }
//                    }.onFailure {
//                        Log.e(TAG, "Failed to parse response: ${it.message}")
//                        runOnUiThread {
//                            Toast.makeText(this@Musor, "Failed to parse response ${it.message}", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//
//        })
//    }
//}
//    private lateinit var binding: NewCriminalCasesActivityBinding
//    private lateinit var apiService: ApiService
//    private val token = ""
//    private val userId = ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = NewCriminalCasesActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .addInterceptor{chain ->
//                val request : Request = chain.request().newBuilder()
//                    .addHeader("user_id", userId)
//                    .addHeader("token", token)
//                    .build()
//                chain.proceed(request)
//            }
//            .build()
//        val retrofit = Retrofit.Builder()
//            .baseUrl("").client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        apiService = retrofit.create(ApiService::class.java)
//
//        var textHide = true
//
//        binding.hideIMG.setOnClickListener {
//            if(textHide){
//                binding.descriptionET.visibility = View.GONE
//                binding.descriptionTitle.visibility = View.GONE
//                binding.descriptionView.visibility = View.GONE
//                textHide = false
//            }else {
//                binding.descriptionET.visibility = View.GONE
//                binding.descriptionTitle.visibility = View.GONE
//                binding.descriptionView.visibility = View.GONE
//                textHide = true
//            }
//        }
//        binding.descriptionET.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val textLength = s?.length ?: 0
//                binding.counter.text = "$textLength / 255"
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//        })
//        binding.sumbitButton.setOnClickListener {
//            binding.progressHorizontal.visibility = View.VISIBLE
//            val requestBody = NewCriminalCase(
//                number = binding.numberET.text.toString(),
//                create_date = binding.dateET.text.toString(),
//                detective = binding.detectiveET.text.toString(),
//                client = binding.clientET.text.toString(),
//                category = binding.categpryET.text.toString(),
//                description = binding.descriptionET.text.toString())
//
//            lifecycleScope.launch {
//                try {
//                    val response = apiService.postData(requestBody)
//                }catch (e: Exception){
//                    Log.e("Activity", "Something get wrong: ${e.message}")
//                }
//            }
//            startActivity(Intent(this, CriminalCasesActivity::class.java))
//            binding.progressHorizontal.visibility = View.GONE
//        }
//
//    }
//}
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = NewCriminalCasesActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .addInterceptor {chain ->
//                val request: Request = chain.request().newBuilder()
//                    .addHeader("user_id", userId)
//                    .addHeader("token", token)
//                    .build()
//                chain.proceed(request)
//            }
//            .build()
//        val retrofit = Retrofit.Builder()
//            .baseUrl("").client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        apiService = retrofit.create(ApiService::class.java)
//
//        var textHide = true
//
//        binding.hideIMG.setOnClickListener {
//            if (textHide){
//                binding.descriptionView.visibility = View.GONE
//                binding.descriptionTitle.visibility = View.GONE
//                binding.descriptionET.visibility = View.GONE
//                textHide = false
//            }else{
//                binding.descriptionET.visibility = View.VISIBLE
//                binding.descriptionTitle.visibility = View.VISIBLE
//                binding.descriptionView.visibility = View.VISIBLE
//                textHide = true
//            }
//        }
//        binding.descriptionET.addTextChangedListener(object: TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val textLength = s?.length ?: 0
//                binding.counter.text = "$textLength / 255"
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//        })
//
//        binding.sumbitButton.setOnClickListener {
//            binding.progressHorizontal.visibility = View.VISIBLE
//            val requestBody = NewCriminalCase(
//                number = binding.numberET.text.toString(),
//                create_date = binding.dateET.text.toString(),
//                detective = binding.detectiveET.text.toString(),
//                client = binding.clientET.text.toString(),
//                category = binding.categpryET.text.toString(),
//                description = binding.descriptionET.text.toString())
//
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    val response = apiService.postData(requestBody)
//                }catch (e: Exception) {
//                    Log.e("NewCriminalCaseActivity", "Error sending data: ${e.message}", e)
//                }
//            }
//            startActivity(Intent(this, CriminalCasesActivity::class.java))
//            binding.progressHorizontal.visibility = View.GONE
//        }
//    }
//}



//package com.example.a01_wskpolice
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
//import com.example.a01_wskpolice.View.recyclerview.session2.DepartmentsAdapter
//import com.example.a01_wskpolice.databinding.DepartmentsActivityBinding
//import kotlinx.coroutines.launch
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class com.example.a01_wskpolice.Musor : AppCompatActivity(){
//
//    private lateinit var binding: DepartmentsActivityBinding
//    private lateinit var adapter : DepartmentsAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DepartmentsActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        adapter = DepartmentsAdapter(this)
//        binding.departRV.adapter = adapter
//        binding.departRV.layoutManager = LinearLayoutManager(this)
//
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()
//        val retrofit = Retrofit.Builder()
//            .baseUrl("").client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val departmentApi = retrofit.create(ApiService::class.java)
//
//        lifecycleScope.launch {
//            val list = departmentApi.getAllDepartments()
//            runOnUiThread {
//                binding.apply {
//                    adapter.submitList(list.data)
//                }
//            }
//        }
//    }
//}



//package com.example.a01_wskpolice
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import android.widget.CheckBox
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
//import com.example.a01_wskpolice.View.recyclerview.session2.WantedAdapter
//import com.example.a01_wskpolice.View.session1.main.MainSignActivity
//import com.example.a01_wskpolice.databinding.WantedActivityBinding
//import kotlinx.coroutines.launch
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class com.example.a01_wskpolice.com.example.a01_wskpolice.Musor : AppCompatActivity(){
//    private lateinit var binding: WantedActivityBinding
//    private lateinit var adapter: WantedAdapter
//    private var isCheckboxVisiable = false
//
//    override fun onCreate(savedInstanceState: Bundle? ) {
//        super.onCreate(savedInstanceState)
//        binding = WantedActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.loadingBar.visibility = View.VISIBLE
//        var isTextVisible = false
//
//        adapter = WantedAdapter(this)
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter
//
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://mad2019.hakta.pro/")
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val wantedApi = retrofit.create(ApiService::class.java)
//
//        lifecycleScope.launch {
//            val list = wantedApi.getAllWanted()
//            runOnUiThread {
//                binding.apply {
//                    adapter.submitList(list.data)
//                    binding.loadingBar.visibility = View.GONE
//                }
//            }
//        }
//        binding.backIMG.setOnClickListener {
//            startActivity(Intent(this, MainSignActivity::class.java))
//        }
//        binding.binIMG.setOnClickListener {
//            isCheckboxVisiable = !isCheckboxVisiable
//            adapter.showCheckBoxes(isCheckboxVisiable)
//            val checkBox = findViewById<CheckBox>(R.id.choseCB)
//            if (isTextVisible){
//                binding.view.visibility = View.GONE
//                checkBox.visibility = View.GONE
//                isTextVisible = false
//            }else {
//                binding.view.visibility = View.VISIBLE
//                checkBox.visibility = View.VISIBLE
//                isTextVisible = true
//            }
//        }
//        binding.deleteBT.setOnClickListener {
//            val selectedItems = adapter.getSelectedItems()
//            adapter.removeItems(selectedItems)
//            adapter.notifyDataSetChanged()
//        }
//    }
//}


//package com.example.a01_wskpolice
//
//import android.content.Intent
//import android.os.Bundle
//import android.os.Message
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import com.example.a01_wskpolice.View.session1.main.MainGuestActivity
//import com.example.a01_wskpolice.View.session1.main.MainSignActivity
//import com.example.a01_wskpolice.databinding.SignActivityBinding
//import okhttp3.Call
//import okhttp3.Callback
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.Response
//import org.json.JSONObject
//import java.io.IOException
//
//class com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor : AppCompatActivity() {
//    private lateinit var binding: SignActivityBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = SignActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.signInBT.setOnClickListener {
//            binding.apply {
//                signInBT.visibility = View.GONE
//                guestBT.visibility = View.GONE
//
//                capchaET.visibility = View.VISIBLE
//                captcha.visibility = View.VISIBLE
//                captchaImage.visibility = View.VISIBLE
//                imageView.visibility = View.VISIBLE
//                textView.visibility = View.VISIBLE
//                okBT.visibility = View.VISIBLE
//            }
//        }
//        var captcha = generateCaptcha()
//
//
//        val randomRotate = (-50..50).random().toFloat()
//
//        binding.captcha.apply {
//            text = captcha
//            rotation = randomRotate
//            scaleX = (1..2).random().toFloat()
//            scaleY = (1..2).random().toFloat()
//        }
//
//        binding.okBT.setOnClickListener {
//            val entredCaptcha = binding.capchaET.text.toString()
//            if (entredCaptcha == captcha){
//                val login = binding.loginET.text.toString()
//                val password = binding.passwordET.text.toString()
//                authorizeUser(login, password)
//            }else{
//                val randomRotater = (-50..50).random().toFloat()
//                showDialog("Invalid captcha")
//                captcha = generateCaptcha()
//                binding.captcha.apply {
//                    text = captcha
//                    rotation = randomRotate
//                    scaleX = (1..2).random().toFloat()
//                    scaleY = (1..2).random().toFloat()
//                }
//            }
//        }
//
//        binding.guestBT.setOnClickListener {
//            startActivity(Intent(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, MainGuestActivity::class.java))
//            Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, "You authorized how ghost", Toast.LENGTH_SHORT).show()
//        }
//    }
//    private fun showDialog(message: String){
//        AlertDialog.Builder(this)
//            .setMessage(message)
//            .setPositiveButton("Try again"){ dialog, _ ->
//                binding.capchaET.text.clear()
//                dialog.dismiss()
//            }
//            .setCancelable(false)
//            .show()
//    }
//    private fun generateCaptcha(): String {
//        val charset = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890"
//        return (1..3)
//            .map { charset.random() }
//            .joinToString("")
//    }
//    private fun authorizeUser(login: String, password: String){
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url("http://mad2019.hakta.pro/api/login/?login=$login&password=$password")
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("Sign activity", "Failed to make request: ${e.message}")
//                Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, "Failed to make request", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body?.string()
//                response.body?.close()
//                responseData?.let {
//                    runCatching {
//                        val jsonObject = JSONObject(it)
//                        val success = jsonObject.getBoolean("success")
//                        val message =
//                            if (success) "Authorization successful" else "Authorization failed"
//                        val token = jsonObject.getJSONObject("data").getString("token")
//                        Log.d("Sign activity", "Authorization: $message. Token: $token")
//                        runOnUiThread {
//                            Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, message, Toast.LENGTH_SHORT).show()
//                        }
//                        if (success) {
//                            startActivity(Intent(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, MainSignActivity::class.java))
//                            finish()
//                        }
//                    }.onFailure {
//                        Log.e("Sign Activity", "Failed to parse response: ${it.message}")
//                        runOnUiThread {
//                            Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, "Faoled to parse response ${it.message}", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//
//            }
//
//        })
//    }
//
//}




//package com.example.a01_wskpolice
//
//import android.content.Context
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView.Adapter
//import com.example.a01_wskpolice.Model.Data.session2.department.Department
//import com.example.a01_wskpolice.View.session2.departments.ShowDepartmentActivity
//import com.example.a01_wskpolice.databinding.DepartmentsItemBinding
//
//class com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor(private val context: Context): ListAdapter<Department, com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor.Holder>(Comparator()){
//
//    inner class Holder(view: View): RecyclerView.ViewHolder(view){
//        private val binding = DepartmentsItemBinding.bind(view)
//
//        fun bind(department: Department) = with(binding){
//            name.text = department.name
//            address.text = department.address
//
//            card.setOnClickListener {
//                val intent = Intent(context, ShowDepartmentActivity::class.java).apply {
//                    putExtra("departmentId", department.id)
//                    putExtra("departmentAddress", department.address)
//                    putExtra("departmentBoss", department.boss)
//                    putExtra("departmentName", department.name)
//                    putExtra("departmentPhone", department.phone)
//                    putExtra("departmentEmail", department.email)
//                    putExtra("departmentDescription", department.description)
//                }
//                context.startActivity(intent)
//            }
//        }
//    }
//    class Comparator: DiffUtil.ItemCallback<Department>(){
//        override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.departments_item, parent, false)
//        return Holder(view)
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//}





//package com.example.a01_wskpolice
//
//import android.content.Context
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.example.a01_wskpolice.Model.Data.session2.department.Department
//import com.example.a01_wskpolice.View.recyclerview.session2.DepartmentsAdapter
//import com.example.a01_wskpolice.View.session2.departments.ShowDepartmentActivity
//import com.example.a01_wskpolice.databinding.DepartmentsItemBinding
//import com.example.a01_wskpolice.R as R1
//
//class com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor(val context: Context) : ListAdapter<Department, com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor.Holder>(Comparator()) {
//    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
//        private val binding = DepartmentsItemBinding.bind(view)
//
//        fun bind(department: Department) = with(binding) {
//            name.text = department.id
//            address.text = department.address
//
//            card.setOnClickListener {
//                val intent = Intent(context, ShowDepartmentActivity::class.java).apply {
//                    putExtra("departmentId", department.id)
//                    putExtra("departmentAddress", department.address)
//                    putExtra("departmentBoss", department.boss)
//                    putExtra("departmentName", department.name)
//                    putExtra("departmentPhone", department.phone)
//                    putExtra("departmentEmail", department.email)
//                    putExtra("departmentDescription", department.description)
//                }
//                context.startActivity(intent)
//
//            }
//        }
//    }
//    class Comparator : DiffUtil.ItemCallback<Department>(){
//        override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R1.layout.departments_item, parent, false)
//        return Holder(view)
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//
//}




//package com.example.a01_wskpolice
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
//import com.example.a01_wskpolice.View.recyclerview.session2.DepartmentsAdapter
//import com.example.a01_wskpolice.databinding.DepartmentsActivityBinding
//import kotlinx.coroutines.launch
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor: AppCompatActivity(){
//
//    private lateinit var binding: DepartmentsActivityBinding
//    private lateinit var adapter: DepartmentsAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DepartmentsActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        adapter = DepartmentsAdapter(this)
//        binding.departRV.layoutManager = LinearLayoutManager(this)
//        binding.departRV.adapter = adapter
//
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://mad2019.hakta.pro/")
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val departmentApi = retrofit.create(ApiService::class.java)
//
//        lifecycleScope.launch {
//            val list = departmentApi.getAllDepartments()
//            runOnUiThread {
//                binding.apply {
//                    adapter.submitList(list.data)
//                }
//            }
//        }
//    }
//}



//package com.example.a01_wskpolice
//
//import android.content.Context
//import android.content.res.AssetManager
//import android.graphics.BitmapFactory
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.recyclerview.widget.RecyclerView
//import java.io.IOException
//
//class com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor (private val context: Context, private val folderName: String) :
//    RecyclerView.Adapter<com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor.ImageViewHolder>(){
//
//        private val assetManager: AssetManager = context.assets
//    private val images: Array<String> = try {
//        assetManager.list(folderName) ?: arrayOf()
//    } catch (e: IOException){
//        arrayOf()
//    }
//
//    inner class ImageViewHolder(itemView: View){
//        val imageView : ImageView = itemView.findViewById(R.id.photorobotIMG)
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
//        return ImageViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return images.size
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        val imagePath = images[position]
//        try {
//            val inputStream = assetManager.open("$folderName/$imagePath")
//            val bitmap = BitmapFactory.decodeStream(inputStream)
//            holder.imageView.setImageBitmap(bitmap)
//        }catch (e: IOException){
//            e.printStackTrace()
//        }
//    }
//}




//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import com.example.a01_wskpolice.View.session1.main.MainGuestActivity
//import com.example.a01_wskpolice.View.session1.main.MainSignActivity
//import com.example.a01_wskpolice.databinding.SignActivityBinding
//import okhttp3.Call
//import okhttp3.Callback
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.Response
//import org.json.JSONObject
//import java.io.IOException
//
//class com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor : AppCompatActivity(){
//
//    private lateinit var binding: SignActivityBinding
//    private val TAG = "Activity Sign"
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = SignActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.signInBT.setOnClickListener {
//            binding.apply {
//                signInBT.visibility = View.GONE
//                guestBT.visibility = View.GONE
//
//                capchaET.visibility = View.VISIBLE
//                captcha.visibility = View.VISIBLE
//                captchaImage.visibility = View.VISIBLE
//                textView.visibility = View.VISIBLE
//                okBT.visibility = View.VISIBLE
//                view.visibility = View.VISIBLE
//            }
//        }
//
//        val captcha = generateCaptcha()
//        val randomCaptcha = (-50..50).random().toFloat()
//
//        binding.captcha.apply {
//            text = captcha
//            scaleY = (1..2).random().toFloat()
//            scaleX = (1..2).random().toFloat()
//            rotation = randomCaptcha
//        }
//        binding.okBT.setOnClickListener {
//            val enterdCaptcha = binding.capchaET.text.toString()
//            if (enterdCaptcha == captcha){
//                val login = binding.loginET.text.toString()
//                val password = binding.passwordET.text.toString()
//                authorizeUser(login, password)
//            }else{
//                val randomRotate = (-50..50).random().toFloat()
//                showDialog("Invalid captcha or no user found")
//                binding.captcha.apply {
//                    text = captcha
//                    scaleY = (1..2).random().toFloat()
//                    scaleX = (1..2).random().toFloat()
//                    rotation = randomCaptcha
//                }
//            }
//        }
//        binding.guestBT.setOnClickListener {
//            startActivity(Intent(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, MainGuestActivity::class.java))
//            Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, "You have authorizated by gost", Toast.LENGTH_SHORT).show()
//        }
//    }
//    private fun showDialog(message : String){
//        AlertDialog.Builder(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor)
//            .setMessage(message)
//            .setPositiveButton("Try again"){ dialog, _ ->
//                val newCaptcha = generateCaptcha()
//                binding.captcha.text = newCaptcha
//                binding.capchaET.text.clear()
//                dialog.dismiss()
//            }
//            .setCancelable(false)
//            .show()
//    }
//    private fun generateCaptcha(): String {
//        val charset = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890"
//        return (1..3)
//            .map { charset.random() }
//            .joinToString("")
//    }
//    private fun authorizeUser(login: String, password: String){
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url("http://mad2019.hakta.pro/api/login/?login=$login&password=$password")
//            .build()
//
//        client.newCall(request).enqueue(object : Callback{
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e(TAG, "Failed to make requst: ${e.message}")
//                Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, "Failed to make requst", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body?.string()
//                response.body?.close()
//
//                responseData.let {
//                    runCatching {
//                        val jsonObject = JSONObject(it)
//                        val success = jsonObject.getBoolean("success")
//                        val message = if (success) "Authorization successful" else "Authorization failed"
//                        val token = jsonObject.getJSONObject("data").getString("token")
//                        Log.d(TAG, "Authorization : $message. Token: $token")
//                        runOnUiThread {
//                            Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, message, Toast.LENGTH_SHORT).show()
//                        }
//                        if (success){
//                            startActivity(Intent(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, MainSignActivity::class.java))
//                            finish()
//                        }
//
//                    }.onFailure {
//                        Log.e(TAG, "Failed to parse response: ${it.message}")
//                        runOnUiThread {
//                            Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, "Failed to response: ${it.message}", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//
//        })
//    }
//}
//



//import android.content.Intent
//import android.os.Bundle
//import android.os.Message
//import android.os.PersistableBundle
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import com.example.a01_wskpolice.View.session1.main.MainSignActivity
//import com.example.a01_wskpolice.databinding.SignActivityBinding
//import okhttp3.Call
//import okhttp3.Callback
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.Response
//import org.json.JSONObject
//import java.io.IOException
//
//class com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor : AppCompatActivity(){
//
//    private val TAG = "LoginActivity"
//
//    private lateinit var binding : SignActivityBinding
//
//    private var failedAttempts = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = SignActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding. signInBT.setOnClickListener {
//            binding.signInBT.visibility = View.GONE
//            binding.guestBT.visibility = View.GONE
//
//            binding.captchaImage.visibility = View.VISIBLE
//            binding.view.visibility = View.VISIBLE
//            binding.okBT.visibility = View.VISIBLE
//            binding.textView.visibility = View.VISIBLE
//            binding.captcha.visibility = View.VISIBLE
//            binding.capchaET.visibility = View.VISIBLE
//        }
//        val captcha = generateCaptcha()
//
//        val randomRotate = (-50..50).random().toFloat()
//        binding.captcha.apply {
//            text = captcha
//            rotation = randomRotate
//            scaleX = (1..2).random().toFloat()
//            scaleY = (1..2).random().toFloat()
//        }
//        binding.okBT.setOnClickListener {
//            val enteredCaptcha = binding.capchaET.text.toString()
//            if (enteredCaptcha == captcha){
//                val login = binding.loginET.text.toString()
//                val password = binding.passwordET.text.toString()
//                authorizeUser(login, password)
//            }else{
//                binding.captcha.apply {
//                    text = captcha
//                    rotation = randomRotate
//                    scaleX = (1..2).random().toFloat()
//                    scaleY = (1..2).random().toFloat()
//                }
//                showDialog("Invalid captcha")
//            }
//        }
//    }
//    private fun showDialog(message: String){
//        AlertDialog.Builder(this)
//            .setMessage(message)
//            .setPositiveButton("Try again") {dialog, _ ->
//                val newCaptcha = generateCaptcha()
//                binding.apply {
//                    captcha.text = newCaptcha
//                    capchaET.text.clear()
//                }
//                dialog.dismiss()
//            }
//            .setCancelable(false)
//            .show()
//    }
//    private fun generateCaptcha(): String{
//        val charest = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890"
//        return (1..3)
//            .map { charest.random() }
//            .joinToString("")
//    }
//
//    private fun authorizeUser(login: String, password: String) {
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url("http://mad2019.hakta.pro/api/login/?login=$login&password=$password")
//            .build()
//
//        client.newCall(request).enqueue(object : Callback{
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e(TAG, "Failed to make requst: ${e.message}")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body?.string()
//                response.body?.close()
//
//                responseData?.let {
//                    runCatching {
//                        val jsonObject = JSONObject(it)
//                        val success = jsonObject.getBoolean("success")
//                        val message = if (success) "Authorization successful" else "Authorization failed"
//                        val token = jsonObject.getJSONObject("data").getString("token")
//                        Log.d(TAG, "Authorization: $message. Token: $token")
//                        runOnUiThread {
//                            Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, message, Toast.LENGTH_SHORT).show()
//                        }
//                        if (success){
//                            startActivity(Intent(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, MainSignActivity::class.java))
//                            finish()
//                        }
//                    }.onFailure {
//                        Log.e(TAG, "Failed to parse response: ${it.message}")
//                        runOnUiThread {
//                            Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, "Failed to parse response ${it.message}", Toast.LENGTH_SHORT).show()
//
//                        }
//                    }
//                }
//            }
//        })
//    }
//}
//
//
//
////package com.example.a01_wskpolice
////
////import android.content.Context
////import android.graphics.Canvas
////import android.graphics.Color
////import android.graphics.Paint
////import android.graphics.Path
////import android.util.AttributeSet
////import android.view.MotionEvent
////import android.view.View
////import android.view.ViewGroup
////import com.example.a01_wskpolice.View.session1.paint.PaintActivity.Companion.paintBrush
////import com.example.a01_wskpolice.View.session1.paint.PaintActivity.Companion.path
////
////class com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor: View {
////    var params: ViewGroup.LayoutParams? = null
////    companion object{
////        var pathList = ArrayList<Path>()
////        var currentBrush = Color.BLACK
////        var colorList = ArrayList<Int>()
////    }
////
////    constructor(context: Context) : this(context, null){
////        init()
////    }
////    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
////        init()
////    }
////
////    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
////        init()
////    }
////
////    private fun init(){
////        paintBrush.isAntiAlias = true
////        paintBrush.color = currentBrush
////        paintBrush.style = Paint.Style.STROKE
////        paintBrush.strokeJoin = Paint.Join.ROUND
////        paintBrush.strokeWidth = 8f
////    }
////    override fun onTouchEvent(event: MotionEvent): Boolean {
////        var x = event.x
////        var y = event.y
////        when(event.action){
////            MotionEvent.ACTION_DOWN ->{
////                path.moveTo(x,y)
////                return true
////            }
////            MotionEvent.ACTION_MOVE -> {
////                path.lineTo(x, y)
////                pathList.add(path)
////                colorList.add(currentBrush)
////            }
////            else -> return false
////        }
////        postInvalidate()
////        return false
////    }
////
////    override fun onDraw(canvas: Canvas) {
////        for (i in pathList.indices){
////            paintBrush.setColor(colorList[i])
////            canvas.drawPath(pathList[i], paintBrush)
////            invalidate()
////        }
////    }
////}
//
//
//
//
//
//
//
//
//
////import android.content.Intent
////import android.os.Bundle
////import android.os.PersistableBundle
////import android.util.Log
////import android.view.View
////import android.widget.Toast
////import androidx.appcompat.app.AlertDialog
////import androidx.appcompat.app.AppCompatActivity
////import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
////import com.example.a01_wskpolice.View.session1.main.MainGuestActivity
////import com.example.a01_wskpolice.View.session1.main.MainSignActivity
////import com.example.a01_wskpolice.databinding.SignActivityBinding
////import okhttp3.Call
////import okhttp3.Callback
////import okhttp3.OkHttpClient
////import okhttp3.Request
////import okhttp3.Response
////import org.json.JSONObject
////import retrofit2.Retrofit
////import retrofit2.converter.gson.GsonConverterFactory
////import java.io.IOException
////
////class com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor : AppCompatActivity(){
////    private lateinit var binding: SignActivityBinding
////    private var failedAttempts = 0
////
////    private val TAG = "Login Activity"
////
////    override fun onCreate(savedInstanceState: Bundle? ) {
////        super.onCreate(savedInstanceState )
////        binding = SignActivityBinding.inflate(layoutInflater)
////        setContentView(binding.root)
////
////        binding.signInBT.setOnClickListener {
////            binding.signInBT.visibility = View.GONE
////            binding.guestBT.visibility = View.GONE
////
////            binding.captchaImage.visibility = View.VISIBLE
////            binding.view.visibility = View.VISIBLE
////            binding.okBT.visibility = View.VISIBLE
////            binding.textView.visibility = View.VISIBLE
////            binding.captcha.visibility = View.VISIBLE
////            binding.capchaET.visibility = View.VISIBLE
////        }
////        val captcha = generateCaptcha()
////
////        val randomRotate = (-50..50).random().toFloat()
////        binding.captcha.text = captcha
////        binding.captcha.rotation = randomRotate
////        val randomHorisontalScale = (1..2).random().toFloat()
////        val randomVerticalScale = (1..2).random().toFloat()
////        binding.captcha.scaleX = randomHorisontalScale
////        binding.captcha.scaleY = randomVerticalScale
////
////
////        binding.okBT.setOnClickListener {
////            val enteredCaptcha = binding.capchaET.text.toString()
////            if (enteredCaptcha == captcha){
////                val login = binding.loginET.text.toString()
////                val password = binding.passwordET.text.toString()
////                authorizeUser(login, password)
////            }else{
////                showDialog("Invalid captcha")
////            }
////        }
////
////        binding.guestBT.setOnClickListener {
////            startActivity(Intent(this, MainGuestActivity::class.java))
////        }
////    }
////    private fun showDialog(message: String){
////        AlertDialog.Builder(this)
////            .setMessage(message)
////            .setPositiveButton("Retry"){dialog, _ ->
////                val newCaptcha = generateCaptcha()
////                binding.captcha.text = newCaptcha
////                binding.capchaET.text.clear()
////                dialog.dismiss()
////            }
////            .setCancelable(false)
////            .show()
////    }
////    private fun generateCaptcha(): String{
////        val charest = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890"
////        return (1..3)
////            .map { charest.random() }
////            .joinToString("")
////    }
////
////    private val retrofit = Retrofit.Builder()
////        .baseUrl("")
////        .addConverterFactory(GsonConverterFactory.create())
////        .build()
////
////    private val apiService = retrofit.create(ApiService::class.java)
////    private fun authorizeUser(login: String, password: String){
////        val client = OkHttpClient()
////        val request = Request.Builder()
////            .url("http://mad2019.hakta.pro/api/login/?login=$login&password=$password")
////            .build()
////
////        client.newCall(request).enqueue(object : Callback{
////            override fun onFailure(call: Call, e: IOException) {
////                Log.e(TAG, "Failed to make request ${e.message}")
////            }
////
////            override fun onResponse(call: Call, response: Response) {
////                val responseData = response.body?.string()
////                response.body?.close()
////
////                responseData?.let {
////                    runCatching {
////                        val jsonObject = JSONObject(it)
////                        val success = jsonObject.getBoolean("success")
////                        val message = if (success) "Authorization successful" else "Authoorization Failed"
////                        val token = jsonObject.getJSONObject("data").getString("token")
////                        Log.d(TAG, "Authorization: $message. Token: $token")
////                        runOnUiThread {
////                            Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, message, Toast.LENGTH_SHORT).show()
////                        }
////                        if (success){
////                            startActivity(Intent(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, MainSignActivity::class.java))
////                            finish()
////                        }
////                    }.onFailure {
////                        Log.e(TAG, "Failed to parse response: ${it.message}")
////                        runOnUiThread {
////                            Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, "Failed to parse response ${it.message}", Toast.LENGTH_SHORT).show()
////                        }
////                    }
////                }
////            }
////
////        })
////
////    }
////}
//
//
//
//
//
//
//
//
//
////package com.example.a01_wskpolice
////
////import android.content.Context
////import android.content.Intent
////import android.view.LayoutInflater
////import android.view.View
////import android.view.ViewGroup
////import androidx.recyclerview.widget.DiffUtil
////import androidx.recyclerview.widget.ListAdapter
////import androidx.recyclerview.widget.RecyclerView
////import com.example.a01_wskpolice.Model.Data.session2.wanted.Wanted
////import com.example.a01_wskpolice.View.session2.departments.ShowDepartmentActivity
////import com.example.a01_wskpolice.databinding.WantedItemBinding
//
////class Muss(private val context: Context): ListAdapter<Wanted, Muss.Holder>(Comparator()){
////
////    class Holder(view: View): RecyclerView.ViewHolder(view){
////        private val binding = WantedItemBinding.bind(view)
////        fun bind(wanted: Wanted) = with(binding){
////            firstName.text = wanted.first_name
////            lastName.text = wanted.last_name
////
////            card.setOnClickListener {
////                val intent = Intent()
////            }
////        }
////    }
////    class Comparator: DiffUtil.ItemCallback<Wanted>(){
////        override fun areItemsTheSame(oldItem: Wanted, newItem: Wanted): Boolean {
////            return oldItem.id == newItem.id
////        }
////
////        override fun areContentsTheSame(oldItem: Wanted, newItem: Wanted): Boolean {
////            return oldItem == newItem
////        }
////
////    }
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
////        val view = LayoutInflater.from(parent.context)
////            .inflate(R.layout.wanted_item, parent, false)
////        return Holder(view)
////    }
////
////    override fun onBindViewHolder(holder: Holder, position: Int) {
////        holder.bind(getItem(position))
////    }
////
////
////}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////import android.content.Intent
////import android.os.Bundle
////import android.os.PersistableBundle
////import android.util.Log
////import android.widget.Toast
////import androidx.appcompat.app.AppCompatActivity
////import com.example.a01_wskpolice.View.session1.main.MainSignActivity
////import com.example.a01_wskpolice.databinding.CriminalCasesActivityBinding
////import com.example.a01_wskpolice.databinding.SignActivityBinding
////import okhttp3.Call
////import okhttp3.Callback
////import okhttp3.OkHttpClient
////import okhttp3.Request
////import okhttp3.Response
////import org.json.JSONObject
////import java.io.IOException
//
//
//
//
//
//
//
//
////package com.example.a01_wskpolice
////
////import android.os.Bundle
////import android.os.PersistableBundle
////import android.util.Log
////import android.view.View
////import androidx.appcompat.app.AppCompatActivity
////import androidx.lifecycle.lifecycleScope
////import androidx.recyclerview.widget.LinearLayoutManager
////import com.example.a01_wskpolice.Model.Data.session3.criminalCase.CriminalCase
////import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
////import com.example.a01_wskpolice.View.recyclerview.session3.CriminalCasesAdapter
////import com.example.a01_wskpolice.databinding.CriminalCasesActivityBinding
////import kotlinx.coroutines.launch
////import okhttp3.OkHttpClient
////import okhttp3.Request
////import okhttp3.logging.HttpLoggingInterceptor
////import retrofit2.Retrofit
////import retrofit2.converter.gson.GsonConverterFactory
////import retrofit2.create
////
////class com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor : AppCompatActivity(){
////    private lateinit var binding : CriminalCasesActivityBinding
////    private lateinit var adapter: CriminalCasesAdapter
////    private lateinit var apiService: ApiService
////    private lateinit var originalCriminalCases: List<CriminalCase>
////    private var filteredCriminalCases: MutableList<CriminalCase> = mutableListOf()
////    private val token = "1f1dfe69-69c0-bf2b-d505-53e550f36fe1"
////    private val userId = "a2f6ecb0-88e1-b4f2-09ea-ec1cfd678d54"
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        // Инициализация объекта привязки, связывающего макет активити с ее кодом
////        binding = CriminalCasesActivityBinding.inflate(layoutInflater)
////        setContentView(binding.root)
////
////        // Инициализация адаптера для списка преступных дел
////        adapter = CriminalCasesAdapter(this)
////        // Установка менеджера компоновки для RecyclerView
////        binding.recyclerView.layoutManager = LinearLayoutManager(this)
////        // Установка адаптера для RecyclerView
////        binding.recyclerView.adapter = adapter
////
////        // Установка видимости RecyclerView в значение "не видимо"
////        binding.recyclerView.visibility = View.GONE
////
////        // Флаг для отслеживания категории
////        var category = true
////
////        // Создание перехватчика для логирования HTTP-запросов и ответов
////        val interceptor = HttpLoggingInterceptor()
////        // Установка уровня логирования для перехватчика (в данном случае - логирование тел запросов и ответов)
////        interceptor.level = HttpLoggingInterceptor.Level.BODY
////
////        // Создание клиента для выполнения сетевых запросов
////        val client = OkHttpClient.Builder()
////            // Добавление перехватчика в цепочку перехватчиков клиента
////            .addInterceptor(interceptor)
////            // Добавление перехватчика для добавления заголовков к каждому запросу
////            .addInterceptor { chain ->
////                val request: Request = chain.request().newBuilder()
////                    .addHeader("user_id", userId) // Добавление заголовка с идентификатором пользователя
////                    .addHeader("token", token)     // Добавление заголовка с токеном
////                    .build()
////                chain.proceed(request)
////            }
////            .build()
////
////        // Создание экземпляра Retrofit для выполнения HTTP-запросов
////        val retrofit = Retrofit.Builder()
////            .baseUrl("http://mad2019.hakta.pro/") // Установка базового URL для API
////            .client(client)                       // Установка клиента для сетевых запросов
////            .addConverterFactory(GsonConverterFactory.create()) // Добавление конвертера для работы с JSON
////            .build()
////
////        // Создание объекта API-сервиса на основе интерфейса ApiService
////        apiService = retrofit.create(ApiService::class.java)
////
////        // Выполнение запроса на получение списка преступных дел с сервера
////        fetchCriminalCases()
////
////        // Настройка обработчиков нажатий кнопок выбора категории
////        lifecycleScope.launch {
////            binding.apply {
////                chose1.setOnClickListener {
////                    if (category) {
////                        binding.detectivesCard.visibility = View.VISIBLE
////                        binding.dateCard.visibility = View.VISIBLE
////                        category = false
////                    } else {
////                        binding.detectivesCard.visibility = View.GONE
////                        binding.dateCard.visibility = View.GONE
////                        category = true
////                    }
////                }
////
////                lifecycleScope.launch {
////                    chose2.setOnClickListener {
////                        if (category) {
////                            binding.allCard.visibility = View.VISIBLE
////                            binding.specificallyCard.visibility = View.VISIBLE
////                            category = false
////                        } else {
////                            binding.allCard.visibility = View.GONE
////                            binding.specificallyCard.visibility = View.GONE
////                            category = true
////                        }
////                    }
////
////                    lifecycleScope.launch {
////                        chose3.setOnClickListener {
////                            if (category) {
////                                binding.ascendingCard.visibility = View.VISIBLE
////                                binding.descendingCard.visibility = View.VISIBLE
////                                category = false
////                            } else {
////                                binding.ascendingCard.visibility = View.GONE
////                                binding.descendingCard.visibility = View.GONE
////                                category = true
////                            }
////                        }
////                    }
////                }
////
////                // Обработчик нажатия кнопки "Применить" для фильтрации по дате
////                dateCard.setOnClickListener {
////                    applyDateFilter()
////                }
////
////                // Обработчик нажатия кнопки сортировки по возрастанию
////                ascendingCard.setOnClickListener {
////                    sortListByDateAscending() // Вызов метода сортировки по возрастанию
////                    isAscending = true // Установка флага сортировки по возрастанию
////                    applyDateFilter() // Применение фильтрации
////                }
////
////                // Обработчик нажатия кнопки сортировки по убыванию
////                descendingCard.setOnClickListener {
////                    sortListByDateDescending() // Вызов метода сортировки по убыванию
////                    isAscending = false // Установка флага сортировки по убыванию
////                    applyDateFilter() // Применение фильтрации
////                }
////            }
////        }
////    }
////    private var isAscending = true
////
////    private fun sortListByDateAscending(){
////        isAscending = true
////        filteredCriminalCases.sortBy { it.create_date } //сортируем по дате
////        adapter.submitList(filteredCriminalCases)// Обновляем ресайлер
////    }
////    private fun sortListByDateDescending(){
////        isAscending = false
////        filteredCriminalCases.sortByDescending { it.create_date }// сортируем по убыванию
////        adapter.submitList(filteredCriminalCases)// обновляем ресайлер
////    }
////    private fun  applyDateFilter(){
////        filteredCriminalCases = when{
////            binding.allCard.visibility == View.VISIBLE -> {
////                originalCriminalCases.toMutableList()
////            }
////            binding.specificallyCard.visibility == View.VISIBLE -> {
////                val filtredList = originalCriminalCases
////                filtredList.toMutableList()
////            }
////            else -> {
////                val sortedList = if (isAscending){
////                    filteredCriminalCases.sortedBy { it.create_date }
////                } else{
////                    filteredCriminalCases.sortedByDescending { it.create_date }
////                }
////                sortedList.toMutableList()
////            }
////        }
////        adapter.submitList(filteredCriminalCases)
////    }
////
////    private fun fetchCriminalCases(){
////        lifecycleScope.launch{
////            try {
////                binding.recyclerView.visibility = View.GONE
////                val response = apiService.getAllCriminalCases()
////                originalCriminalCases = response.data
////                filteredCriminalCases.clear()
////                filteredCriminalCases.addAll(originalCriminalCases)
////                adapter.submitList(filteredCriminalCases)
////            }catch (e: Exception){
////                Log.e("CriminalCasesActivity", "Error: ${e.message}", e)
////            }
////        }
////    }
////
////}
//
//
//
//
//
//
//
////import android.content.Intent
////import android.os.Bundle
////import android.os.Message
////import android.os.PersistableBundle
////import android.util.Log
////import android.view.View
////import android.widget.Toast
////import androidx.appcompat.app.AlertDialog
////import androidx.appcompat.app.AppCompatActivity
////import com.example.a01_wskpolice.View.session1.main.MainSignActivity
////import com.example.a01_wskpolice.databinding.SignActivityBinding
////import okhttp3.Call
////import okhttp3.Callback
////import okhttp3.OkHttpClient
////import okhttp3.Request
////import okhttp3.Response
////import org.json.JSONObject
////import java.io.IOException
////
////class com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor : AppCompatActivity(){
////
////    private val TAG = "LoginActivity"
////
////    private lateinit var binding: SignActivityBinding
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////
////        binding = SignActivityBinding.inflate(layoutInflater)
////        setContentView(binding.root)
////
////        binding.signInBT.setOnClickListener {
////            binding.apply {
////                signInBT.visibility = View.GONE
////                guestBT.visibility = View.GONE
////                okBT.visibility = View.VISIBLE
////                textView.visibility = View.VISIBLE
////                capchaET.visibility = View.VISIBLE
////                captcha.visibility = View.VISIBLE
////            }
////        }
////        val captcha = generateCaptcha()
////
////        val randomRotate = (-50..50).random().toFloat()
////
////        binding.captcha.text = captcha
////        binding.captcha.rotation = randomRotate
////
////
////        binding. okBT.setOnClickListener {
////            val enteredCaptcha = binding.capchaET.text.toString()
////            if (enteredCaptcha == captcha){
////                val login = binding.loginET.text.toString()
////                val password = binding.passwordET.text.toString()
////                authorizeUser(login, password)
////            }else{
////                showDialog("Invalid captcha")
////            }
////        }
////
////    }
////    private fun showDialog(message: String){
////        AlertDialog.Builder(this)
////            .setMessage(message)
////            .setPositiveButton("Retry"){
////                dialog, _ ->
////                val newCaptcha = generateCaptcha()
////                binding.captcha.text = newCaptcha
////                binding.capchaET.text.clear()
////                dialog.dismiss()
////            }
////            .setCancelable(false)
////            .show()
////    }
////    private fun generateCaptcha(): String{
////        val charset = "QWERTYUIOPASDFGHJKLZXCVBNM123456789"
////        return (1..3)
////            .map { charset.random() }
////            .joinToString("")
////    }
////
////    private fun authorizeUser(login: String, password: String){
////        val client = OkHttpClient()
////        val request = Request.Builder()
////            .url("http://mad2019.hakta.pro/api/login/?login=$login&password=$password")
////            .build()
////
////        client.newCall(request).enqueue(object : Callback{
////            override fun onFailure(call: Call, e: IOException) {
////                Log.e(TAG, "Failed to make request: ${e.message}")
////            }
////
////            override fun onResponse(call: Call, response: Response) {
////                val responeData = response.body?.string()
////                response.body?.close()
////
////                responeData?.let {
////                    runCatching {
////                        val jsonObject = JSONObject(it)
////                        val success = jsonObject.getBoolean("success")
////                        val message = if (success) "Authorization successful" else "Authorization failed"
////                        val token = jsonObject.getJSONObject("data").getString("token")
////                        Log.d(TAG, "Authorization: $message. Token $token")
////                        runOnUiThread{
////                            Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, message, Toast.LENGTH_SHORT).show()
////                        }
////                        if(success){
////                            startActivity(Intent(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, MainSignActivity::class.java))
////                            finish()
////                        }
////                    }.onFailure {
////                        Log.e(TAG, "Failed to parse responses: ${it.message}")
////                        runOnUiThread{
////                            Toast.makeText(this@com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.com.example.a01_wskpolice.Musor, "Failed to parse response ${it.message}", Toast.LENGTH_SHORT).show()
////                        }
////                    }
////                }
////            }
////
////        })
////    }
////
////}
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
//////package com.example.a01_wskpolice.View.session1
//////
//////import android.content.Intent
//////import android.os.Bundle
//////import android.widget.Toast
//////import androidx.appcompat.app.AlertDialog
//////import androidx.appcompat.app.AppCompatActivity
//////import com.example.a01_wskpolice.databinding.SignCaptchaActivityBinding
//////
//////class SignCaptcaActivity : AppCompatActivity() {
//////
//////    private lateinit var binding: SignCaptchaActivityBinding
//////
//////    override fun onCreate(savedInstanceState: Bundle?) {
//////        super.onCreate(savedInstanceState)
//////        binding = SignCaptchaActivityBinding.inflate(layoutInflater)
//////        setContentView(binding.root)
//////
//////        val captcha = generateCaptcha()
//////        binding.capcha.text = captcha
//////
//////        binding.signInBT.setOnClickListener {
//////            val enteredCaptcha = binding.capchaET.text.toString()
//////            val savedLogin = "savedLogin"
//////            val savedPassword = "savedPassword"
//////
//////            if (validateCaptcha(enteredCaptcha, captcha)) {
//////                if (validateUser(savedLogin, savedPassword)) {
//////                    startActivity(Intent(this@SignCaptcaActivity, MainActivity::class.java))
//////                    finish()
//////                } else {
//////                    showRetryDialog("No user found")
//////                }
//////            } else {
//////                showRetryDialog("Invalid captcha")
//////            }
//////        }
//////    }
//////
//////    private fun generateCaptcha(): String {
//////        return "ABC"
//////    }
//////
//////    private fun validateCaptcha(enteredCaptcha: String, captcha: String): Boolean {
//////        return enteredCaptcha == captcha
//////    }
//////
//////    private fun validateUser(login: String, password: String): Boolean {
//////        return true
//////    }
//////
//////    private fun showRetryDialog(message: String) {
//////        AlertDialog.Builder(this@SignCaptcaActivity)
//////            .setMessage(message)
//////            .setPositiveButton("Retry") { dialog, _ ->
//////                val newCaptcha = generateCaptcha()
//////                binding.capcha.text = newCaptcha
//////                binding.capchaET.text.clear()
//////                dialog.dismiss()
//////            }
//////            .setCancelable(false)
//////            .show()
//////    }
//////}
//////
//////
//////
//////
//////
////
////
////
////
////
////
//////
//////
//////package com.example.a01_wskpolice.View.session1
//////
//////import android.content.Intent
//////import android.os.Bundle
//////import android.widget.Toast
//////import androidx.appcompat.app.AppCompatActivity
//////import com.example.a01_wskpolice.databinding.SignActivityBinding
//////
//////class SignActivity : AppCompatActivity() {
//////
//////    private lateinit var binding: SignActivityBinding
//////
//////    override fun onCreate(savedInstanceState: Bundle?) {
//////        super.onCreate(savedInstanceState)
//////        binding = SignActivityBinding.inflate(layoutInflater)
//////        setContentView(binding.root)
//////
//////        binding.signInBT.setOnClickListener {
//////            val login = binding.loginET.text.toString()
//////            val password = binding.passwordET.text.toString()
//////
//////            saveUserData(login, password)
//////
//////            startActivity(Intent(this@SignActivity, SignCaptcaActivity::class.java))
//////        }
//////
//////        binding.guestBT.setOnClickListener {
//////            startActivity(Intent(this@SignActivity, MainActivity::class.java))
//////        }
//////    }
//////
//////    private fun saveUserData(login: String, password: String) {
//////    }
//////}
////
