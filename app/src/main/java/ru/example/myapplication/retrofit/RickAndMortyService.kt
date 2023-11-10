package ru.example.myapplication.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.example.myapplication.model.GetCharacterByIdResponse

interface RickAndMortyService {

    @GET("character/{id}")
    fun getCharacterById(@Path("id") characterId: Int): Call<GetCharacterByIdResponse>
}