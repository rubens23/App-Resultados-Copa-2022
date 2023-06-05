package com.example.jogoscopadomundo2022.data.apijogos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AutorGolVisitante(
    val nome_jogador: String?,
    val minuto_gol: String?
): Parcelable
