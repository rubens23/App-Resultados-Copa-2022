package com.example.jogoscopadomundo2022.data.apitabelas

data class InfoTimeTabela(
    val nome: String,
    val imagem: String,
    val posicao: Int,
    val pontos: Int,
    val jogos: Int,
    val vitorias: Int,
    val empates: Int,
    val derrotas: Int,
    val gols_pro: Int,
    val gols_contra: Int,
    val saldo_gols: Int,
    val aproveitamento: Int
)
