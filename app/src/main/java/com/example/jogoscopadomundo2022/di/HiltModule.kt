package com.example.jogoscopadomundo2022.di

import com.example.jogoscopadomundo2022.data.apijogos.repository.JogosRepository
import com.example.jogoscopadomundo2022.data.apijogos.repository.JogosRepositoryImpl
import com.example.jogoscopadomundo2022.data.apitabelas.repository.TabelasRepository
import com.example.jogoscopadomundo2022.data.apitabelas.repository.TabelasRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    fun providesTabelasRepository(): TabelasRepository{
        return TabelasRepositoryImpl()
    }

    @Provides
    fun providesJogosRepository(): JogosRepository{
        return JogosRepositoryImpl()
    }
}