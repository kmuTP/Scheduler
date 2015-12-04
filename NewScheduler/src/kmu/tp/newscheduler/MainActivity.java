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
import android.view.View.OnClickListener;
import kmu.tp.newscheduler.GlobalVariable;

import kmu.tp.newscheduler.GlobalVariable;

public class MainActivity extends Activity {
	
	private int[] colorList = {0xffFF9F94,0xffFFD294,0xffFBFF94,0xffAFFF94,0xff94FFC8,0xff94E6FF,0xff9F94FF,0xffEA94FF};
	private int colorsize = colorList.length;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final DBManager dbManager = new DBManager(getApplicationContext(),"schedule.db",null,1);
		SQLiteDatabase db = dbManager.getReadableDatabase();
		
		db.execSQL("insert into schedule(subject,startdate,enddate,content,favorite) values('세희바보','2015-12-21','2016-03-01','신나게놀기','0');");
		//스케줄 목록 호출
		Cursor scheduleLists = db.rawQuery("select * from schedule order by favorite desc, no desc",null);
		RelativeLayout container = (RelativeLayout) findViewById(R.id.scheduleListView);
		//목록이 있는지 확인한다.
		if(scheduleLists.getCount() == 0) 
		{	
			//TextView 생성
			TextView noSchedule = new TextView(this);
			noSchedule.setTextSize(14);
			noSchedule.setTextColor(Color.BLACK);
			noSchedule.setGravity(Gravity.CENTER_HORIZONTAL);
			noSchedule.setText("일정이 없습니다. 지금 일정을 추가해 보세요!");
			
			container.addView(noSchedule);
		}
		else
		{
			
			int i=0;
			if(scheduleLists.moveToFirst())
			{
				while(scheduleLists.moveToNext())
				{
					final int no = scheduleLists.getInt(0);
					String Subject = scheduleLists.getString(1);
					String StartDate = scheduleLists.getString(2);
					String EndDate = scheduleLists.getString(3);
					int isFavorited = scheduleLists.getInt(5);	//0 : Off, 1 : On
					TextView Schedules = new TextView(this);
					Schedules.setTextSize(16);
					Schedules.setId(i++);
					Schedules.setTextColor(Color.BLACK);
					Schedules.setPadding(0, 50, 0, 50);
					Schedules.setBackgroundColor(colorList[i%colorsize]);
					Schedules.setGravity(Gravity.CENTER_HORIZONTAL);
					if(i>1)
					{
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,350);
						params.addRule(RelativeLayout.BELOW,i);
						//params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,i-1);
						Schedules.setLayoutParams(params);
					}
					else
					{
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,350);
						params.addRule(RelativeLayout.ALIGN_START);
						Schedules.setLayoutParams(params);
					}
					//시작일과 종료일, 그리고 제목만 간단하게 표시한다.
					Schedules.setText("제목 : "+Subject+"\n시작일 : "+StartDate+"\n종료일 : "+EndDate);
					
					container.addView(Schedules);
					
					Schedules.setOnClickListener(new OnClickListener()
					{
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									GlobalVariable gb = (GlobalVariable)getApplicationContext();
									gb.detailNum = no;
									
									Intent intent = new Intent(MainActivity.this, DetailSchedule.class);
									MainActivity.this.startActivity(intent);
								}
					});
				}
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
