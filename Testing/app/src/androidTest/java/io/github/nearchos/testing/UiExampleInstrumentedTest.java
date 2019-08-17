package io.github.nearchos.testing;

import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.nearchos.testing.model.Recipe;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.startsWith;

/**
 * Instrumented test, which executes on an Android device.
 *
 * It demonstrates an integration test by testing the UI functionality.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 * @see <a href="https://developer.android.com/training/testing/espresso">Testing UI with Espresso</a>
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class UiExampleInstrumentedTest {

    private String textToBeTyped;

    @Before
    public void before() {
        // initialize the string to be saved in the database (as recipe title)
        textToBeTyped = "UiExampleTitle-" + System.currentTimeMillis();
    }

    @Rule
    public ActivityTestRule<MainActivity> activityRule = // launch the MainActivity to prepare for testing
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkAddRecipe() {

        // ensures the ListView in the Main activity is displayed
        onView(withId(R.id.recipesListView)) // select MainActivity's ListView
                .check(matches(isDisplayed())); // check it is displayed

        // this executes in {@link MainActivity}
        onView(withId(R.id.fabAddRecipe)) // select MainActivity's FAB
                .perform(click()); // click the FAB button to trigger adding a new recipe

        // ensures the title TextView in the EditRecipeActivity is displayed
        onView(withId(R.id.titleLabelTextView)) // select EditRecipeActivity's title TextView
                .check(matches(isDisplayed())); // check it is displayed

        // this executes in {@link EditRecipeActivity}
        onView(withId(R.id.fabAddIngredient)).perform(click());
    }

    @Test
    public void checkEditRecipeTitle() {

        // first move on to the EditRecipeActivity
        onView(withId(R.id.fabAddRecipe)) // select MainActivity's FAB
                .perform(click()); // click the FAB button to trigger adding a new recipe

        // ensures the title EditText in the EditRecipeActivity is displayed and adds text to it
        onView(withId(R.id.nameEditText)) // select EditRecipeActivity's title EditText
                .perform(typeText(textToBeTyped)); // set its text

        // first make sure soft keyboard is closed (or it might 'absorb' the back-press next)
        closeSoftKeyboard();
        // trigger 'back' button - this should cause the edited recipe to be saved
        pressBack();

        // confirm that an item with the specified recipe name was added
        onData(withName(textToBeTyped)) // find the item with the specified recipe name...
                .check(matches(withText(startsWith(textToBeTyped)))); // ... and verify it 'exists'

        // finally, delete the newly added item
        onData(withName(textToBeTyped)) // find the item with the specified recipe name...
                .perform(longClick()); // ... and delete it with a long press

        try { Thread.sleep(5000); } catch (InterruptedException ie) {}

    }

    public static Matcher<Recipe> withName(String name) {
        return new TypeSafeMatcher<Recipe>() {
            @Override
            public boolean matchesSafely(Recipe recipe) {
                return name.equals(recipe.getName());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("checks if name matches");
            }
        };
    }
}