package com.example.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText

@RunWith(AndroidJUnit4::class)
class MainActivityUiTest {

    // изначаль
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loginButton_updatesTextView_onValidInput() {
        // Arrange
        val textToType = "Espresso Test"

        // Act
        // Находим EditText  вводим в него текст
        onView(withId(R.id.editTextLogin)).perform(typeText(textToType))

        // Находим кнопку, и нажимаем на нее
        onView(withId(R.id.buttonLogin)).perform(click())

        // Assert
        // Находим TextView и проверяем что его текст совпадает с введенным
        onView(withId(R.id.textViewDisplay)).check(matches(withText(textToType)))
    }
}