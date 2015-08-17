package com.speed.gank.componets;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.speed.gank.activitys.MainActivity;

import java.util.Stack;

public abstract class BaseActivity extends FragmentActivity {

	private Stack<Activity> stack = new Stack<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		stack.push(this);
		initView();
		initData();
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public abstract void initView();
	
	public abstract void initData();

	public Activity currentActivity() {
		if(!stack.isEmpty()) {
			return stack.peek();
		}
		return null;
	}

	public boolean onOptionsItemSelected(MenuItem item){
		int itemId = item.getItemId();
		switch(itemId){
			case android.R.id.home:
				if(!(currentActivity() instanceof MainActivity)) {
					finish();
				}
			break;
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void geWeb(String url) {
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
}
