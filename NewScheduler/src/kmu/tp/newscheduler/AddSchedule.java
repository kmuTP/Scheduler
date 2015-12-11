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
	String sTime = "";
	String eTime = "";
	String checkSTime = "", checkETime = "";
	String inputSubject="", inputContent="";
	int favorited=0;
	SimpleDateFormat SFormat, EFormat;
	boolean isSTime = true;

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
				sTime += String.format("�� %02d:%02d", hourOfDay, minute);
				txtLabel = (TextView) findViewById(R.id.txtTime);
			} else {
				eTime += String.format("�� %02d:%02d", hourOfDay, minute);
				txtLabel = (TextView) findViewById(R.id.txtTime2);
			}
			txtLabel.setText(isSTime == true ? sTime : eTime);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_schedule);

		Button btn_startDate = (Button) findViewById(R.id.plan_btn_startDate);
		final RatingBar ratings = (RatingBar) findViewById(R.id.plan_select_rating);

		btn_startDate.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				isSTime = true;
				// TODO Auto-generated method stub
				final DatePickerDialog dateDialog = new DatePickerDialog(AddSchedule.this, dateSetListener,
						calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

				dateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "����", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == DialogInterface.BUTTON_POSITIVE) {
							DatePicker temp = dateDialog.getDatePicker();
							sTime = String.valueOf(temp.getYear()) + "�� " + String.valueOf(temp.getMonth() + 1) + "�� "
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

				dateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "����", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == DialogInterface.BUTTON_POSITIVE) {
							DatePicker temp = dateDialog.getDatePicker();
							eTime = String.valueOf(temp.getYear()) + "�� " + String.valueOf(temp.getMonth() + 1) + "�� "
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
		final EditText subjectForm = (EditText) findViewById(R.id.plan_text_name);
		final EditText contentForm = (EditText) findViewById(R.id.plan_text_content);
		btn_save.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//��ȿ�� �˻�(���� ���� �׽�Ʈ)
				inputSubject=subjectForm.getText().toString();	//����
				inputContent=contentForm.getText().toString();	//����
				
				SFormat = new SimpleDateFormat("yyyy�� MM�� dd�� hh:mm");
				EFormat = new SimpleDateFormat("yyyy�� MM�� dd�� hh:mm");
				
				if (inputSubject.length() == 0)
				{
					Toast.makeText(getApplicationContext(), "������ �Է����ּ���.", Toast.LENGTH_SHORT).show();
					subjectForm.requestFocus();
				}
				else if (TextUtils.isEmpty(sTime))
					Toast.makeText(getApplicationContext(), "���� ��¥�� �ð��� �������ּ���.", Toast.LENGTH_SHORT).show();
				else if (TextUtils.isEmpty(eTime))
					Toast.makeText(getApplicationContext(), "���� ��¥�� �ð��� �������ּ���.", Toast.LENGTH_SHORT).show();
				else {
					
					try {
						Date SDate = SFormat.parse(sTime); // ���۳�¥
						Date EDate = EFormat.parse(eTime); // ���ᳯ¥
						Date CDate = new Date(); // ���糯¥

						// 1. ����ð��� ����ð����� �� ������ ���� ����.
						
						if (EDate.getTime() - CDate.getTime() < 0) {
							Toast.makeText(getApplicationContext(), "����ð��� ����ð� ���Ŀ��� �մϴ�.", Toast.LENGTH_SHORT).show();
						// 2. ����ð��� ���۽ð����� ���� �� ����.
						} else if (EDate.getTime() - SDate.getTime() < 0) {
							Toast.makeText(getApplicationContext(), "���� �ð��� ���� �ð����� ���� �� �����ϴ�.", Toast.LENGTH_SHORT).show();
						} else {
							// ������ ��� �����Ѵ�.
							if(inputContent.length() == 0)
							{
								AlertDialog.Builder builder = new AlertDialog.Builder(AddSchedule.this);
								
								builder.setTitle("������ �������")
									   .setMessage("������ �������� ����Ͻðڽ��ϱ�?")
									   .setCancelable(false)
									   .setPositiveButton("���", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											//ū����ǥ, ��������ǥ, ����, ��ǥ, -�� escape �Ѵ�.
											
											inputContent = " ";
											insertData(ratings);
				
										}
									})
									   .setNegativeButton("���", new DialogInterface.OnClickListener() {
										
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
		inputSubject = escapeString(inputSubject);
		inputContent = escapeString(inputContent);
		float chkFavorited = ratings.getRating();
		
		if(chkFavorited == 1.0)
			favorited = 1;
		else
			favorited = 0;
		
		final DBManager dbManager = new DBManager(getApplicationContext(), "schedule.db", null, 1);
		SQLiteDatabase db = dbManager.getReadableDatabase();
		
		//����Ѵ�.
		db.execSQL("insert into schedule(subject,startdate,enddate,content,favorite) values("+inputSubject+",'"+sTime+"','"+eTime+"',"+inputContent+",'"+favorited+"');");
		Toast.makeText(getApplicationContext(), "insert into schedule(subject,startdate,enddate,content,favorite) values("+inputSubject+",'"+sTime+"','"+eTime+"',"+inputContent+","+favorited+");", Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(), "insert into schedule(subject,startdate,enddate,content,favorite) values("+inputSubject+",'"+sTime+"','"+eTime+"',"+inputContent+","+favorited+");", Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(), "insert into schedule(subject,startdate,enddate,content,favorite) values("+inputSubject+",'"+sTime+"','"+eTime+"',"+inputContent+","+favorited+");", Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(AddSchedule.this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		AddSchedule.this.startActivity(intent);
	}
}
