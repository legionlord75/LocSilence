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

public class MapsActivityTest {


    @Rule
    public ActivityTestRule<MapsActivity> mapsActivityTestRule = new ActivityTestRule<MapsActivity>(MapsActivity.class);

    private MapsActivity mActivity = null;

    @Before
    public void setUp() throws Exception{
        mActivity = mapsActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mActivity.findViewById(R.id.layout_maps);
        assertNotNull(view);
    }

    @After
    public void tearDown(){
        mActivity = null;
    }

}
