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

// ������ �ҽ��� ���������� �ٷ�� Ŭ����
public class DataSource {
	
	// ������ �ҽ��� ������ ������ ����� �������� ���� �ٸ���.
	// ������ �ҽ��� �����Ͱ� ��� �Դ���, ������ ������ � �������� ���˵Ǿ������� ����Ų��.
	// �̿� ���� ���ش� �Ȱ��� ������ �������� ���������� ������ �ҽ��� �����ϴµ��� �ʼ����̴�
	
	
	// ������ �ҽ��� ������ ������ ������ ����
	public enum DATASOURCE { CONVENIENCE, CAFE, BANK, PCCAFE, HOSPITAL, PHARMACY, HOTEL, SEARCH };
	public enum DATAFORMAT { CONVENIENCE, CAFE, BANK, PCCAFE, HOSPITAL, PHARMACY, HOTEL, SEARCH };	

	/** �⺻ URL */

	//TODO : search > tmap ���κ����Ұ� //TODO : APIKEY �����
	// URL string ����
	//https://apis.skplanetx.com/tmap/routes/pedestrian?version=1&appKey=aa5012bc-76cf-316c-b000-a5f0913cb56b&startX=126.9823439963945&startY=37.56461982743129&endX=126.98031634883303
	//&endY=37.57007473965354&startName=st1&endName=en1&reqCoordType=WGS84GEO&resCoordType=WGS84GEO
	private static final String DAUM_BASE_URL = "https://apis.daum.net/local/v1/search/category.json?apikey=1a3de4395e79f6c3704841434f6aa305&code=";
	private static final String TMAP_URL = 
			"https://apis.skplanetx.com/tmap/routes/pedestrian?version=1&appKey=aa5012bc-76cf-316c-b000-a5f0913cb56b"
			+"&startName=st1&endName=en1&reqCoordType=WGS84GEO&resCoordType=WGS84GEO";
	
	// �����Ұ�! ����� ���� ������(MB���� �̻�)�� ������ ������, ���� �ݰ��̳� Ư���� �������� ����ؾ��Ѵ�
	/** URL �κ� �� */
	
	
	// �����ܵ�. Ʈ���Ϳ� ���� 
	public static Bitmap cafeIcon;
	public static Bitmap convenienceIcon;
	public static Bitmap bankIcon;
	public static Bitmap pccafeIcon;
	public static Bitmap hospitalIcon;
	public static Bitmap pharmacyIcon;
	public static Bitmap hotelIcon;
	
	
	// �⺻ ������
	public DataSource() {
		
	}
	
	// ���ҽ��κ��� �� ������ ����
	public static void createIcons(Resources res) {
		cafeIcon=BitmapFactory.decodeResource(res, R.drawable.cafe);
		convenienceIcon=BitmapFactory.decodeResource(res, R.drawable.convenience);
		bankIcon=BitmapFactory.decodeResource(res, R.drawable.bank);

		pccafeIcon=BitmapFactory.decodeResource(res, R.drawable.pccafe);

		hospitalIcon=BitmapFactory.decodeResource(res, R.drawable.hospital);
		pharmacyIcon=BitmapFactory.decodeResource(res, R.drawable.pharmacy);
		hotelIcon=BitmapFactory.decodeResource(res, R.drawable.hotel);
		

	}
	
	// ������ ��Ʈ���� ����
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
	
	// ������ �ҽ��κ��� ������ ������ ����
	public static DATAFORMAT dataFormatFromDataSource(DATASOURCE ds) {
		
		DATAFORMAT ret = DATAFORMAT.CONVENIENCE;
				// �ҽ� ���Ŀ� ���� ������ �Ҵ��Ѵ�
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
		return ret;	// ���� ����
	}
	
	
	// �� ������� �ϼ��� URL ������Ʈ�� ����
	public static String createRequestURL(DATASOURCE source, double lat, double lon, double alt, float radius,String locale, double slat, double slon) {
		String ret="";	// ��� ��Ʈ��
		String plus = "&location="+lat+","+lon+"&radius="+700;
		
		// �ҽ��� ���� �ּ� �Ҵ�. �켱 ����� ������ ������ �Ҵ��Ѵ�
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
				//TODO : T-map URL�� �����ؾ��� 16.12.11
				ret= TMAP_URL + "&startX=" + lon +"&startY="+ lat +"&endX="+slon +"&endY=" +slat;
				Log.d("tmapurl", ret);	break;
				
		}
		
		// ���Ϸκ��� �д� ���� �ƴ϶��
		if (!ret.startsWith("file://")) {			
		//		ret+="&location=" + lat + "," + lon + "&radius=" + 700;			
		}
		
		
		return ret;
	}
	
	
	// �� �ҽ��� ���� ���� ���� // ���δ� default ����
	public static int getColor(DATASOURCE datasource) {
		int ret;
		switch(datasource) {
			default:		ret = Color.BLUE; break;
		}
		return ret;
	}

}
