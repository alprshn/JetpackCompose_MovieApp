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
        // Başlangıç ekranının SplashScreen olduğunu doğrula
        composeTestRule.onNodeWithContentDescription("SplashScreen").assertExists()

        // SplashScreen'in sona erdiğinde LoginScreen'e geçiş yapıldığını doğrula
        composeTestRule.onNodeWithText("Login").assertExists()

        // LoginScreen'deki butona tıklayarak SignUpScreen'e git
        composeTestRule.onNodeWithText("Sign Up").performClick()

        // SignUpScreen'e geçildiğini doğrula
        composeTestRule.onNodeWithText("Create Account").assertExists()
    }

    @Test
    fun testBottomNavigationVisibility() {
        // Uygulama başlatıldığında SplashScreen'den FavoritesScreen'e navigasyon yap
        composeTestRule.onNodeWithText("Login").performClick() // Login ekranından bir giriş yapılır
        composeTestRule.onNodeWithText("Sign Up").performClick() // SignUp ekranına geçilir
        composeTestRule.onNodeWithText("Create Account").performClick() // Favoriler ekranına geçiş yapılır

        // Bottom Navigation'ın FavoritesScreen'de göründüğünü doğrula
        composeTestRule.onNodeWithContentDescription("Favorites").assertExists()
        composeTestRule.onNodeWithContentDescription("Search").assertExists()
        composeTestRule.onNodeWithContentDescription("WatchList").assertExists()
    }

    @Test
    fun testSettingsScreenNavigation() {
        // Login ekranından SettingsScreen'e navigasyon yap
        composeTestRule.onNodeWithText("Login").performClick()
        composeTestRule.onNodeWithText("Settings").performClick()

        // SettingsScreen'de olduğumuzu doğrula
        composeTestRule.onNodeWithText("Settings").assertExists()
    }
}