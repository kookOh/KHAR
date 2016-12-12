package org.mixare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MixareActivity extends Activity {
    /** Called when the activity is first created. */
	

	static int position = 0;
	
	private void showGpsoptions(){
		
		Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(gpsOptionsIntent);
	}
	private void createGpsDisabledAlert(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("GPS is disabled. switch on GPS finder")
		.setCancelable(false)
		.setPositiveButton("Enabl GPS", 
				new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						showGpsoptions();
					}
				})
		.setNegativeButton("Do nothing",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
	}

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mixaremain);
        // GPS 꺼져있을때 설정창 실행
    	LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    	
    	if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
    		createGpsDisabledAlert();
    	}
      
    	  	
       ImageView imageView1 = (ImageView)findViewById(R.id.imageView1);
  
       imageView1.setOnClickListener(new OnClickListener() {
						@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				position = 1;
			    Intent intent = new Intent(MixareActivity.this, MixView.class);
			 	intent.putExtra("Category", position);
			    startActivity(intent);			
							
			}
		});
       ImageView imageView2 = (ImageView)findViewById(R.id.imageView2); 
       imageView2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				position = 2;
			    Intent intent = new Intent(MixareActivity.this, MixView.class);
			 	intent.putExtra("Category", position);

			    startActivity(intent);				
//				Log.i(MixView.TAG, "calling draw : "+ String.valueOf(cnt++)+"\n");
				// TODO Auto-generated method stub
				
			}
		});
      
        ImageView imageView3 = (ImageView)findViewById(R.id.imageView3);
           imageView3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				position = 3;
			    Intent intent = new Intent(MixareActivity.this, MixView.class);
			 	intent.putExtra("Category", position);
			    startActivity(intent);				
	
				// TODO Auto-generated method stub
				
			}
		});
        
        ImageView imageView4 = (ImageView)findViewById(R.id.imageView4); 
       imageView4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				position = 4;
			    Intent intent = new Intent(MixareActivity.this, MixView.class);
			 	intent.putExtra("Category", position);
			    startActivity(intent);				
	
				// TODO Auto-generated method stub
				
			}
		});
        
        ImageView imageView5 = (ImageView)findViewById(R.id.imageView5);
       imageView5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				position = 5;
			    Intent intent = new Intent(MixareActivity.this, MixView.class);
			 	intent.putExtra("Category", position);
			    startActivity(intent);				
	
				// TODO Auto-generated method stub
				
			}
		});
        ImageView imageView6 = (ImageView)findViewById(R.id.imageView6);
        imageView6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				position = 6;
			    Intent intent = new Intent(MixareActivity.this, MixView.class);
			 	intent.putExtra("Category", position);
			    startActivity(intent);				
	
				// TODO Auto-generated method stub
				
			}
		});
        ImageView imageView7 = (ImageView)findViewById(R.id.imageView7); 
       
     //   Bitmap img7 = BitmapFactory.decodeResource(getResources(), R.drawable.hotel);
     //   imageView7.setImageBitmap(img7);
        imageView7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				position = 7;
			    Intent intent = new Intent(MixareActivity.this, MixView.class);
			 	intent.putExtra("Category", position);
			    startActivity(intent);				
	
				// TODO Auto-generated method stub
				
			}
		});
        

        ImageView imageView9 = (ImageView)findViewById(R.id.imageView9);
        imageView9.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			position = 8;
    //			 Toast.makeText(getApplication(), "Search", Toast.LENGTH_SHORT).show();
    			Intent intent = new Intent(MixareActivity.this, SearchActivity.class);
    			intent.putExtra("Category", position);
    		    startActivity(intent);				

    			// TODO Auto-generated method stub
    			
    		}
    	});
        
    } // oncreate	

  

	public int getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

}