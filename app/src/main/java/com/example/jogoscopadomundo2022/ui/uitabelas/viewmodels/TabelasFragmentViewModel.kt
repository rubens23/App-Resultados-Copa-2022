package com.example.jogoscopadomundo2022.ui.uitabelas.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.jogoscopadomundo2022.data.apitabelas.Tabelas
import com.example.jogoscopadomundo2022.data.apitabelas.repository.TabelasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TabelasFragmentViewModel @Inject constructor(
    private val tabelasRepository: TabelasRepository
): ViewModel() {



    val tabelasLiveDataTest: MutableLiveData<String> = MutableLiveData()
    val listaTabelasLDViewModel: MutableLiveData<MutableList<Tabelas>> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun callTabelasApi(spinnerItem: Int){
        tabelasRepository.callTabelasApi(spinnerItem)
    }

    fun tabelasRepositoryListener(){
        viewModelScope.launch {
            tabelasRepository.listaTabelasLiveData.asFlow().collect{
                listaTabelasLDViewModel.postValue(it)
            }

        }
    }

    fun tabelasErrorLiveData(){
        viewModelScope.launch {
            tabelasRepository.errorLiveData.asFlow().collect{
                errorLiveData.postValue(it)
            }
        }
    }

}