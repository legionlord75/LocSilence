package com.visual.android.locsilence;

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

public class SettingsActivityTest {

    @Rule
    public ActivityTestRule<SettingsActivity> SettingsActivityTestRule =
            new ActivityTestRule<SettingsActivity>(SettingsActivity.class);

    private SettingsActivity mSettingsActivity = null;

    @Before
    public void setUp() throws Exception {
        mSettingsActivity = SettingsActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = mSettingsActivity.findViewById(R.id.layout_settings);
        //assertNotNull(view);
    }

    @After
    public void tearDown() {
        mSettingsActivity = null;
    }

}


