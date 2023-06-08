package com.example.jogoscopadomundo2022.ui.uijogos.fragments

import android.content.Intent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith
import com.example.jogoscopadomundo2022.R
import org.junit.Test
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import com.example.jogoscopadomundo2022.ui.MainActivity
import org.hamcrest.CoreMatchers.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.jogoscopadomundo2022.launchFragmentInHiltContainer
import com.example.jogoscopadomundo2022.ui.uijogos.adapters.PartidasAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matcher


@RunWith(AndroidJUnit4ClassRunner::class)
class JogosFragmentTest{


    lateinit var navController: TestNavHostController


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup(){
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        launchFragmentInHiltContainer<JogosFragment>(null, R.style.Theme_JogosCopaDoMundo2022){

            navController.setGraph(R.navigation.main_navigation)

            Navigation.setViewNavController(this.requireView(), navController)
        }



    }

    @Test
    fun fragmentIsVisible(){


        assertThat(navController.currentDestination?.id, `is`(R.id.jogosFragment))


    }

    @Test
    fun testSpinnerSelectionPrimeiraRodada(){
        onView(withId(R.id.tv_spinner)).perform(click())
        onView(withText("1ª Rodada")).inRoot(RootMatchers.isPlatformPopup()).perform(click())
        onView(withId(R.id.tv_spinner)).check(matches(withText("1ª Rodada")))


    }

    @Test
    fun testSpinnerSelectionSegundaRodada(){
        onView(withId(R.id.tv_spinner)).perform(click())
        onView(withText("2ª Rodada")).inRoot(RootMatchers.isPlatformPopup()).perform(click())
        onView(withId(R.id.tv_spinner)).check(matches(withText("2ª Rodada")))


    }

    @Test
    fun testSpinnerSelectionTerceiraRodada(){
        onView(withId(R.id.tv_spinner)).perform(click())
        onView(withText("3ª Rodada")).inRoot(RootMatchers.isPlatformPopup()).perform(click())
        onView(withId(R.id.tv_spinner)).check(matches(withText("3ª Rodada")))


    }

    @Test
    fun testRecyclerViewItemClick(){
        onView(withId(R.id.tv_spinner)).perform(click())
        onView(withText("1ª Rodada")).inRoot(RootMatchers.isPlatformPopup()).perform(click())
        onView(withId(R.id.tv_spinner)).check(matches(withText("1ª Rodada")))

        onView(withId(R.id.rvMatches)).perform(actionOnItemAtPosition<PartidasAdapter.ViewHolder>(1, click()))

        assertThat(navController.currentDestination?.id, `is`(R.id.detalhesJogoFragment))

    }


}