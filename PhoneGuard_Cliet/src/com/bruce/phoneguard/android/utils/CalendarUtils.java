package com.bruce.phoneguard.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.util.Log;


/**
 * 获取日期
 * @author Kinmio
 *
 */
public class CalendarUtils {
	
	final private static String TAG = "CalendarUtils";
	
	/**
	 * 返回 yyyy-MM-dd 格式的String型日�?
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentDate(){
		Date dt = new Date();
		SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");		
		String currentDate = dateFormat.format(dt).toString();
		Log.i(TAG ,"CurrentDate�?"+currentDate);
		return currentDate;
	}
	
	/**
	 * 返回 hh:mm:ss 格式的String型时�?
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTime(){
		Date dt = new Date();
		SimpleDateFormat timeFormat =new SimpleDateFormat("HH:mm:ss");
		String currentTime = timeFormat.format(dt).toString();
		Log.i(TAG ,"CurrentDate�?"+currentTime);
		return currentTime;
	}
	
	/**
	 * 返回两个时间段之间的时间间隔
	 * @param beginDate
	 * @param beginTime
	 * @param endDate
	 * @param endTime
	 * @return
	 * @throws ParseException 
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getBetweenDays(String beginDate , String beginTime , String endDate , String endTime) throws ParseException{		
		String begin = beginDate + " " + beginTime ;
		String end = endDate + " " + endTime ;
		SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date begin1 = sDF.parse(begin);
		Date end1 = sDF.parse(end);
		Log.d(TAG,"end1.getTime():"+end1.getTime()+" begin1.getTime():" + begin1.getTime());
		long betweenTime = (end1.getTime() - begin1.getTime())/1000 ;	//化成了秒�?
		if(betweenTime <= 0 ){
			return "e";	//表示错误
		}else if( betweenTime < 60){
			return Long.toString(betweenTime)+"�?";
		}else if( betweenTime < 3600 ){
			return Long.toString(betweenTime/60)+"�?";
		}else if( betweenTime < 86400 ){
			return Long.toString(betweenTime/3600)+"小时";
		}else
			return Long.toString(betweenTime / 86400 ) + "�?" ;
	}
	
}
