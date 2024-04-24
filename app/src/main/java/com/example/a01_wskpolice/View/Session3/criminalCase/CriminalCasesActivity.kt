package com.example.a01_wskpolice.View.Session3.criminalCase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a01_wskpolice.Model.Data.session3.criminalCase.CriminalCase
import com.example.a01_wskpolice.Model.apis.retrofit.ApiService
import com.example.a01_wskpolice.View.recyclerview.session3.CriminalCasesAdapter
import com.example.a01_wskpolice.View.session1.main.MainSignActivity
import com.example.a01_wskpolice.databinding.CriminalCasesActivityBinding
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CriminalCasesActivity : AppCompatActivity() {

    private lateinit var binding: CriminalCasesActivityBinding
    private lateinit var adapter: CriminalCasesAdapter
    private lateinit var apiService: ApiService
    private lateinit var originalCriminalCases: List<CriminalCase>
    private var filteredCriminalCases: MutableList<CriminalCase> = mutableListOf()
    private val token = "1f1dfe69-69c0-bf2b-d505-53e550f36fe1"
    private val userId = "a2f6ecb0-88e1-b4f2-09ea-ec1cfd678d54"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CriminalCasesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CriminalCasesAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.newIMG.setOnClickListener {
            startActivity(Intent(this@CriminalCasesActivity, NewCriminalCasesActivity::class.java))
        }
        binding.backIMG.setOnClickListener {
            startActivity(Intent(this@CriminalCasesActivity, MainSignActivity::class.java))
        }

        binding.recyclerView.visibility = View.GONE

        var category = true

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

        fetchCriminalCases()


        binding.newIMG.setOnClickListener {
            startActivity(Intent(this, NewCriminalCasesActivity::class.java))
        }
        lifecycleScope.launch {
            binding.apply {
                chose1.setOnClickListener {
                    if (category) {
                        binding.detectivesCard.visibility = View.VISIBLE
                        binding.dateCard.visibility = View.VISIBLE
                        category = false
                    } else {
                        binding.detectivesCard.visibility = View.GONE
                        binding.dateCard.visibility = View.GONE
                        category = true
                    }
                }


            lifecycleScope.launch {
                chose2.setOnClickListener {
                    if (category) {
                        binding.allCard.visibility = View.VISIBLE
                        binding.specificallyCard.visibility = View.VISIBLE
                        category = false
                    } else {
                        binding.allCard.visibility = View.GONE
                        binding.specificallyCard.visibility = View.GONE
                        category = true
                    }
                }

                lifecycleScope.launch {
                    chose3.setOnClickListener {
                        if (category) {
                            binding.ascendingCard.visibility = View.VISIBLE
                            binding.descendingCard.visibility = View.VISIBLE
                            category = false
                        } else {
                            binding.ascendingCard.visibility = View.GONE
                            binding.descendingCard.visibility = View.GONE
                            category = true
                        }
                    }
                }
            }
                dateCard.setOnClickListener {

                applyDateFilter()
            }
                ascendingCard.setOnClickListener {
                    sortListByDateAscending()
                    isAscending = true
                    applyDateFilter()
                }
                descendingCard.setOnClickListener {
                    sortListByDateDescending()
                    isAscending = false
                    applyDateFilter()
                }
            }

        }

    }
    private var isAscending = true // Флаг для отслеживания текущего направления сортировки

    private fun sortListByDateAscending() {
        isAscending = true // Устанавливаем флаг в значение по возрастанию
        filteredCriminalCases.sortBy { it.create_date } // Сортируем список по дате создания дела
        adapter.submitList(filteredCriminalCases) // Обновляем RecyclerView
    }

    private fun sortListByDateDescending() {
        isAscending = false // Устанавливаем флаг в значение по убыванию
        filteredCriminalCases.sortByDescending { it.create_date } // Сортируем список по убыванию даты создания дела
        adapter.submitList(filteredCriminalCases) // Обновляем RecyclerView
    }

    private fun applyDateFilter() {
        filteredCriminalCases = when {
            binding.allCard.visibility == View.VISIBLE -> {
                originalCriminalCases.toMutableList() // Если видна карточка "allCard", копируем все преступные дела в список
            }
            binding.specificallyCard.visibility == View.VISIBLE -> {
                val filteredList = originalCriminalCases // Если видна карточка "specificallyCard", также копируем все преступные дела в список
                filteredList.toMutableList()
            }
            else -> {
                val sortedList = if (isAscending) { // В зависимости от значения флага сортируем список по возрастанию или убыванию даты
                    filteredCriminalCases.sortedBy { it.create_date } // Сортируем список по возрастанию даты создания дела
                } else {
                    filteredCriminalCases.sortedByDescending { it.create_date } // Сортируем список по убыванию даты создания дела
                }
                sortedList.toMutableList()
            }
        }
        adapter.submitList(filteredCriminalCases) // Обновляем RecyclerView после применения фильтра
    }

    private fun fetchCriminalCases() {
        lifecycleScope.launch {
            try {
                binding.recyclerView.visibility = View.VISIBLE
                val response = apiService.getAllCriminalCases() // Получаем список преступных дел из API
                originalCriminalCases = response.data // Обновляем список оригинальных преступных дел
                filteredCriminalCases.clear() // Очищаем список отфильтрованных преступных дел
                filteredCriminalCases.addAll(originalCriminalCases) // Копируем оригинальный список в отфильтрованный
                adapter.submitList(filteredCriminalCases) // Обновляем RecyclerView
            } catch (e: Exception) {
                Log.e("CriminalCasesActivity", "Error: ${e.message}", e)
            }
        }
    }
}
