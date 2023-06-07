package com.example.jogoscopadomundo2022.data.apijogos.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jogoscopadomundo2022.data.apijogos.Partida
import com.example.jogoscopadomundo2022.data.jogos.MatchesApi
import com.example.jogoscopadomundo2022.ui.uijogos.adapters.PartidasAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date

class JogosRepositoryImpl: JogosRepository {

    private var matchesApi: MatchesApi? = null
    override var listaPartidasFromFindTodayMatches: MutableLiveData<MutableList<Partida>> = MutableLiveData()
    override var listaPartidasPrimeiraRodada: MutableLiveData<MutableList<Partida>> = MutableLiveData()
    override var listaPartidasSegundaRodada: MutableLiveData<MutableList<Partida>> = MutableLiveData()
    override var listaPartidasTerceiraRodada: MutableLiveData<MutableList<Partida>> = MutableLiveData()
    override var listaTodasPartidas: MutableLiveData<List<Partida>> = MutableLiveData()
    override var errorFromFindTodayMatches: MutableLiveData<String> = MutableLiveData()

    init {
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
    }

    override fun findTodayMatchesFromApi(){
        matchesApi!!.getMatches().enqueue(object: Callback<List<Partida>> {
            override fun onResponse(call: Call<List<Partida>>, response: Response<List<Partida>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    val listaPartidas: MutableList<Partida> = mutableListOf()
                    response.body()?.forEach { partida->
                        val diaAtual = getCurrentDateToGetTodayMatches()
                        if (partida.estadio.data_jogo == diaAtual){
                            listaPartidas.add(partida)


                        }

                    }

                    passarListaParaOLiveDataPartidasDeHoje(listaPartidas)





                } else {
                    showErrorMessage()
                }
            }

            override fun onFailure(call: Call<List<Partida>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                errorFromFindTodayMatches.postValue("ocorreu um erro ao carregar os dados, verifique sua conexão com a internet e tente novamente!")


            }

        })

    }

    private fun passarListaParaOLiveDataPartidasDeHoje(listaPartidas: MutableList<Partida>) {
        listaPartidasFromFindTodayMatches.value = listaPartidas

    }

    private fun getCurrentDateToGetTodayMatches(): String {
        val formatarData = SimpleDateFormat("dd/MM/yyyy")
        val data = Date()
        val dataFormatada = formatarData.format(data)

        return dataFormatada

    }

    private fun showErrorMessage() {
        errorFromFindTodayMatches.value = "ocorreu um erro ao carregar os dados, verifique sua conexão com a internet e tente novamente!"
    }

    override fun findPartidasDaPrimeiraRodada(grupo: String){
        matchesApi!!.getMatches().enqueue(object: Callback<List<Partida>> {
            override fun onResponse(call: Call<List<Partida>>, response: Response<List<Partida>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    val listaPartidas: MutableList<Partida> = mutableListOf()
                    response.body()?.forEach { partida->

                        if (partida.estadio.rodada == 1){
                            if (grupo == "Todos os Grupos"){
                                listaPartidas.add(partida)
                            }else{
                                when(partida.estadio.grupo){
                                    "A" -> {if (grupo == "A"){listaPartidas.add(partida)}}
                                    "B" -> {if (grupo == "B"){listaPartidas.add(partida)}}
                                    "C" -> {if (grupo == "C"){listaPartidas.add(partida)}}
                                    "D" -> {if (grupo == "D"){listaPartidas.add(partida)}}
                                    "E" -> {if (grupo == "E"){listaPartidas.add(partida)}}
                                    "F" -> {if (grupo == "F"){listaPartidas.add(partida)}}
                                    "G" -> {if (grupo == "G"){listaPartidas.add(partida)}}
                                    "H" -> {if (grupo == "H"){listaPartidas.add(partida)}}

                                }
                            }

                        }

                    }

                    passarListaParaOLiveDataPartidasPrimeiraRodada(listaPartidas)




                } else {
                    showErrorMessage()
                }
            }

            override fun onFailure(call: Call<List<Partida>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                showErrorMessage()


            }

        })

    }

    override fun findPartidasDaSegundaRodada(grupo: String){
        matchesApi!!.getMatches().enqueue(object: Callback<List<Partida>> {
            override fun onResponse(call: Call<List<Partida>>, response: Response<List<Partida>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    val listaPartidas: MutableList<Partida> = mutableListOf()
                    response.body()?.forEach { partida->
                        if (partida.estadio.rodada == 2){
                            if (grupo == "Todos os Grupos"){
                                listaPartidas.add(partida)
                            }else{
                                when(partida.estadio.grupo){
                                    "A" -> {if (grupo == "A"){listaPartidas.add(partida)}}
                                    "B" -> {if (grupo == "B"){listaPartidas.add(partida)}}
                                    "C" -> {if (grupo == "C"){listaPartidas.add(partida)}}
                                    "D" -> {if (grupo == "D"){listaPartidas.add(partida)}}
                                    "E" -> {if (grupo == "E"){listaPartidas.add(partida)}}
                                    "F" -> {if (grupo == "F"){listaPartidas.add(partida)}}
                                    "G" -> {if (grupo == "G"){listaPartidas.add(partida)}}
                                    "H" -> {if (grupo == "H"){listaPartidas.add(partida)}}

                                }
                            }
                        }

                    }

                    passarListaParaOLiveDataPartidasSegundaRodada(listaPartidas)




                } else {
                    showErrorMessage()
                }
            }

            override fun onFailure(call: Call<List<Partida>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                showErrorMessage()

            }

        })

    }

    private fun passarListaParaOLiveDataPartidasSegundaRodada(listaPartidas: MutableList<Partida>) {
        listaPartidasSegundaRodada.value = listaPartidas
    }

    private fun passarListaParaOLiveDataPartidasPrimeiraRodada(listaPartidas: MutableList<Partida>) {
        listaPartidasPrimeiraRodada.value = listaPartidas
    }

    override fun findPartidasDaTerceiraRodada(grupo: String){
        matchesApi!!.getMatches().enqueue(object: Callback<List<Partida>> {
            override fun onResponse(call: Call<List<Partida>>, response: Response<List<Partida>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    val listaPartidas: MutableList<Partida> = mutableListOf()
                    response.body()?.forEach { partida->
                        if (partida.estadio.rodada == 3){
                            if (grupo == "Todos os Grupos"){
                                listaPartidas.add(partida)
                            }else{
                                when(partida.estadio.grupo){
                                    "A" -> {if (grupo == "A"){listaPartidas.add(partida)}}
                                    "B" -> {if (grupo == "B"){listaPartidas.add(partida)}}
                                    "C" -> {if (grupo == "C"){listaPartidas.add(partida)}}
                                    "D" -> {if (grupo == "D"){listaPartidas.add(partida)}}
                                    "E" -> {if (grupo == "E"){listaPartidas.add(partida)}}
                                    "F" -> {if (grupo == "F"){listaPartidas.add(partida)}}
                                    "G" -> {if (grupo == "G"){listaPartidas.add(partida)}}
                                    "H" -> {if (grupo == "H"){listaPartidas.add(partida)}}

                                }
                            }
                        }

                    }

                    passarListaParaOLiveDataDeTerceiraRodada(listaPartidas)




                } else {
                    showErrorMessage()
                }
            }

            override fun onFailure(call: Call<List<Partida>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                showErrorMessage()

            }

        })

    }

    private fun passarListaParaOLiveDataDeTerceiraRodada(listaPartidas: MutableList<Partida>) {
        listaPartidasTerceiraRodada.value = listaPartidas
    }

    override fun findMatchesFromApi(){
        matchesApi!!.getMatches().enqueue(object: Callback<List<Partida>> {
            override fun onResponse(call: Call<List<Partida>>, response: Response<List<Partida>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    val partidas: List<Partida> = response.body()!!

                    passarListaParaOLiveDataDeListaDeTodasAsPartidas(partidas)


                } else {
                    showErrorMessage()
                }
            }

            override fun onFailure(call: Call<List<Partida>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                showErrorMessage()

            }

        })

    }

    private fun passarListaParaOLiveDataDeListaDeTodasAsPartidas(partidas: List<Partida>) {
        listaTodasPartidas.value = partidas
    }


}