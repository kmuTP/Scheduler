package kmu.tp.newscheduler.test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import kmu.tp.newscheduler.MainActivity;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest() {
		super(MainActivity.class);
	}

	MainActivity activity;

	protected void setUP() throws Exception {
		super.setUp();
		setActivityInitialTouchMode( false );
		activity = getActivity();
		Log.d("MainActivityTest", "setUp()");
	}

	public void testFail() {

		fail("check Android JUnit Env. Setup");

	}
	protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
        Log.d( "MyClassTest", "tearDown()" );
    }

	public void testFunc1() {
        Log.d( "MyClassTest", "testFunc1()" );
    }


}
