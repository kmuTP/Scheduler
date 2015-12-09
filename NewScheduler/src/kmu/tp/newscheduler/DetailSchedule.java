package kmu.tp.newscheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class DetailSchedule extends Activity {
	
	public String Subject,StartDate,EndDate,Contents;
	public int no,isFavorited;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_schedule);
		final RatingBar ratings = (RatingBar) findViewById(R.id.todo_btn_setPriority);
		
		/*DB 읽어오기*/
		final DBManager dbManager = new DBManager(getApplicationContext(),"schedule.db",null,1);
		SQLiteDatabase db = dbManager.getReadableDatabase();
		final GlobalVariable gb = (GlobalVariable)getApplicationContext();
		
	
		Cursor detail = db.rawQuery("select * from schedule where no = "+gb.detailNum,null);
		/*DB 처리하기*/
		if(detail.moveToFirst())
		{
			no = detail.getInt(0);
			Subject = detail.getString(1);
			StartDate = detail.getString(2);
			EndDate = detail.getString(3);
			Contents = detail.getString(4);
			isFavorited = detail.getInt(5);	//0 : Off, 1 : On
			
			Toast.makeText(getApplicationContext(), Subject, Toast.LENGTH_LONG).show();
			
			TextView vSubject = (TextView)findViewById(R.id.todo_view_subject);
			vSubject.setText(Subject);
			
			TextView vContent = (TextView)findViewById(R.id.todo_view_detail);
			vContent.setText(Contents);
			
			//startDate와 endDate, 그리고 currentDate를 가진 Date형 3개를 만든다.
			SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			try {
				Date startTime = startDateFormat.parse(StartDate);
				Date endTime = endDateFormat.parse(EndDate);
				Date curTime = new Date();
				
				TextView vStatus = (TextView)findViewById(R.id.detail_text_status);
				vStatus.setText(Subject);
				
				if(startTime.getTime() < curTime.getTime())
					vStatus.setText("시작 전");
				else if(startTime.getTime() < curTime.getTime() && curTime.getTime()<=endTime.getTime())
					vStatus.setText("진행 중");
				else vStatus.setTag("종료됨");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		ImageButton modifyBtn = (ImageButton) findViewById(R.id.todo_btn_modification);
		modifyBtn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				//Global Variable에 저장한다.
				
				gb.modNum = gb.detailNum;
				
				Intent intent = new Intent(DetailSchedule.this, AddSchedule.class);
				DetailSchedule.this.startActivity(intent);
			}
		});
		
		
		ratings.setStepSize((float) 1.0);
		if(isFavorited == 1)
			ratings.setRating((float)1.0);
		else
			ratings.setRating((float)0.0);
		LayerDrawable stars = (LayerDrawable) ratings.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(Color.YELLOW,PorterDuff.Mode.SRC_ATOP);
		ratings.setIsIndicator(false);
		ratings.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction()==MotionEvent.ACTION_UP)
				{
					// TODO Auto-generated method stub
					if(ratings.getRating()==(float)0.0)
						ratings.setRating((float)1.0);
					else
						ratings.setRating((float)0.0);
				}
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_schedule, menu);
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
