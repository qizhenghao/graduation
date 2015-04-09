/**
 * 
 */
package com.bruce.phoneguard.android.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LogUtils
{
	/** null device */
	private static final byte			LOG_NULL	= 0;
	/** console log */
	private static final byte			LOG_CONSOLE	= 1;
	/** file log */
	private static final byte			LOG_FILE	= 2;
	/** both */
	private static final byte			LOG_BOTH	= 3;

//	private static byte					logDevice	= TestUtils.enableLogToFile ? LOG_BOTH : LOG_CONSOLE;
	private static byte					logDevice	= 1;

	private static SimpleDateFormat		logTimeFormat;
	private static FileLogHandler		fileLog;

	private static Map<String, Long>	timeMap		= new HashMap<String, Long>();

	private static final String			eTagSuffix	= "_Catched";
	private static final String			NO_TAG		= "Luxy";
	/** 是否是混淆包 */
	private static boolean				confused	= false;

	static
	{
		try
		{
			Class.forName("com.luxy.utils.LogUtils");
		}
		catch (Exception e)
		{
			confused = true;
		}
		if (logDevice > LOG_CONSOLE)
		{
			logTimeFormat = new SimpleDateFormat("hh:mm:ss.SSS: ", Locale.US);
			fileLog = new FileLogHandler();
		}
	}

	public static void d(String msg)
	{
		StackTraceElement[] sElements = new Throwable().getStackTrace();
		if (confused || sElements == null || sElements.length < 2)
			d(NO_TAG, msg);
		else
		{
			// Throwable instance must be created before any methods
			d(createTag(sElements[1]), createMessage(msg, sElements[1]));
		}
	}

	public static void w(String msg)
	{
		StackTraceElement[] sElements = new Throwable().getStackTrace();
		if (confused || sElements == null || sElements.length < 2)
			w(NO_TAG, msg);
		else
		{
			// Throwable instance must be created before any methods
			w(createTag(sElements[1]), createMessage(msg, sElements[1]));
		}
	}

	public static void e(String msg)
	{
		StackTraceElement[] sElements = new Throwable().getStackTrace();
		if (confused || sElements == null || sElements.length < 2)
			e(NO_TAG, msg);
		else
		{
			// Throwable instance must be created before any methods
			e(createTag(sElements[1]), createMessage(msg, sElements[1]));
		}
	}

	public static void e(Throwable e)
	{
		StackTraceElement[] sElements = e.getStackTrace();
		if (confused || sElements == null || sElements.length < 2)
			e(NO_TAG, e.toString(), e, logDevice);
		else
		{
			// Throwable instance must be created before any methods
			e(createTag(sElements[1]), createMessage(e.toString(), sElements[1]), e, logDevice);
		}
	}

	public static void d(String TAG, String msg)
	{
		d(TAG, msg, logDevice);
	}

	public static void e(String TAG, String msg)
	{
		e(TAG, msg, logDevice);
	}

	public static void w(String TAG, String msg)
	{
		w(TAG, msg, logDevice);
	}

	public static void e(String TAG, Throwable e)
	{
		StackTraceElement[] sElements = e.getStackTrace();
		if (confused || sElements == null || sElements.length < 2)
			e(TAG, e.toString(), e, logDevice);
		else
		{
			// Throwable instance must be created before any methods
			e(TAG, createMessage(e.toString(), sElements[1]), e, logDevice);
		}
	}

	protected static void d(String TAG, String msg, int device)
	{
		if (msg == null)
			msg = "NULL MSG";

		switch (device)
		{
			case LOG_FILE:
				writeToLog("D", TAG, msg);
				break;
			case LOG_BOTH:
				writeToLog("D", TAG, msg);
				// 故意不写break
			case LOG_CONSOLE:
				Log.d(TAG, msg);
				break;
			case LOG_NULL:
			default:
				break;
		}
	}

	protected static void w(String TAG, String msg, int device)
	{
		if (msg == null)
			msg = "NULL MSG";

		switch (device)
		{
			case LOG_FILE:
				writeToLog("W", TAG, msg);
				break;
			case LOG_BOTH:
				writeToLog("W", TAG, msg);
				// 故意不写break
			case LOG_CONSOLE:
				Log.w(TAG, msg);
				break;
			case LOG_NULL:
			default:
				break;
		}
	}

	protected static void e(String TAG, String msg, int device)
	{
		if (msg == null)
			msg = "NULL MSG";

		switch (device)
		{
			case LOG_FILE:
				writeToLog("E", TAG, msg);
				break;
			case LOG_BOTH:
				writeToLog("E", TAG, msg);
				// 故意不写break
			case LOG_CONSOLE:
				Log.e(TAG + eTagSuffix, msg);
				break;
			case LOG_NULL:
			default:
				break;
		}
	}

	protected static void e(String TAG, String msg, Throwable e, int device)
	{
		if (e == null)
			return;

		switch (device)
		{
			case LOG_FILE:
				writeToLog("E", TAG, msg);
				break;
			case LOG_BOTH:
				writeToLog("E", TAG, msg);
				// 故意不写break
			case LOG_CONSOLE:
				Log.e(TAG + eTagSuffix, msg, e);
				break;
			case LOG_NULL:
			default:
				break;
		}
	}

	private static String createTag(StackTraceElement element)
	{
		String className = element.getFileName();
		if (className == null)
			return NO_TAG;
		// 去掉后缀名
		return className.length() > 5 ? className.substring(0, className.length() - 5) : className;
	}

	private static String createMessage(String msg, StackTraceElement element)
	{
		return "[" + element.getMethodName() + ":" + element.getLineNumber() + "]\t" + msg;
	}

	public static void timeStamp(Exception exception, String step)
	{
		StackTraceElement stackTraceElement = exception.getStackTrace()[0];
		String className = stackTraceElement.getClassName();
		String methodName = stackTraceElement.getMethodName();
		int lineNum = stackTraceElement.getLineNumber();
		if (step == null)
			step = "";
		else
			step += "-";
		Log.d("TimeStamp", step + className + "." + methodName + "():" + lineNum);
	}

	/**
	 * Get whether log switch is on.
	 */
	public static boolean getIsLogged()
	{
		return (logDevice != LOG_NULL);
	}

	/**
	 * Set whether log switch is on.
	 */
	public static void setIsLogged(boolean isLogged)
	{
		if (isLogged)
		{
			logDevice = LOG_CONSOLE;
		}
		else
		{
			logDevice = LOG_NULL;
		}
	}

	public static void init()
	{
		if (logDevice > LOG_CONSOLE)
		{
			fileLog = new FileLogHandler();
		}
	}

	private static void writeToLog(String level, String tag, String log)
	{
		Message msg = fileLog.obtainMessage();
		Thread currentThread = Thread.currentThread();
		msg.obj = logTimeFormat.format(new Date()) + level + "/" + tag + "(" + currentThread.getId() + "-" + currentThread.getName() + "): " + log;
		fileLog.sendMessage(msg);
	}


	private static class FileLogHandler extends Handler
	{
		private boolean				hasSDCard	= false;
		private FileOutputStream	logOutput;
		private File				logFile;

		FileLogHandler()
		{
			hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
			if (hasSDCard)
			{
				try
				{
					logFile = new File(Environment.getExternalStorageDirectory(), "luxyLog.txt");
					if (logFile.exists())
						logFile.delete();
					logFile.createNewFile();
				}
				catch (IOException e)
				{
				}
			}
		}


		public void handleMessage(Message msg)
		{
			if (!hasSDCard)
			{
				return;
			}

			try
			{
				String log = (String) msg.obj + "\n";
				if (log != null)
				{
					byte[] logData = log.getBytes();
					getLogOutput().write(logData, 0, logData.length);
				}
			}
			catch (Exception e)
			{
			}
		}

		FileOutputStream getLogOutput() throws Exception
		{
			if (logOutput == null)
			{
				logOutput = new FileOutputStream(logFile, true);
			}
			return logOutput;
		}
	}

	public static final boolean	FILE_LOGABLE	= true;

	/**
	 * 写日志到sdcard. 用于跟踪特殊场景下， 异常上报，行为日志都不能分析问题时，调用. 慎用.
	 */
	public static void writeLog(String file, String log)
	{
		if (!FILE_LOGABLE)
			return;
		if (fileLog != null && fileLog.hasSDCard)
		{
			try
			{
				File logFile = new File(file);
				PrintWriter printWriter = new PrintWriter(new FileOutputStream(logFile, true));
				printWriter.println(log);
				printWriter.flush();
				printWriter.close();

			}
			catch (FileNotFoundException e)
			{
				e.getMessage();
			}
		}
	}

	/**
	 * 开始测量时间
	 * 
	 * @param key:被测量区间的标记
	 */
	public static void startTiming(String key)
	{
		long currTime = System.currentTimeMillis();
		timeMap.put(key, currTime);
	}

	/**
	 * 打印当前距离测量起始消耗的时长
	 * 
	 * @param TAG:输出日志的TAG
	 * @param msg:输出日志的信息
	 * @param key:被测量区间的标记
	 */
	public static void printCostTime(String msg, String key)
	{
		long lastTime = 0;
		if (timeMap.containsKey(key))
		{
			lastTime = timeMap.get(key);
		}
		long currTime = System.currentTimeMillis();
		d(msg + ", cost time:" + (currTime - lastTime));
		timeMap.put(key, currTime);
	}

	/**
	 * 打印当前距离测量起始消耗的时长
	 * 
	 * @param TAG:输出日志的TAG
	 * @param msg:输出日志的信息
	 * @param key:被测量区间的标记
	 */
	public static void printCostTime(String TAG, String msg, String key)
	{
		long lastTime = 0;
		if (timeMap.containsKey(key))
		{
			lastTime = timeMap.get(key);
		}
		long currTime = System.currentTimeMillis();
		d(TAG, msg + ", cost time:" + (currTime - lastTime));
		timeMap.put(key, currTime);
	}
}
