package com.example.viewpager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AboutUs extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		
		Button aboutUs = (Button) findViewById(R.id.aboutUs);
		
		Intent intent = getIntent();  
		String language = intent.getStringExtra("language");
		if(language.equals("English")){
			aboutUs.setBackground(getResources().getDrawable(R.drawable.aboutuse));
		}else{
			aboutUs.setBackground(getResources().getDrawable(R.drawable.aboutusc));
		}

		

		aboutUs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AboutUs.this,MainActivity.class);	  			  			
				startActivity(intent);
			}
		});
	
	}

}
