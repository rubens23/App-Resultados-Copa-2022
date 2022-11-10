package com.example.jogoscopadomundo2022.ui.uijogos.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.jogoscopadomundo2022.databinding.FragmentDetalhesJogoBinding
import com.example.jogoscopadomundo2022.domain.apijogos.AutorGolMandante
import com.example.jogoscopadomundo2022.domain.apijogos.AutorGolVisitante
import com.example.jogoscopadomundo2022.domain.apijogos.Partida
import com.example.jogoscopadomundo2022.ui.uijogos.adapters.GolsMandanteAdapter
import com.example.jogoscopadomundo2022.ui.uijogos.adapters.GolsVisitanteAdapter
import com.example.jogoscopadomundo2022.ui.uijogos.viewmodels.ActivityDetailViewModel

class DetalhesJogoFragment: Fragment() {

    private lateinit var binding: FragmentDetalhesJogoBinding
    private val args: DetalhesJogoFragmentArgs by navArgs()
    private var partida: Partida? = null
    private lateinit var golsMandanteAdapter: GolsMandanteAdapter
    private lateinit var golsVisitanteAdapter: GolsVisitanteAdapter
    private lateinit var viewModel: ActivityDetailViewModel
    val listaGolsMandante: ArrayList<AutorGolMandante> = ArrayList()
    val listaGolsVisitante: ArrayList<AutorGolVisitante> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetalhesJogoBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        partida = args.partida

        bindPartidaDataInViews()
        fazerListasDeGols()

        initViewModel()
        setupMatchesRefresh()
        
        
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ActivityDetailViewModel::class.java]
        //no observer vai ter que ter um algoritmo para filtrar só a partida que é do meu
        //interesse
        viewModel.partidas.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "recebi dados", Toast.LENGTH_SHORT).show()
            it.forEach {
                if(it.mandante.nome == partida?.mandante?.nome
                    && it.visitante.nome == partida?.visitante?.nome
                    &&it.estadio.rodada == partida?.estadio?.rodada){
                    atualizarListaGolsMandante(it)
                    atualizarListaGolsVisitante(it)
                    updatePartidaDataInViews(it)

                }
            }
        }
    }

    private fun setupMatchesRefresh() {
        binding!!.srlMatches.setOnRefreshListener {
            //Toast.makeText(requireContext(), "comecei a fazer o refresh", Toast.LENGTH_SHORT).show()

            viewModel.swipeRefreshLayout.observe(viewLifecycleOwner){
                if(!it){
                    binding.srlMatches.isRefreshing = false
                }
            }
            viewModel.findMatchesFromApi()


        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.findMatchesFromApi()
    }

    private fun atualizarListaGolsMandante(p: Partida) {
        listaGolsMandante.clear()
        p.jogo.autores_gols_time_mandante.forEach { jogadorQueFezGol ->
            if(jogadorQueFezGol.nome_jogador != null && jogadorQueFezGol.minuto_gol != null){
                listaGolsMandante.add(jogadorQueFezGol)
            }
        }
        if(listaGolsMandante.size > 0){
            setarListaGolsMandantesNoAdapter(listaGolsMandante)
            golsMandanteAdapter.notifyDataSetChanged()
        }
    }

    private fun atualizarListaGolsVisitante(p: Partida) {
        listaGolsVisitante.clear()
        p.jogo.autores_gols_time_visitante.forEach { jogadorQueFezGol ->
            if(jogadorQueFezGol.nome_jogador != null && jogadorQueFezGol.minuto_gol != null){
                listaGolsVisitante.add(jogadorQueFezGol)
            }
        }
        if(listaGolsVisitante.size > 0){
            setarListaGolsVisitantesNoAdapter(listaGolsVisitante)
            golsVisitanteAdapter.notifyDataSetChanged()
        }
    }

    private fun fazerListasDeGols() {
        fazerListaDeGolsDoTimeMandante()
        fazerListaDeGolsDoTimeVisitante()
    }


    private fun fazerListaDeGolsDoTimeMandante() {

        partida?.jogo?.autores_gols_time_mandante?.forEach { jogadorQueFezGol ->
            if(jogadorQueFezGol.nome_jogador != null && jogadorQueFezGol.minuto_gol != null){
                listaGolsMandante.add(jogadorQueFezGol)
            }
        }
        if(listaGolsMandante.size > 0){
            setarListaGolsMandantesNoAdapter(listaGolsMandante)
        }

    }

    private fun fazerListaDeGolsDoTimeVisitante() {
        partida?.jogo?.autores_gols_time_visitante?.forEach { jogadorQueFezGol ->
            if(jogadorQueFezGol.nome_jogador != null && jogadorQueFezGol.minuto_gol != null){
                listaGolsVisitante.add(jogadorQueFezGol)
            }
        }
        if(listaGolsVisitante.size > 0){
            setarListaGolsVisitantesNoAdapter(listaGolsVisitante)
        }



    }

    private fun setarListaGolsMandantesNoAdapter(listaGolsMandante: ArrayList<AutorGolMandante>) {
        golsMandanteAdapter = GolsMandanteAdapter(listaGolsMandante)
        binding.recyclerGoalsHomeTeam.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerGoalsHomeTeam.adapter = golsMandanteAdapter
    }


    private fun setarListaGolsVisitantesNoAdapter(listaGolsVisitante: ArrayList<AutorGolVisitante>) {
        golsVisitanteAdapter = GolsVisitanteAdapter(listaGolsVisitante)
        binding.recyclerGoalsAwayTeam.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerGoalsAwayTeam.adapter = golsVisitanteAdapter
    }

    private fun bindPartidaDataInViews() {
        partida?.let {

            binding.tvDescription.text = it.descricao
            binding.tvFase.text = it.estadio.fase
            binding.tvRodada.text = "${it.estadio.rodada}ª rodada"
            binding.tvGrupo.text = "Grupo ${it.estadio.grupo}"

            Glide.with(requireContext()).load(it.mandante.imagem).circleCrop().into(binding.ivHomeTeam)
            binding.tvHomeTeamName.text = it.mandante.nome
            Glide.with(requireContext()).load(it.visitante.imagem).circleCrop().into(binding.ivAwayTeam)
            binding.tvAwayTeamName.text = it.visitante.nome
            if(it.mandante.placar != null){
                binding.tvHomeTeamScore.text = it.mandante.placar.toString()
            }
            if (it.visitante.placar != null){
                binding.tvAwayTeamScore.text = it.visitante.placar.toString()
            }
        }
    }

    private fun updatePartidaDataInViews(p: Partida) {
        p?.let {

            binding.tvDescription.text = it.descricao
            binding.tvFase.text = it.estadio.fase
            binding.tvRodada.text = "${it.estadio.rodada}ª rodada"
            binding.tvGrupo.text = "Grupo ${it.estadio.grupo}"

            Glide.with(requireContext()).load(it.mandante.imagem).circleCrop().into(binding.ivHomeTeam)
            binding.tvHomeTeamName.text = it.mandante.nome
            Glide.with(requireContext()).load(it.visitante.imagem).circleCrop().into(binding.ivAwayTeam)
            binding.tvAwayTeamName.text = it.visitante.nome
            if(it.mandante.placar != null){
                binding.tvHomeTeamScore.text = it.mandante.placar.toString()
            }
            if (it.visitante.placar != null){
                binding.tvAwayTeamScore.text = it.visitante.placar.toString()
            }
        }
    }
}