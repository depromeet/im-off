package com.depromeet.tmj.im_off;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.depromeet.tmj.im_off.shared.BaseActivity;

public class SettingActivity extends BaseActivity
{
//    private Button          _btn_goWork;                //출근시간 설정버튼
//    private Button          _btn_offWork;               //퇴근시간 설정버튼
//    private Button          _btn_selectJob;             //직업 선택버튼
/////////////////////////////// < Value > /////////////////////////////////
    private LinearLayout    _layout_goWork;             //출근시간 설정버튼
    private LinearLayout    _layout_offWork;             //퇴근시간 설정버튼
    private LinearLayout    _layout_selectJob;             //직업 선택버튼
    private TextView        _view_showSelectedJob;      //선택된 직업 보여주는 곳
    //  직업선택 스크롤 픽커
    private RelativeLayout  _rayout_selectJob;
    private TextView        _view_selectJob;
    private NumberPicker    _picker_selectJob;
    private Button          _btn_confirm_selectJob;
    private Button          _btn_cancel_selectJob;
    private int             _selectedJobIndex = -1;

    // 출근 시간
    public int             _time_goWork_hour;
    public int             _time_goWork_minute;
    // 퇴근 시간
    public int             _time_offWork_hour;
    public int             _time_offWork_minute;
    // 칼퇴알람 On/Off 여부
    public Boolean         _isOn_offWork_noti;

    /////////////////////////////// < Func > /////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // < 직군 추가는 여기에 >
        final String[] values = {"경영.사무", "마케팅.홍보", "IT.개발", "디자인", "기획", "무역.유통", "영업.고객상담",
                "서비스", "연구개발.설계", "생산.제조", "교육", "의료", "법률"};

        // < 출근시간 입력버튼 >
        _layout_goWork = (LinearLayout) findViewById(R.id.layout_goWork) ;
        _layout_goWork.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(SettingActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, goWorktimeSetListener, 15, 60, false);
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });

        // < 퇴근시간 입력버튼 >
        _layout_offWork = (LinearLayout) findViewById(R.id.layout_offWork) ;
        _layout_offWork.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(SettingActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, offtimeSetListener, 15, 60, false);
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });

        // < 직군선택 버튼 >
         _layout_selectJob = (LinearLayout) findViewById(R.id.layout_select_job);
         _layout_selectJob.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingActivity.this);

                //제목
                alertDialogBuilder.setItems(values,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 직군이 선택되었을 때
                                TextView view = (TextView) findViewById(R.id.tv_select_job);
                                view.setText(values[which]);
                                dialog.dismiss();
                            }
                        });
                //Dialog 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                //다이얼로그 생성
                alertDialog.show();
            }
        });

        // < 칼퇴 알람 On/Off >
        Switch sw = (Switch) findViewById(R.id.switch_OnNoti);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                _isOn_offWork_noti = isChecked;
            }
        });

    } //onCreate

    // [ Call Back] Go Work Dialog
    private TimePickerDialog.OnTimeSetListener goWorktimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String showMsg = "";
            _time_goWork_hour   = hourOfDay;
            _time_goWork_minute = minute;

            if( 12 < hourOfDay )
            {
                showMsg += "오후 ";
                showMsg += String.format("%d : %d" , hourOfDay - 12, minute);
            }
            else
            {
                showMsg += "오전 ";
                showMsg += String.format("%d : %d" , hourOfDay, minute);
            }
            // 0이 하나만 나오는 경우 방지
            if ( 0 == minute )
            {
                showMsg += "0";
            }
            TextView tv = (TextView) findViewById(R.id.tv_goWork);
            tv.setText(showMsg);
        }
    };

    // [ Call Back] Off Work Dialog
    private TimePickerDialog.OnTimeSetListener offtimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String showMsg = "";
            _time_offWork_hour   = hourOfDay;
            _time_offWork_minute = minute;

            if( 12 < hourOfDay )
            {
                showMsg += "오후 ";
                showMsg += String.format("%d : %d" , hourOfDay - 12, minute);
            }
            else
            {
                showMsg += "오전 ";
                showMsg += String.format("%d : %d" , hourOfDay, minute);
            }
            // 0이 하나만 나오는 경우 방지
            if ( 0 == minute )
            {
                showMsg += "0";
            }
            TextView tv = (TextView) findViewById(R.id.tv_offWork);
            tv.setText(showMsg);
        }
    };
}
