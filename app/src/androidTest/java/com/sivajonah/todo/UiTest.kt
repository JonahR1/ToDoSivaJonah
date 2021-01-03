package com.sivajonah.todo

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sivajonah.todo.authentication.AuthenticationActivity
import com.sivajonah.todo.authentication.LoginFragment
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UiTest {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var nom_prenom: String
    private lateinit var titre: String
    private lateinit var description: String
    private lateinit var titreBis: String

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var activityRule: ActivityScenarioRule<AuthenticationActivity>
            = ActivityScenarioRule(AuthenticationActivity::class.java)

    @Before
    fun initValidString() {
        // Specify a valid string.
        email = "js@js.com"
        password = "Js123_"
        nom_prenom = "John Smith"

        titre = "Test UI"
        description = "Ceci est un test UI"

        titreBis = "Test UI edite"
    }

    @Test
    fun A_login() {

        /*launchFragmentInContainer<AuthenticationFragment>()
        onView(withId(R.id.login_auth)).perform(click())*/

        launchFragmentInContainer<LoginFragment>()

        onView(withId(R.id.email))
            .perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.password))
            .perform(typeText(password), closeSoftKeyboard())

        onView(withId(R.id.login)).perform(click())

        Thread.sleep(5000)

        onView(withId(R.id.textView))
            .check(matches(withText(nom_prenom)))
    }

    @Test
    fun B_add() {
        onView(withId(R.id.floatingActionButton)).perform(click())

        onView(withId(R.id.titre))
            .perform(typeText(titre), closeSoftKeyboard())

        onView(withId(R.id.description))
            .perform(typeText(description), closeSoftKeyboard())

        onView(withId(R.id.valider)).perform(click())

        Thread.sleep(5000)

        onView(withId(R.id.recycler_view))
            .check(matches(atPosition(1, hasDescendant(withText(titre)))));
    }

    @Test
    fun C_edit() {
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(1,clickItemWithId(R.id.editButton)))

        onView(withId(R.id.titre))
            .perform(replaceText(titreBis), closeSoftKeyboard())

        Thread.sleep(500)

        onView(withId(R.id.valider)).perform(click())

        Thread.sleep(5000)

        onView(withId(R.id.recycler_view))
            .check(matches(atPosition(1, hasDescendant(withText(titreBis)))));
    }

    @Test
    fun D_delete() {
        Thread.sleep(1000)

        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(1,clickItemWithId(R.id.imageButton)))

        Thread.sleep(1000)

        onView(withId(R.id.recycler_view))
            .check(matches(not(atPosition(1, hasDescendant(withText(titreBis))))));

        //onView(withId(R.id.recycler_view)).check(matches(not(hasItem(hasDescendant(withText(titre))))));

    }

    @Test
    fun E_disconnect() {
        onView(withId(R.id.disconnectButton)).perform(click())

        Thread.sleep(5000)

        onView(withId(R.id.nav_host_fragment))
            .check(matches(isDisplayed()))
    }

    fun clickItemWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as View
                v.performClick()
            }
        }
    }

    fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?>? {
        checkNotNull(itemMatcher)
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}