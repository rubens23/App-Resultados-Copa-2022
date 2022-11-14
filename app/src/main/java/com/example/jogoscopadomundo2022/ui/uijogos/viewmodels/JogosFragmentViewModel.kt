package com.example.jogoscopadomundo2022.ui.uijogos.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel

class JogosFragmentViewModel: ViewModel() {

    private var positionRecycler: Int? = null

    fun setPosition(position: Int){
        positionRecycler = position
        Log.d("posicaoviewmodel", "coloquei um novo valor de posicao: ${positionRecycler}")
    }

    fun getCurrentPosition(): Int? = positionRecycler



}