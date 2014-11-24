package com.wbw.birthday.widget.dialog;


import com.wbw.birthday.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ExitDialog {
	private static ExitDialog exitdialog;
	public static ExitDialog init(){
		if(exitdialog == null)
			exitdialog = new ExitDialog();
		return exitdialog;
	}
	
	private Context mContext = null;  
	public  Dialog creatExitDialog(Context context){
		mContext = context;
		LayoutInflater inf = LayoutInflater.from(context); 		
		final View view = inf.inflate(R.layout.exit_dialog, null);  		
		//final SharedPreferencesXml spxml = SharedPreferencesXml.init();
		
		
		
		Button ok = (Button) view.findViewById(R.id.exit_dialog_ok);
		Button cancle = (Button) view.findViewById(R.id.exit_dialog_cancle);
		
		//final AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.MyDialog);		
		//final AlertDialog dialog = builder.create();
		//��dialog��û�кڱ�
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		//dialog.setView(view , 0, 0, 0, 0 );
		dialog.setContentView(view);
		
		cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				if(dialog.isShowing())
					dialog.dismiss();
			}
		});
		
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				closeAction();
				
				if(dialog.isShowing())
					dialog.dismiss();
			}
		});
		
		return dialog;
	}
	
	
	private void closeAction(){
		// ���ʹ�û��ֹ�棬����ص��û��ֹ��ĳ�ʼ���ӿ�:
		//OffersManager.getInstance(mContext).onAppExit();
		System.exit(0);
	}
}
