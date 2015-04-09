package com.ultrawise.softwareproduct.idevplatform.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ultrawise.softwareproduct.idevplatform.db.sqlite.DbModel;
import com.ultrawise.softwareproduct.idevplatform.entity.ResultItem;
import org.json.JSONArray;
import org.json.JSONObject;

public class BeanUtils {
	/**
	 * 获取对象的属性
	 * 
	 * @param obj
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getDbFieldValue(Object obj, String key) {
		if (obj != null) {
			if (obj instanceof Map<?, ?>) {
				return ((Map<String, Object>) obj).get(key);
			} else if (obj instanceof DbModel) {
				return ((DbModel) obj).get(key);
			} else if (obj instanceof String) {
				return obj;
			}
		}
		return null;
	}
    /**
     * 获取对象的属性
     *
     * @param obj
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object getFieldValue(Object obj, String key) {
        if (obj != null) {
            if (obj instanceof Map<?, ?>) {
                return ((Map<String, Object>) obj).get(key);
            } else if (obj instanceof ResultItem) {
                return ((ResultItem) obj).getValue(key);
            } else if (obj instanceof String) {
                return obj;
            }
        }
        return null;
    }
	/**
	 * 验证文件路径是否存在，不存在则进行创建操作
	 * 
	 * @param filePath
	 */
	public static void checkFileExist(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	/** 判断文件是否存在 */
	public static boolean isFileExist(String filePath) {
		boolean hasFile = false;
		try {
			File file = new File(filePath);
			hasFile = file.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasFile;
	}
	/**
	 * 文件保存
	 * 
	 * @param
	 */
	public static void saveFile(InputStream inps, String filePath) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			byte[] buffer = new byte[2048];
			int len = -1;
			while ((len = inps.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(inps);
			close(out);
		}
	}

	/**
	 * 判断数据是否为空
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("all")
	public static boolean isEmpty(Object obj) {
		boolean flag = true;
		if (obj != null) {
			if (obj instanceof String) {
				flag = (obj.toString().trim().length() == 0);

			} else if (obj instanceof Collection<?>) {
				flag = ((Collection) obj).size() == 0;

			} else if (obj instanceof Map) {
				flag = ((Map) obj).size() == 0;

			} else if (obj instanceof DbModel) {
				flag = ((DbModel) obj).getDataMap().size() == 0;

			} else if (obj instanceof Object[]) {
				flag = ((Object[]) obj).length == 0;

			} else {
				flag = false;
			}
		}
		return flag;
	}

	/***
	 * 生成32位的MD5加密方式
	 * 
	 * @param
	 * @return
	 */
	public static String md532(String source) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = source.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				// 将没个数(int)b进行双字节加密
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 通用资源的关闭操作
	 * 
	 * @param closeable
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

    /**
     * 字符串转换日期
     *
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static Date parseDate(String dateStr, String formatStr) {
        Date result = null;
        try {
            if (dateStr.length() < formatStr.length()) {
                dateStr = "0" + dateStr;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            result = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /** 将JsonObject转换成ResultItem */
    @SuppressWarnings("unchecked")
    public static ResultItem convertJSONObject(JSONObject jsonObj) {
        ResultItem resultItem = new ResultItem();
        if (jsonObj != null) {
            // 遍历所有的KEY值
            Iterator<String> keys = jsonObj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                try {
                    // 获取具体对象
                    Object obj = jsonObj.get(key);
                    if (obj != null) {
                        if (obj instanceof JSONObject) {
                            // 添加属性(递归添加)
                            resultItem.addValue(key,
                                    convertJSONObject((JSONObject) obj));

                        } else if (obj instanceof JSONArray) {
                            // 列表对象
                            List<Object> listItems = new ArrayList<Object>();
                            // 将JSONArray足个解析
                            JSONArray tempArray = (JSONArray) obj;
                            for (int i = 0; i < tempArray.length(); i++) {
                                Object itempObj = tempArray.get(i);
                                if (itempObj instanceof JSONObject) {
                                    // 递归添加
                                    listItems.add(convertJSONObject(tempArray
                                            .getJSONObject(i)));
                                } else {
                                    listItems.add(itempObj);
                                }
                            }
                            resultItem.addValue(key, listItems);

                        } else {
                            resultItem.addValue(key, obj.toString());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resultItem;
    }

    /** 根据json对象生成ResultItem对象 */
    public static ResultItem getResultItemByJson(String context) {
        ResultItem item = new ResultItem();
        try {
            // 生成jsonObject
            JSONObject jsonObj = new JSONObject(context);
            // 转换为统一的ResultItem
            item = BeanUtils.convertJSONObject(jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    /** 自定义的getBtyes */
    protected static byte[] getBytes(String message) {
        byte[] values = new byte[message.length()];
        for (int i = 0; i < message.length(); i++) {
            values[i] = (byte) message.charAt(i);
        }
        return values;
    }
    /**
     * URl的拼接
     *
     * @param url
     * @param parts
     * @return
     */
    public static String urlAppend(String url, String parts) {
        url = url.trim();
        if (!BeanUtils.isEmpty(parts)) {
            if (url.indexOf("?") < 0) {
                url += "?";
            } else {
                url += "&";
            }
        }
        return url + parts;
    }
}
