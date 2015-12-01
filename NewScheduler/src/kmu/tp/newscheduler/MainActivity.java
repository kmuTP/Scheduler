package kmu.tp.newscheduler;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
		
		final DBManager dbManager = new DBManager(getApplicationContext(),"schedule.db",null,1);
		SQLiteDatabase db = dbManager.getReadableDatabase();
		//스케줄 목록 호출
		Cursor scheduleLists = db.rawQuery("select * from schedule order by favorite desc, no desc",null);
		
		//목록이 있는지 확인한다.
		if(scheduleLists.getCount() == 0) 
		{
			
		}
		else
		{
			while(scheduleLists.moveToNext())
			{
				
			}
		}
		
		
		Button addMenuBtn = (Button) findViewById(R.id.addSchedule);
		addMenuBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				//일정 추가 View Load
			}
		});
		Button delMenuBtn = (Button) findViewById(R.id.delSchedule);
		addMenuBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				//일정 삭제 View Load
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
