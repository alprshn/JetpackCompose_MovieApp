package com.example.movieapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.movieapp.movieList.presentation.components.MovieLoginText
import org.junit.Rule
import org.junit.Test

class NewTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testSplashScreenNavigation() {

        composeTestRule.onNodeWithContentDescription("SplashScreen").assertExists()


        composeTestRule.onNodeWithText("Login").assertExists()


        composeTestRule.onNodeWithText("Sign Up").performClick()

        composeTestRule.onNodeWithText("Create Account").assertExists()
    }

    @Test
    fun testBottomNavigationVisibility() {

        composeTestRule.onNodeWithText("Login").performClick()
        composeTestRule.onNodeWithText("Sign Up").performClick()
        composeTestRule.onNodeWithText("Create Account").performClick()

        composeTestRule.onNodeWithContentDescription("Favorites").assertExists()
        composeTestRule.onNodeWithContentDescription("Search").assertExists()
        composeTestRule.onNodeWithContentDescription("WatchList").assertExists()
    }

    @Test
    fun testSettingsScreenNavigation() {

        composeTestRule.onNodeWithText("Settings").performClick()
        composeTestRule.onNodeWithText("Settings").assertExists()
    }
}