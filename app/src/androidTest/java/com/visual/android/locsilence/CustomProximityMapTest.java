package com.visual.android.locsilence;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Thomas Brochard on 12/3/2017.
 */

public class CustomProximityMapTest {



    Location mockLocation = new Location("id", "name", "address", 1, 1,
            "createdAt", "updatedAt", "circleId");

    @Rule
    public ActivityTestRule<CustomProximityMap> CustomProximityMapTestRule =
            new ActivityTestRule<CustomProximityMap>(CustomProximityMap.class){
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, CustomProximityMap.class);
                    result.putExtra("selectedLocation", mockLocation);
                    return result;
                }
            };

    private CustomProximityMap mActivity = null;

    @Before
    public void setUp() throws Exception{
        mActivity = CustomProximityMapTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mActivity.findViewById(R.id.layout_customProximityMap);
        assertNotNull(view);
    }

    @After
    public void tearDown(){
        mActivity = null;
    }


}
