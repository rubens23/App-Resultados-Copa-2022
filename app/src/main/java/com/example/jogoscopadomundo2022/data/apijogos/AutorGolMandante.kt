package com.example.jogoscopadomundo2022.data.apijogos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AutorGolMandante(
    val nome_jogador: String?,
    val minuto_gol: String?
): Parcelable
