package com.jk.mindvalley


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun check_all_recycler_view() {
        onView(withId(R.id.new_episode_recyclerView))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(1))


        onView(withId(R.id.channel_recyclerview))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(1))

    }
}
