package com.example.jogoscopadomundo2022.ui.uijogos.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jogoscopadomundo2022.data.jogos.MatchesApi
import com.example.jogoscopadomundo2022.data.apijogos.Partida
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityDetailViewModel: ViewModel() {

    private var matchesApi: MatchesApi? = null
    var partidas: MutableLiveData<List<Partida>> = MutableLiveData()
    var swipeRefreshLayout: MutableLiveData<Boolean> = MutableLiveData()

    init{
        Log.d("testeviewmodel", "to no view model")
        setupHttpClient()
    }

    private fun setupHttpClient() {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()


        val retrofit = Retrofit.Builder().baseUrl(MatchesApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        matchesApi = retrofit.create(MatchesApi::class.java)

        findMatchesFromApi()
    }

    fun findMatchesFromApi() {
        swipeRefreshLayout.postValue(true)
        matchesApi!!.getMatches().enqueue(object: Callback<List<Partida>> {
            override fun onResponse(call: Call<List<Partida>>, response: Response<List<Partida>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    Log.d("responseissucc", "deu certo")
                    partidas.postValue(response.body())
                    ///agora eu coloco um observer pra saber que ele recebeu dados
                    //mas blz no oncreate ele vai receber dados mas e se o dado
                    //mudar quando ele ja estiver na activity?
                    //ele vai conseguir reconhecer?
                    swipeRefreshLayout.postValue(false)

                    //eu tenho que testar


                } else {
                    Log.d("responseelse", "algo deu errado")
                    swipeRefreshLayout.postValue(false)
                }
                swipeRefreshLayout.postValue(false)

            }

            override fun onFailure(call: Call<List<Partida>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                swipeRefreshLayout.postValue(false)


            }

        })

    }


    //vamo ver se eu consigo fazer a requisicao aqui de dentro
    //eu preciso dos dados só da partida específica
}