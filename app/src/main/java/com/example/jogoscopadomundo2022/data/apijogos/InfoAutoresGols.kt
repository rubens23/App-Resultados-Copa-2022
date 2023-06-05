package com.example.jogoscopadomundo2022.data.apijogos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoAutoresGols(
    val autores_gols_time_mandante: ArrayList<AutorGolMandante>,
    val autores_gols_time_visitante: ArrayList<AutorGolVisitante>
): Parcelable
