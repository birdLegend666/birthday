package com.wbw.birthday.data;

import java.io.UnsupportedEncodingException;






import com.wbw.birthday.util.BASE64Decoder;
import com.wbw.birthday.util.BASE64Encoder;
import com.wbw.birthday.util.Comments;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesXml {
	public   SharedPreferences sp_config;
	public static String perference_m;
	private BASE64Decoder decode;  //����
	private BASE64Encoder encode;  //����
	private Context mContext;
	
	private static SharedPreferencesXml sp_xml;
	public static SharedPreferencesXml init(){
		if(sp_xml == null)
			sp_xml = new SharedPreferencesXml();
		return sp_xml;
	}
	
	public SharedPreferencesXml(){
		mContext = Comments.defaultContext;
		if(sp_config == null)
			sp_config = mContext.getSharedPreferences("config", mContext.MODE_PRIVATE); 
		decode = new BASE64Decoder();  //����
		encode = new BASE64Encoder();  //����
	}
	

	
	/**
	 * ���ò���
	 * @param key
	 * @param value
	 */
	public void setConfigSharedPreferences(String key,String value){
		
		String key_en,value_en;
		//if(Comments.DEBUG) System.out.println("key:"+key+"value:"+value);
		key_en = encode.encode(key.getBytes());
		value_en = encode.encode(value.getBytes());
		//if(Comments.DEBUG) System.out.println("ja mi key:"+key_en+" value:"+value_en);
		sp_config.edit().putString(key_en, value_en).commit();
	}
	
	/**
	 * ��ȡ����
	 * @param key
	 * @param defaultvalue
	 * @return
	 */
	public String getConfigSharedPreferences(String key,String defaultvalue){
		
		String key_en = encode.encode(key.getBytes());
		String getvalue = sp_config.getString(key_en, defaultvalue);  //��õ�vaule,����Ǵ�xml���õ�ֵ��Ҫ���ܣ������
																	//defaultvalue�ղ���
		//if(Comments.DEBUG) System.out.println("get the config value+getvalue:"+getvalue);
		if(getvalue.equals(defaultvalue)){
			return defaultvalue;
		}else{
			try {
				String getvalue_de = new String(decode.decode(getvalue.getBytes()),"UTF-8");				
				return getvalue_de;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return defaultvalue;
			}
		}
	}
	
	

}
