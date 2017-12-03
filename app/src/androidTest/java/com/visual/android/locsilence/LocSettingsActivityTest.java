package com.visual.android.locsilence;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Thomas Brochard on 12/3/2017.
 */

public class LocSettingsActivityTest {

    Location mockLocation = new Location("id", "name", "address", 1, 1,
            "createdAt", "updatedAt", "circleId");

    @Rule
    public ActivityTestRule<LocSettingsActivity> LocSettingsTestRule =
            new ActivityTestRule<LocSettingsActivity>(LocSettingsActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, CustomProximityMap.class);
                    result.putExtra("selectedLocation", mockLocation);
                    return result;
                }
            };

    private LocSettingsActivity mLocSettings = null;

    @Before
    public void setUp() throws Exception{
        mLocSettings = LocSettingsTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mLocSettings.findViewById(R.id.settings_listview);
        assertNotNull(view);
    }

    @After
    public void tearDown(){
        mLocSettings = null;
    }

}
