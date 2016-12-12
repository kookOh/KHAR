package org.mixare;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.text.style.BulletSpan;
import android.util.Log;
import android.widget.Toast;


public class GetJSONThread extends Thread{
	//메인스레드의 핸들러
	Handler handler;
	//전송할 데이터가 담겨있는 map객체
	Map<String, String> map;
	//전송할 url 주소
	String url;

	//생성자
	public GetJSONThread(Handler handler,  Map<String, String> map, String url){
	this.handler = handler;
	this.map = map;
	this.url = url;
	}
	//스레드 본체
	@Override
	public void run() {
	HttpURLConnection conn = null;
	StringBuilder builder = new StringBuilder();
	try {
	URL url=new URL(this.url);
	    conn= (HttpURLConnection)url.openConnection();
	    if(conn != null){//정상접속이 되었다면
	    conn.setConnectTimeout(10000);//최대 대기시간10초
	    conn.setUseCaches(false);//캐쉬사용안함
	    if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
	    //InputStreamReader 객체 얻어오기
	    InputStreamReader isr=
	    new InputStreamReader(conn.getInputStream());
	    BufferedReader br=new BufferedReader(isr);
	    //반복문 돌면서 읽어오기
	    while(true){
	    String line=br.readLine();
	    if(line==null)break;
	    //읽어온 문자열을 객체에 저장
	    builder.append(line);
	    }
	    br.close();
	    }//if
	    }//if
	    
	Message msg= new Message();
	msg.what=0; //성공
	msg.obj = builder.toString();
	handler.sendMessage(msg);
	} catch (Exception e) {
	// TODO Auto-generated catch block
	Log.e("post 전송중 에러!", e.getMessage());
	Message msg= new Message();
	msg.what=1; //실패
	msg.obj = "데이터를 받아올 수 없습니다.";
	handler.sendMessage(msg);
	}finally{
	conn.disconnect(); //접속 종료
	}
	}//run
	}