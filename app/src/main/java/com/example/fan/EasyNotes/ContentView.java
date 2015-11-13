package com.example.fan.EasyNotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ContentView extends AppCompatActivity {

	private TextView tvtitle;
	private TextView tv;
	private Button changeBtn;
	private Button delBtn;
	private NotesDB notesDB;
	private SQLiteDatabase dbWritter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_view);
		tvtitle = (TextView) findViewById(R.id.titletv);
		tv=(TextView) findViewById(R.id.contenttv);
		changeBtn = (Button) findViewById(R.id.change);
		delBtn = (Button) findViewById(R.id.delete);
		tvtitle.setText(getIntent().getStringExtra(NotesDB.TITLE));
		tv.setText(getIntent().getStringExtra(NotesDB.CONTENT));
		tv.setMovementMethod(new ScrollingMovementMethod());
		changeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ContentView.this,changeContent.class);
				i.putExtra(NotesDB.ID, getIntent().getIntExtra(NotesDB.ID, 0));
				i.putExtra(NotesDB.TITLE, getIntent().getStringExtra(NotesDB.TITLE));
				i.putExtra(NotesDB.CONTENT, getIntent().getStringExtra(NotesDB.CONTENT));
				startActivity(i);
				finish();
			}
		});
		delBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new AlertDialog.Builder(ContentView.this).setTitle("删除 ")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								delete();
								finish();
							}

						})
						.setNegativeButton("返回", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

							}
						}).show();

			}
		});
	}

	private void delete() {
		notesDB =new NotesDB(this);
		dbWritter = notesDB.getWritableDatabase();
		dbWritter.delete(NotesDB.TABLE_NAME, "_id="+getIntent().getIntExtra(NotesDB.ID, 0), null);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
