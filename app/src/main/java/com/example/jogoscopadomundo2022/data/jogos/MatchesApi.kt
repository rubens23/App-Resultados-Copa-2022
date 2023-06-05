package com.example.jogoscopadomundo2022.data.jogos

import com.example.jogoscopadomundo2022.data.apijogos.Partida
import retrofit2.Call
import retrofit2.http.GET

interface MatchesApi {

    @GET(END_POINT)
    fun getMatches(): Call<List<Partida>>

    companion object{
        const val END_POINT = "jogoscopa.json"
        const val BASE_URL = "https://rubens23.github.io/api-jogos-copa/"
    }
}