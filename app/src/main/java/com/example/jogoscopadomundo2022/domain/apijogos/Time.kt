package com.example.jogoscopadomundo2022.domain.apijogos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Time(
    val nome: String,
    val posicao_ranking: Int,
    val imagem: String,
    var placar: Int?
): Parcelable
