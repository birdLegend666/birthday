package com.wbw.birthday;

//������
//���ǲ��԰�����
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;



import com.wbw.birthday.effect.MyAnimation;
import com.wbw.birthday.effect.TouchLight_dark;
import com.wbw.birthday.effect.TouchLight_light;
import com.wbw.birthday.widget.BorderText;
import com.wbw.birthday.widget.BorderTextView;
import com.wbw.birthday.widget.CalendarView;

import android.os.Bundle;
import android.R.bool;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnGestureListener,OnClickListener,OnLongClickListener {
	private static final String Tag="CalendarActivity";
	private static int jumpMonth = 0;      //ÿ�λ��������ӻ��ȥһ����,Ĭ��Ϊ0������ʾ��ǰ�£�
	private static int jumpYear = 0;       //������Խһ�꣬�����ӻ��߼�ȥһ��,Ĭ��Ϊ0(����ǰ��)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";
	private ViewFlipper flipper = null;
	private GestureDetector gestureDetector = null;
	private CalendarView calV = null;
	private GridView gridView = null;
	private BorderText topText = null;
	private Drawable draw = null;
	private BorderTextView schdule_tip;
	private Button add;
	private Button quit;
	private TextView day_tv;
	private TextView launarDay;
	private ListView listView;
	private TextView weekday;
	private TextView lunarTime;
	private ListView list;
	private String dateInfo;//���gridview��������Ϣ
	private LayoutInflater inflater;
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = MainActivity.this;
		Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    	currentDate = sdf.format(date);  //��������
    	year_c = Integer.parseInt(currentDate.split("-")[0]);
    	month_c = Integer.parseInt(currentDate.split("-")[1]);
    	day_c = Integer.parseInt(currentDate.split("-")[2]);
    	gestureDetector = new GestureDetector(this);
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.removeAllViews();
        calV = new CalendarView(this, getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
        
        addGridView();
        gridView.setAdapter(calV);
        //flipper.addView(gridView);
        flipper.addView(gridView,0);
        
		topText = (BorderText) findViewById(R.id.schedule_toptext);
		addTextToTopTextView(topText);
		
		addMenu();
	}
	
	private RelativeLayout relate_level2;
	private TextView birthday_goin,config_goin;
	private boolean areLevel2Showing = false;
	private boolean isleftshowing = false,isrightshowing = false;
	private ImageView iv_goto,iv_today,iv_convert;
	private ImageView home;
	private void addMenu(){
		relate_level2 = (RelativeLayout) findViewById(R.id.relate_level2);
		//relate_level3 = (RelativeLayout) findViewById(R.id.relate_level3);
		home = (ImageView) findViewById(R.id.home);
		birthday_goin = (TextView) findViewById(R.id.birthday_goin);
		birthday_goin.setVisibility(View.GONE);
		config_goin = (TextView) findViewById(R.id.config_goin);
		config_goin.setVisibility(View.GONE);
		iv_goto = (ImageView) findViewById(R.id.iv_goto);
		iv_today = (ImageView) findViewById(R.id.iv_today);
		iv_convert = (ImageView) findViewById(R.id.iv_convert);
		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!areLevel2Showing) {
					MyAnimation.startAnimationsIn(relate_level2, 500);
				} else {				
					MyAnimation.startAnimationsOut(relate_level2, 500, 0);					
				}
				areLevel2Showing = !areLevel2Showing;
				if(isleftshowing){
					//��ʾ�У�Ҫ����
					MyAnimation.startAnimationsHide(birthday_goin,500,
							MainActivity.this,R.anim.push_right_out);
				}else{
					MyAnimation.startAnimationsShow(birthday_goin,500,
							MainActivity.this,R.anim.push_right_in);
				}
				isleftshowing = !isleftshowing;
				if(isrightshowing){
					//��ʾ�У�Ҫ����
					MyAnimation.startAnimationsHide(config_goin,500,
							MainActivity.this,R.anim.push_left_out);
				}else{
					MyAnimation.startAnimationsShow(config_goin,500,
							MainActivity.this,R.anim.push_left_in);
				}
				isrightshowing = !isrightshowing;
			}
		});
		iv_goto.setOnTouchListener(TouchLight_light.init());
		iv_today.setOnTouchListener(TouchLight_light.init());
		iv_convert.setOnTouchListener(TouchLight_light.init());
		iv_today.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionMenu(type_today);
			}
		});
		iv_goto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionMenu(type_goto);
			}
		});
		iv_convert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionMenu(type_convert);
			}
		});
	}
	
	private final int type_today = 1,type_goto=2,type_convert = 3;
	private void actionMenu(int type){
        switch (type){
        case type_today:
        	//��ת������
        	int xMonth = jumpMonth;
        	int xYear = jumpYear;
        	int gvFlag =0;
        	jumpMonth = 0;
        	jumpYear = 0;
        	addGridView();   //���һ��gridView
        	year_c = Integer.parseInt(currentDate.split("-")[0]);
        	month_c = Integer.parseInt(currentDate.split("-")[1]);
        	day_c = Integer.parseInt(currentDate.split("-")[2]);
        	calV = new CalendarView(this, getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
	        gridView.setAdapter(calV);
	        addTextToTopTextView(topText);
	        gvFlag++;
	        flipper.addView(gridView,gvFlag);
	        if(xMonth == 0 && xYear == 0){
	        	//nothing to do
	        }else if((xYear == 0 && xMonth >0) || xYear >0){
	        	this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_in));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_out));
				this.flipper.showNext();
	        }else{
	        	this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_right_in));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_right_out));
				this.flipper.showPrevious();
	        }
			flipper.removeViewAt(0);
        	break;
        case type_goto:
        	
        	new DatePickerDialog(this, new OnDateSetListener() {
				
				
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					//1901-1-1 ----> 2049-12-31
					if(year < 1901 || year > 2049){
						//���ڲ�ѯ��Χ��
						new AlertDialog.Builder(mContext).setTitle("��������").setMessage("��ת���ڷ�Χ(1901/1/1-2049/12/31)").setPositiveButton("ȷ��", null).show();
					}else{
						int gvFlag = 0;
						addGridView();   //���һ��gridView
			        	calV = new CalendarView(mContext, mContext.getResources(),year,monthOfYear+1,dayOfMonth);
				        gridView.setAdapter(calV);
				        addTextToTopTextView(topText);
				        gvFlag++;
				        flipper.addView(gridView,gvFlag);
				        if(year == year_c && monthOfYear+1 == month_c){
				        	//nothing to do
				        }
				        if((year == year_c && monthOfYear+1 > month_c) || year > year_c ){
				        	MainActivity.this.flipper.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.push_left_in));
				        	MainActivity.this.flipper.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.push_left_out));
				        	MainActivity.this.flipper.showNext();
				        }else{
				        	MainActivity.this.flipper.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.push_right_in));
				        	MainActivity.this.flipper.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.push_right_out));
				        	MainActivity.this.flipper.showPrevious();
				        }
				        flipper.removeViewAt(0);
				        //��ת֮����ת֮�����������Ϊ��������
				        year_c = year;
						month_c = monthOfYear+1;
						day_c = dayOfMonth;
						jumpMonth = 0;
						jumpYear = 0;
					}
				}
			},year_c, month_c-1, day_c).show();
        	break;
        	
        case type_convert:
        //	Intent mIntent=new Intent(CalendarActivity.this, CalendarConvertTrans.class);
       // startActivity(mIntent);
        	
        	break;
        	
        }
	
	}
	

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int gvFlag = 0;         //ÿ�����gridview��viewflipper��ʱ���ı��
		if (e1.getX() - e2.getX() > 50) {
            //���󻬶�
			addGridView();   //���һ��gridView
			jumpMonth++;     //��һ����
			
			calV = new CalendarView(this, getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
	        gridView.setAdapter(calV);
	        //flipper.addView(gridView);
	        addTextToTopTextView(topText);
	        gvFlag++;
	        flipper.addView(gridView, gvFlag);
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_out));
			this.flipper.showNext();
			flipper.removeViewAt(0);
			return true;
		} else if (e1.getX() - e2.getX() < -50) {
            //���һ���
			addGridView();   //���һ��gridView
			jumpMonth--;     //��һ����
			
			calV = new CalendarView(this, getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
	        gridView.setAdapter(calV);
	        gvFlag++;
	        addTextToTopTextView(topText);
	        //flipper.addView(gridView);
	        flipper.addView(gridView,gvFlag);
	        
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_right_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_right_out));
			this.flipper.showPrevious();
			flipper.removeViewAt(0);
			return true;
		}
		return false;
	}

	public boolean onTouchEvent(MotionEvent event) {

		return this.gestureDetector.onTouchEvent(event);
	}

	
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * ��ӻ���ͷ������� �����µ���Ϣ
	 * */
	public void addTextToTopTextView(TextView view){
		StringBuffer textDate = new StringBuffer();
		draw = getResources().getDrawable(R.drawable.schedule_title_bg);
		view.setBackgroundDrawable(draw);
		textDate.append(calV.getShowYear()).append("��").append(
				calV.getShowMonth()).append("��").append("\t");
		if (!calV.getLeapMonth().equals("") && calV.getLeapMonth() != null) {
			textDate.append("��").append(calV.getLeapMonth()).append("��")
					.append("\t");
		}
		textDate.append(calV.getAnimalsYear()).append("��").append("(").append(
				calV.getCyclical()).append("��)");
		view.setText(textDate);
		view.setTextColor(Color.WHITE);
		view.setTextSize(15.0f);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}
	
	
	//���ũ����Ϣ
	public void addLunarDayInfo(TextView text){
		StringBuffer textDate = new StringBuffer();
		if (!calV.getLeapMonth().equals("") && calV.getLeapMonth() != null) {
			textDate.append("��").append(calV.getLeapMonth()).append("��")
					.append("\t");
		}
		textDate.append(calV.getAnimalsYear()).append("��").append("(").append(
				calV.getCyclical()).append("��)");
		text.setText(textDate);
	}
	
	//���gridview,��ʾ���������
	@SuppressLint("ResourceAsColor")
	private void addGridView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		//ȡ����Ļ�Ŀ�Ⱥ͸߶�
		WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int Width = display.getWidth(); 
        int Height = display.getHeight();
        
        Log.d(Tag, "��Ļ�ֱ���=="+"height*weight"+Height+Width);
        
		gridView = new GridView(this);
		gridView.setNumColumns(7);
		gridView.setColumnWidth(46);
	//	gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		if(Width == 480 && Height == 800){
			gridView.setColumnWidth(69);
		}else if(Width==800&&Height==1280){
			gridView.setColumnWidth(69);
		}
		
		
		gridView.setGravity(Gravity.CENTER_VERTICAL);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT)); // ȥ��gridView�߿�
		gridView.setVerticalSpacing(1);
		gridView.setHorizontalSpacing(1);
        gridView.setBackgroundResource(R.drawable.gridview_bk);
		gridView.setOnTouchListener(new OnTouchListener() {
            //��gridview�еĴ����¼��ش���gestureDetector
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return MainActivity.this.gestureDetector
						.onTouchEvent(event);
			}
		});

		
		gridView.setOnItemClickListener(new OnItemClickListener() {
            //gridView�е�ÿһ��item�ĵ���¼�
			
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				  //����κ�һ��item���õ����item������(�ų�����������յ�����(�������Ӧ))
				  int startPosition = calV.getStartPositon();
				  int endPosition = calV.getEndPosition();
				  if(startPosition <= position  && position <= endPosition){
					  String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0];  //��һ�������
					  //String scheduleLunarDay = calV.getDateByClickItem(position).split("\\.")[1];  //��һ�������
	                  String scheduleYear = calV.getShowYear();
	                  String scheduleMonth = calV.getShowMonth();
	                  String week = "";
	                 
	                  Log.i("�ճ���ʷ���", scheduleDay);
	                  
	                  //ͨ�����ڲ�ѯ��һ���Ƿ񱻱�ǣ����������ճ̾Ͳ�ѯ������������ճ���Ϣ
	                 // scheduleIDs = dao.getScheduleByTagDate(Integer.parseInt(scheduleYear), Integer.parseInt(scheduleMonth), Integer.parseInt(scheduleDay));
	                  
	                  //�õ���һ�������ڼ�
	                  switch(position%7){
	                  case 0:
	                	  week = "������";
	                	  break;
	                  case 1:
	                	  week = "����һ";
	                	  break;
	                  case 2:
	                	  week = "���ڶ�";
	                	  break;
	                  case 3:
	                	  week = "������";
	                	  break;
	                  case 4:
	                	  week = "������";
	                	  break;
	                  case 5:
	                	  week = "������";
	                	  break;
	                  case 6:
	                	  week = "������";
	                	  break;
	                  }
					 
	            
	                	  
	                  }else{ //���û�б��λֱ�������Ϊ�����ް��š�
	                 
	                	  
	                	  schdule_tip.setText("���ް���");
	                	  listView.setVisibility(View.INVISIBLE);
	                	
	                  }	                  
	               
			}
		});
		gridView.setLayoutParams(params);
	}
	
	/**
	 * 
	 * ���������Ӧ���ճ̰���*/
		
		
		
		
		
	
		 @Override
			protected void onRestart() {
				int xMonth = jumpMonth;
		    	int xYear = jumpYear;
		    	int gvFlag =0;
		    	jumpMonth = 0;
		    	jumpYear = 0;
		    	addGridView();   //���һ��gridView
		    	year_c = Integer.parseInt(currentDate.split("-")[0]);
		    	month_c = Integer.parseInt(currentDate.split("-")[1]);
		    	day_c = Integer.parseInt(currentDate.split("-")[2]);
		    	calV = new CalendarView(this, getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
		        gridView.setAdapter(calV);
		        addTextToTopTextView(topText);
		        gvFlag++;
		        flipper.addView(gridView,gvFlag);
				flipper.removeViewAt(0);
				super.onRestart();
			}

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}



			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				return false;
			}
	
}
