package com.example.android.journalapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DisplayJournalActivityTest {

    @Rule
    public ActivityTestRule<DisplayJournalActivity> mActivityTestRule =
            new ActivityTestRule<>(DisplayJournalActivity.class);

    @Test
    public void testFloatingActionButton(){
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.save_btn)).check(matches(isDisplayed()));
    }

    @Test
    public void signOutTest(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Sign-Out")).perform(click());
    }
}
