package com.example.jogoscopadomundo2022.ui.uitabelas.interfaces

import android.app.Activity
import android.content.Context

interface ContextProvider {

    fun getContext(): Context

    fun getActivity(): Activity
}