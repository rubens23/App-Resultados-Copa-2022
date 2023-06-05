package com.example.jogoscopadomundo2022.data.apijogos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Partida(
    val descricao: String,
    val estadio: Estadio,
    val mandante: Time,
    val visitante: Time,
    val jogo: InfoAutoresGols
): Parcelable
