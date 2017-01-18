package com.xeed.cheapnsale;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Store;

import javax.inject.Inject;

public class OrderActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @Inject
    public CheapnsaleService cheapnsaleService;

    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;

    //Dialog pickerDialog;
    NumberPicker numberPicker;

    TextView pickerCancelButton;
    TextView pickerAcceptButton;

    int selectedNumberIndex = 0;

    final ColorStateList colorStateList_select = new ColorStateList(
            new int[][]{
                    new int[]{android.R.attr.state_enabled} //enabled
            },
            new int[]{
                    Color.parseColor("#111cc4") //enabled
            }
    );
    final ColorStateList colorStateList_unselect = new ColorStateList(
            new int[][]{
                    new int[]{android.R.attr.state_enabled} //enabled
            },
            new int[]{
                    Color.parseColor("#C8000000")
            }
    );
    public MaterialDialog pickerDialog;
    private Store store;
    private String storeId = "1";

    public TextView orderPickUpTimeTextView;
    private TextView timeToPickupValue;
    private RelativeLayout todayOrderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ((Application) getApplication()).getApplicationComponent().inject(this);

        radioGroup = (RadioGroup) findViewById(R.id.order_time_rg);
        radioButton1 = (RadioButton) findViewById(R.id.order_time_now_radio_button);
        radioButton2 = (RadioButton) findViewById(R.id.order_time_today_radio_button);

        radioGroup.setOnCheckedChangeListener(this);
        radioButton1.setChecked(true);

        pickerDialog = new MaterialDialog.Builder(this)
                .customView(R.layout.popup_order_time_picker, false).build();

        numberPicker = (NumberPicker) pickerDialog.getView().findViewById(R.id.order_time_picker);

        pickerCancelButton = (TextView) pickerDialog.getView().findViewById(R.id.order_time_picker_cancel);
        pickerAcceptButton = (TextView) pickerDialog.getView().findViewById(R.id.order_time_picker_accept);

        orderPickUpTimeTextView = (TextView) findViewById(R.id.order_pick_up_time);

        timeToPickupValue = (TextView) findViewById(R.id.time_to_pickup_value);

        //TODO : 바탕영역 눌러서 dismiss 될때 지금 주문 버튼이 선택되도록!

        pickerCancelButton.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioGroup.check(radioButton1.getId());
                pickerDialog.dismiss();
            }
        });

        pickerAcceptButton.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pickTime = Integer.parseInt(orderPickUpTimeTextView.getText().toString().replaceAll("분","")) + 5*selectedNumberIndex;
                timeToPickupValue.setText(String.valueOf(pickTime));
                todayOrderLayout.setVisibility(RelativeLayout.VISIBLE);
                pickerDialog.dismiss();
            }
        });

        ImageButton backButton = (ImageButton) findViewById(R.id.main_toolbar_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                store = cheapnsaleService.getStore(storeId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                orderPickUpTimeTextView.setText(store.getAvgPrepTime()+getString(R.string.minute));
            }
        }.execute();

    }

    private void show() {
        int minTime = Integer.parseInt(store.getAvgPrepTime());
        String[] displayedValue = new String[((65 - minTime) / 5)];

        for (int i = minTime, k = 0; i <= 60; k++) {
            displayedValue[k] = String.format(getResources().getString(R.string.after_minute), i);
            i = i + 5;
        }

        numberPicker.setMaxValue(displayedValue.length - 1);
        numberPicker.setMinValue(0);
        numberPicker.setDisplayedValues(displayedValue);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int beforeIndex, int selectedIndex) {
                selectedNumberIndex = selectedIndex;
            }
        });

        pickerDialog.getWindow().setGravity(Gravity.BOTTOM);
        pickerDialog.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        displayRadioGroup(checkedId);
    }

    private void displayRadioGroup(int checkedId) {

        todayOrderLayout = (RelativeLayout) findViewById(R.id.today_order_layout);

        if (checkedId == radioButton1.getId()) {
            radioButton1.setTextColor(Color.parseColor("#111cc4"));
            radioButton2.setTextColor(Color.parseColor("#C8000000"));
            radioButton1.setTypeface(null, Typeface.BOLD);
            radioButton2.setTypeface(null, Typeface.NORMAL);
            radioButton1.setButtonTintList(colorStateList_select);
            radioButton2.setButtonTintList(colorStateList_unselect);
            todayOrderLayout.setVisibility(RelativeLayout.INVISIBLE);
            if(numberPicker != null){
                numberPicker.setValue(0);
            }
            selectedNumberIndex = 0;
        }

        if (checkedId == radioButton2.getId()) {
            radioButton1.setTextColor(Color.parseColor("#C8000000"));
            radioButton2.setTextColor(Color.parseColor("#111cc4"));
            radioButton1.setTypeface(null, Typeface.NORMAL);
            radioButton2.setTypeface(null, Typeface.BOLD);
            radioButton2.setButtonTintList(colorStateList_select);
            radioButton1.setButtonTintList(colorStateList_unselect);
            show();
        }
    }
}
