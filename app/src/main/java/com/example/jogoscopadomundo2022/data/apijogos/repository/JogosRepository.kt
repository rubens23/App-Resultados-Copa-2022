package com.example.jogoscopadomundo2022.data.apijogos.repository

import androidx.lifecycle.MutableLiveData
import com.example.jogoscopadomundo2022.data.apijogos.Partida

interface JogosRepository {
    fun findTodayMatchesFromApi()
    var listaPartidasFromFindTodayMatches: MutableLiveData<MutableList<Partida>>
    var errorFromFindTodayMatches: MutableLiveData<String>
    fun findPartidasDaPrimeiraRodada(grupo: String)
    var listaPartidasPrimeiraRodada: MutableLiveData<MutableList<Partida>>
    fun findPartidasDaSegundaRodada(grupo: String)
    var listaPartidasSegundaRodada: MutableLiveData<MutableList<Partida>>
    fun findPartidasDaTerceiraRodada(grupo: String)
    var listaPartidasTerceiraRodada: MutableLiveData<MutableList<Partida>>
    fun findMatchesFromApi()
    var listaTodasPartidas: MutableLiveData<List<Partida>>
}