package com.example.jogoscopadomundo2022.ui.uijogos.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jogoscopadomundo2022.R
import com.example.jogoscopadomundo2022.data.jogos.MatchesApi
import com.example.jogoscopadomundo2022.databinding.FragmentJogosBinding
import com.example.jogoscopadomundo2022.domain.apijogos.Partida
import com.example.jogoscopadomundo2022.ui.uijogos.adapters.PartidasAdapter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.properties.Delegates


class JogosFragment: Fragment() {

    private lateinit var key2: Preferences.Key<String>
    private lateinit var key: Preferences.Key<String>
    private var currentCounterValue by Delegates.notNull<Int>()
    private lateinit var datastore: DataStore<Preferences>
    private lateinit var binding: FragmentJogosBinding
    private var matchesApi: MatchesApi? = null
    private var adapter = PartidasAdapter(Collections.emptyList())
    private var mostrarTodasPartidas = false
    private var mostrarPartidasDoDia = false
    private var mostrarPartidasPrimeiraRodada = false
    private var mostrarPartidasSegundaRodada = false
    private var mostrarPartidasTerceiraRodada = false
    private lateinit var listaSpinnerGrupos: Array<String>
    private lateinit var listaSpinnerJogos: Array<String>


    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")










    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentJogosBinding.inflate(inflater)

        Log.d("ciclodevida", "to no onCreateView")


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("testandoretornosave", "${savedInstanceState?.getString("teste")}")






        /*
        if(savedInstanceState != null){
            createAdapterDoSpinnerGrupos(savedInstanceState.getStringArray("LISTA_GRUPOS") as Array<String>)
            createAdapterDoSpinnerJogos(savedInstanceState.getStringArray("LISTA_GRUPOS") as Array<String>)
            Log.d("onsavedinstance", "eu to no onsavedinstancestate")
            mostrarPartidasDoDia = true
            setupHttpClient()
            setupMatchesList()
            setupMatchesRefresh()
        }else{

        }

         */

        //mostrarPartidasDoDia = true
        createSpinnerList()
        setupHttpClient()
        setupMatchesList()
        Log.d("entendendo2", "to na linha acima da chamada do matches refresh")
        setupMatchesRefresh()

        //saves key-value in datastore
        //key do primeiro spinner





        Log.d("ciclodevida", "to no onviewcreated")


    }









    private fun createSpinnerList() {
        listaSpinnerJogos = arrayOf("Jogos de Hoje", "1ª Rodada", "2ª Rodada", "3ª Rodada")


        createAdapterDoSpinnerJogos(listaSpinnerJogos)
    }

    private fun createSpinnerGruposList(){
        listaSpinnerGrupos = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "Todos os Grupos")

        createAdapterDoSpinnerGrupos(listaSpinnerGrupos)
    }







    private fun createAdapterDoSpinnerJogos(listaSpinnerJogos: Array<String>) {
        val adapterSpinnerJogos = ArrayAdapter(
            requireActivity(),
            R.layout.drop_down_item,
            listaSpinnerJogos
        )

        setSpinnerAdapter(adapterSpinnerJogos)


    }
    private fun createAdapterDoSpinnerGrupos(listaSpinnerGrupos: Array<String>) {
        val adapterSpinnerGrupos = ArrayAdapter(
            requireActivity(),
            R.layout.drop_down_item,
            listaSpinnerGrupos
        )

        setSpinnerGruposAdapter(adapterSpinnerGrupos)

    }



    private fun setSpinnerAdapter(adapterSpinnerJogos: ArrayAdapter<String>) {
        binding?.tvSpinner?.setAdapter(adapterSpinnerJogos)

        setJogosSpinnerClickListener()

    }

    private fun setSpinnerGruposAdapter(adapterSpinnerGrupos: ArrayAdapter<String>) {
        binding.tvSpinnerGrupos.setAdapter(adapterSpinnerGrupos)

        setSpinnerGruposClickListener()
    }



    //todo fazer outro spinner que vai aparecer quando o usuario escolher a rodada aí ele vai poder escolher os jogos por grupo ou ver os jogos de todos os grupos

    private fun setJogosSpinnerClickListener() {
        binding.tvSpinner.setOnItemClickListener { _, _, pos, _ ->
            if(pos == 0){
                hideSpinnerGrupos()
                findTodayMatchesFromApi()
                mostrarPartidasDoDia = true
                mostrarTodasPartidas = false
                mostrarPartidasPrimeiraRodada = false
                mostrarPartidasSegundaRodada = false
                mostrarPartidasTerceiraRodada = false
            }
            if (pos == 1){
                createSpinnerGruposList()
                showSpinnerGrupos()
                clearRecycleView()
                if(binding!!.tvSpinnerGrupos.text.toString() == "" || binding!!.tvSpinnerGrupos.text.toString() == "Todos os Grupos"){
                    findPartidasDaPrimeiraRodada("Todos os Grupos")

                }else{
                    findPartidasDaPrimeiraRodada(binding!!.tvSpinnerGrupos.text.toString())
                }
                mostrarPartidasDoDia = false
                mostrarTodasPartidas = false
                mostrarPartidasPrimeiraRodada = true
                mostrarPartidasSegundaRodada = false
                mostrarPartidasTerceiraRodada = false
            }
            //fazer a lista do segundo spinner
            //fazer o adapter do segundo spinner
            //eu n sei se vou precisar de um metodo pra fazer o hide do segundo spinner


            if (pos == 2){
                createSpinnerGruposList()
                showSpinnerGrupos()
                clearRecycleView()
                if(binding!!.tvSpinnerGrupos.text.toString() == "" || binding!!.tvSpinnerGrupos.text.toString() == "Todos os Grupos"){
                    findPartidasDaSegundaRodada("Todos os Grupos")

                }else{
                    findPartidasDaSegundaRodada(binding!!.tvSpinnerGrupos.text.toString())
                }

                mostrarPartidasDoDia = false
                mostrarTodasPartidas = false
                mostrarPartidasPrimeiraRodada = false
                mostrarPartidasSegundaRodada = true
                mostrarPartidasTerceiraRodada = false
            }
            if (pos == 3){
                createSpinnerGruposList()
                showSpinnerGrupos()
                clearRecycleView()
                if(binding!!.tvSpinnerGrupos.text.toString() == "" || binding!!.tvSpinnerGrupos.text.toString() == "Todos os Grupos"){
                    findPartidasDaTerceiraRodada("Todos os Grupos")

                }else{
                    findPartidasDaTerceiraRodada(binding!!.tvSpinnerGrupos.text.toString())
                }

                /*
                entao aqui nessa navegacao ele chama o oncreate sempre, o fragmento é sempre reconstruido
                 */

                mostrarPartidasDoDia = false
                mostrarTodasPartidas = false
                mostrarPartidasPrimeiraRodada = false
                mostrarPartidasSegundaRodada = false
                mostrarPartidasTerceiraRodada = true

            }
        }


    }



    private fun clearRecycleView() {
        binding?.rvMatches?.adapter = PartidasAdapter(emptyList())
    }

    private fun setSpinnerGruposClickListener() {
        binding?.tvSpinnerGrupos?.setOnItemClickListener { adapterView, view, pos, id ->
            if (mostrarPartidasPrimeiraRodada){
                clearRecycleView()
                when(binding?.tvSpinnerGrupos?.text.toString()){
                    "A" -> {findPartidasDaPrimeiraRodada("A")}
                    "B" -> {findPartidasDaPrimeiraRodada("B")}
                    "C" -> {findPartidasDaPrimeiraRodada("C")}
                    "D" -> {findPartidasDaPrimeiraRodada("D")}
                    "E" -> {findPartidasDaPrimeiraRodada("E")}
                    "F" -> {findPartidasDaPrimeiraRodada("F")}
                    "G" -> {findPartidasDaPrimeiraRodada("G")}
                    "H" -> {findPartidasDaPrimeiraRodada("H")}
                    "Todos os Grupos" -> {
                        findPartidasDaPrimeiraRodada("Todos os Grupos")
                    }

                }

            }
            if (mostrarPartidasSegundaRodada){
                clearRecycleView()
                when(binding?.tvSpinnerGrupos?.text.toString()){
                    "A" -> {findPartidasDaSegundaRodada("A")}
                    "B" -> {findPartidasDaSegundaRodada("B")}
                    "C" -> {findPartidasDaSegundaRodada("C")}
                    "D" -> {findPartidasDaSegundaRodada("D")}
                    "E" -> {findPartidasDaSegundaRodada("E")}
                    "F" -> {findPartidasDaSegundaRodada("F")}
                    "G" -> {findPartidasDaSegundaRodada("G")}
                    "H" -> {findPartidasDaSegundaRodada("H")}
                    "Todos os Grupos" -> {findPartidasDaSegundaRodada("Todos os Grupos")}

                }
            }
            if (mostrarPartidasTerceiraRodada){
                clearRecycleView()
                when(binding?.tvSpinnerGrupos?.text.toString()){
                    "A" -> {findPartidasDaTerceiraRodada("A")}
                    "B" -> {findPartidasDaTerceiraRodada("B")}
                    "C" -> {findPartidasDaTerceiraRodada("C")}
                    "D" -> {findPartidasDaTerceiraRodada("D")}
                    "E" -> {findPartidasDaTerceiraRodada("E")}
                    "F" -> {findPartidasDaTerceiraRodada("F")}
                    "G" -> {findPartidasDaTerceiraRodada("G")}
                    "H" -> {findPartidasDaTerceiraRodada("H")}
                    "Todos os Grupos" -> {findPartidasDaTerceiraRodada("Todos os Grupos")}

                }

            }
        }
    }

    private fun showSpinnerGrupos() {
        binding.TILSpinnerGrupos.visibility = View.VISIBLE
    }

    private fun hideSpinnerGrupos() {
        binding.tvSpinnerGrupos.text?.clear()
        binding.TILSpinnerGrupos.visibility = View.GONE
    }


    private fun setupHttpClient() {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()


        val retrofit = Retrofit.Builder().baseUrl(MatchesApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        matchesApi = retrofit.create(MatchesApi::class.java)
    }

    private fun setupMatchesRefresh() {
        binding.srlMatches.setOnRefreshListener {
            Log.d("entendendo2", "to no setupMatchesRefresh")
            dealWithDataUpdateOnRefresh()

        }
    }

    private fun dealWithDataUpdateOnRefresh() {
        Log.d("entendendo3", "mostrarTodasPartidas: ${mostrarTodasPartidas}")
        Log.d("entendendo3", "mostrarPartidasDoDia: ${mostrarPartidasDoDia}")
        Log.d("entendendo3", "mostrarPartidasPrimeiraRodada: ${mostrarPartidasPrimeiraRodada}")
        Log.d("entendendo3", "mostrarPartidasSegundaRodada: ${mostrarPartidasSegundaRodada}")
        Log.d("entendendo3", "mostrarPartidasTerceiraRodada: ${mostrarPartidasTerceiraRodada}")
        if(mostrarTodasPartidas){
            findMatchesFromApi()
            Log.d("entendendo3", "to no refresh mostrando todas as partidas")
        }
        if (mostrarPartidasDoDia){
            findTodayMatchesFromApi()
            Log.d("entendendo3", "to no refresh mostrando todas as partidas do dia")
        }
        if (mostrarPartidasPrimeiraRodada){
            Log.d("entendendo3", "to no refresh mostrando todas as partidas da primeira rodada")
            if(binding.tvSpinnerGrupos.text.toString() == ""){
                findPartidasDaPrimeiraRodada("Todos os Grupos")
                Log.d("entendendo3", "to no refresh mostrando todas as partidas da primeira rodada")
            }else{
                findPartidasDaPrimeiraRodada(binding.tvSpinnerGrupos.text.toString())
                Log.d("entendendo3", "to no refresh mostrando todas as partidas da primeira rodada de algum grupo")
            }

        }
        if (mostrarPartidasSegundaRodada){
            Log.d("entendendo3", "to no refresh mostrando todas as partidas da segunda rodada")
            if(binding.tvSpinnerGrupos.text.toString() == ""){
                findPartidasDaSegundaRodada("Todos os Grupos")
            }else{
                findPartidasDaSegundaRodada(binding.tvSpinnerGrupos.text.toString())
                Log.d("entendendo3", "to no refresh mostrando todas as partidas da segunda rodada de algum grupo")

            }
        }
        if (mostrarPartidasTerceiraRodada){
            Log.d("entendendo3", "to no refresh mostrando todas as partidas da terceira rodada")
            if(binding.tvSpinnerGrupos.text.toString() == ""){
                findPartidasDaTerceiraRodada("Todos os Grupos")
            }else{
                findPartidasDaTerceiraRodada(binding.tvSpinnerGrupos.text.toString())
                Log.d("entendendo3", "to no refresh mostrando todas as partidas da terceira rodada de algum grupo")

            }
        }

    }

    private fun setupMatchesList() {
        binding.rvMatches.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMatches.adapter = adapter

    }







    fun findTodayMatchesFromApi(){
        binding.srlMatches.isRefreshing = true
        matchesApi!!.getMatches().enqueue(object: Callback<List<Partida>> {
            override fun onResponse(call: Call<List<Partida>>, response: Response<List<Partida>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    val listaPartidas: MutableList<Partida> = mutableListOf()
                    response.body()?.forEach { partida->
                        if (partida.estadio.data_jogo == "20/11/2022"){
                            listaPartidas.add(partida)

                        }

                    }



                    adapter = PartidasAdapter(listaPartidas)
                    binding.rvMatches.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvMatches.adapter = adapter
                } else {
                    showErrorMessage()
                }
                binding.srlMatches.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Partida>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                Toast.makeText(requireActivity(), "ocorreu um erro ao carregar os dados, verifique sua conexão com a internet e tente novamente!", Toast.LENGTH_LONG).show()
                binding!!.srlMatches.isRefreshing = false

            }

        })

    }

    override fun onStop() {
        super.onStop()
        Log.d("ciclofragment", "to no on stop do fragment jogos")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ciclofragment", "to no on destroy do fragment jogos")

    }

    private fun findPartidasDaPrimeiraRodada(grupo: String) {
        binding!!.srlMatches.isRefreshing = true
        matchesApi!!.getMatches().enqueue(object: Callback<List<Partida>> {
            override fun onResponse(call: Call<List<Partida>>, response: Response<List<Partida>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    val listaPartidas: MutableList<Partida> = mutableListOf()
                    response.body()?.forEach { partida->

                        if (partida.estadio.rodada == 1){
                            if (grupo == "Todos os Grupos"){
                                listaPartidas.add(partida)
                            }else{
                                when(partida.estadio.grupo){
                                    "A" -> {if (grupo == "A"){listaPartidas.add(partida)}}
                                    "B" -> {if (grupo == "B"){listaPartidas.add(partida)}}
                                    "C" -> {if (grupo == "C"){listaPartidas.add(partida)}}
                                    "D" -> {if (grupo == "D"){listaPartidas.add(partida)}}
                                    "E" -> {if (grupo == "E"){listaPartidas.add(partida)}}
                                    "F" -> {if (grupo == "F"){listaPartidas.add(partida)}}
                                    "G" -> {if (grupo == "G"){listaPartidas.add(partida)}}
                                    "H" -> {if (grupo == "H"){listaPartidas.add(partida)}}

                                }
                            }

                        }

                    }



                    adapter = PartidasAdapter(listaPartidas)
                    binding!!.rvMatches.layoutManager = LinearLayoutManager(requireActivity())
                    binding!!.rvMatches.adapter = adapter
                } else {
                    showErrorMessage()
                }
                binding!!.srlMatches.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Partida>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                Toast.makeText(requireActivity(), "ocorreu um erro ao carregar os dados, verifique sua conexão com a internet e tente novamente!", Toast.LENGTH_LONG).show()
                binding!!.srlMatches.isRefreshing = false

            }

        })
    }

    private fun findPartidasDaSegundaRodada(grupo: String) {
        binding!!.srlMatches.isRefreshing = true
        matchesApi!!.getMatches().enqueue(object: Callback<List<Partida>> {
            override fun onResponse(call: Call<List<Partida>>, response: Response<List<Partida>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    val listaPartidas: MutableList<Partida> = mutableListOf()
                    response.body()?.forEach { partida->
                        if (partida.estadio.rodada == 2){
                            if (grupo == "Todos os Grupos"){
                                listaPartidas.add(partida)
                            }else{
                                when(partida.estadio.grupo){
                                    "A" -> {if (grupo == "A"){listaPartidas.add(partida)}}
                                    "B" -> {if (grupo == "B"){listaPartidas.add(partida)}}
                                    "C" -> {if (grupo == "C"){listaPartidas.add(partida)}}
                                    "D" -> {if (grupo == "D"){listaPartidas.add(partida)}}
                                    "E" -> {if (grupo == "E"){listaPartidas.add(partida)}}
                                    "F" -> {if (grupo == "F"){listaPartidas.add(partida)}}
                                    "G" -> {if (grupo == "G"){listaPartidas.add(partida)}}
                                    "H" -> {if (grupo == "H"){listaPartidas.add(partida)}}

                                }
                            }
                        }

                    }



                    adapter = PartidasAdapter(listaPartidas)
                    binding!!.rvMatches.layoutManager = LinearLayoutManager(requireActivity())
                    binding!!.rvMatches.adapter = adapter
                } else {
                    showErrorMessage()
                }
                binding!!.srlMatches.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Partida>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                Toast.makeText(requireActivity(), "ocorreu um erro ao carregar os dados, verifique sua conexão com a internet e tente novamente!", Toast.LENGTH_LONG).show()
                binding!!.srlMatches.isRefreshing = false

            }

        })
    }

    override fun onResume() {
        super.onResume()

        //dealWithDataUpdateOnRefresh()
        createSpinnerList()
        createSpinnerGruposList()
        binding!!.tvSpinner.setSelection(0)
        Log.d("ciclodevida", "to no onresume")
        dealWithSpinnerAfterReturningFromAnotherFragment()
        //read key value in datastore


    }

    private fun dealWithSpinnerAfterReturningFromAnotherFragment() {
        if(binding.tvSpinner.text.toString() != ""){
            if (binding.tvSpinner.text.toString() == "Jogos de Hoje"){
                findTodayMatchesFromApi()
                Log.d("entendendo", "to no jogos de hoje")

            }
            if (binding.tvSpinner.text.toString() == "1ª Rodada"){
                Log.d("entendendo", "to no jogos primeira rodada")
                if(binding.tvSpinnerGrupos.text.toString() != ""){
                    findPartidasDaPrimeiraRodada(binding.tvSpinnerGrupos.text.toString())
                    showSpinnerGrupos()
                    Log.d("entendendo", "to no jogos da primeira rodada de algum grupo")
                }else{
                    findPartidasDaPrimeiraRodada("Todos os Grupos")
                    Log.d("entendendo", "to no jogos da primeira rodada de todos os grupos")
                }
            }
            if (binding.tvSpinner.text.toString() == "2ª Rodada"){
                Log.d("entendendo", "to no jogos segunda rodada")
                if(binding.tvSpinnerGrupos.text.toString() != ""){
                    findPartidasDaSegundaRodada(binding.tvSpinnerGrupos.text.toString())
                    showSpinnerGrupos()
                    Log.d("entendendo", "to no jogos da primeira rodada de algum grupo")
                }else{
                    findPartidasDaSegundaRodada("Todos os Grupos")
                    Log.d("entendendo", "to no jogos da segunda rodada de todos os grupos")
                }
            }
            if (binding.tvSpinner.text.toString() == "3ª Rodada"){
                Log.d("entendendo", "to no jogos terceira rodada")
                if(binding.tvSpinnerGrupos.text.toString() != ""){
                    findPartidasDaTerceiraRodada(binding.tvSpinnerGrupos.text.toString())
                    showSpinnerGrupos()
                    Log.d("entendendo", "to no jogos da primeira rodada de algum grupo")
                }else{
                    findPartidasDaTerceiraRodada("Todos os Grupos")
                    Log.d("entendendo", "to no jogos da terceira rodada de todos os grupos")
                }
            }

        }else{
            //significa que ele esta vazio, se ele esta vazio, ele tem que mostrar os jogos do dia
            findTodayMatchesFromApi()
        }

    }

    private fun findPartidasDaTerceiraRodada(grupo: String) {
        binding!!.srlMatches.isRefreshing = true
        matchesApi!!.getMatches().enqueue(object: Callback<List<Partida>> {
            override fun onResponse(call: Call<List<Partida>>, response: Response<List<Partida>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    val listaPartidas: MutableList<Partida> = mutableListOf()
                    response.body()?.forEach { partida->
                        if (partida.estadio.rodada == 3){
                            if (grupo == "Todos os Grupos"){
                                listaPartidas.add(partida)
                            }else{
                                when(partida.estadio.grupo){
                                    "A" -> {if (grupo == "A"){listaPartidas.add(partida)}}
                                    "B" -> {if (grupo == "B"){listaPartidas.add(partida)}}
                                    "C" -> {if (grupo == "C"){listaPartidas.add(partida)}}
                                    "D" -> {if (grupo == "D"){listaPartidas.add(partida)}}
                                    "E" -> {if (grupo == "E"){listaPartidas.add(partida)}}
                                    "F" -> {if (grupo == "F"){listaPartidas.add(partida)}}
                                    "G" -> {if (grupo == "G"){listaPartidas.add(partida)}}
                                    "H" -> {if (grupo == "H"){listaPartidas.add(partida)}}

                                }
                            }
                        }

                    }



                    adapter = PartidasAdapter(listaPartidas)
                    binding!!.rvMatches.layoutManager = LinearLayoutManager(requireActivity())
                    binding!!.rvMatches.adapter = adapter
                } else {
                    showErrorMessage()
                }
                binding!!.srlMatches.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Partida>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                Toast.makeText(requireActivity(), "ocorreu um erro ao carregar os dados, verifique sua conexão com a internet e tente novamente!", Toast.LENGTH_LONG).show()
                binding!!.srlMatches.isRefreshing = false

            }

        })
    }



    private fun findMatchesFromApi() {
        binding!!.srlMatches.isRefreshing = true
        matchesApi!!.getMatches().enqueue(object: Callback<List<Partida>> {
            override fun onResponse(call: Call<List<Partida>>, response: Response<List<Partida>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    val partidas: List<Partida> = response.body()!!

                    adapter = PartidasAdapter(partidas)
                    binding!!.rvMatches.layoutManager = LinearLayoutManager(requireActivity())
                    binding!!.rvMatches.adapter = adapter
                } else {
                    showErrorMessage()
                }
                binding!!.srlMatches.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Partida>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                Toast.makeText(requireActivity(), "ocorreu um erro ao carregar os dados, verifique sua conexão com a internet e tente novamente!", Toast.LENGTH_LONG).show()
                binding!!.srlMatches.isRefreshing = false

            }

        })

    }

    private fun showErrorMessage() {
        Toast.makeText(requireActivity(), "ocorreu um erro ao carregar os dados, verifique sua conexão com a internet e tente novamente!", Toast.LENGTH_LONG).show()
    }

}