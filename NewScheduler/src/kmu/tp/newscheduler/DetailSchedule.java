package kmu.tp.newscheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

	public String Subject, StartDate, EndDate, Contents;
	public int no, isFavorited;
	Date startTime,endTime,curTime;

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_schedule);
		final RatingBar ratings = (RatingBar) findViewById(R.id.todo_btn_setPriority);

		/* DB �о���� */
		final DBManager dbManager = new DBManager(getApplicationContext(), "schedule.db", null, 1);
		final SQLiteDatabase db = dbManager.getReadableDatabase();
		final GlobalVariable gb = (GlobalVariable) getApplicationContext();
		final int currentNum = gb.detailNum;

		Cursor detail = db.rawQuery("select * from schedule where no = " + currentNum, null);
		/* DB ó���ϱ� */
		if (detail.moveToFirst()) {
			no = detail.getInt(0);
			Subject = detail.getString(1);
			StartDate = detail.getString(2);
			EndDate = detail.getString(3);
			Contents = detail.getString(4);
			isFavorited = detail.getInt(5); // 0 : Off, 1 : On

			TextView vSubject = (TextView) findViewById(R.id.todo_view_subject);
			vSubject.setText(Subject);

			TextView vContent = (TextView) findViewById(R.id.todo_view_detail);
			vContent.setText(Contents);

			// startDate�� endDate, �׸��� currentDate�� ���� Date�� 3���� �����.
			SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� HH:mm");
			SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� HH:mm");
			try {
				startTime = startDateFormat.parse(StartDate);
				endTime = endDateFormat.parse(EndDate);
				curTime = new Date();
				
				TextView vStatus = (TextView) findViewById(R.id.todo_status_schedule);

				if (curTime.getTime() < startTime.getTime())
					vStatus.setText("������");
				else if (startTime.getTime() < curTime.getTime() && curTime.getTime() <= endTime.getTime())
					vStatus.setText("������");
				else
					vStatus.setText("�����");
				
			} catch (ParseException e) {
				
				vContent.setText(e.toString());
			}
		}

		ImageButton modifyBtn = (ImageButton) findViewById(R.id.todo_btn_modification);
		modifyBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Global Variable�� �����Ѵ�.

				gb.modNum = gb.detailNum;

				Intent intent = new Intent(DetailSchedule.this, AddSchedule.class);
				DetailSchedule.this.startActivity(intent);
			}
		});
		
		ImageButton deleteBtn = (ImageButton) findViewById(R.id.todo_btn_delete);
		deleteBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Global Variable�� �����Ѵ�.

				AlertDialog.Builder builder = new AlertDialog.Builder(DetailSchedule.this);

				builder.setTitle("����").setMessage("�� ������ �����Ͻðڽ��ϱ�?").setCancelable(false)
						.setPositiveButton("��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						db.execSQL("delete from schedule where no=" + currentNum);
						Intent intent = new Intent(DetailSchedule.this, MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						DetailSchedule.this.startActivity(intent);
					}
				}).setNegativeButton("���", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});

				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
		
		ImageButton backBtn = (ImageButton) findViewById(R.id.todo_btn_back);
		backBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Global Variable�� �����Ѵ�.

				Intent intent = new Intent(DetailSchedule.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				DetailSchedule.this.startActivity(intent);
			}
		});

		ratings.setStepSize((float) 1.0);
		if (isFavorited == 1)
			ratings.setRating((float) 1.0);
		else
			ratings.setRating((float) 0.0);
		LayerDrawable stars = (LayerDrawable) ratings.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
		ratings.setIsIndicator(false);
		ratings.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_UP) {
					// TODO Auto-generated method stub
					if (ratings.getRating() == (float) 0.0) {
						AlertDialog.Builder builder = new AlertDialog.Builder(DetailSchedule.this);

						builder.setTitle("�߿䵵").setMessage("�߿� �������� ����Ͻðڽ��ϱ�?").setCancelable(false)
								.setPositiveButton("��", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								// ū����ǥ, ��������ǥ, ����, ��ǥ, -�� escape �Ѵ�.

								ratings.setRating((float) 1.0);
								db.execSQL("update schedule set favorite = 1 where no=" + currentNum);

							}
						}).setNegativeButton("���", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						});

						AlertDialog dialog = builder.create();
						dialog.show();

					} else {
						AlertDialog.Builder builder = new AlertDialog.Builder(DetailSchedule.this);

						builder.setTitle("�߿䵵").setMessage("�߿� ���� ����� �����Ͻðڽ��ϱ�?").setCancelable(false)
								.setPositiveButton("��", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								// ū����ǥ, ��������ǥ, ����, ��ǥ, -�� escape �Ѵ�.

								ratings.setRating((float) 0.0);
								db.execSQL("update schedule set favorite = 0 where no=" + currentNum);

							}
						}).setNegativeButton("���", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						});

						AlertDialog dialog = builder.create();
						dialog.show();
					}
				}
				return true;
			}
		});
	}
}
