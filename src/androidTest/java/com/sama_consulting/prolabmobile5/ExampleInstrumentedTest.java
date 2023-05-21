package com.sama_consulting.prolabmobile5;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.sama_consulting.prolabmobile5", appContext.getPackageName());
        assertEquals("sa",Decripter("iW"));
    }
    public String Decripter(String str ) {
        String ss;
        if (str.isEmpty())
            return str;


        ss = "";
        for( int i = 0;i< str.length();i++) {
            char c = str.charAt(i);
            c=(char)((int)c+10);
            ss = ss.concat(Character.toString(c));
        }

        return ss;
    }
}
