/*
 * Copyright (C) 2010- Peer internet solutions
 *
 * This file is part of mixare.
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>
 */
package org.mixare.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.mixare.Marker;
import org.mixare.MixView;
import org.mixare.POIMarker;
import org.mixare.data.DataSource.DATAFORMAT;

import android.util.Log;

// JSON 파일을 다루는 클래스
public class Json extends DataHandler {

	public static final int MAX_JSON_OBJECTS=1000;	// JSON 객체의 최대 수
	
	// 각종 데이터를 로드
	public List<Marker> load(JSONObject root, DATAFORMAT dataformat) {
		// 데이터를 읽는데 사용할 JSON 객체와 데이터행렬, 마커들
		JSONObject jo = null;
		JSONArray dataArray = null;
    	List<Marker> markers=new ArrayList<Marker>();
    	Log.i(MixView.TAG,"Into load");
		try {
	    	// 각 스키마에 따른 데이터를 읽어온다
			// 다음 api에서 가져온 json 클래스를 읽어들임
			if(root.has("channel") && root.getJSONObject("channel").has("item"))
				dataArray = root.getJSONObject("channel").getJSONArray("item");
			
			if(root.has("feature") && root.getJSONObject("features").has("geometry"))
				dataArray = root.getJSONArray("features");
				
			// 데이터행렬에 데이터들이 있다면
			if (dataArray != null) {
				// 일단 로그 생성. 데이터 포맷을 기록한다
				Log.i(MixView.TAG, "processing "+dataformat+" JSON Data Array");
				// 최대 객체 수와 실제 데이터 길이를 비교해 최소치를 탑으로 지정 
				int top = Math.min(MAX_JSON_OBJECTS, dataArray.length());				

				// 각 데이터들에 대한 처리
				for (int i = 0; i <= top; i++) {					
					// 처리할 JSON 객체를 할당
					jo = dataArray.getJSONObject(i);
					Marker ma = null;
					
					ma = processDaumJSONObject(jo, dataformat);
					// 마커 추가
					if(ma!=null)
						markers.add(ma);
				}
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		// 모든 마커가 추가된 리스트를 리턴
		return markers;
	}
	
	//  데이터 처리
	public Marker processDaumJSONObject(JSONObject jo, DATAFORMAT df) throws JSONException {
		Log.i(MixView.TAG,"Into Daum JSON object");
		Marker ma = null;	// 임시객체
		
		// 형식에 맞는지 검사. 타이틀과 위도, 경도, 고도 태그를 찾는다 
		if(jo.has("feature"))
		{
			Log.i("json", " json get");
			JSONArray tmp1 = jo.getJSONArray("geometry");
			JSONObject tmp2 = tmp1.getJSONObject(0);
			//tmp2.getString("coordinates");
			String tmp = tmp2.getString("coordinates");
			StringTokenizer tokens = new StringTokenizer(tmp);
			String lon = tokens.nextToken(",");
			String lat = tokens.nextToken(",");
			double lat1 = Double.parseDouble(lat);
			double lon1 = Double.parseDouble(lon);
			Log.i("josn",lat);
			ma = new POIMarker(
					unescapeHTML("경유지 i번쨰",0),
					lat1,
					lon1,
					0.0,
					"null",
					DataSource.DATASOURCE.SEARCH);
			
		}
	
		
		if (jo.has("title") && jo.has("latitude") && jo.has("longitude") && jo.has("placeUrl")) {

			Log.v(MixView.TAG, "processing Daum JSON object");	// 로그 출력
	
			switch(df){
				case CAFE:
					// 할당된 값들로 마커 생성
					ma = new POIMarker(
							unescapeHTML(jo.getString("title"), 0), 
							jo.getDouble("latitude"), 
							jo.getDouble("longitude"), 
							0.0, 
							jo.getString("placeUrl"),		// 다음의 url 을 입력
							DataSource.DATASOURCE.CAFE);
					break;
				case CONVENIENCE:
					// 할당된 값들로 마커 생성
					ma = new POIMarker(
							unescapeHTML(jo.getString("title"), 0), 
							jo.getDouble("latitude"), 
							jo.getDouble("longitude"), 
							0.0, 
							jo.getString("placeUrl"),		// 다음의 url 을 입력
							DataSource.DATASOURCE.CONVENIENCE);
					break;
				case BANK:
					// 할당된 값들로 마커 생성
					ma = new POIMarker(
							unescapeHTML(jo.getString("title"), 0), 
							jo.getDouble("latitude"), 
							jo.getDouble("longitude"), 
							0.0, 
							jo.getString("placeUrl"),		// 다음의 url 을 입력
							DataSource.DATASOURCE.BANK);
					break;
				case PCCAFE:
					// 할당된 값들로 마커 생성
					ma = new POIMarker(
							unescapeHTML(jo.getString("title"), 0), 
							jo.getDouble("latitude"), 
							jo.getDouble("longitude"), 
							0.0, 
							jo.getString("placeUrl"),		// 다음의 url 을 입력
							DataSource.DATASOURCE.PCCAFE);
					break;
				case HOSPITAL:
					// 할당된 값들로 마커 생성
					ma = new POIMarker(
							unescapeHTML(jo.getString("title"), 0), 
							jo.getDouble("latitude"), 
							jo.getDouble("longitude"), 
							0.0, 
							jo.getString("placeUrl"),		// 다음의 url 을 입력
							DataSource.DATASOURCE.HOSPITAL);
					break;
				case PHARMACY:
					// 할당된 값들로 마커 생성
					ma = new POIMarker(
							unescapeHTML(jo.getString("title"), 0), 
							jo.getDouble("latitude"), 
							jo.getDouble("longitude"), 
							0.0, 
							jo.getString("placeUrl"),		// 다음의 url 을 입력
							DataSource.DATASOURCE.PHARMACY);
					break;
				case HOTEL:
					// 할당된 값들로 마커 생성
					ma = new POIMarker(
							unescapeHTML(jo.getString("title"), 0), 
							jo.getDouble("latitude"), 
							jo.getDouble("longitude"), 
							0.0, 
							jo.getString("placeUrl"),		// 다음의 url 을 입력
							DataSource.DATASOURCE.HOTEL);
					break;
					
			}
		}
		return ma;	// 마커 리턴
	}
	
	// html 엔트리의 해쉬맵
	private static HashMap<String, String> htmlEntities;
	static {
		htmlEntities = new HashMap<String, String>();
		htmlEntities.put("&lt;", "<");
		htmlEntities.put("&gt;", ">");
		htmlEntities.put("&amp;", "&");
		htmlEntities.put("&quot;", "\"");
		htmlEntities.put("&agrave;", "à");
		htmlEntities.put("&Agrave;", "À");
		htmlEntities.put("&acirc;", "â");
		htmlEntities.put("&auml;", "ä");
		htmlEntities.put("&Auml;", "Ä");
		htmlEntities.put("&Acirc;", "Â");
		htmlEntities.put("&aring;", "å");
		htmlEntities.put("&Aring;", "Å");
		htmlEntities.put("&aelig;", "æ");
		htmlEntities.put("&AElig;", "Æ");
		htmlEntities.put("&ccedil;", "ç");
		htmlEntities.put("&Ccedil;", "Ç");
		htmlEntities.put("&eacute;", "é");
		htmlEntities.put("&Eacute;", "É");
		htmlEntities.put("&egrave;", "è");
		htmlEntities.put("&Egrave;", "È");
		htmlEntities.put("&ecirc;", "ê");
		htmlEntities.put("&Ecirc;", "Ê");
		htmlEntities.put("&euml;", "ë");
		htmlEntities.put("&Euml;", "Ë");
		htmlEntities.put("&iuml;", "ï");
		htmlEntities.put("&Iuml;", "Ï");
		htmlEntities.put("&ocirc;", "ô");
		htmlEntities.put("&Ocirc;", "Ô");
		htmlEntities.put("&ouml;", "ö");
		htmlEntities.put("&Ouml;", "Ö");
		htmlEntities.put("&oslash;", "ø");
		htmlEntities.put("&Oslash;", "Ø");
		htmlEntities.put("&szlig;", "ß");
		htmlEntities.put("&ugrave;", "ù");
		htmlEntities.put("&Ugrave;", "Ù");
		htmlEntities.put("&ucirc;", "û");
		htmlEntities.put("&Ucirc;", "Û");
		htmlEntities.put("&uuml;", "ü");
		htmlEntities.put("&Uuml;", "Ü");
		htmlEntities.put("&nbsp;", " ");
		htmlEntities.put("&copy;", "\u00a9");
		htmlEntities.put("&reg;", "\u00ae");
		htmlEntities.put("&euro;", "\u20a0");
	}

	// HTML 아스키 값들을 다시 복원. 변환할 소스와 시작점을 인자로 받는다
	public String unescapeHTML(String source, int start) {
		int i, j;	// 임시 변수

		// &와 ;의 위치로 값들을 읽는다
		i = source.indexOf("&", start);
		if (i > -1) {
			j = source.indexOf(";", i);
			if (j > i) {
				// 검색된 위치에서 값을 읽어옴
				String entityToLookFor = source.substring(i, j + 1);
				String value = (String) htmlEntities.get(entityToLookFor);
				
				// 값이 있을 시 복원작업 시작. 재귀호출 이용
				if (value != null) {
					source = new StringBuffer().append(source.substring(0, i))
					.append(value).append(source.substring(j + 1))
					.toString();
					return unescapeHTML(source, i + 1); // recursive call
				}
			}
		}
		return source;	// 복원된 소스 리턴
	}
}