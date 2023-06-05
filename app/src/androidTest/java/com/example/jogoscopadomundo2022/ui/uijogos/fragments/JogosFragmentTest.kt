package com.example.jogoscopadomundo2022.ui.uijogos.fragments

import android.content.Intent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith
import com.example.jogoscopadomundo2022.R
import org.junit.Test
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import com.example.jogoscopadomundo2022.ui.MainActivity
import org.hamcrest.CoreMatchers.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher


@RunWith(AndroidJUnit4::class)
class JogosFragmentTest{

    private lateinit var scenario: FragmentScenario<JogosFragment>
    private lateinit var recyclerView: RecyclerView

    private val spinnerOptions = arrayOf("Opção 1", "Opção 2", "Opção 3")

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
        scenario = launchFragmentInContainer<JogosFragment>(themeResId = R.style.Theme_JogosCopaDoMundo2022)

        scenario.onFragment{
            //recyclerView = it.view?.findViewById(R.id.rvMatches)!!
            //it.setSpinnerAdapter(ArrayAdapter(it.requireContext(), R.layout.drop_down_item, spinnerOptions))
        }
    }

    @Test
    fun testSpinnerSelection(){
        onView(withId(R.id.tv_spinner)).perform(click())
        Thread.sleep(1000) // espera 1 segundo
        onData(anything()).atPosition(0).perform(click())


    }


}