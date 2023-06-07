package com.example.jogoscopadomundo2022.ui.uijogos.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.jogoscopadomundo2022.data.apijogos.Partida
import com.example.jogoscopadomundo2022.data.apijogos.repository.JogosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JogosFragmentViewModel @Inject constructor(
    private val jogosRepository: JogosRepository
): ViewModel() {

    private var positionRecycler: Int? = null
    var listaJogosFromTodayMatches: MutableLiveData<MutableList<Partida>> = MutableLiveData()
    var listaPartidasPrimeiraRodada: MutableLiveData<MutableList<Partida>> = MutableLiveData()
    var listaPartidasSegundaRodada: MutableLiveData<MutableList<Partida>> = MutableLiveData()
    var listaPartidasTerceiraRodada: MutableLiveData<MutableList<Partida>> = MutableLiveData()
    var listaTodasPartidas: MutableLiveData<List<Partida>> = MutableLiveData()
    var errorFromTodayMatchesLiveData: MutableLiveData<String> = MutableLiveData()

    fun setPosition(position: Int){
        positionRecycler = position
        Log.d("posicaoviewmodel", "coloquei um novo valor de posicao: ${positionRecycler}")
    }

    fun getCurrentPosition(): Int? = positionRecycler

    fun listaPartidasFromTodayListener(){
        viewModelScope.launch {
            jogosRepository.listaPartidasFromFindTodayMatches.asFlow().collect{
                listaJogosFromTodayMatches.postValue(it)
            }
        }
    }

    fun listaPartidasPrimeiraRodada(){
        viewModelScope.launch {
            jogosRepository.listaPartidasPrimeiraRodada.asFlow().collect{
                listaPartidasPrimeiraRodada.postValue(it)
            }
        }
    }

    fun listaPartidasSegundaRodada(){
        viewModelScope.launch {
            jogosRepository.listaPartidasSegundaRodada.asFlow().collect{
                listaPartidasSegundaRodada.postValue(it)
            }
        }
    }

    fun listaPartidasTerceiraRodada(){
        viewModelScope.launch {
            jogosRepository.listaPartidasTerceiraRodada.asFlow().collect{
                listaPartidasTerceiraRodada.postValue(it)
            }
        }
    }

    fun listaTodasPartidasListener(){
        viewModelScope.launch {
            jogosRepository.listaTodasPartidas.asFlow().collect{
                listaTodasPartidas.postValue(it)
            }
        }
    }

    fun errorFromTodayMatches(){
        viewModelScope.launch {
            jogosRepository.errorFromFindTodayMatches.asFlow().collect{
                errorFromTodayMatchesLiveData.postValue(it)
            }
        }
    }

    fun findTodayMatchesFromApi(){
        jogosRepository.findTodayMatchesFromApi()
    }

    fun findPartidasDaPrimeiraRodada(grupo:String){
        jogosRepository.findPartidasDaPrimeiraRodada(grupo)
    }

    fun findMatchesFromApi(){
        jogosRepository.findMatchesFromApi()
    }

    fun findPartidasDaSegundaRodada(grupo:String){
        jogosRepository.findPartidasDaSegundaRodada(grupo)
    }

    fun findPartidasDaTerceiraRodada(grupo:String){
        jogosRepository.findPartidasDaTerceiraRodada(grupo)
    }



}