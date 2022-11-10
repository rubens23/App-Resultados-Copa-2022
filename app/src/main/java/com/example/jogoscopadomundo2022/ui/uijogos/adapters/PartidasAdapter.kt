package com.example.jogoscopadomundo2022.ui.uijogos.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jogoscopadomundo2022.databinding.MatchItemBinding
import com.example.jogoscopadomundo2022.domain.apijogos.Partida
import com.example.jogoscopadomundo2022.ui.uijogos.fragments.JogosFragment



class PartidasAdapter(private val matchesList: List<Partida>): RecyclerView.Adapter<PartidasAdapter.ViewHolder>() {

    fun getPartidas(): List<Partida> {
        return matchesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(matchesList[position])

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = matchesList.size
    inner class ViewHolder(private val binding: MatchItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindData(partida: Partida) {
            Glide.with(binding.root.context).load(partida.mandante.imagem).circleCrop().into(binding.ivHomeTeam)
            Glide.with(binding.root.context).load(partida.visitante.imagem).circleCrop().into(binding.ivAwayTeam)
            binding.tvHomeTeamName.text = partida.mandante.nome
            binding.tvAwayTeamName.text = partida.visitante.nome
            binding.tvGrupo.text = "Grupo ${partida.estadio.grupo}"
            binding.tvRodada.text = "${partida.estadio.rodada}ª rodada"
            binding.horarioJogo.text = partida.estadio.horario_jogo
            binding.tvNomeEstadio.text = "Estádio ${partida.estadio.nome}"
            binding.tvDataJogo.text = partida.estadio.data_jogo

            if(partida.mandante.placar != null){
                binding.tvHomeTeamScore.text = partida.mandante.placar.toString()
            }
            if(partida.visitante.placar != null){
                binding.tvAwayTeamScore.text = partida.visitante.placar.toString()
            }
            itemView.setOnClickListener {
//                val intent = Intent(binding.root.context, ActivityDetail::class.java)
//                intent.putExtra(ActivityDetail.Extras.MATCH, partida)
//                binding.root.context.startActivity(intent)



            }



            Log.d("testandoapi", ""+partida.mandante.nome)


        }
    }

}