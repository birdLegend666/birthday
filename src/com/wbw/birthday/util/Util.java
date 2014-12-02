package com.wbw.birthday.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;




import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Util {
	private static Util util;
	public static Util init(){
		if(util == null)
			util = new Util();
		return util;
	}
	
	
	private  Util(){
		
	}
	/**
	 * 
	 * @param path����·���ַ���
	 * @return File
	 */
	public File creatFileIfNotExist(String path){
		File file = new File(path);
		if(!file.exists()){
			  try {	  
                  new File(path.substring(0, path.lastIndexOf(File.separator))).mkdirs();
                  file.createNewFile();
                 Log.i("creatFileIfNotExits", "Path:"+path+"creat success");
			  } catch (IOException e) {
                  e.printStackTrace();
                  Log.i("creatFileIfNotExits","Path:"+path+"&"+e.getMessage());
			  }
		}
		return file;
	}
	
	
	/**
	 * 
	 * @param path����·���ַ���
	 * @return File
	 */
	public File creatDirIfNotExist(String path){
		File file = new File(path);
		if(!file.exists()){
			  try {	  
                  file.mkdirs();
                 
			  } catch (Exception e) {
                  e.printStackTrace();
                  Log.i("creatFileIfNotExits","Path:"+path+"&"+e.getMessage());
			  }
		}
		return file;
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public boolean IsExist(String path){
		File file = new File(path);
		if(!file.exists()) return false;
		else return true;
	}

	/**
	 * �����µ��ļ�������о��ļ�����ɾ���ٴ���
	 * @param path
	 * @return
	 */
	public File creatNewFile(String path){
		File file = new File(path);
		if(IsExist(path))
			file.delete();
		creatFileIfNotExist(path);
		return file;
	}
	
	/**
	 * ɾ���ļ�
	 * @param path
	 * @return
	 */
	public boolean deleteFile(String path){
		File file = new File(path);
		if(IsExist(path))
			file.delete();
		return true;
	}
	
	
	//ɾ��һ��Ŀ¼
	public boolean deleteFileDir(String path){
		boolean flag = false;
		File file = new File(path);
		if(!IsExist(path)){
			return flag;
		}
		if(!file.isDirectory()){			
			Log.i("FILE", "ɾ�����ļ�"+file.getName());
	        file.delete();
			return true;
		}
		String[] filelist = file.list();
		File temp = null;
		for(int i =0;i<filelist.length;i++){
			 if (path.endsWith(File.separator)) {
	             temp = new File(path + filelist[i]);
	          } else {
	              temp = new File(path + File.separator + filelist[i]);
	          }
	          if (temp.isFile()) {
	        	 Log.i("FILE", "ɾ�����ļ�"+temp.getName());
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	        	 deleteFileDir(path + "/" + filelist[i]);//��ɾ���ļ���������ļ�
	          }
		}		
        file.delete();
        Log.i("FILE", "ɾ�����ļ�"+file.getName());
		flag = true;
		return flag;		
	}	
	
	//ɾ���ļ���
	//param folderPath �ļ�����������·��

	  public  boolean delFolder(String folderPath) {
		  boolean flag = false;
	     try {
	        flag = delAllFile(folderPath); //ɾ����������������
	        if(!flag) return flag;
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        flag = myFilePath.delete(); //ɾ�����ļ���
	        return flag;
	     } catch (Exception e) {
	       e.printStackTrace();
	       return false;
	     }
	}

	//ɾ��ָ���ļ����������ļ�
	//param path �ļ�����������·��
	  public  boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       if(tempList.length == 0){
	    	   return true;
	       }
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//��ɾ���ļ���������ļ�
	             delFolder(path + "/" + tempList[i]);//��ɾ�����ļ���
	             flag = true;
	          }
	       }
	       return flag;
	     }
	
	public String[] getFlieName(String rootpath){
		File root = new File(rootpath);
		File[] filesOrDirs = 
				root.listFiles();
		if(filesOrDirs != null){
			String[] dir = new String[filesOrDirs.length];
			int num = 0;
			 for (int i = 0; i < filesOrDirs.length; i++) {
		            if (filesOrDirs[i].isDirectory()) {	               
		                dir[i] = filesOrDirs[i].getName();
		                Log.v("FILE", i+"��;"+dir[i]);
		            	num++;
		            }
			 }
			 String[] dir_r = new String[num];
			 num = 0;
			 for(int i = 0;i<dir.length;i++){
				 if(dir[i] != null && !dir[i].equals("")){
					 dir_r[num] = dir[i];
					 num++;
				 }
			 }
			 return dir_r;
		}else return null;
	}
	
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	
	//�����
	public BufferedWriter getWriter(String path) throws FileNotFoundException, UnsupportedEncodingException {
		FileOutputStream fileout = null;
		fileout = new FileOutputStream(new File(path));
		OutputStreamWriter writer = null;
		writer = new OutputStreamWriter(fileout,"UTF-8");
		BufferedWriter w=new BufferedWriter(writer);  //������
		return w;
	}
	
	public InputStream getInputStream(String path) throws FileNotFoundException{
		//if(Comments.DEBUG) System.out.println("path:"+path);
		FileInputStream filein = null;
		//if(Comments.DEBUG) System.out.println("2");
		//File file = creatFileIfNotExist(path);
		File file = new File(path);
		filein = new FileInputStream(file);
		//if(Comments.DEBUG) System.out.println("3");
		BufferedInputStream in = null;
		//if(Comments.DEBUG) System.out.println("4");
		if(filein != null)
			in = new BufferedInputStream(filein);
		return in;
	}
	
	public boolean StateXmlControl(String path){
		File f = new File(path);
		if(!f.exists()) return false;
		if(f.length() == 0) return false;
		return true;
	}
	
	/**  
	 * ��InputStreamת����byte����  
	 * @param in InputStream  
	 * @return byte[]  
	 * @throws IOException  
	 */ 

	 public static byte[] InputStreamTOByte(InputStream in) throws IOException{  
		 ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
		 byte[] data = new byte[6*1024];  
		 int count = -1;  
		 while((count = in.read(data,0,4*1024)) != -1)  
			 outStream.write(data, 0, count);  
		 	data = null;  
		 	return outStream.toByteArray();  
	 	}  
	 
	 /**  
		 * ��OutputStreamת����byte����  
		 * @param in OutputStream  
		 * @return byte[]  
		 * @throws IOException  
		 */ 

		 public static byte[] OutputStreamTOByte(OutputStream out) throws IOException{  
		
			 byte[] data = new byte[6*1024];  
			 out.write(data);
			 return data;  
		 }  
    
	
	/**  
    * ��byte����ת����InputStream  
	* @param in  
	* @return  
	* @throws Exception  
	*/ 

	public static InputStream byteTOInputStream(byte[] in){  
		ByteArrayInputStream is = new ByteArrayInputStream(in);  
        return is;  
    }  

	
	/**  
	    * ��byte����ת����OutputStream  
		* @param in  
		* @return  
	 * @throws IOException 
		* @throws Exception  
		*/ 

		public static OutputStream byteTOOutputStream(byte[] in) throws IOException{  
			ByteArrayOutputStream out = new ByteArrayOutputStream();  
			out.write(in);
	        return out;  
	    }  
	
	/**
	 * ���������е��������뵽Path����ļ���
	 * @param path
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	public File writeFromInputToSD(String path,InputStream inputStream){
		File file = null;
		OutputStream output = null;
		try{
			file = creatFileIfNotExist(path);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[4*1024];
			int temp;
			while((temp=inputStream.read(buffer))!=-1){
				output.write(buffer,0,temp);
			}
			output.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				output.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return file;
	}
	
	/**
	 * ���������뵽Path����ļ���
	 * @param path
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	public File writeFromInputToSD(String path,byte[] b){
		File file = null;
		OutputStream output = null;
		try{
			file = creatFileIfNotExist(path);
			output = new FileOutputStream(file);
			output.write(b);	
			output.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				output.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return file;
	}
	

	
	
	 /** 
	     * ��������һ���ı����浽������·����.  
	     */  
	   public void saveTxtFile(String filePath, String text) {   
	       try {   
	        // ���ȹ���һ���ļ������,�������ļ���д������. 
	    	creatFileIfNotExist(filePath);
	    	String txt = readTextLine(filePath);
	    	text = text +"\r\n"+ txt;
	        FileOutputStream out = new FileOutputStream(filePath);   
	        // ����һ��д����,����������д���ַ�����   
	             OutputStreamWriter writer =    
	              new OutputStreamWriter(out, "gb2312");   
	        writer.write(text);   
	        // �ر�Writer,�ر������   
	        writer.close();   
	        out.close();   
	       } catch (Exception e) {   
	            String ext = e.getLocalizedMessage();   
	           // Toast.makeText(this, ext, Toast.LENGTH_LONG).show();   
	        }   
	  
	    }   
	   
	   public void clearTxtFile(String filePath){
		   try {   
		        // ���ȹ���һ���ļ������,�������ļ���д������. 
		    	String text = "";
		        FileOutputStream out = new FileOutputStream(filePath);   
		        // ����һ��д����,����������д���ַ�����   
		             OutputStreamWriter writer =    
		              new OutputStreamWriter(out, "gb2312");   
		        writer.write(text);   
		        // �ر�Writer,�ر������   
		        writer.close();   
		        out.close();   
		       } catch (Exception e) {   
		            String ext = e.getLocalizedMessage();   
		           // Toast.makeText(this, ext, Toast.LENGTH_LONG).show();   
		        }   
	   }
	  
	  
	    // ��ȡһ���������ı��ļ�����,����������һ���ַ�������ʽ����   
	    public String readTextLine(String textFile) {   
	        try {   
	            // ���ȹ���һ���ļ�������,�������ڴ��ı��ļ��ж�ȡ����   
	            FileInputStream input = new FileInputStream(textFile);   
	            // Ϊ���ܹ������ж�ȡ�ı�����,��������Ҫ����һ���ض���Reader��ʵ��,   
	            // ��Ϊ�����Ǵ�һ���������ж�ȡ����,���������ʺ�ʹ��InputStreamReader.   
	            InputStreamReader streamReader =    
	                    new InputStreamReader(input,"gb2312");   
	            // Ϊ���ܹ�ʵ��һ�ζ�ȡһ���ı��Ĺ���,����ʹ���� LineNumberReader��,   
	            // Ҫ����LineNumberReader��ʵ��,����Ҫ��һ��Readerʵ��������,   
	            // ���Ǵ���ǰ���Ѿ������õ�Reder.   
	            LineNumberReader reader = new LineNumberReader(streamReader);   
	            // �ַ���line��������ÿ�ζ�ȡ����һ���ı�.   
	            String line = null;   
	            // ��������ʹ��һ��StringBuilder���洢��ȡ����ÿһ���ı�,   
	                // ֮���Բ���String,����Ϊ��ÿ���޸Ķ������һ���µ�ʵ��,   
	                           //  �����˷ѿռ�,Ч�ʵ�.   
	            StringBuilder allLine = new StringBuilder();   
	            // ÿ�ζ�ȡ��һ��,ֱ����ȡ���   
	            while ((line = reader.readLine()) != null) {   
	                allLine.append(line);   
	                // ����ÿһ�к���,����һ�����з�,LINUX�л����ǡ�\n��,   
	                // windows�л����ǡ�\r\n��.   
	                allLine.append("\n");   
	            }   
	            // ��Reader��Stream�ر�   
	            streamReader.close();   
	            reader.close();   
	            input.close();   
	            // �Ѷ�ȡ���ַ�������   
	            return allLine.toString();   
	       } catch (Exception e) {   
	           // Toast.makeText(this, e.getLocalizedMessage(),   
	            //        Toast.LENGTH_LONG).show();   
	            return "";   
	        }   
	   }   
	
	
	    //ת��dipΪpx 
	    public  int convertDipOrPx(Context context, int dip) { 
	        float scale = context.getResources().getDisplayMetrics().density; 
	        return (int)(dip*scale + 0.5f*(dip>=0?1:-1)); 
	    } 
	     
	    //ת��pxΪdip 
	    public  int convertPxOrDip(Context context, int px) { 
	        float scale = context.getResources().getDisplayMetrics().density; 
	        return (int)(px/scale + 0.5f*(px>=0?1:-1)); 
	    } 
	    
	    /**
	     * ��pxֵת��Ϊspֵ����֤���ִ�С����
	     * 
	     * @param pxValue
	     * @param fontScale��DisplayMetrics��������scaledDensity��
	     * @return
	     */
	    public  int px2sp(Context context,float pxValue) {
	    	float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
	     return (int) (pxValue / fontScale + 0.5f);
	    }

	    /**
	     * ��spֵת��Ϊpxֵ����֤���ִ�С����
	     * 
	     * @param spValue
	     * @param fontScale��DisplayMetrics��������scaledDensity��
	     * @return
	     */
	    public  int sp2px(Context context,float spValue) {
	    	float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
	     return (int) (spValue * fontScale + 0.5f);
	    }
	    
		//���ּӳ���ʹ����Թ����������ֽ���
		public String dealString(String st,int size){
			int value = size;
			if(st.length()>=value)
				return "  "+st+"  ";
			else{
				int t = (value - st.length())/2;
				for(int i=0;i<t;i++){
					st = " "+st+"  ";
				}
				return st;
			}
		}
		
		public String getTimeByFormat(String format){
			SimpleDateFormat formatter = new SimpleDateFormat(format);       
			Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��       
			String str = formatter.format(curDate); 
			return str;
		}
		
		 public String getDateTimeBylong(long time_data,String dateformat_batt){
			 java.util.Date date = new java.util.Date(time_data);
			 SimpleDateFormat format = new SimpleDateFormat(dateformat_batt);   
		     return format.format(date); 
		 }
		
		//ȡǰ������֡�"."
		public String getNameByFlag(String source,String flag){
			//String[] source_spli = source.split(flag);
			String s = source.toLowerCase().replace(flag, "");
			return s.trim();
		}
		
		
		/**
		 * turn bytes array to string
		 * @param buf
		 * @param offset
		 * @param length
		 * @return
		 */
		public String bytesToHexString(byte[] buf, int offset, int length) {
			StringBuffer sb = new StringBuffer("");
			if (buf == null || buf.length <= 0)
				return "";
			for (int i = offset; i < length+offset; i++) {
				int v = buf[i] & 0xFF;// 
				String hv = Integer.toHexString(v);
				if (hv.length() < 2) {
					sb.append("0");
				}
				sb.append(hv);
			}
			return sb.toString();
		}
		
		/**
		 * turn bytes array to string
		 * @param buf
		 * @param offset
		 * @param length
		 * @return
		 */
		public String bytesToHexStringSep(byte[] buf, int offset, int length) {
			StringBuffer sb = new StringBuffer("");
			if (buf == null || buf.length <= 0)
				return "";
			for (int i = offset; i <length+offset; i++) {
				int v = buf[i] & 0xFF;// 
				String hv = Integer.toHexString(v);
				if (hv.length() < 2) {
					sb.append("0");
				}
				sb.append(hv+" ");
			}
			return sb.toString();
		}

		
		  public  Animation loadAnimation(Context context, int id)
		            throws Resources.NotFoundException {

		        XmlResourceParser parser = null;
		        try {
		            parser = context.getResources().getAnimation(id);
		            return createAnimationFromXml(context, parser);
		        } catch (XmlPullParserException ex) {
		            Resources.NotFoundException rnf = new Resources.NotFoundException("Can't load animation resource ID #0x" +
		                    Integer.toHexString(id));
		            rnf.initCause(ex);
		            throw rnf;
		        } catch (IOException ex) {
		            Resources.NotFoundException rnf = new Resources.NotFoundException("Can't load animation resource ID #0x" +
		                    Integer.toHexString(id));
		            rnf.initCause(ex);
		            throw rnf;
		        } finally {
		            if (parser != null) parser.close();
		        }
		    }
		  
		  private static Animation createAnimationFromXml(Context c, XmlPullParser parser)
		            throws XmlPullParserException, IOException {

		        return createAnimationFromXml(c, parser, null, Xml.asAttributeSet(parser));
		    }

		    private static Animation createAnimationFromXml(Context c, XmlPullParser parser,
		                                                    AnimationSet parent, AttributeSet attrs) throws XmlPullParserException, IOException {

		        Animation anim = null;

		        // Make sure we are on a start tag.
		        int type;
		        int depth = parser.getDepth();

		        while (((type=parser.next()) != XmlPullParser.END_TAG || parser.getDepth() > depth)
		                && type != XmlPullParser.END_DOCUMENT) {

		            if (type != XmlPullParser.START_TAG) {
		                continue;
		            }

		            String  name = parser.getName();

		            if (name.equals("set")) {
		                anim = new AnimationSet(c, attrs);
		                createAnimationFromXml(c, parser, (AnimationSet)anim, attrs);
		            } else if (name.equals("alpha")) {
		                anim = new AlphaAnimation(c, attrs);
		            } else if (name.equals("scale")) {
		                anim = new ScaleAnimation(c, attrs);
		            }  else if (name.equals("rotate")) {
		                anim = new RotateAnimation(c, attrs);
		            }  else if (name.equals("translate")) {
		                anim = new TranslateAnimation(c, attrs);
		            } else {
		                try {
		                    anim = (Animation) Class.forName(name).getConstructor(Context.class, AttributeSet.class).newInstance(c, attrs);
		                } catch (Exception te) {
		                    throw new RuntimeException("Unknown animation name: " + parser.getName() + " error:" + te.getMessage());
		                }
		            }

		            if (parent != null) {
		                parent.addAnimation(anim);
		            }
		        }

		        return anim;

		    }
}
