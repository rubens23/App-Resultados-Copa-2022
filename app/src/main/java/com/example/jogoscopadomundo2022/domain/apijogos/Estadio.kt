package com.example.jogoscopadomundo2022.domain.apijogos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Estadio(
    val nome: String,
    val data_jogo: String,
    val horario_jogo: String,
    val fase: String,
    val rodada: Int,
    val grupo: String
): Parcelable
