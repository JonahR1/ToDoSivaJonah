//package com.sivajonah.todo.authentication
//
//
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions.*
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.test.espresso.matcher.ViewMatchers.withText
//import androidx.test.ext.junit.rules.ActivityScenarioRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.filters.LargeTest
//import com.sivajonah.todo.R
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//
//class AuthenticationTest {
//    @get:Rule
//    var activityRule: ActivityScenarioRule<AuthenticationActivity>
//            = ActivityScenarioRule(AuthenticationActivity::class.java)
//
//
//    private lateinit var email: String
//    private lateinit var password: String
//    private lateinit var nom_prenom: String
//
//    @Before
//    fun initValidString() {
//        // Specify a valid string.
//        email = "js@js.com"
//        password = "Js123_"
//        nom_prenom = "John Smith"
//    }
//
//    @Test
//    fun login() = runBlockingTest {
//        onView(withId(R.id.login_auth)).perform(click())
//
//        onView(withId(R.id.email))
//            .perform(typeText(email), closeSoftKeyboard())
//        onView(withId(R.id.password))
//            .perform(typeText(password), closeSoftKeyboard())
//
//        onView(withId(R.id.login)).perform(click())
//
//        onView(withId(R.id.textView))
//            .check(matches(withText(nom_prenom)))
//    }
//}