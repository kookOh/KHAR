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

import org.mixare.MixListView;
import org.mixare.MixView;
import org.mixare.R;
import org.mixare.SearchActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

/**
 * @author hannes
 *
 */

// 데이터 소스를 실질적으로 다루는 클래스
public class DataSource {
	
	// 데이터 소스와 데이터 포맷은 비슷해 보이지만 전혀 다르다.
	// 데이터 소스는 데이터가 어디서 왔는지, 데이터 포맷은 어떤 형식으로 포맷되었는지를 가르킨다.
	// 이에 대한 이해는 똑같은 데이터 포맷으로 여러가지의 데이터 소스를 실험하는데에 필수적이다
	
	
	// 데이터 소스와 데이터 포맷의 열거형 변수
	public enum DATASOURCE { CONVENIENCE, CAFE, BANK, PCCAFE, HOSPITAL, PHARMACY, HOTEL, SEARCH };
	public enum DATAFORMAT { CONVENIENCE, CAFE, BANK, PCCAFE, HOSPITAL, PHARMACY, HOTEL, SEARCH };	

	/** 기본 URL */

	//TODO : search > tmap 으로변경할것 //TODO : APIKEY 숨길것
	private static final String DAUM_BASE_URL = "https://apis.daum.net/local/v1/search/category.json?apikey=1a3de4395e79f6c3704841434f6aa305&code=";
	private static final String TMAP_URL = "https://apis.skplanetx.com/tmap/routes/pedestrian?version=1&appKey=aa5012bc-76cf-316c-b000-a5f0913cb56b&"
			+"resCoordType=WGS84GEO&startName=stp&endName=enp&reqCoordType=WGS84GEO";
	
	// 주의할것! 방대한 양의 데이터(MB단위 이상)을 산출할 때에는, 작은 반경이나 특정한 쿼리만을 사용해야한다
	/** URL 부분 끝 */
	
	
	// 아이콘들. 트위터와 버즈 
	public static Bitmap cafeIcon;
	public static Bitmap convenienceIcon;
	public static Bitmap bankIcon;
	public static Bitmap pccafeIcon;
	public static Bitmap hospitalIcon;
	public static Bitmap pharmacyIcon;
	public static Bitmap hotelIcon;
	
	
	// 기본 생성자
	public DataSource() {
		
	}
	
	// 리소스로부터 각 아이콘 생성
	public static void createIcons(Resources res) {
		cafeIcon=BitmapFactory.decodeResource(res, R.drawable.cafe);
		convenienceIcon=BitmapFactory.decodeResource(res, R.drawable.convenience);
		bankIcon=BitmapFactory.decodeResource(res, R.drawable.bank);

		pccafeIcon=BitmapFactory.decodeResource(res, R.drawable.pccafe);

		hospitalIcon=BitmapFactory.decodeResource(res, R.drawable.hospital);
		pharmacyIcon=BitmapFactory.decodeResource(res, R.drawable.pharmacy);
		hotelIcon=BitmapFactory.decodeResource(res, R.drawable.hotel);
		

	}
	
	// 아이콘 비트맵의 게터
	public static Bitmap getBitmap(DATASOURCE ds) {
		Bitmap bitmap=null;
		switch (ds) {
			case CAFE: bitmap=cafeIcon; break;
			case CONVENIENCE: bitmap=convenienceIcon; break;
			case BANK: bitmap = bankIcon; break;
			case PCCAFE: bitmap = pccafeIcon; break;
			case HOSPITAL: bitmap = hospitalIcon; break;
			case PHARMACY: bitmap = pharmacyIcon; break;
			case HOTEL: bitmap = hotelIcon; break;
			case SEARCH: bitmap = hotelIcon; break;
		}
		return bitmap;
	}
	
	// 데이터 소스로부터 데이터 포맷을 추출
	public static DATAFORMAT dataFormatFromDataSource(DATASOURCE ds) {
		
		DATAFORMAT ret = DATAFORMAT.CONVENIENCE;
				// 소스 형식에 따라 포맷을 할당한다
		switch (ds) {
			case CONVENIENCE: ret=DATAFORMAT.CONVENIENCE; break;
			case CAFE: ret=DATAFORMAT.CAFE; break;
			case BANK: ret=DATAFORMAT.BANK; break;
			case PCCAFE: ret=DATAFORMAT.PCCAFE; break;
			case HOSPITAL: ret=DATAFORMAT.HOSPITAL; break;
			case PHARMACY: ret=DATAFORMAT.PHARMACY; break;
			case HOTEL: ret=DATAFORMAT.HOTEL; break;
			case SEARCH: ret=DATAFORMAT.SEARCH; break;
		}
		return ret;	// 포맷 리턴
	}
	
	
	// 각 정보들로 완성된 URL 리퀘스트를 생성
	public static String createRequestURL(DATASOURCE source, double lat, double lon, double alt, float radius,String locale, double slat, double slon) {
		String ret="";	// 결과 스트링
		String plus = "&location="+lat+","+lon+"&radius="+700;
		
		// 소스에 따라 주소 할당. 우선 상수로 설정된 값들을 할당한다
		switch(source) {
			
			case CONVENIENCE: 
				ret = DAUM_BASE_URL + "CS2" + plus; break;
			case CAFE: 
				ret = DAUM_BASE_URL + "CE7"+ plus; break;			
			case BANK:
				ret = DAUM_BASE_URL + "BK9"+ plus; break;
			case PCCAFE:
				ret = DAUM_BASE_URL + "CT1"+ plus;break;
			case HOSPITAL:
				ret = DAUM_BASE_URL +"HP8"+ plus;break;
			case PHARMACY:
				ret = DAUM_BASE_URL + "PM9"+ plus;break;
			case HOTEL:
				ret = DAUM_BASE_URL + "AD5"+ plus;break;
			case SEARCH: 
				//TODO : T-map URL로 변경해야함 16.12.11
				ret= TMAP_URL + "&startX=" + lat +"&startY="+ lon +"&endX="+slat +"&endY=" +slon;
				Log.d("tmapurl", "tmap _ url ");	break;
				
		}
		
		// 파일로부터 읽는 것이 아니라면
		if (!ret.startsWith("file://")) {			
		//		ret+="&location=" + lat + "," + lon + "&radius=" + 700;			
		}
		
		
		return ret;
	}
	
	
	// 각 소스에 따른 색을 리턴 // 전부다 default 설정
	public static int getColor(DATASOURCE datasource) {
		int ret;
		switch(datasource) {
			default:		ret = Color.BLUE; break;
		}
		return ret;
	}

}
