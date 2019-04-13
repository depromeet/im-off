package com.depromeet.tmj.im_off;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.depromeet.tmj.im_off.shared.BaseActivity;

public class SettingActivity extends BaseActivity
{
    /////////////////////////////// < Value > /////////////////////////////////
    private Button          _btn_goWork;                //출근시간 설정버튼
    private Button          _btn_offWork;               //퇴근시간 설정버튼
    private Button          _btn_selectJob;             //직업 선택버튼
    private TextView        _view_showSelectedJob;      //선택된 직업 보여주는 곳
    //  직업선택 스크롤 픽커
    private RelativeLayout  _rayout_selectJob;
    private TextView        _view_selectJob;
    private NumberPicker    _picker_selectJob;
    private Button          _btn_confirm_selectJob;
    private Button          _btn_cancel_selectJob;
    private int             _selectedJobIndex = -1;

    /////////////////////////////// < Func > /////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // < 직군 추가는 여기에 >
        final String[] values = {"경영.사무","마케팅.홍보", "기획", "영업.고객상담", "디자인"};

        // < 출근시간 입력버튼 >
        _btn_goWork = (Button) findViewById(R.id.btn_timepicker_am);
        _btn_goWork.setOnClickListener(new Button.OnClickListener(){
         @Override
         public void onClick(View view)
         {
             TimePickerDialog dialog = new TimePickerDialog(SettingActivity.this, AlertDialog.THEME_HOLO_LIGHT,goWorktimeSetListener,15,60,false);
             dialog.show();}
        });

        // < 퇴근시간 입력버튼 >
        _btn_offWork = (Button) findViewById(R.id.btn_timepicker_pm);
        _btn_offWork.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                TimePickerDialog dialog = new TimePickerDialog(SettingActivity.this, AlertDialog.THEME_HOLO_LIGHT,offtimeSetListener,15,60,false);
                dialog.show();}
        });

        // < 퇴근시간 입력버튼 >
        _btn_offWork = (Button) findViewById(R.id.btn_timepicker_pm);
        _btn_offWork.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                TimePickerDialog dialog = new TimePickerDialog(SettingActivity.this, AlertDialog.THEME_HOLO_LIGHT,offtimeSetListener,15,60,false);
                dialog.show();}
        });


        // -----------< 직군 선택 스크롤 >-------------

        _btn_confirm_selectJob = (Button) findViewById(R.id.btn_confirm);
        _btn_cancel_selectJob = (Button) findViewById(R.id.btn_cancel);
        _view_showSelectedJob = (TextView) findViewById(R.id.tv_select_job);

        _rayout_selectJob = (RelativeLayout) findViewById(R.id.rl);

        _picker_selectJob = (NumberPicker) findViewById(R.id.np);
        _picker_selectJob.setMinValue(0);
        _picker_selectJob.setMaxValue(values.length -1);
        _picker_selectJob.setDisplayedValues(values);
        _picker_selectJob.setWrapSelectorWheel(true); // 휠 on/off

        // 확인버튼 Listner
        _btn_confirm_selectJob.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(_selectedJobIndex == -1) {
                    // Select 된적이 없는 경우
                    _view_showSelectedJob.setText("직군을 선택해주세요");
                }
                else {
                    _view_showSelectedJob.setText("직군 선택 : " + values[_selectedJobIndex]);
                }
                visibleBackgroundForPicker(Boolean.TRUE);
                _rayout_selectJob.setVisibility(View.INVISIBLE);
            }
        });

        // 취소버튼 Listner
        _btn_cancel_selectJob.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                visibleBackgroundForPicker(Boolean.TRUE);
                _rayout_selectJob.setVisibility(View.INVISIBLE);
            }
        });

        // 직군선택 Listner
        _picker_selectJob.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                _selectedJobIndex = newVal;
            }
        });
        _rayout_selectJob.setVisibility(View.INVISIBLE);


        //-------------------------------------------------

        // < 직군 선택버튼 >
        _btn_selectJob = (Button) findViewById(R.id.btn_select_job);
        _btn_selectJob.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                //visibleBackgroundForPicker(Boolean.FALSE);
                _rayout_selectJob.setVisibility(View.VISIBLE);
            }
        });
    }

    // 배경버튼 날리기
    private void visibleBackgroundForPicker(Boolean isVisible)
    {
        if(isVisible)
        {
            _btn_goWork.setVisibility(View.VISIBLE);
            _btn_offWork.setVisibility(View.VISIBLE);
            _btn_selectJob.setVisibility(View.VISIBLE);
            findViewById(R.id.tv_goto_work).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_get_off).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_title).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_am).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_pm).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_select_job).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_worktime).setVisibility(View.VISIBLE);
        }
        else {
            _btn_goWork.setVisibility(View.INVISIBLE);
            _btn_offWork.setVisibility(View.INVISIBLE);
            _btn_selectJob.setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_goto_work).setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_get_off).setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_title).setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_am).setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_pm).setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_select_job).setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_worktime).setVisibility(View.INVISIBLE);
        }
    };

    private TimePickerDialog.OnTimeSetListener goWorktimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String msg = String.format("%d : %d" , hourOfDay, minute);
            _btn_goWork.setText(msg);
        }
    };

    private TimePickerDialog.OnTimeSetListener offtimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String msg = String.format("%d : %d" , hourOfDay, minute);
            _btn_offWork.setText(msg);
        }
    };
}
