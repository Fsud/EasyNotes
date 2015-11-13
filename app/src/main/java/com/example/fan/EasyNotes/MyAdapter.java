package com.example.fan.EasyNotes;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MyAdapter extends BaseAdapter{

	private Cursor cursor;
	private Context context;
	private LinearLayout layout;
	
	
	public MyAdapter(Context context,Cursor cursor){
		this.cursor = cursor;
		this.context = context;
	}
	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		return cursor.getPosition();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = (LinearLayout) inflater.inflate(R.layout.cell, null);
		TextView titletv = (TextView) layout.findViewById(R.id.list_title);
		TextView contenttv = (TextView) layout.findViewById(R.id.list_content);
		TextView timetv = (TextView) layout.findViewById(R.id.list_time);
		ImageView imgiv= (ImageView) layout.findViewById(R.id.list_img);
		ImageView videoiv = (ImageView) layout.findViewById(R.id.list_video);
		cursor.moveToPosition(position);
		titletv.setText(cursor.getString(cursor.getColumnIndex("title")));
		contenttv.setText(cursor.getString(cursor.getColumnIndex("content")));
		timetv.setText(cursor.getString(cursor.getColumnIndex("time")));
		imgiv.setImageBitmap(getImageThumbnail(cursor.getString(cursor.getColumnIndex("path")), 200, 200));
		return layout;
	}
	public Bitmap getImageThumbnail(String uri,int width,int height){
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		bitmap = BitmapFactory.decodeFile(uri, options);
		options.inJustDecodeBounds = false;
		int beWidth = options.outWidth/width;
		int beHeight = options.outHeight/height;
		int be = 1;
		if(beWidth<beHeight){
			be = beWidth;
		}else {
			be = beHeight;
		}
		if(be <= 0){
			be=1;
		}
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(uri, options);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

}
