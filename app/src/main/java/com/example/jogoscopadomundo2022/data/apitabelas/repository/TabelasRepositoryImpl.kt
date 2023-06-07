package com.example.jogoscopadomundo2022.data.apitabelas.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jogoscopadomundo2022.data.apitabelas.Tabelas
import com.example.jogoscopadomundo2022.data.tabelas.TabelasApi
import com.example.jogoscopadomundo2022.ui.uitabelas.adapters.TabelasAdapter
import com.example.jogoscopadomundo2022.ui.uitabelas.interfaces.ContextProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TabelasRepositoryImpl: TabelasRepository {
    override var testLiveData: MutableLiveData<String> = MutableLiveData()
    override var listaTabelasLiveData: MutableLiveData<MutableList<Tabelas>> = MutableLiveData()
    override var errorLiveData: MutableLiveData<String> = MutableLiveData()
    private var tabelasApi: TabelasApi? = null

    init{
        setupHttpClient()
    }

    private fun setupHttpClient() {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder().baseUrl(TabelasApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        tabelasApi = retrofit.create(TabelasApi::class.java)
    }

    override fun callTabelasApi(posicaoSpinner: Int) {
        tabelasApi!!.getTabelas().enqueue(object: Callback<List<Tabelas>> {
            override fun onResponse(call: Call<List<Tabelas>>, response: Response<List<Tabelas>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    val listaTabelas: MutableList<Tabelas> = mutableListOf()
                    when(posicaoSpinner){
                        //grupo A
                        0->{
                            response.body()?.forEach {
                                if(it.grupo == "A"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo B
                        1->{
                            response.body()?.forEach {
                                if(it.grupo == "B"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo C
                        2->{
                            response.body()?.forEach {
                                if(it.grupo == "C"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo D
                        3->{
                            response.body()?.forEach {
                                if(it.grupo == "D"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo E
                        4->{
                            response.body()?.forEach {
                                if(it.grupo == "E"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo F
                        5->{
                            response.body()?.forEach {
                                if(it.grupo == "F"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo G
                        6->{
                            response.body()?.forEach {
                                if(it.grupo == "G"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo H
                        7->{
                            response.body()?.forEach {
                                if(it.grupo == "H"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //Todos os grupos
                        8->{
                            response.body()?.forEach {
                                listaTabelas.add(it)

                            }
                        }
                    }

                    passarListaTabelasParaLiveData(listaTabelas)

                    //todo no observer eu atualizo o adapter




                } else {
                    showErrorMessage()
                }
            }

            override fun onFailure(call: Call<List<Tabelas>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                errorLiveData.value = "ocorreu um erro ao carregar os dados, verifique sua conexão com a internet e tente novamente!"


            }

        })
    }

    private fun passarListaTabelasParaLiveData(listaTabelas: MutableList<Tabelas>) {
        listaTabelasLiveData.value = listaTabelas
    }

    private fun showErrorMessage() {
        errorLiveData.value = "ocorreu um erro ao carregar os dados, verifique sua conexão com a internet e tente novamente!"
    }


}