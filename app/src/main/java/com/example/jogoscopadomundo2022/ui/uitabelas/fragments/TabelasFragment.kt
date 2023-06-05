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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jogoscopadomundo2022.R
import com.example.jogoscopadomundo2022.data.tabelas.TabelasApi
import com.example.jogoscopadomundo2022.data.tabelas.TabelasApi.Companion.BASE_URL
import com.example.jogoscopadomundo2022.databinding.FragmentTabelasBinding
import com.example.jogoscopadomundo2022.data.apitabelas.Tabelas
import com.example.jogoscopadomundo2022.ui.uitabelas.adapters.TabelasAdapter
import com.example.jogoscopadomundo2022.ui.uitabelas.interfaces.ContextProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class TabelasFragment: Fragment() {


    private lateinit var binding: FragmentTabelasBinding
    private var tabelasApi: TabelasApi? = null
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
        createSpinnerList()
        setupHttpClient()
        setupTabelasList()
        setupTabelasRefresh()
        
        
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

    private fun setupHttpClient() {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        tabelasApi = retrofit.create(TabelasApi::class.java)
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
        tabelasApi!!.getTabelas().enqueue(object: Callback<List<Tabelas>> {
            override fun onResponse(call: Call<List<Tabelas>>, response: Response<List<Tabelas>>) {
                Log.d("onresponse", "to no onresponse" + response.code())
                if (response.isSuccessful()) {
                    val listaTabelas: MutableList<Tabelas> = mutableListOf()
                    when(posicaoSpinner){
                        //grupo A
                        0->{
                            response.body()?.forEach {
                                if(it.grupo == "A"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo B
                        1->{
                            response.body()?.forEach {
                                if(it.grupo == "B"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo C
                        2->{
                            response.body()?.forEach {
                                if(it.grupo == "C"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo D
                        3->{
                            response.body()?.forEach {
                                if(it.grupo == "D"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo E
                        4->{
                            response.body()?.forEach {
                                if(it.grupo == "E"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo F
                        5->{
                            response.body()?.forEach {
                                if(it.grupo == "F"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo G
                        6->{
                            response.body()?.forEach {
                                if(it.grupo == "G"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //grupo H
                        7->{
                            response.body()?.forEach {
                                if(it.grupo == "H"){
                                    listaTabelas.add(it)
                                }
                            }
                        }
                        //Todos os grupos
                        8->{
                            response.body()?.forEach {
                                listaTabelas.add(it)

                            }
                        }
                    }


                    adapter = TabelasAdapter(object: ContextProvider{
                        override fun getContext(): Context = requireContext()

                        override fun getActivity(): Activity = requireActivity()

                    },listaTabelas)
                    binding.rvTabelas.layoutManager = LinearLayoutManager(requireActivity())
                    binding.rvTabelas.adapter = adapter
                } else {
                    showErrorMessage()
                }
                binding.srlMatches.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Tabelas>>, t: Throwable) {
                Log.d("onfailure", "deu erro" + t.message.toString())
                Toast.makeText(requireActivity(), "ocorreu um erro ao carregar os dados, verifique sua conexão com a internet e tente novamente!", Toast.LENGTH_LONG).show()
                binding.srlMatches.isRefreshing = false

            }

        })
    }

    private fun showErrorMessage() {
        Toast.makeText(requireActivity(), "ocorreu um erro ao carregar os dados, verifique sua conexão com a internet e tente novamente!", Toast.LENGTH_LONG).show()
    }
}