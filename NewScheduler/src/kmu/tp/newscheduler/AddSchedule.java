package kmu.tp.newscheduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
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
	
	
	String sTime = "", eTime = "", checkSTime = "", checkETime = "", inputSubject="", inputContent="";
	boolean isModifyMode = false;
	int favorited=0;
	SimpleDateFormat SFormat, EFormat;
	boolean isSTime = true;
	
	public long getDiffTime(Date start,Date end){
		   return end.getTime()-start.getTime();
		 }

	DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// TODO Auto-generated method stub
			calendar.set(year, monthOfYear, dayOfMonth);
			setLabel();
		}
	};

	TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calendar.set(Calendar.MINUTE, minute);
			if (isSTime == true) {
				sTime += String.format("일 %02d:%02d", hourOfDay, minute);
				txtLabel = (TextView) findViewById(R.id.view_startTime);
			} else {
				eTime += String.format("일 %02d:%02d", hourOfDay, minute);
				txtLabel = (TextView) findViewById(R.id.view_EndTime);
			}
			txtLabel.setText(isSTime == true ? sTime : eTime);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_schedule);
		
		final DBManager dbManager = new DBManager(getApplicationContext(), "schedule.db", null, 1);
		final SQLiteDatabase db = dbManager.getReadableDatabase();
		final GlobalVariable gb = (GlobalVariable) getApplicationContext();
		Button btn_startDate = (Button) findViewById(R.id.plan_btn_startDate);
		final RatingBar ratings = (RatingBar) findViewById(R.id.plan_select_rating);
		final EditText subjectForm = (EditText) findViewById(R.id.plan_text_name);
		final EditText contentForm = (EditText) findViewById(R.id.plan_text_content);
		
		if(gb.modNum != 0)isModifyMode=true;
		
		if(isModifyMode == true)
		{
			//Toast.makeText(getApplicationContext(), "수정모드 돌입!.", Toast.LENGTH_SHORT).show();
			//수정 모드일 경우 DB를 읽어온다.
			Cursor content = db.rawQuery("select * from schedule where no = "+gb.modNum, null);
			
			if (content.moveToFirst()) {
				String mSubject = content.getString(1);
				String mStartDate = content.getString(2);
				String mEndDate = content.getString(3);
				String mContent = content.getString(4);
				int mFavorite = content.getInt(5);
				
				//내용을 채워넣는다.
				sTime = mStartDate;
				eTime = mEndDate;
				txtLabel = (TextView) findViewById(R.id.view_startTime);
				txtLabel.setText(sTime);
				txtLabel = (TextView) findViewById(R.id.view_EndTime);
				txtLabel.setText(eTime);
				subjectForm.setText(mSubject);
				contentForm.setText(mContent);
				if(mFavorite == 1)
					ratings.setRating((float)1.0);
				else
					ratings.setRating((float)0.0);
			}
		}

		
		
		btn_startDate.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				isSTime = true;
				// TODO Auto-generated method stub
				final DatePickerDialog dateDialog = new DatePickerDialog(AddSchedule.this, dateSetListener,
						calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

				dateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "다음", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == DialogInterface.BUTTON_POSITIVE) {
							DatePicker temp = dateDialog.getDatePicker();
							sTime = String.valueOf(temp.getYear()) + "년 " + String.valueOf(temp.getMonth() + 1) + "월 "
									+ String.valueOf(temp.getDayOfMonth());

							TimePickerDialog tdialog = new TimePickerDialog(AddSchedule.this, timeSetListener,
									calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

							tdialog.show();
						}

					}
				});
				dateDialog.show();
			}
		});
		Button btn_endDate = (Button) findViewById(R.id.plan_btn_endDate);

		btn_endDate.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				isSTime = false;
				// TODO Auto-generated method stub
				final DatePickerDialog dateDialog = new DatePickerDialog(AddSchedule.this, dateSetListener,
						calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

				dateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "다음", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == DialogInterface.BUTTON_POSITIVE) {
							DatePicker temp = dateDialog.getDatePicker();
							eTime = String.valueOf(temp.getYear()) + "년 " + String.valueOf(temp.getMonth() + 1) + "월 "
									+ String.valueOf(temp.getDayOfMonth());
							TimePickerDialog tdialog = new TimePickerDialog(AddSchedule.this, timeSetListener,
									calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

							tdialog.show();
						}

					}
				});
				dateDialog.show();
			}
		});

		Button btn_save = (Button) findViewById(R.id.plan_btn_save);
		
		btn_save.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//유효성 검사(단위 유닛 테스트)
				inputSubject=subjectForm.getText().toString();	//제목
				inputContent=contentForm.getText().toString();	//내용
				
				SFormat = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm");
				EFormat = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm");
				
				if (inputSubject.length() == 0)
				{
					Toast.makeText(getApplicationContext(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
					subjectForm.requestFocus();
				}
				else if (TextUtils.isEmpty(sTime))
					Toast.makeText(getApplicationContext(), "시작 날짜와 시간을 설정해주세요.", Toast.LENGTH_SHORT).show();
				else if (TextUtils.isEmpty(eTime))
					Toast.makeText(getApplicationContext(), "종료 날짜와 시간을 설정해주세요.", Toast.LENGTH_SHORT).show();
				else {
					
					try {
						Date SDate = SFormat.parse(sTime); // 시작날짜
						Date EDate = EFormat.parse(eTime); // 종료날짜
						Date CDate = new Date(); // 현재날짜

						// 1. 종료시간이 현재시간보다 더 이전일 수는 없다.
						
						if (getDiffTime(EDate,CDate) < 0) {
							Toast.makeText(getApplicationContext(), "종료시간은 현재시간 이후여야 합니다.", Toast.LENGTH_SHORT).show();
						// 2. 종료시간이 시작시간보다 빠를 수 없다.
						} else if (getDiffTime(EDate,SDate) < 0) {
							Toast.makeText(getApplicationContext(), "종료 시간이 시작 시간보다 빠를 수 없습니다.", Toast.LENGTH_SHORT).show();
						} else {
							// 일정을 등록 시작한다.
							if(inputContent.length() == 0)
							{
								AlertDialog.Builder builder = new AlertDialog.Builder(AddSchedule.this);
								
								builder.setTitle("내용이 비어있음")
									   .setMessage("일정을 공백으로 등록하시겠습니까?")
									   .setCancelable(false)
									   .setPositiveButton("등록", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											//큰따옴표, 작은따옴표, 온점, 쉼표, -를 escape 한다.
											
											inputContent = " ";
											insertData(ratings);
				
										}
									})
									   .setNegativeButton("취소", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											if(inputContent.length() == 0)
												inputContent = " ";
										}
									});
								
								AlertDialog dialog = builder.create();
								dialog.show();
							}
							else
							{
								insertData(ratings);
							}
							// Toast.makeText(getApplicationContext(), ".",
							// Toast.LENGTH_SHORT).show();
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		
		ratings.setStepSize((float) 1.0);
		ratings.setRating((float) 0.0);
		LayerDrawable stars = (LayerDrawable) ratings.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
		ratings.setIsIndicator(false);
		ratings.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_UP) {
					// TODO Auto-generated method stub
					if (ratings.getRating() == (float) 0.0)
						ratings.setRating((float) 1.0);
					else
						ratings.setRating((float) 0.0);
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

	// 1
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

	public String escapeString(String str) {

		return DatabaseUtils.sqlEscapeString(str);
	}
	
	public void insertData(RatingBar ratings)
	{
		final DBManager dbManager = new DBManager(getApplicationContext(), "schedule.db", null, 1);
		final SQLiteDatabase db = dbManager.getReadableDatabase();
		final GlobalVariable gb = (GlobalVariable) getApplicationContext();
		
		inputSubject = escapeString(inputSubject);
		inputContent = escapeString(inputContent);
		float chkFavorited = ratings.getRating();
		
		if(chkFavorited == 1.0)
			favorited = 1;
		else
			favorited = 0;
		
		
		
		
		if(isModifyMode == false)
		{
			db.execSQL("insert into schedule(subject,startdate,enddate,content,favorite) values("+inputSubject+",'"+sTime+"','"+eTime+"',"+inputContent+",'"+favorited+"');");
			Toast.makeText(getApplicationContext(), "성공적으로 등록되었습니다.", Toast.LENGTH_LONG).show();
		}
		else
		{
			db.execSQL("update schedule set subject="+inputSubject+",startdate='"+sTime+"',enddate='"+eTime+"',content="+inputContent+",favorite='"+favorited+"' where no="+gb.modNum);
			Toast.makeText(getApplicationContext(), "성공적으로 수정되었습니다.", Toast.LENGTH_LONG).show();
		}
		Intent intent = new Intent(AddSchedule.this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		AddSchedule.this.startActivity(intent);
	}
}
