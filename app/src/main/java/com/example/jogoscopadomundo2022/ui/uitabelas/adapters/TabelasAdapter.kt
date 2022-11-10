package com.example.jogoscopadomundo2022.ui.uitabelas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jogoscopadomundo2022.databinding.ItemTabelaBinding
import com.example.jogoscopadomundo2022.domain.apitabelas.Tabelas


class TabelasAdapter(val listaTabelas: List<Tabelas>): RecyclerView.Adapter<TabelasAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemTabelaBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(tabela: Tabelas){
            binding.tvTabelaGrupo.text = "Grupo ${tabela.grupo}"
            bindLinhaPrimeiraPosicaoTabela(tabela)
            bindLinhaSegundaPosicaoTabela(tabela)
            bindLinhaTerceiraPosicaoTabela(tabela)
            bindLinhaQuartaPosicaoTabela(tabela)

        }

        private fun bindLinhaPrimeiraPosicaoTabela(tabela: Tabelas) {
            Glide.with(binding.root.context).load(tabela.time1.imagem).circleCrop().into(binding.imagemTime1)
            binding.posicao1.text = tabela.time1.posicao.toString()
            binding.nome1.text = tabela.time1.nome
            binding.pontos1.text = tabela.time1.pontos.toString()
            binding.qntJogos1.text = tabela.time1.jogos.toString()
            binding.qntVitorias1.text = tabela.time1.vitorias.toString()
            binding.qntDerrotas1.text = tabela.time1.derrotas.toString()
            binding.qntEmpates1.text = tabela.time1.empates.toString()
            binding.saldoGols1.text = tabela.time1.saldo_gols.toString()
        }

        private fun bindLinhaSegundaPosicaoTabela(tabela: Tabelas) {
            Glide.with(binding.root.context).load(tabela.time2.imagem).circleCrop().into(binding.imagemTime2)
            binding.posicao2.text = tabela.time2.posicao.toString()
            binding.nome2.text = tabela.time2.nome
            binding.pontos2.text = tabela.time2.pontos.toString()
            binding.qntJogos2.text = tabela.time2.jogos.toString()
            binding.qntVitorias2.text = tabela.time2.vitorias.toString()
            binding.qntDerrotas2.text = tabela.time2.derrotas.toString()
            binding.qntEmpates2.text = tabela.time2.empates.toString()
            binding.saldoGols2.text = tabela.time2.saldo_gols.toString()

        }

        private fun bindLinhaTerceiraPosicaoTabela(tabela: Tabelas) {
            Glide.with(binding.root.context).load(tabela.time3.imagem).circleCrop().into(binding.imagemTime3)
            binding.posicao3.text = tabela.time3.posicao.toString()
            binding.nome3.text = tabela.time3.nome
            binding.pontos3.text = tabela.time3.pontos.toString()
            binding.qntJogos3.text = tabela.time3.jogos.toString()
            binding.qntVitorias3.text = tabela.time3.vitorias.toString()
            binding.qntDerrotas3.text = tabela.time3.derrotas.toString()
            binding.qntEmpates3.text = tabela.time3.empates.toString()
            binding.saldoGols3.text = tabela.time3.saldo_gols.toString()

        }

        private fun bindLinhaQuartaPosicaoTabela(tabela: Tabelas) {
            Glide.with(binding.root.context).load(tabela.time4.imagem).circleCrop().into(binding.imagemTime4)
            binding.posicao4.text = tabela.time4.posicao.toString()
            binding.nome4.text = tabela.time4.nome
            binding.pontos4.text = tabela.time4.pontos.toString()
            binding.qntJogos4.text = tabela.time4.jogos.toString()
            binding.qntVitorias4.text = tabela.time4.vitorias.toString()
            binding.qntDerrotas4.text = tabela.time4.derrotas.toString()
            binding.qntEmpates4.text = tabela.time4.empates.toString()
            binding.saldoGols4.text = tabela.time4.saldo_gols.toString()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTabelaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(listaTabelas[position])

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = listaTabelas.size
}