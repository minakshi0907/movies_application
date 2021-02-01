package com.example.moviesapplication.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.moviesapplication.R
import com.example.moviesapplication.data.FakeMovieData
import androidx.test.espresso.contrib.RecyclerViewActions.*
import com.example.moviesapplication.ui.adapter.MoviesListPagedAdapter
import com.example.moviesapplication.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MoviesActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MoviesActivity::class.java)

    val LIST_ITEM_IN_TEST = 3
    val MOVIE_IN_TEST = FakeMovieData.movies[LIST_ITEM_IN_TEST]


    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun test_isListVisible_onAppLaunch() {
        onView(withId(R.id.moviesRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isMovieDetailActivityVisible() {
        // Click list item #LIST_ITEM_IN_TEST
        onView(withId(R.id.moviesRecyclerView))
            .perform(actionOnItemAtPosition<MoviesListPagedAdapter.MovieItemViewHolder>(LIST_ITEM_IN_TEST, click()))

        // Confirm nav to MovieDetailActivity and display title
        onView(withId(R.id.movietitle)).check(matches(withText(MOVIE_IN_TEST.title)))
    }

    @Test
    fun test_backNavigation_toMovieListFragment() {
        // Click list item #LIST_ITEM_IN_TEST
        onView(withId(R.id.moviesRecyclerView))
            .perform(actionOnItemAtPosition<MoviesListPagedAdapter.MovieItemViewHolder>(LIST_ITEM_IN_TEST, click()))

        // Confirm nav to MovieDetailActivity and display title
        onView(withId(R.id.movietitle)).check(matches(withText(MOVIE_IN_TEST.title)))

        pressBack()

        // Confirm MovieListActivity in view
        onView(withId(R.id.moviesRecyclerView)).check(matches(isDisplayed()))
    }

}