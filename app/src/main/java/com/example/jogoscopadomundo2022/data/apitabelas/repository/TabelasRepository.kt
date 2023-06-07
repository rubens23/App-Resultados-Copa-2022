package com.example.jogoscopadomundo2022.data.apitabelas.repository

import androidx.lifecycle.MutableLiveData
import com.example.jogoscopadomundo2022.data.apitabelas.Tabelas

interface TabelasRepository {
    var testLiveData: MutableLiveData<String>
    var listaTabelasLiveData: MutableLiveData<MutableList<Tabelas>>


    fun callTabelasApi(posicaoSpinner: Int)

    var errorLiveData: MutableLiveData<String>
}