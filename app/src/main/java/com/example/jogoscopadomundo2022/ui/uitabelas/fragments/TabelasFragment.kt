package com.example.jogoscopadomundo2022.ui.uitabelas.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jogoscopadomundo2022.R
import com.example.jogoscopadomundo2022.databinding.FragmentTabelasBinding
import com.example.jogoscopadomundo2022.data.apitabelas.Tabelas
import com.example.jogoscopadomundo2022.ui.uitabelas.viewmodels.TabelasFragmentViewModel
import com.example.jogoscopadomundo2022.ui.uitabelas.adapters.TabelasAdapter
import com.example.jogoscopadomundo2022.ui.uitabelas.interfaces.ContextProvider
import java.util.*


class TabelasFragment: Fragment() {


    private lateinit var binding: FragmentTabelasBinding
    private lateinit var tabelasFragmentViewModel: TabelasFragmentViewModel
    private var adapter = TabelasAdapter(object: ContextProvider{
        override fun getContext(): Context {
            return requireContext()
        }

        override fun getActivity(): Activity {
            return requireActivity()
        }

    },Collections.emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTabelasBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        createSpinnerList()
        setupTabelasList()
        setupTabelasRefresh()
        
        
    }



    private fun initViewModel() {
        tabelasFragmentViewModel = ViewModelProvider(requireActivity())[TabelasFragmentViewModel::class.java]
        initViewModelLiveDatas()
        initObservers()
    }

    private fun initViewModelLiveDatas(){
        tabelasFragmentViewModel.tabelasErrorLiveData()
        tabelasFragmentViewModel.tabelasRepositoryListener()
    }

    private fun initObservers() {
        tabelasFragmentViewModel.tabelasLiveDataTest.observe(viewLifecycleOwner){
            Log.d("testeobserve","to aqui no fragment $it")
        }
        tabelasFragmentViewModel.listaTabelasLDViewModel.observe(viewLifecycleOwner){
            listaTabelas->
            setupAdapter(listaTabelas)
        }

        tabelasFragmentViewModel.errorLiveData.observe(viewLifecycleOwner){
            showErrorMessage(it)
        }
    }

    private fun showErrorMessage(it: String?) {
        it?.let{
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()

        }
        binding.srlMatches.isRefreshing = false


    }

    private fun setupAdapter(listaTabelas: MutableList<Tabelas>?) {
        if(listaTabelas != null){
            adapter = TabelasAdapter(object: ContextProvider {
                override fun getContext(): Context = requireContext()

                override fun getActivity(): Activity = requireActivity()

            },listaTabelas)

            binding.rvTabelas.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvTabelas.adapter = adapter
        }

        binding.srlMatches.isRefreshing = false



    }

    private fun createSpinnerList() {
        val listaSpinnerTabelas = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "Todos os Grupos")

        createAdapterDoSpinnerTabelas(listaSpinnerTabelas)
    }

    private fun createAdapterDoSpinnerTabelas(listaSpinnerTabelas: Array<String>) {
        val adapterSpinnerTabelas = ArrayAdapter(
            requireActivity(),
            R.layout.drop_down_item,
            listaSpinnerTabelas
        )

        setSpinnerAdapter(adapterSpinnerTabelas)

    }

    private fun setSpinnerAdapter(adapterSpinnerTabelas: ArrayAdapter<String>) {
        binding.tvSpinnerTabelas.setAdapter(adapterSpinnerTabelas)

        setSpinnerTabelasClickListener()
    }

    private fun setSpinnerTabelasClickListener() {
        binding.tvSpinnerTabelas.setOnItemClickListener { _, _, pos, _ ->
            when(pos){
                0->{
                    callTabelasApi(0)
                }
                //grupo B
                1->{
                    callTabelasApi(1)
                }
                //grupo C
                2->{
                    callTabelasApi(2)

                }
                //grupo D
                3->{
                    callTabelasApi(3)

                }
                //grupo E
                4->{
                    callTabelasApi(4)

                }
                //grupo F
                5->{
                    callTabelasApi(5)

                }
                //grupo G
                6->{
                    callTabelasApi(6)

                }
                //grupo H
                7->{
                    callTabelasApi(7)

                }
                //Todos os grupos
                8->{
                    callTabelasApi(8)

                }
            }

        }
    }



    private fun setupTabelasList() {
        binding.rvTabelas.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvTabelas.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        updateDataAccordingToSpinnerSelectedText()
        createSpinnerList()
        Log.d("ciclofragmenttab", "to no on resume do fragment tabelas")


    }

    override fun onStop() {
        super.onStop()

        Log.d("ciclofragmenttab", "to no on stop do fragment tabelas")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ciclofragmenttab", "to no on destroy do fragment tabelas")
    }

    private fun setupTabelasRefresh() {
        binding.srlMatches.setOnRefreshListener{
            updateDataAccordingToSpinnerSelectedText()
        }
    }

    private fun updateDataAccordingToSpinnerSelectedText() {
        if (binding.tvSpinnerTabelas.text.toString() == ""){
            callTabelasApi(0)
        }else {
            when (binding.tvSpinnerTabelas.text.toString()) {
                //grupo A
                "A" -> {
                    callTabelasApi(0)
                }
                //grupo B
                "B" -> {
                    callTabelasApi(1)
                }
                //grupo C
                "C" -> {
                    callTabelasApi(2)
                }
                //grupo D
                "D" -> {
                    callTabelasApi(3)
                }
                //grupo E
                "E" -> {
                    callTabelasApi(4)
                }
                //grupo F
                "F" -> {
                    callTabelasApi(5)
                }
                //grupo G
                "G" -> {
                    callTabelasApi(6)
                }
                //grupo H
                "H" -> {
                    callTabelasApi(7)
                }
                //Todos os grupos
                "Todos os Grupos" -> {
                    callTabelasApi(8)
                }
            }
        }

    }


    private fun callTabelasApi(posicaoSpinner: Int) {
        binding.srlMatches.isRefreshing = true
        tabelasFragmentViewModel.callTabelasApi(posicaoSpinner)

    }


}