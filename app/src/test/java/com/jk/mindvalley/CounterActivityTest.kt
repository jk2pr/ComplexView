package com.jk.mindvalley

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jk.mindvalley.ui.main.MainFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CounterActivityTest {
    @Test
    fun testIncrement() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        val fragmentArgs = Bundle()
        val factory = MainFragmentFactory()

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)
        // check initial state
        onView(withId(R.id.channel_recyclerview)).check(matches(withText("0")))
        // perform action
        onView(withId(R.id.up)).perform(click())

        val fragmentScenario= launchFragmentInContainer<MainFragment>(fragmentArgs,factory = factory)
        onView(withId(R.id.text)).check(matches(withText("Hello World!")))
        // check result
        //onView(withId(R.id.counter)).check(matches(withText("1")))
    }
}