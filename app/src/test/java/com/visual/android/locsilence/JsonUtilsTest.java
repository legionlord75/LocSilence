package com.visual.android.locsilence;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

/**
 * Created by Thomas Brochard on 12/2/2017.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(android.util.Log.class)
public class JsonUtilsTest {

    final private String validListOfLatLngJson = "[{\"latitude\":1,\"longitude\":1,\"mVersionCode\":0}]";
    final private String validListOfInts = "[0,1,2]";
    final private String invalidJsonString = "(notValidJson)";
    final private String tag = "tag";
    final private String msg = "msg";
    final private int returnCode = 1;


    @Mock
    JsonParseException e;

    @Before
    public void setUp(){
        // Ignore possible debug message by mocking the static Log class
        PowerMockito.mockStatic(android.util.Log.class);
        when(android.util.Log.e(tag,msg)).thenReturn(returnCode);
    }

    /* CustomProxToList tests */

    @Test
    public void valid_customProxToList() {
        ArrayList<LatLng> validListOfProximities = JsonUtils.customProxToList(validListOfLatLngJson);
        assertThat(validListOfProximities, is(notNullValue()));
    }

    @Test
    public void invalid_customProxToList() {
       ArrayList<LatLng> inValidListOfProximities = JsonUtils.customProxToList(invalidJsonString);
       assertThat(inValidListOfProximities, is(nullValue()));
    }

    @Test
    public void null_customProxToList() {
        ArrayList<LatLng> inValidListOfProximities = JsonUtils.customProxToList(null);
        assertThat(inValidListOfProximities, is(nullValue()));
    }

    /* volumeLevelsToList tests */

    @Test
    public void valid_volumeLevelsToList() {
        List<Integer> validListOfProximities = JsonUtils.volumeLevelsToList(validListOfInts);
        assertThat(validListOfProximities, is(notNullValue()));
    }

    @Test
    public void invalid_volumeLevelsToList() {
        List<Integer> inValidListOfProximities = JsonUtils.volumeLevelsToList(invalidJsonString);
        assertThat(inValidListOfProximities, is(nullValue()));
    }

    @Test
    public void null_volumeLevelsToList() {
        List<Integer> inValidListOfProximities = JsonUtils.volumeLevelsToList(null);
        assertThat(inValidListOfProximities, is(nullValue()));
    }


}
