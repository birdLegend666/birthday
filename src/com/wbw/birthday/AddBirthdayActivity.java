package com.wbw.birthday;


import java.util.ArrayList;

import com.wbw.birthday.calender.LunarCalendar;

import android.R.integer;
import android.R.interpolator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddBirthdayActivity extends Activity{
	private int rilikind = 0;  //0Ϊ������1Ϊũ��
	private int tixingkind = 0;  //0 1 2 3 4 5
	private int duplicatekind = 0; //0һ��     1ÿ��
	
	ArrayList<String> scheduleDate = new ArrayList<String>();
	String scheduleDay ;  //��һ�������
	String scheduleYear;
    String scheduleMonth;
    String week;
    String chinese_year;
    
    private String dateInfo_yangli,dateInfo_yinli;
    private LunarCalendar lcCalendar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addbirthday);
		Bundle bundle = getIntent().getExtras();
		scheduleDate = bundle.getStringArrayList("info");
		scheduleYear =  scheduleDate.get(0);
		scheduleMonth =  scheduleDate.get(1);
		scheduleDay =  scheduleDate.get(2);
		lcCalendar=new LunarCalendar();
		dateInfo_yangli=scheduleYear+"��"+scheduleMonth+"��"+scheduleDay+"��";
       	//�ȴ��������ٴ���ũ��
		findAllViews();
		setDefaults();
//		setImages();
		createAction();
	}
	
	private ImageView iv_return,iv_complete;
	private TextView tv_title,tv_selectrili,tv_select_year,tv_select_hour,tv_tixingkind,tv_duplicate;
	private EditText et_inputname,et_inputremark;
	private Button bt_option_gongli,bt_option_nongli;
	private Button bt_option_0,bt_option_1,bt_option_2,bt_option_3,bt_option_4,bt_option_5;
	private Button bt_option_once,bt_option_everyyear;
	private Button[] bt_options ;
	private void findAllViews(){
		iv_return = (ImageView) findViewById(R.id.iv_return);
		iv_complete = (ImageView) findViewById(R.id.iv_complete);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_selectrili = (TextView) findViewById(R.id.tv_selectrili);
		tv_select_year = (TextView) findViewById(R.id.tv_select_year);
		tv_select_hour = (TextView) findViewById(R.id.tv_select_hour);
		tv_tixingkind = (TextView) findViewById(R.id.tv_tixingkind);
		tv_duplicate = (TextView) findViewById(R.id.tv_duplicate);
		et_inputname = (EditText) findViewById(R.id.et_inputname);
		et_inputremark = (EditText) findViewById(R.id.et_inputremark);
		bt_option_gongli = (Button) findViewById(R.id.bt_option_gongli);
		bt_option_nongli = (Button) findViewById(R.id.bt_option_nongli);
		bt_option_0 = (Button) findViewById(R.id.bt_option_0);
		bt_option_1 = (Button) findViewById(R.id.bt_option_1);
		bt_option_2 = (Button) findViewById(R.id.bt_option_2);
		bt_option_3 = (Button) findViewById(R.id.bt_option_3);
		bt_option_4 = (Button) findViewById(R.id.bt_option_4);
		bt_option_5 = (Button) findViewById(R.id.bt_option_5);
		Button[] tButtons = {bt_option_0,bt_option_1,bt_option_2,bt_option_3,bt_option_4,bt_option_5};
		bt_options = tButtons;
		bt_option_once = (Button) findViewById(R.id.bt_option_once);
		bt_option_everyyear = (Button) findViewById(R.id.bt_option_everyyear);
	}
	
	private void setDefaults(){
		tv_title.setText(R.string.add_birthday);
		tv_select_year.setText(dateInfo_yangli);
		setOptionSelectRiLi(rilikind);
		setOptionTiXingKind(tixingkind);
		setOptionDuplicate(duplicatekind);
	}
	
	
	private void createAction(){
		iv_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddBirthdayActivity.this.finish();
			}
		});
		
		bt_option_gongli.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				setOptionSelectRiLi(0);
			}
		});
		bt_option_nongli.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO �Զ����ɵķ������
						setOptionSelectRiLi(1);
					}
		});
		for(int i=0;i<bt_options.length;i++){
			final int tmp = i;
			bt_options[i].setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					setOptionTiXingKind(tmp);
				}
			});
		}
		bt_option_once.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setOptionDuplicate(0);
			}
		});
		bt_option_everyyear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setOptionDuplicate(1);
			}
		});
	}
	
	//���������ѡ��
	private void setOptionSelectRiLi(int num){
		//1Ϊ9����  2Ϊ12���� 3Ϊ15����
		rilikind = num;
		bt_option_gongli.setBackgroundResource(R.drawable.option);
		bt_option_nongli.setBackgroundResource(R.drawable.option);		
		String meitu = getString(R.string.select_rili);
		String every = getString(R.string.everty_year);
		String yeartString = tv_select_year.getText().toString();
		if(num == 0 ){
			bt_option_gongli.setBackgroundResource(R.drawable.option_select);
			meitu = String.format(meitu, bt_option_gongli.getText().toString());
			every = String.format(every, bt_option_gongli.getText().toString(),yeartString);
		}else
		if(num == 1 ){
			bt_option_nongli.setBackgroundResource(R.drawable.option_select);
			meitu = String.format(meitu, bt_option_nongli.getText().toString());
			every = String.format(every, bt_option_nongli.getText().toString(),yeartString);
		}
		tv_selectrili.setText(meitu);
		//���ѡ��������������
		
		bt_option_everyyear.setText(every);
	}
	
	//���ѷ�ʽ��ѡ��
	private void setOptionTiXingKind(int num){
		setBtOptionNormal();
		tixingkind = num;
		bt_options[num].setBackgroundResource(R.drawable.option_select);
		String meitu = getString(R.string.tixingkind);
		meitu = String.format(meitu, bt_options[num].getText().toString());	
		tv_tixingkind.setText(meitu);
	}
	
	private void setBtOptionNormal(){
		for(int i=0;i<bt_options.length;i++){
			bt_options[i].setBackgroundResource(R.drawable.option);
		}
	}
	
	private void setOptionDuplicate(int num){
		duplicatekind = num;
		bt_option_once.setBackgroundResource(R.drawable.option);
		bt_option_everyyear.setBackgroundResource(R.drawable.option);		
		String meitu = getString(R.string.duplicate);
		if(num == 0 ){
			bt_option_once.setBackgroundResource(R.drawable.option_select);
			meitu = String.format(meitu, bt_option_once.getText().toString());
			
		}else
		if(num == 1 ){
			bt_option_everyyear.setBackgroundResource(R.drawable.option_select);
			meitu = String.format(meitu, bt_option_everyyear.getText().toString());		
		}
		tv_duplicate.setText(meitu);
	}
	
}
