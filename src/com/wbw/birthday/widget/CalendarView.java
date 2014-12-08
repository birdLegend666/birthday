package com.wbw.birthday.widget;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.wbw.birthday.R;
import com.wbw.birthday.calender.LunarCalendar;
import com.wbw.birthday.calender.SpecialCalendar;
import com.wbw.birthday.info.BirthdayInfo;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * ����gridview�е�ÿһ��item��ʾ��textview
 * @author jack_peng
 *
 */
@SuppressLint("ResourceAsColor")
public class CalendarView extends BaseAdapter implements Cloneable{
	
	private static final String Tag="CalendarView";

	
	private boolean isLeapyear = false;  //�Ƿ�Ϊ����
	private int daysOfMonth = 0;      //ĳ�µ�����
	private int firstDayOfMonth = 0;        //����ĳһ�������ڼ�
	private int lastDaysOfMonth = 0;  //��һ���µ�������
	private Context context;
	private String[] dayNumber = new String[49];  //һ��gridview�е����ڴ����������
	private static String week[] = {"����","��һ","�ܶ�","����","����","����","����"};
	//private static String week[] = {"SUN","MON","TUE","WED","THU","FRI","SAT"};
	private SpecialCalendar specialCalendar = null;
	private LunarCalendar lunarCalendar = null; 
	private Resources res = null;
	private Drawable drawable = null;
	
	private String currentYear = "";
	private String currentMonth = "";
	private String currentDay = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	private int currentFlag = -1;     //���ڱ�ǵ���
	private String showYear = "";   //������ͷ����ʾ�����
	private String showMonth = "";  //������ͷ����ʾ���·�
	private String animalsYear = ""; 
	private String leapMonth = "";   //����һ����
	private String cyclical = "";   //��ɵ�֧
	//ϵͳ��ǰʱ��
	private String sysDate = "";  
	private String sys_year = "";
	private String sys_month = "";
	private String sys_day = "";
	

	private int screenwidth;
	
	public CalendarView(){
		Date date = new Date();
		sysDate = sdf.format(date);  //��������
		sys_year = sysDate.split("-")[0];
		sys_month = sysDate.split("-")[1];
		sys_day = sysDate.split("-")[2];
		
	}
	
	public CalendarView(Context context,Resources rs,int jumpMonth,int jumpYear,
			int year_c,int month_c,int day_c,int screenwidth){
		this();
		this.context= context;
		specialCalendar = new SpecialCalendar();
		lunarCalendar = new LunarCalendar();
		this.res = rs;
		this.screenwidth = screenwidth;
		int stepYear = year_c+jumpYear;
		int stepMonth = month_c+jumpMonth ;
		if(stepMonth > 0){
			//����һ���»���
			if(stepMonth%12 == 0){
				stepYear = year_c + stepMonth/12 -1;
				stepMonth = 12;
			}else{
				stepYear = year_c + stepMonth/12;
				stepMonth = stepMonth%12;
			}
		}else{
			//����һ���»���
			stepYear = year_c - 1 + stepMonth/12;
			stepMonth = stepMonth%12 + 12;
			if(stepMonth%12 == 0){
				
			}
		}
	
		currentYear = String.valueOf(stepYear);;  //�õ���ǰ�����
		currentMonth = String.valueOf(stepMonth);  //�õ����� ��jumpMonthΪ�����Ĵ�����ÿ����һ�ξ�����һ�»��һ�£�
		currentDay = String.valueOf(day_c);  //�õ���ǰ����������
		
		getCalendar(Integer.parseInt(currentYear),Integer.parseInt(currentMonth));
		
	}
	
	public CalendarView(Context context,Resources rs,int year, int month, int day){
		this();
		this.context= context;
		specialCalendar = new SpecialCalendar();
		lunarCalendar = new LunarCalendar();
		this.res = rs;
		currentYear = String.valueOf(year); //�õ���ת�������
		currentMonth = String.valueOf(month);  //�õ���ת�����·�
		currentDay = String.valueOf(day);  //�õ���ת������
		
		getCalendar(Integer.parseInt(currentYear),Integer.parseInt(currentMonth));
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return dayNumber.length;
	}

	
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	  private class ViewHolder {
	      
		  TextView textView;
	        public ViewHolder(View view) {
	        	 textView = (TextView) view.findViewById(R.id.tvtext);
	        }
	    }

	//��Gridview���ֵ
	@SuppressLint("ResourceAsColor")
	public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder;
	     if (convertView != null) {
	          holder = (ViewHolder) convertView.getTag();
	    } else {
	        	convertView = LayoutInflater.from(context).inflate(R.layout.calendar, null);	    		
	            holder = new ViewHolder(convertView);
	            convertView.setTag(holder);
	        }
//		if(convertView == null){
//			convertView = LayoutInflater.from(context).inflate(R.layout.calendar, null);
//		 }
		TextView textView = holder.textView;
		String d = dayNumber[position].split("\\.")[0];
		String dv = dayNumber[position].split("\\.")[1];
		String f = dayNumber[position].split("\\.")[2];
		Log.i("calendarview", d+","+dv);
		//Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica.ttf");
		//textView.setTypeface(typeface);
		SpannableString sp = new SpannableString(d+"\n"+dv);
		
		Log.i(Tag, "SpannableString---"+sp);
		
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(1.2f) , 0, d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new TypefaceSpan("monospace"), 0, d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		//sp.setSpan(new BackgroundColorSpan(Color.RED), 0, d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		if(dv != null && dv != "" && !dv.equals(" ") && !dv.trim().equals("")){
			//ũ����ʾ����ʽ
            sp.setSpan(new RelativeSizeSpan(0.75f), d.length()+1, d.length()+dv.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //sp.setSpan(new BackgroundColorSpan(Color.RED), d.length()+1, dayNumber[position].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		
		textView.setText(sp);
		//textView.setTextColor(R.color.little_grey);
		textView.setTextColor(Color.RED);
		
		//if(drawable == null)
//			drawable = res.getDrawable(R.drawable.item);
//		textView.setBackgroundDrawable(drawable);
		textView.setBackgroundResource(R.drawable.item);
		
		// ��ǰ���������ԣ�������ͱ���
		if (position < daysOfMonth + firstDayOfMonth+7 && position >= firstDayOfMonth+7) {			
			textView.setTextColor(Color.BLACK);// �����������
			
			//textView.setBackgroundColor(Color.WHITE);
			//����һ���������Ӻ�
			if(position%7==0||position%7==6){
				textView.setTextColor(Color.rgb(255,120,20));
			}
			
		}else {

			//�����ܵ���������,���positionΪ0-6
			if(position<7){
				
				textView.setTextColor(Color.BLACK);
				textView.setTextSize(14.0f);
//				textView.setGravity(45);
				//drawable = res.getDrawable(R.drawable.week_top);
				//textView.setBackgroundDrawable(drawable);
				textView.setBackgroundResource(R.drawable.week_top);
			}
			//���õ�����������������ʾ������Ϊǳ��ɫ
			else{
		
				textView.setTextColor(Color.rgb(200, 195, 200));
			}
		}
		
					//�������ճ̰��ŵı�Ǳ���
		if(Boolean.valueOf(f))		
			textView.setBackgroundResource(R.drawable.mark);
//		
		//���õ���ı���
		if(currentFlag == position){ 
			
			//drawable = res.getDrawable(R.drawable.current_day_bgc);
			//textView.setBackgroundDrawable(drawable);
			textView.setBackgroundResource(R.drawable.current_day_bgc);
			textView.setTextColor(Color.WHITE);
		}
		//���ÿ���µ���ĩ
		Calendar calendar=Calendar.getInstance();
		if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			textView.setTextColor(Color.rgb(255, 145, 90));
		}
		int lll = convertView.getHeight();
		
		int l = textView.getHeight();
		 LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) textView.getLayoutParams();
		 lp.width = screenwidth/7;
		 
         lp.height = lp.width+10;
       
         textView.setLayoutParams(lp);
		return convertView;
	}
	
	//�õ�ĳ���ĳ�µ����������µĵ�һ�������ڼ�
	public void getCalendar(int year, int month){
		isLeapyear = specialCalendar.isLeapYear(year);              //�Ƿ�Ϊ����
		daysOfMonth = specialCalendar.getDaysOfMonth(isLeapyear, month);  //ĳ�µ�������
		firstDayOfMonth = specialCalendar.getWeekdayOfMonth(year, month);      //ĳ�µ�һ��Ϊ���ڼ�
		lastDaysOfMonth = specialCalendar.getDaysOfMonth(isLeapyear, month-1);  //��һ���µ�������
		
		Log.d("DAY", isLeapyear+" ======  "+daysOfMonth+"  ============  "+firstDayOfMonth+"  =========   "+lastDaysOfMonth);
		getweek(year,month);
	}
	
	//��һ�����е�ÿһ���ֵ���������dayNuMber��
	private void getweek(int year, int month) {
		int j = 1;
		int flag = 0;
		String lunarDay = "";

//		
		for (int i = 0; i < dayNumber.length; i++) {
			// ��һ
			if(i<7){
				dayNumber[i]=week[i]+"."+" "+"."+" ";
			}
			else if(i < firstDayOfMonth+7){  //ǰһ����
				int temp = lastDaysOfMonth - firstDayOfMonth+1-7;
				//���������Ӧ��ũ��
				
				lunarDay = lunarCalendar.getLunarDate(year, month-1, temp+i,false);
				boolean f = matchScheduleDate(year,month-1,temp+i);
				dayNumber[i] = (temp + i)+"."+lunarDay+"."+String.valueOf(f);
			}else if(i < daysOfMonth + firstDayOfMonth+7){   //����
				String day = String.valueOf(i-firstDayOfMonth+1-7); 
				//�õ�������
				lunarDay = lunarCalendar.getLunarDate(year, month, i-firstDayOfMonth+1-7,false);
				boolean f = matchScheduleDate(year,month,i-firstDayOfMonth+1-7);
				dayNumber[i] = i-firstDayOfMonth+1-7+"."+lunarDay+"."+String.valueOf(f);
				//���ڵ�ǰ�²�ȥ��ǵ�ǰ����
				if(sys_year.equals(String.valueOf(year)) && sys_month.equals(String.valueOf(month)) && sys_day.equals(day)){
					//��ǵ�ǰ����
					currentFlag = i;
				}
				

				
				setShowYear(String.valueOf(year));
				setShowMonth(String.valueOf(month));
				setAnimalsYear(lunarCalendar.animalsYear(year));
				setLeapMonth(lunarCalendar.leapMonth == 0?"":String.valueOf(lunarCalendar.leapMonth));
				setCyclical(lunarCalendar.cyclical(year));
			}else{   //��һ����
				lunarDay = lunarCalendar.getLunarDate(year, month+1, j,false);
				boolean f = matchScheduleDate(year,month+1,j);
				dayNumber[i] = j+"."+lunarDay+"."+String.valueOf(f);
				j++; 
			}
		}
        
        String dayStr = "";
        for(int i = 0; i < dayNumber.length; i++){
        	dayStr = dayStr+dayNumber[i]+":";
        }
        Log.d("calendarview",dayStr);


	}
	
	
	
	/**
	 * ���빫�������գ�ƥ���Ƿ���
	 * @param year
	 * @param month
	 * @param day
	 */
	public boolean matchScheduleDate(int year, int month, int day){
		List<BirthdayInfo> list = BirthdayInfo.binfo_list;
		for(int i=0;i<list.size();i++){
			BirthdayInfo tmpBirthdayInfo = list.get(i);
			if(tmpBirthdayInfo.getKind() == 1){
				//ũ��
//				int[] chinesedate = lunarCalendar.getLunarDateAll(Integer.valueOf(year), 
//						Integer.valueOf(month),  Integer.valueOf(day));
				int[] chinesedate = lunarCalendar.getSimapleLunarDateAll();
				
				int chinese_year = chinesedate[0];
				int chinese_month = chinesedate[1];
				int chinese_day = chinesedate[2];
				if(tmpBirthdayInfo.getDuplicatekind() == 0){
					//һ���Ի��Ҫ����
					if(tmpBirthdayInfo.getYear() == chinese_year && tmpBirthdayInfo.getMonth() == chinese_month
							&& tmpBirthdayInfo.getDay() == chinese_day){
						return true;
					}
				}else{
					//ÿ��ģ�ֻ���º���
					if(tmpBirthdayInfo.getMonth() == chinese_month
							&& tmpBirthdayInfo.getDay() == chinese_day){
						return true;
					}
				}
			}else{
				//����
				if(tmpBirthdayInfo.getDuplicatekind() == 0){
					//һ���Ի��Ҫ����
					if(tmpBirthdayInfo.getYear() == year && tmpBirthdayInfo.getMonth() == month
							&& tmpBirthdayInfo.getDay() == day){
						return true;
					}
				}else{
					//ÿ��ģ�ֻ���º���
					if(tmpBirthdayInfo.getMonth() == month
							&& tmpBirthdayInfo.getDay() == day){
						return true;
					}
				}
			}
		}
		return false;
		
	}
	
	/**
	 * ���ÿһ��itemʱ����item�е�����
	 * @param position
	 * @return
	 */
	public String getDateByClickItem(int position){
		return dayNumber[position];
	}
	
	/**
	 * �ڵ��gridViewʱ���õ�������е�һ���λ��
	 * @return
	 */
	public int getStartPositon(){
		return firstDayOfMonth+7;
	}
	
	/**
	 * �ڵ��gridViewʱ���õ�����������һ���λ��
	 * @return
	 */
	public int getEndPosition(){
		return  (firstDayOfMonth+daysOfMonth+7)-1;
	}
	
	public String getShowYear() {
		return showYear;
	}

	public void setShowYear(String showYear) {
		this.showYear = showYear;
	}

	public String getShowMonth() {
		return showMonth;
	}

	public void setShowMonth(String showMonth) {
		this.showMonth = showMonth;
	}
	
	public String getAnimalsYear() {
		return animalsYear;
	}

	public void setAnimalsYear(String animalsYear) {
		this.animalsYear = animalsYear;
	}
	
	public String getLeapMonth() {
		return leapMonth;

	}
	public void setLeapMonth(String leapMonth) {
		this.leapMonth = leapMonth;
	}
	
	public String getCyclical() {
		return cyclical;
	}

	public void setCyclical(String cyclical) {
		this.cyclical = cyclical;
	}
	
	  @Override
	public Object clone() {  
		   CalendarView o = null;
	        try{
	            o = (CalendarView)super.clone();
	        }catch(CloneNotSupportedException e){
	            e.printStackTrace();
	        } 
	        return o;
	    }  
}
