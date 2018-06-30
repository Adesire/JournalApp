package com.example.android.journalapp;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class JournalActivityTest {
    @Rule
    public ActivityTestRule<JournalActivity> mActivityTestRule =
            new ActivityTestRule<>(JournalActivity.class);

    @Test
    public void journalActivityTests(){
        onView(withId(R.id.titleEditText)).perform(typeText("MyTitle"));closeSoftKeyboard();
        onView(withId(R.id.messageEditText)).perform(typeText("MyBody"));closeSoftKeyboard();
        onView(withId(R.id.save_btn)).perform(click());
    }
}
