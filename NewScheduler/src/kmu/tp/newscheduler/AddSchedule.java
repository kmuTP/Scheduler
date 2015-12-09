package kmu.tp.newscheduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddSchedule extends Activity {
	
	long nTime = System.currentTimeMillis();
	Calendar calendar = Calendar.getInstance();
	TextView txtLabel;
	String sTime="";
	String eTime="";
	String checkSTime="",checkETime="";
	boolean isSTime = true;
	
	DatePickerDialog.OnDateSetListener dateSetListener=
			new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					calendar.set(year,monthOfYear,dayOfMonth);					
					setLabel();
				}
			};
			
	TimePickerDialog.OnTimeSetListener timeSetListener =
			new TimePickerDialog.OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					 calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
					 calendar.set(Calendar.MINUTE, minute);
					 if(isSTime==true)
					 {
						 sTime += String.format("일 %02d:%02d",hourOfDay,minute);
						 checkSTime = String.format("%04d-%02d-%02d %02d:%02d",
								 Calendar.YEAR,Calendar.MONTH,Calendar.DATE,Calendar.HOUR_OF_DAY,Calendar.MINUTE);
						 txtLabel = (TextView) findViewById(R.id.txtTime);
					 }
					 else
					 {
						 eTime += String.format("일 %02d:%02d",hourOfDay,minute);
						 checkETime = String.format("%04d-%02d-%02d %02d:%02d",
								 Calendar.YEAR,Calendar.MONTH,Calendar.DATE,Calendar.HOUR_OF_DAY,Calendar.MINUTE);
						 txtLabel = (TextView) findViewById(R.id.txtTime2);
					 }
					 txtLabel.setText(isSTime==true?sTime:eTime);
				}
			};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_schedule);
		
		Button btn_startDate = (Button)findViewById(R.id.plan_btn_startDate);
		
		btn_startDate.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				isSTime = true;
				// TODO Auto-generated method stub
				final DatePickerDialog dateDialog = new DatePickerDialog(AddSchedule.this,
									dateSetListener,
									calendar.get(Calendar.YEAR),
									calendar.get(Calendar.MONTH),
									calendar.get(Calendar.DAY_OF_MONTH));
			
				
				
				
				dateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "다음", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(which == DialogInterface.BUTTON_POSITIVE)
						{
							DatePicker temp = dateDialog.getDatePicker();
							sTime = String.valueOf(temp.getYear())+"년 "+
									String.valueOf(temp.getMonth()+1)+"월 "+
									String.valueOf(temp.getDayOfMonth());
							TimePickerDialog tdialog = new TimePickerDialog(AddSchedule.this,
									timeSetListener,
									calendar.get(Calendar.HOUR_OF_DAY),
									calendar.get(Calendar.MINUTE),
									true);
							
							tdialog.show();
						}
							
					}
				});
				dateDialog.show();
			}
		});
		Button btn_endDate = (Button)findViewById(R.id.plan_btn_endDate);
		
		btn_endDate.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				isSTime = false;
				// TODO Auto-generated method stub
				final DatePickerDialog dateDialog = new DatePickerDialog(AddSchedule.this,
									dateSetListener,
									calendar.get(Calendar.YEAR),
									calendar.get(Calendar.MONTH),
									calendar.get(Calendar.DAY_OF_MONTH));
			
				
				dateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "다음", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(which == DialogInterface.BUTTON_POSITIVE)
						{
							DatePicker temp = dateDialog.getDatePicker();
							eTime = String.valueOf(temp.getYear())+"년 "+
									String.valueOf(temp.getMonth()+1)+"월 "+
									String.valueOf(temp.getDayOfMonth());
							TimePickerDialog tdialog = new TimePickerDialog(AddSchedule.this,
									timeSetListener,
									calendar.get(Calendar.HOUR_OF_DAY),
									calendar.get(Calendar.MINUTE),
									true);
							
							tdialog.show();
						}
							
					}
				});
				dateDialog.show();
			}
		});
		
		Button btn_save = (Button)findViewById(R.id.plan_btn_save);
        final EditText textedit = (EditText) findViewById(R.id.plan_text_name);
        btn_save.setOnClickListener(new Button.OnClickListener() {

           @Override
           public void onClick(View v) {
              // TODO Auto-generated method stub
                          if (textedit.getText().toString().length() == 0 ) 
                             Toast.makeText(getApplicationContext(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                          else if(TextUtils.isEmpty(sTime))
                        	 Toast.makeText(getApplicationContext(), "시작 날짜와 시간을 설정해주세요.", Toast.LENGTH_SHORT).show();
                          else if(TextUtils.isEmpty(eTime))
                        	 Toast.makeText(getApplicationContext(), "종료 날짜와 시간을 설정해주세요.", Toast.LENGTH_SHORT).show();
                          else
                          {
                        	  SimpleDateFormat SFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        	  SimpleDateFormat EFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        	  try {
								Date SDate = SFormat.parse(checkSTime);	//시작날짜
								Date EDate = EFormat.parse(checkETime);	//종료날짜
								Date CDate = new Date();				//현재날짜
								
								Toast.makeText(getApplicationContext(), "현재날짜"+CDate, Toast.LENGTH_SHORT).show();
								Toast.makeText(getApplicationContext(), "시작날짜"+SDate, Toast.LENGTH_SHORT).show();
					
								//1. 시작시간이 현재시간보다 더 이전일 수는 없다.
								//2. 종료시간이 시작시간보다 빠를 수 없다.
								if(SDate.getTime() - CDate.getTime() < 0)
								{
									Toast.makeText(getApplicationContext(), "시작시간이 현재시간 이후여야 합니다.", Toast.LENGTH_SHORT).show();
								}
								else if(EDate.getTime()-SDate.getTime()<0)
								{
									Toast.makeText(getApplicationContext(), "종료 시간이 시작 시간보다 빠를 수 업습니다.", Toast.LENGTH_SHORT).show();
								}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                          }              
           }
        });
     
		final RatingBar ratings = (RatingBar) findViewById(R.id.plan_select_rating);
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

	protected void setLabel() {
		// TODO Auto-generated method stub
		 sTime = DateFormat.getDateTimeInstance().format(calendar.getTime());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_schedule, menu);
		return true;
	}
	//1
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
