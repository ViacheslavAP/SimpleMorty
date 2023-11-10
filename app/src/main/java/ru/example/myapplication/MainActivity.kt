package ru.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.example.myapplication.databinding.ActivityMainBinding
import ru.example.myapplication.model.GetCharacterByIdResponse
import ru.example.myapplication.retrofit.RickAndMortyService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rickAndMortyService: RickAndMortyService =
            retrofit.create(RickAndMortyService::class.java)

        rickAndMortyService.getCharacterById(10).enqueue(object : Callback<GetCharacterByIdResponse> {

            override fun onResponse(
                call: Call<GetCharacterByIdResponse>,
                response: Response<GetCharacterByIdResponse>
            ) {
                Log.d("MainActivity", response.toString())
                if (!response.isSuccessful){
                    Toast.makeText(
                        this@MainActivity,
                        "Unsuccessful network call!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                val body = response.body()!!
                val name = body.name
                binding.tv.text = name
            }


            override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
                Log.d("MainActivity", t.message ?: "Null message")
            }
        })
    }

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}