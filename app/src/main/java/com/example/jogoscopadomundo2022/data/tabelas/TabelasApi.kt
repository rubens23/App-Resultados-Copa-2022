package com.example.jogoscopadomundo2022.data.tabelas


import com.example.jogoscopadomundo2022.data.apitabelas.Tabelas
import retrofit2.Call
import retrofit2.http.GET

interface TabelasApi {

    @GET(END_POINT)
    fun getTabelas(): Call<List<Tabelas>>


    companion object{
        const val END_POINT = "tabelascopa.json"
        const val BASE_URL = "https://rubens23.github.io/api-tabelas-copa/"

    }
}