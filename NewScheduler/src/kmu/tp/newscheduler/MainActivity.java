package kmu.tp.newscheduler;

import android.app.Activity;
import android.widget.*;
import android.widget.Button;
import android.view.*;
import android.app.Activity;
import android.app.*;
import android.os.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Button addMenuBtn = (Button) findViewById(R.id.button1);
		
		addMenuBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				Toast.makeText(getApplicationContext(), "±èÁöÇö ¹Ùº¸", Toast.LENGTH_LONG).show();
			}
		});
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
