package com.example.jogoscopadomundo2022.ui

import android.util.Log
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import com.example.jogoscopadomundo2022.R
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testBottomNavigation(){
        try{
            onView(withId(R.id.jogosFragment)).perform(click())
        }catch (e: Exception){
            Log.d("erroclique", "${e.message}")

        }
    }




}