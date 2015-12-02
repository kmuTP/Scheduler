package kmu.tp.newscheduler;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.widget.*;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
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
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);

		
		final DBManager dbManager = new DBManager(getApplicationContext(),"schedule.db",null,1);
		SQLiteDatabase db = dbManager.getReadableDatabase();
		//������ ��� ȣ��
		Cursor scheduleLists = db.rawQuery("select * from schedule order by favorite desc, no desc",null);
		RelativeLayout container = (RelativeLayout) findViewById(R.id.scheduleListView);
		
		//����� �ִ��� Ȯ���Ѵ�.
		if(scheduleLists.getCount() == 0) 
		{	
			//TextView ����
			TextView noSchedule = new TextView(this);
			noSchedule.setTextSize(14);
			noSchedule.setTextColor(Color.BLACK);
			noSchedule.setGravity(Gravity.CENTER_HORIZONTAL);
			noSchedule.setText("������ �����ϴ�. ���� ������ �߰��� ������!");
			
			container.addView(noSchedule);
		}
		else
		{
			for(boolean Lists = scheduleLists.moveToFirst(); Lists; Lists=scheduleLists.moveToNext())
			{
				String Subject = scheduleLists.getString(1);
				String StartDate = scheduleLists.getString(2);
				String EndDate = scheduleLists.getString(3);
				int isFavorited = scheduleLists.getInt(5);	//0 : Off, 1 : On
				
				TextView Schedules = new TextView(this);
				Schedules.setTextSize(16);
				Schedules.setTextColor(Color.BLACK);
				Schedules.setGravity(Gravity.CENTER_HORIZONTAL);
				//�����ϰ� ������, �׸��� ���� �����ϰ� ǥ���Ѵ�.
				Schedules.setText("���� : "+Subject+"\n������ : "+StartDate+"\n������ : "+EndDate);
			}
		}
		
		
		Button addMenuBtn = (Button) findViewById(R.id.addSchedule);
		addMenuBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				Intent intent = new Intent(MainActivity.this, AddSchedule.class);
				MainActivity.this.startActivity(intent);
			}
		});
		Button delMenuBtn = (Button) findViewById(R.id.delSchedule);
		delMenuBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				//���� ���� View Load
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
