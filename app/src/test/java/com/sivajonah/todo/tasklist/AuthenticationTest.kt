package com.sivajonah.todo.tasklist

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.sivajonah.todo.R
import com.sivajonah.todo.authentication.AuthenticationActivity
import com.sivajonah.todo.authentication.LoginFragment
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock


@RunWith(AndroidJUnit4::class)
@LargeTest
class AuthenticationTest {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var nom_prenom: String

    @get:Rule
    var activityRule: ActivityScenarioRule<AuthenticationActivity>
            = ActivityScenarioRule(AuthenticationActivity::class.java)


    @Before
    fun initValidString() {
        // Specify a valid string.
        email = "js@js.com"
        password = "Js123_"
        nom_prenom = "John Smith"
    }

    @Test
    fun completedTaskDetails_DisplayedInUi() = runBlockingTest{
        launchFragmentInContainer<LoginFragment>()

        onView(withId(R.id.login_auth)).perform(click())

        // Type text and then press the button.
        onView(withId(R.id.email))
                .perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.password))
                .perform(typeText(password), closeSoftKeyboard())

        onView(withId(R.id.login)).perform(click())

        // Check that the text was changed.
        onView(withId(R.id.textView))
                .check(matches(withText(nom_prenom)))
    }
}