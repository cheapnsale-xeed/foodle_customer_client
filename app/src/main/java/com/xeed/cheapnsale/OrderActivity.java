package com.xeed.cheapnsale;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xeed.cheapnsale.activity.PaymentActivity;
import com.xeed.cheapnsale.adapter.OrderCartItemListAdapter;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Store;

import java.text.DecimalFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity {

    int selectedNumberIndex = 0;

    @Inject
    public CheapnsaleService cheapnsaleService;

    @BindView(R.id.order_time_rg)
    public RadioGroup radioPickupTimeGroup;

    @BindView(R.id.order_time_now_radio_button)
    public RadioButton radioPickupTimeNow;

    @BindView(R.id.order_time_today_radio_button)
    public RadioButton radioPickupTimeToday;

    @BindView(R.id.order_payment_group)
    public RadioGroup radioPaymentGroup;

    @BindView(R.id.order_payment_credit)
    public RadioButton radioPaymentCredit;

    @BindView(R.id.order_payment_kakaopay)
    public RadioButton radioPaymentKakaopay;

    @BindView(R.id.order_payment_mobile_phone)
    public RadioButton radioPaymentMobile;

    @BindView(R.id.order_pick_up_time)
    public TextView orderPickUpTimeTextView;

    @BindView(R.id.time_to_pickup_value)
    public TextView timeToPickupValue;

    @BindView(R.id.today_order_layout)
    public RelativeLayout todayOrderLayout;

    @BindView(R.id.main_toolbar_back_button)
    public ImageButton backButton;

    @BindView(R.id.order_detail_title)
    public LinearLayout orderDetailTitleLayout;

    @BindView(R.id.order_detail_total_price)
    public TextView orderDetailTotalPrice;

    @BindView(R.id.order_cart_item_list_recycler_view)
    public RecyclerView orderCartItemListView;

    @BindView(R.id.layout_agree_payment)
    public LinearLayout agreePaymentLayout;

    NumberPicker numberPicker;
    TextView pickerCancelButton;
    TextView pickerAcceptButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ((Application) getApplication()).getApplicationComponent().inject(this);
        ButterKnife.bind(this);

        radioPickupTimeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                displayPickupTimeRadioGroup(checkedId);
            }
        });
        radioPickupTimeNow.setChecked(true);

        radioPaymentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                displayPaymentRadioGroup(checkedId);
            }
        });
        radioPaymentCredit.setChecked(true);

        pickerDialog = new MaterialDialog.Builder(this)
                .customView(R.layout.popup_order_time_picker, false).build();

        numberPicker = (NumberPicker) pickerDialog.getView().findViewById(R.id.order_time_picker);
        pickerCancelButton = (TextView) pickerDialog.getView().findViewById(R.id.order_time_picker_cancel);
        pickerAcceptButton = (TextView) pickerDialog.getView().findViewById(R.id.order_time_picker_accept);

        pickerCancelButton.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioPickupTimeGroup.check(radioPickupTimeNow.getId());
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        orderDetailTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View orderCartBar = findViewById(R.id.order_cart_bar);
                RecyclerView orderCartListView = (RecyclerView) findViewById(R.id.order_cart_item_list_recycler_view);
                ImageView orderListFoldingButton = (ImageView) findViewById(R.id.order_detail_list_button);

                if (orderCartBar.getVisibility() == View.GONE) {
                    orderListFoldingButton.setImageResource(R.drawable.ico_close);

                    orderCartBar.setVisibility(View.VISIBLE);
                    orderCartListView.setVisibility(View.VISIBLE);

                } else {
                    orderListFoldingButton.setImageResource(R.drawable.ico_open);

                    orderCartBar.setVisibility(View.GONE);
                    orderCartListView.setVisibility(View.GONE);
                }
            }
        });

        final DecimalFormat formatter = new DecimalFormat("#,###,###");

        orderDetailTotalPrice.setText(""+formatter.format(((Application) getApplication()).getCart().getTotalPrice()));

        // Order list Adapter
        OrderCartItemListAdapter orderCartItemListAdapter =
                new OrderCartItemListAdapter(((Application) getApplication()).getCart().getCartItems());

        orderCartItemListView.setLayoutManager(new LinearLayoutManager(getApplication()));
        orderCartItemListView.setAdapter(orderCartItemListAdapter);

        agreePaymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, PaymentActivity.class);
                startActivity(intent);
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

    private void displayPickupTimeRadioGroup(int checkedId) {

        if (checkedId == radioPickupTimeNow.getId()) {
            radioPickupTimeNow.setTextColor(Color.parseColor("#111cc4"));
            radioPickupTimeToday.setTextColor(Color.parseColor("#C8000000"));
            radioPickupTimeNow.setTypeface(null, Typeface.BOLD);
            radioPickupTimeToday.setTypeface(null, Typeface.NORMAL);
            radioPickupTimeNow.setButtonTintList(colorStateList_select);
            radioPickupTimeToday.setButtonTintList(colorStateList_unselect);
            todayOrderLayout.setVisibility(RelativeLayout.INVISIBLE);
            if(numberPicker != null){
                numberPicker.setValue(0);
            }
            selectedNumberIndex = 0;
        }

        if (checkedId == radioPickupTimeToday.getId()) {
            radioPickupTimeNow.setTextColor(Color.parseColor("#C8000000"));
            radioPickupTimeToday.setTextColor(Color.parseColor("#111cc4"));
            radioPickupTimeNow.setTypeface(null, Typeface.NORMAL);
            radioPickupTimeToday.setTypeface(null, Typeface.BOLD);
            radioPickupTimeToday.setButtonTintList(colorStateList_select);
            radioPickupTimeNow.setButtonTintList(colorStateList_unselect);
            show();
        }
    }

    private void displayPaymentRadioGroup(int checkedId) {

        if (checkedId == radioPaymentCredit.getId()) {
            radioPaymentCredit.setTextColor(Color.parseColor("#111cc4"));
            radioPaymentKakaopay.setTextColor(Color.parseColor("#C8000000"));
            radioPaymentMobile.setTextColor(Color.parseColor("#C8000000"));

            radioPaymentCredit.setTypeface(null, Typeface.BOLD);
            radioPaymentKakaopay.setTypeface(null, Typeface.NORMAL);
            radioPaymentMobile.setTypeface(null, Typeface.NORMAL);

            radioPaymentCredit.setButtonTintList(colorStateList_select);
            radioPaymentKakaopay.setButtonTintList(colorStateList_unselect);
            radioPaymentMobile.setButtonTintList(colorStateList_unselect);
        }

        if (checkedId == radioPaymentKakaopay.getId()) {
            radioPaymentKakaopay.setTextColor(Color.parseColor("#111cc4"));
            radioPaymentCredit.setTextColor(Color.parseColor("#C8000000"));
            radioPaymentMobile.setTextColor(Color.parseColor("#C8000000"));

            radioPaymentKakaopay.setTypeface(null, Typeface.BOLD);
            radioPaymentCredit.setTypeface(null, Typeface.NORMAL);
            radioPaymentMobile.setTypeface(null, Typeface.NORMAL);

            radioPaymentKakaopay.setButtonTintList(colorStateList_select);
            radioPaymentCredit.setButtonTintList(colorStateList_unselect);
            radioPaymentMobile.setButtonTintList(colorStateList_unselect);
        }

        if (checkedId == radioPaymentMobile.getId()) {
            radioPaymentMobile.setTextColor(Color.parseColor("#111cc4"));
            radioPaymentKakaopay.setTextColor(Color.parseColor("#C8000000"));
            radioPaymentCredit.setTextColor(Color.parseColor("#C8000000"));

            radioPaymentMobile.setTypeface(null, Typeface.BOLD);
            radioPaymentKakaopay.setTypeface(null, Typeface.NORMAL);
            radioPaymentCredit.setTypeface(null, Typeface.NORMAL);

            radioPaymentMobile.setButtonTintList(colorStateList_select);
            radioPaymentKakaopay.setButtonTintList(colorStateList_unselect);
            radioPaymentCredit.setButtonTintList(colorStateList_unselect);
        }
    }
}
