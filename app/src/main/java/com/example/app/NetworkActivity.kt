package com.example.app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkActivity : AppCompatActivity() {
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        _recyclerView = findViewById(R.id.recyclerViewPosts)
        _progressBar = findViewById(R.id.progressBar)
        _recyclerView.layoutManager = LinearLayoutManager(this)

        fetchPosts()
    }

    private fun fetchPosts() {
        // Показываем ProgressBar
        _progressBar.visibility = View.VISIBLE

        // то же самое что было из mainactivity
        RetrofitInstance.api.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                // Скрываем ProgressBar
                _progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val posts = response.body()
                    if (posts != null) {
                        // Устанавливаем адаптер
                        _recyclerView.adapter = PostAdapter(posts)
                    }
                } else {
                    Toast.makeText(this@NetworkActivity, "Ошибка: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // Скрываем ProgressBar
                _progressBar.visibility = View.GONE

                Toast.makeText(this@NetworkActivity, "Ошибка сети: ${t.message}", Toast.LENGTH_LONG).show()
                Log.e("NetworkActivity", "Ошибка сети", t)
            }
        })
    }
}
