package org.mixare;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mixare.GetJSONThread;
import org.mixare.data.DataSource;
import org.mixare.data.DataSource.DATAFORMAT;
import org.mixare.data.DataSource.DATASOURCE;
import org.mixare.data.Json;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends Activity implements OnClickListener, OnItemClickListener {

		

		
				
		
		int position;
		Button btn_search;
		EditText editText;
		ListView listView;
		ArrayList<String> list; 
		ArrayAdapter<String> adapter;
		//		MixContext mixContext;
		//JSONObject jo[] ;
		
//		DataView dataview = new DataView(mixContext);
		
//		private Location curFix = mixContext.getCurrentLocation();
//		double lat = curFix.getLatitude(), lon = curFix.getLongitude(),alt = curFix.getAltitude();
		double lat[]=new double[50], lon[] = new double[50];
		String title[] = new String[50];
		
	    final static String opendaumURL = "https://apis.daum.net/local/v1/search/keyword.json?apikey=15d3b3e0188915e52cd00b0e8d467b08&sort=2";
		  
		
		@Override
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			setContentView(R.layout.search);

			
			editText=(EditText)findViewById(R.id.editText1);
			listView = (ListView)findViewById(R.id.listView1);
	        list = new ArrayList<String>();
	        //리스트뷰에 모델객체를 연결할 아답타 객체
	        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
	        //리스트뷰에 아답타 연결하기

	        adapter = new ArrayAdapter<String>(this, R.layout.simpleitem, list);
	        listView.setAdapter(adapter);
	        listView.setDivider(new ColorDrawable(Color.YELLOW));
	        listView.setDividerHeight(1); //구분선
			listView.setOnItemClickListener(new OnItemClickListener() {
			
				public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
	//				JSONArray items = adapterView.getItemAtPosition(i);
				
					
					Intent intent = new Intent(SearchActivity.this, MixView.class);
					intent.putExtra("Category", 8);
					intent.putExtra("lat", lat[i]);
					intent.putExtra("lon", lon[i]);
					intent.putExtra("title",title[i]);
			//		Toast.makeText(SearchActivity.this, "lat : "+lat[i], 0).show();
					
					startActivity(intent);
					
					
				}
				
			});
			
			btn_search=(Button)findViewById(R.id.button1);
			btn_search.setOnClickListener(this);
		
		}
		
		
		
		public void setTitle(String title, double lat, double lon, int i) 
		{
			this.title[i] = title;
			this.lat[i] = lat;
			this.lon[i] = lon;
		}
		
		@Override
		public void onClick(View v){
			//TODO auto-generated method stub
			
			 //public void find(View v) throws Exception{
			 //버튼이 눌릴때마다 데이터가 쌓이는 것을 방지하기 위해
			 list.clear();
			//요청 url 만들기
			String keyWord = editText.getText().toString();
			//한글이 깨지지 않게 하기 위해
			String encodedK=null;
			try {
				encodedK = URLEncoder.encode(keyWord, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append("https://apis.daum.net/local/v1/search/keyword.json?apikey=15d3b3e0188915e52cd00b0e8d467b08&sort=2");
			//한글일 경우 인코딩 필요!(영어로 가정한다)
			buffer.append("&query="+encodedK);
			//buffer.append("&location="+curFix.getLatitude() +","+curFix.getLongitude());
			buffer.append("&location=37.23958813136382,127.08439079819328");
			buffer.append("&radius=1000");
			
			
		    String url = buffer.toString();
				    
				    //스레드 객체를 생성해서 다운로드 받는다.
		   GetJSONThread thread = new GetJSONThread(handler, null, url);
		   thread.start();
			//	    }
	/*
			dataview.requestData(DataSource.createRequestURL(DataSource.DATASOURCE.SEARCH, lon, lat, alt, 20, Locale.getDefault().getLanguage()),
					DataSource.dataFormatFromDataSource(DataSource.DATASOURCE.SEARCH),DataSource.DATASOURCE.SEARCH);
			Intent intent = new Intent(SearchActivity.this, DataSource.class);
			intent.putExtra("query", getQuery());
	*/	}
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0 : //success
			Toast.makeText(SearchActivity.this, "성공?", 0).show();
			//json문자열을 ㅡ읽어오기
			String jsonStr = (String)msg.obj;
			try{
			//문자열을 json 객체로 변환
			//1. channel이라는 키값으로 {} jsonObject가 들어있다)
			//2. jsonObject안에는 item이라는 키값으로 [] jsonArray 벨류값을 가지고 있다.
			JSONObject jsonObj = new JSONObject(jsonStr);
			//1.
			JSONObject channel = jsonObj.getJSONObject("channel");
			//2.
			JSONArray items = channel.getJSONArray("item");
		
			//3.반복문 돌면서 필요한 정보만 얻어온다.
			for(int i=0 ; i<items.length() ; i++){
			//4. 검색결과 값을 얻어온다.
			JSONObject tmp = items.getJSONObject(i);
			
			String title = tmp.getString("title");
			String distance = tmp.getString("distance");
			double lat = tmp.getDouble("latitude");
			double lon = tmp.getDouble("longitude");
			list.add(title+" / "+ distance +"m"); 
			setTitle(title, lat, lon, i);
			}
			//모델의 데이터가 바뀌었다고 아답타 객체에 알린다.
			adapter.notifyDataSetChanged();
			}catch (Exception e) {

			}

			break;
			case 1 : //fail
			break;
			}
			}
			};
		
		public String getQuery(){
			return editText.getText().toString();
		}



		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			
		}
		
		
}

