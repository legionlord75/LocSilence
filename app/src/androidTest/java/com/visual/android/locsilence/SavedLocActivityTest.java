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

public class SavedLocActivityTest {

    @Rule
    public ActivityTestRule<SavedLocActivity> SavedLocActivityTestRule = new ActivityTestRule<SavedLocActivity>(SavedLocActivity.class);

    private SavedLocActivity mActivity = null;

    @Before
    public void setUp() throws Exception{
        mActivity = SavedLocActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mActivity.findViewById(R.id.listview_savedLocs);
        assertNotNull(view);
    }

    @After
    public void tearDown(){
        mActivity = null;
    }

}
