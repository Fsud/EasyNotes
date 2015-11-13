package com.example.fan.EasyNotes2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddContent extends AppCompatActivity implements OnClickListener {

	private Button btnSave, btnCancel;
	private EditText etText;
	private EditText etTitle;
	private ImageView imgView;
	private VideoView videoView;
	private String val;
	private NotesDB db;
	private SQLiteDatabase dbWritter;
	private File phoneFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontent);
		val = getIntent().getStringExtra("flag");
		btnSave = (Button) findViewById(R.id.save);
		btnCancel = (Button) findViewById(R.id.cancel);
		imgView = (ImageView) findViewById(R.id.c_img);
		videoView = (VideoView) findViewById(R.id.v_video);
		etText = (EditText) findViewById(R.id.ettext);
		etTitle = (EditText) findViewById(R.id.ettitle);
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		db = new NotesDB(this);
		dbWritter = db.getWritableDatabase();
		initView();
	}

	public void initView() {
		if (val.equals("1")) { // 文字
			imgView.setVisibility(View.GONE);
			videoView.setVisibility(View.GONE);
		}
		if (val.equals("2")) {
			imgView.setVisibility(View.VISIBLE);
			videoView.setVisibility(View.GONE);
			Intent iimg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			phoneFile = new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + "/" + getTime() + ".jpg");
			iimg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(phoneFile));
			startActivityForResult(iimg, 1);
		}
		if(val.equals("3")){
			imgView.setVisibility(View.GONE);
			videoView.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.save:
				addDB();
				finish();
				break;
			case R.id.cancel:
				finish();
				break;
			default:
				break;
		}
	}

	private void addDB() {
		ContentValues cv = new ContentValues();
		cv.put(NotesDB.CONTENT, etText.getText().toString());
		cv.put(NotesDB.TITLE, etTitle.getText().toString());
		cv.put(NotesDB.TIME, getTime());
		cv.put(NotesDB.PATH, phoneFile+"");
		cv.put(NotesDB.VIDEO, "");
		dbWritter.insert(NotesDB.TABLE_NAME, null, cv);
	}

	private String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日 HH-mm-ss");
		Date date = new Date();
		String str = formatter.format(date);
		return str;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			Bitmap bitmap = BitmapFactory.decodeFile(phoneFile
					.getAbsolutePath());
			imgView.setImageBitmap(bitmap);
		}

	}
}
