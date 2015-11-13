package com.example.fan.EasyNotes;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class changeContent extends AppCompatActivity implements OnClickListener{

	private Button btnSave, btnCancel;
	private EditText etText;
	private EditText etTitle;
	private NotesDB db;
	private SQLiteDatabase dbWritter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.changecontent);
		btnSave = (Button) findViewById(R.id.changecontent_save);
		btnCancel = (Button) findViewById(R.id.changecontent_cancel);
		etText = (EditText) findViewById(R.id.ettext);
		etTitle = (EditText) findViewById(R.id.ettitle);
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		etTitle.setText(getIntent().getStringExtra(NotesDB.TITLE));
		etText.setText(getIntent().getStringExtra(NotesDB.CONTENT));
		db = new NotesDB(this);
	}


	public void changeDB() {
		ContentValues cv = new ContentValues();
		cv.put(NotesDB.TITLE, etTitle.getText().toString());
		cv.put(NotesDB.CONTENT, etText.getText().toString());
		cv.put(NotesDB.TIME, getTime());
		dbWritter = db.getWritableDatabase();
		dbWritter.update(NotesDB.TABLE_NAME, cv, "_id="+getIntent().getIntExtra(NotesDB.ID, 0), null);
	}

	public String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日 HH-mm-ss");
		Date date = new Date();
		String str = formatter.format(date);
		return str;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.changecontent_save:
				changeDB();
				//startActivity(new Intent(this,MainActivity.class));
				finish();
				break;
			case R.id.changecontent_cancel:
				finish();
				break;
			default:
				break;
		}
	}


}
