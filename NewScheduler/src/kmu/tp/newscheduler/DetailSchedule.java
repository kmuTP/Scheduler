package kmu.tp.newscheduler;

import android.app.Activity;
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
import android.view.View.OnTouchListener;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailSchedule extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_schedule);
		final RatingBar ratings = (RatingBar) findViewById(R.id.todo_btn_setPriority);
		
		/*DB 읽어오기*/
		final DBManager dbManager = new DBManager(getApplicationContext(),"schedule.db",null,1);
		SQLiteDatabase db = dbManager.getReadableDatabase();
		GlobalVariable gb = (GlobalVariable)getApplicationContext();
	
		Cursor detail = db.rawQuery("select * from schedule where no = "+gb.detailNum,null);
		/*DB 처리하기*/
		if(detail.moveToFirst())
		{
			final int no = detail.getInt(0);
			String Subject = detail.getString(1);
			String StartDate = detail.getString(2);
			String EndDate = detail.getString(3);
			String Contents = detail.getString(4);
			int isFavorited = detail.getInt(5);	//0 : Off, 1 : On
			
			TextView vSubject = (TextView)findViewById(R.id.todo_view_subject);
			vSubject.setText(Subject);
			
		}
		
		
		ratings.setStepSize((float) 1.0);
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
