package com.example.jogoscopadomundo2022.ui.uijogos.fragments


import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jogoscopadomundo2022.R
import com.example.jogoscopadomundo2022.databinding.FragmentJogosBinding
import com.example.jogoscopadomundo2022.data.apijogos.Partida
import com.example.jogoscopadomundo2022.ui.uijogos.adapters.PartidasAdapter
import com.example.jogoscopadomundo2022.ui.uijogos.viewmodels.JogosFragmentViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class JogosFragment: Fragment(){


    private lateinit var binding: FragmentJogosBinding
    private var adapter = PartidasAdapter(Collections.emptyList())
    private var mostrarTodasPartidas = false
    private var mostrarPartidasDoDia = false
    private var mostrarPartidasPrimeiraRodada = false
    private var mostrarPartidasSegundaRodada = false
    private var mostrarPartidasTerceiraRodada = false
    private lateinit var listaSpinnerGrupos: Array<String>
    private lateinit var listaSpinnerJogos: Array<String>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var lastPosition: Int? = 0
    private lateinit var viewModel: JogosFragmentViewModel


    //recycler view in landscape mode
    //https://stackoverflow.com/questions/59443286/android-i-wanna-to-make-recyclerview-in-landscape-mode-and-all-the-app-in-prtra










    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentJogosBinding.inflate(inflater)

        Log.d("ciclodevida", "to no onCreateView")
        linearLayoutManager = LinearLayoutManager(requireActivity())


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createSpinnerList()
        setupMatchesList()
        setupMatchesRefresh()
        initViewModel()




    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[JogosFragmentViewModel::class.java]
        initViewModelsLiveDatas()
        initObservers()


    }

    private fun initObservers() {
        viewModel.listaJogosFromTodayMatches.observe(viewLifecycleOwner){
            listaPartidas->
            setupAdapterWithReceivedList(listaPartidas)
        }
        viewModel.errorFromTodayMatchesLiveData.observe(viewLifecycleOwner){
            showErrorMessage(it)
        }

        viewModel.listaPartidasPrimeiraRodada.observe(viewLifecycleOwner){
            listaPartidas->
            setAdapterWithPrimeiraRodadaMatches(listaPartidas)
        }

        viewModel.listaPartidasSegundaRodada.observe(viewLifecycleOwner){
            listaPartidas->
            setAdapterWithSegundaRodadaMatches(listaPartidas)
        }

        viewModel.listaPartidasTerceiraRodada.observe(viewLifecycleOwner){
            listaPartidas->
            setAdapterWithTerceiraRodadaMatches(listaPartidas)
        }

        viewModel.listaTodasPartidas.observe(viewLifecycleOwner){
            listaPartidas->
            setAdapterWithListaTodasPartidas(listaPartidas)
        }
    }

    private fun setAdapterWithListaTodasPartidas(listaPartidas: List<Partida>?) {
        if(listaPartidas != null){
            adapter = PartidasAdapter(listaPartidas)
            binding.rvMatches.layoutManager = linearLayoutManager
            binding.rvMatches.adapter = adapter
        }
        binding.srlMatches.isRefreshing = false

    }

    private fun setAdapterWithTerceiraRodadaMatches(listaPartidas: MutableList<Partida>?) {

        if(listaPartidas != null){
            adapter = PartidasAdapter(listaPartidas)
            binding.rvMatches.layoutManager = linearLayoutManager
            binding.rvMatches.adapter = adapter
            if(lastPosition != null){
                binding.rvMatches.scrollToPosition(lastPosition!!)
            }
        }
        binding.srlMatches.isRefreshing = false

    }

    private fun setAdapterWithSegundaRodadaMatches(listaPartidas: MutableList<Partida>?) {
        if(listaPartidas != null){
            adapter = PartidasAdapter(listaPartidas)
            binding.rvMatches.layoutManager = linearLayoutManager
            binding.rvMatches.adapter = adapter
            if(lastPosition != null){
                binding.rvMatches.scrollToPosition(lastPosition!!)
            }
        }
        binding.srlMatches.isRefreshing = false


    }

    private fun setAdapterWithPrimeiraRodadaMatches(listaPartidas: MutableList<Partida>?) {
        if(listaPartidas != null){
            adapter = PartidasAdapter(listaPartidas)
            binding.rvMatches.layoutManager = linearLayoutManager
            binding.rvMatches.adapter = adapter
            if(lastPosition != null){
                binding.rvMatches.scrollToPosition(lastPosition!!)
            }
        }
        binding.srlMatches.isRefreshing = false


    }

    private fun setupAdapterWithReceivedList(listaPartidas: MutableList<Partida>?) {
        if(listaPartidas != null){
            if(listaPartidas.isNotEmpty()){
                adapter = PartidasAdapter(listaPartidas)
                binding.rvMatches.layoutManager = LinearLayoutManager(requireContext())
                binding.rvMatches.adapter = adapter
                if(lastPosition != null){
                    binding.rvMatches.scrollToPosition(lastPosition!!)
                }

            }else{
                //lista é vazia e mensagem tem que aparecer
                turnOnNoMatchesTextView()
                adapter = PartidasAdapter(listaPartidas)
                //binding.rvMatches.layoutManager = LinearLayoutManager(requireContext())
                binding.rvMatches.adapter = adapter
                if(lastPosition != null){
                    binding.rvMatches.scrollToPosition(lastPosition!!)

                }
            }
        }
        binding.srlMatches.isRefreshing = false


    }

    private fun initViewModelsLiveDatas() {
        viewModel.listaPartidasFromTodayListener()
        viewModel.errorFromTodayMatches()
        viewModel.listaPartidasPrimeiraRodada()
        viewModel.listaPartidasSegundaRodada()
        viewModel.listaPartidasTerceiraRodada()
        viewModel.listaTodasPartidasListener()
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



    fun setSpinnerAdapter(adapterSpinnerJogos: ArrayAdapter<String>) {
        binding.tvSpinner.setAdapter(adapterSpinnerJogos)

        setJogosSpinnerClickListener()

    }

    private fun setSpinnerGruposAdapter(adapterSpinnerGrupos: ArrayAdapter<String>) {
        binding.tvSpinnerGrupos.setAdapter(adapterSpinnerGrupos)

        setSpinnerGruposClickListener()
    }




    private fun setJogosSpinnerClickListener() {

        binding.tvSpinner.setOnItemClickListener { _, _, pos, _ ->
            Log.d("testecliquespinner", "cliquei em algum item, o spinner esta funcionando")
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
                if(binding.tvSpinnerGrupos.text.toString() == "" || binding.tvSpinnerGrupos.text.toString() == "Todos os Grupos"){
                    findPartidasDaPrimeiraRodada("Todos os Grupos")

                }else{
                    findPartidasDaPrimeiraRodada(binding.tvSpinnerGrupos.text.toString())
                }
                mostrarPartidasDoDia = false
                mostrarTodasPartidas = false
                mostrarPartidasPrimeiraRodada = true
                mostrarPartidasSegundaRodada = false
                mostrarPartidasTerceiraRodada = false
            }


            if (pos == 2){
                createSpinnerGruposList()
                showSpinnerGrupos()
                clearRecycleView()
                if(binding.tvSpinnerGrupos.text.toString() == "" || binding.tvSpinnerGrupos.text.toString() == "Todos os Grupos"){
                    findPartidasDaSegundaRodada("Todos os Grupos")

                }else{
                    findPartidasDaSegundaRodada(binding.tvSpinnerGrupos.text.toString())
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
                if(binding.tvSpinnerGrupos.text.toString() == "" || binding.tvSpinnerGrupos.text.toString() == "Todos os Grupos"){
                    findPartidasDaTerceiraRodada("Todos os Grupos")

                }else{
                    findPartidasDaTerceiraRodada(binding.tvSpinnerGrupos.text.toString())
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
        binding.rvMatches.adapter = PartidasAdapter(emptyList())
    }

    private fun setSpinnerGruposClickListener() {
        binding.tvSpinnerGrupos.setOnItemClickListener { _, _, _, _ ->
            dealWithSpinnerAfterReturningFromAnotherFragment()
            if (mostrarPartidasPrimeiraRodada){
                clearRecycleView()
                when(binding.tvSpinnerGrupos.text.toString()){
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
                when(binding.tvSpinnerGrupos.text.toString()){
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
                when(binding.tvSpinnerGrupos.text.toString()){
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




    private fun setupMatchesRefresh() {
        binding.srlMatches.setOnRefreshListener {
            dealWithDataUpdateOnRefresh()

        }
    }

    private fun dealWithDataUpdateOnRefresh() {


        dealWithSpinnerAfterReturningFromAnotherFragment()
        if(mostrarTodasPartidas){
            findMatchesFromApi()
        }
        if (mostrarPartidasDoDia){
            findTodayMatchesFromApi()
        }
        if (mostrarPartidasPrimeiraRodada){
            if(binding.tvSpinnerGrupos.text.toString() == ""){
                findPartidasDaPrimeiraRodada("Todos os Grupos")
            }else{
                findPartidasDaPrimeiraRodada(binding.tvSpinnerGrupos.text.toString())
            }

        }
        if (mostrarPartidasSegundaRodada){
            if(binding.tvSpinnerGrupos.text.toString() == ""){
                findPartidasDaSegundaRodada("Todos os Grupos")
            }else{
                findPartidasDaSegundaRodada(binding.tvSpinnerGrupos.text.toString())

            }
        }
        if (mostrarPartidasTerceiraRodada){
            if(binding.tvSpinnerGrupos.text.toString() == ""){
                findPartidasDaTerceiraRodada("Todos os Grupos")
            }else{
                findPartidasDaTerceiraRodada(binding.tvSpinnerGrupos.text.toString())

            }
        }

    }

    private fun setupMatchesList() {
        binding.rvMatches.layoutManager = linearLayoutManager
        binding.rvMatches.adapter = adapter

    }







    private fun findTodayMatchesFromApi(){
        turnOffNoMatchesTextView()
        binding.srlMatches.isRefreshing = true
        viewModel.findTodayMatchesFromApi()

    }

    private fun turnOnNoMatchesTextView(){
        binding.tvNaoTemJogo.visibility = View.VISIBLE
    }

    private fun turnOffNoMatchesTextView(){
        binding.tvNaoTemJogo.visibility = View.GONE
    }






    override fun onStop() {
        super.onStop()
        if(lastPosition != null){


            viewModel.setPosition(lastPosition!!)
        }else{
            Log.d("ciclofragment", "last position é null, por isso que ele n enviou as informacoes para o viewModel")
        }


    }



    private fun findPartidasDaPrimeiraRodada(grupo: String) {
        turnOffNoMatchesTextView()
        binding.srlMatches.isRefreshing = true
        viewModel.findPartidasDaPrimeiraRodada(grupo)
    }

    private fun findPartidasDaSegundaRodada(grupo: String) {
        turnOffNoMatchesTextView()
        binding.srlMatches.isRefreshing = true
        viewModel.findPartidasDaSegundaRodada(grupo)
    }

    override fun onResume() {
        super.onResume()





        ifLandscapeModeHideToolBar()
        createSpinnerList()
        createSpinnerGruposList()
        binding.tvSpinner.setSelection(0)
        dealWithSpinnerAfterReturningFromAnotherFragment()


        lastPosition = viewModel.getCurrentPosition()


    }

    private fun ifLandscapeModeHideToolBar(){
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            activity?.findViewById<Toolbar>(R.id.toolbar)?.visibility = View.GONE

            binding.rvMatches.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)




                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    lastPosition = linearLayoutManager.findFirstVisibleItemPosition()
                    Log.d("testeposition", "Acabei de colocar o valor de lastposition aqui no scrollstate changed: $lastPosition")



                    val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                    if(newState == RecyclerView.SCROLL_STATE_IDLE){
                        bottomNav?.visibility = View.VISIBLE


                    }
                    if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                        bottomNav?.visibility = View.GONE
                    }
                }
            })


        }else{
            binding.rvMatches.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)




                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    lastPosition =
                        (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
                    //o onscroll state changed ta mudando quando eu scrollo os jogos de hj
                    Log.d("testeposition", "Acabei de colocar o valor de lastposition aqui no scrollstate changed: $lastPosition")
                    




                    if(newState == RecyclerView.SCROLL_STATE_IDLE){



                    }
                    if(newState == RecyclerView.SCROLL_STATE_DRAGGING){

                    }
                }
            })
        }
    }

    private fun dealWithSpinnerAfterReturningFromAnotherFragment() {
        if(binding.tvSpinner.text.toString() != ""){
            if (binding.tvSpinner.text.toString() == "Jogos de Hoje"){
                findTodayMatchesFromApi()
                hideSpinnerGrupos()
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
                    showSpinnerGrupos()
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
                    showSpinnerGrupos()
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
                    showSpinnerGrupos()
                    Log.d("entendendo", "to no jogos da terceira rodada de todos os grupos")
                }
            }

        }else{
            //significa que ele esta vazio, se ele esta vazio, ele tem que mostrar os jogos do dia
            findTodayMatchesFromApi()
            hideSpinnerGrupos()
        }

    }

    private fun findPartidasDaTerceiraRodada(grupo: String) {
        turnOffNoMatchesTextView()
        binding.srlMatches.isRefreshing = true
        viewModel.findPartidasDaTerceiraRodada(grupo)
    }



    private fun findMatchesFromApi() {
        binding.srlMatches.isRefreshing = true
        viewModel.findMatchesFromApi()
    }

    private fun showErrorMessage(s: String) {
        Toast.makeText(requireActivity(), s, Toast.LENGTH_LONG).show()
        binding.srlMatches.isRefreshing = false

    }

}