package com.xeed.cheapnsale.activity;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.OrderCartItemListAdapter;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Cart;
import com.xeed.cheapnsale.service.model.Order;
import com.xeed.cheapnsale.service.model.Payment;
import com.xeed.cheapnsale.service.model.Store;
import com.xeed.cheapnsale.util.DateUtil;
import com.xeed.cheapnsale.util.NumbersUtil;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity {

    @Inject
    public CheapnsaleService cheapnsaleService;

    @BindView(R.id.radio_group_pickup_time_order)
    public RadioGroup radioGroupPickupTimeOrder;

    @BindView(R.id.radio_now_button_order)
    public RadioButton radioNowButtonOrder;

    @BindView(R.id.radio_today_button_order)
    public RadioButton radioTodayButtonOrder;

    @BindView(R.id.radio_group_payment_way_order)
    public RadioGroup radioGroupPaymentWayOrder;

    @BindView(R.id.radio_credit_button_order)
    public RadioButton radioCreditButtonOrder;

    @BindView(R.id.radio_kakaopay_button_order)
    public RadioButton radioKakaopayButtonOrder;

    @BindView(R.id.radio_mobile_button_order)
    public RadioButton radioMobileButtonOrder;

    @BindView(R.id.text_pickup_time_order)
    public TextView textPickupTimeOrder;

    @BindView(R.id.text_dialog_picked_time_order)
    public TextView textDialogPickedTimeOrder;

    @BindView(R.id.relative_today_detail_order)
    public RelativeLayout relativeTodayDetailOrder;

    @BindView(R.id.image_back_button_order)
    public ImageView imageBackButtonOrder;

    @BindView(R.id.linear_detail_order)
    public LinearLayout linearDetailOrder;

    @BindView(R.id.text_total_price_order)
    public TextView textTotalPriceOrder;

    @BindView(R.id.recycler_detail_list_order)
    public RecyclerView recyclerDetailListOrder;

    @BindView(R.id.layout_agree_payment)
    public LinearLayout linearAgreePayment;

    @BindView(R.id.image_detail_pickup_term)
    public ImageView imageDetailPickupTerm;

    @BindView(R.id.linear_order_detail_pickup_term)
    public LinearLayout linearOrderDetailPickupTerm;

    @BindView(R.id.button_reselect_time_order)
    public Button buttonReselectTimeOrder;

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
    private NumberPicker numberPickerOrder;
    private TextView textCancelButtonPicker;
    private TextView textAcceptButtonPicker;

    private Store store;
    private String storeId;

    public int selectedNumberIndex = 0;
    private Payment payment;
    private boolean isReset = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ((Application) getApplication()).getApplicationComponent().inject(this);
        ButterKnife.bind(this);

        Cart cart = ((Application) getApplication()).getCart();
        storeId = cart.getStoreId();

        radioGroupPickupTimeOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                displayPickupTimeRadioGroup(checkedId);
            }
        });
        radioNowButtonOrder.setChecked(true);

        radioGroupPaymentWayOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                displayPaymentRadioGroup(checkedId);
            }
        });
        radioCreditButtonOrder.setChecked(true);

        pickerDialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_order_time_picker, false).build();

        numberPickerOrder = (NumberPicker) pickerDialog.getView().findViewById(R.id.number_picker_order);
        textCancelButtonPicker = (TextView) pickerDialog.getView().findViewById(R.id.text_cancel_button_picker);
        textAcceptButtonPicker = (TextView) pickerDialog.getView().findViewById(R.id.text_accept_button_picker);

        textCancelButtonPicker.setOnClickListener(new TextView.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!isReset) {
                    radioGroupPickupTimeOrder.check(radioNowButtonOrder.getId());
                }
                pickerDialog.dismiss();
            }
        });

        textAcceptButtonPicker.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pickTime = Integer.parseInt(textPickupTimeOrder.getText().toString().replaceAll("분","")) + 5*selectedNumberIndex;
                textDialogPickedTimeOrder.setText(String.valueOf(pickTime));
                relativeTodayDetailOrder.setVisibility(RelativeLayout.VISIBLE);
                pickerDialog.dismiss();
            }
        });

        textTotalPriceOrder.setText(""+ NumbersUtil.format(((Application) getApplication()).getCart().getTotalPrice()));

        // Order list Adapter
        OrderCartItemListAdapter orderCartItemListAdapter =
                new OrderCartItemListAdapter(((Application) getApplication()).getCart().getMenus());

        recyclerDetailListOrder.setLayoutManager(new LinearLayoutManager(getApplication()));
        recyclerDetailListOrder.setAdapter(orderCartItemListAdapter);

        linearAgreePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTask<Void, Void, Void>() {
                    Cart cart = ((Application) getApplication()).getCart();
                    Order order = new Order();

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();

                        order.setOrderId(UUID.randomUUID().toString().replaceAll("-",""));
                        order.setMenus(cart.getMenus());
                        order.setStoreId(storeId);
                        order.setStatus(Order.STATUS.READY.name());
                        order.setStoreName(store.getName());
                        order.setEmail(((Application) getApplication()).getUserEmail());
                        order.setFcmToken(((Application) getApplication()).getFcmToken());

                        if (radioNowButtonOrder.isChecked()) {
                            order.setPickupTime(DateUtil.calcPickupTime(DateUtil.getCurrentTime(), store.getAvgPrepTime()));
                        } else {
                            order.setPickupTime(DateUtil.calcPickupTime(DateUtil.getCurrentTime(), textDialogPickedTimeOrder.getText().toString()));
                        }

                        order.setOrderAt(DateUtil.getCurrentTime());
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        payment = cheapnsaleService.putPreparePayment(order);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        Intent intent = new Intent(OrderActivity.this, PaymentActivity.class);
                        intent.putExtra("payment", payment);
                        intent.putExtra("order", order);
                        startActivity(intent);
                    }
                }.execute();
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
                textPickupTimeOrder.setText(store.getAvgPrepTime()+getString(R.string.minute));
            }
        }.execute();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.image_back_button_order)
    public void onClickBackButtonImage(View view) {
        onBackPressed();
    }

    @OnClick(R.id.linear_detail_order)
    public void onClickLinearDetailOrderLayout(View view) {
        View orderCartBar = findViewById(R.id.view_detail_horizon_bar_order);
        RecyclerView orderCartListView = (RecyclerView) findViewById(R.id.recycler_detail_list_order);
        ImageView orderListFoldingButton = (ImageView) findViewById(R.id.image_detail_status_order);

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

    @OnClick(R.id.image_detail_pickup_term)
    public void onClickImageDetailPickupTerm(View view) {
        if(linearOrderDetailPickupTerm.getVisibility() == View.GONE) {
            imageDetailPickupTerm.setImageResource(R.drawable.ico_close);
            linearOrderDetailPickupTerm.setVisibility(View.VISIBLE);
        } else {
            imageDetailPickupTerm.setImageResource(R.drawable.ico_open);
            linearOrderDetailPickupTerm.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.button_reselect_time_order)
    public void onClickButtonReselectTimeOrder(View view) {
        isReset = true;
        show();
    }

    private void show() {

        int minTime = Integer.parseInt(store.getAvgPrepTime());
        String[] displayedValue = new String[((65 - minTime) / 5)];

        for (int i = minTime, k = 0; i <= 60; k++) {
            displayedValue[k] = String.format(getResources().getString(R.string.after_minute), i);
            i = i + 5;
        }

        numberPickerOrder.setMaxValue(displayedValue.length - 1);
        numberPickerOrder.setMinValue(0);
        numberPickerOrder.setDisplayedValues(displayedValue);
        numberPickerOrder.setWrapSelectorWheel(false);
        numberPickerOrder.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPickerOrder.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int beforeIndex, int selectedIndex) {
                selectedNumberIndex = selectedIndex;
            }
        });

        pickerDialog.getWindow().setGravity(Gravity.BOTTOM);
        pickerDialog.show();
    }

    private void displayPickupTimeRadioGroup(int checkedId) {

        if (checkedId == radioNowButtonOrder.getId()) {
            radioNowButtonOrder.setTextColor(Color.parseColor("#111cc4"));
            radioTodayButtonOrder.setTextColor(Color.parseColor("#C8000000"));
            radioNowButtonOrder.setTypeface(null, Typeface.BOLD);
            radioTodayButtonOrder.setTypeface(null, Typeface.NORMAL);
            radioNowButtonOrder.setButtonTintList(colorStateList_select);
            radioTodayButtonOrder.setButtonTintList(colorStateList_unselect);
            relativeTodayDetailOrder.setVisibility(RelativeLayout.INVISIBLE);
            if(numberPickerOrder != null){
                numberPickerOrder.setValue(0);
            }
            selectedNumberIndex = 0;
        }

        if (checkedId == radioTodayButtonOrder.getId()) {
            radioNowButtonOrder.setTextColor(Color.parseColor("#C8000000"));
            radioTodayButtonOrder.setTextColor(Color.parseColor("#111cc4"));
            radioNowButtonOrder.setTypeface(null, Typeface.NORMAL);
            radioTodayButtonOrder.setTypeface(null, Typeface.BOLD);
            radioTodayButtonOrder.setButtonTintList(colorStateList_select);
            radioNowButtonOrder.setButtonTintList(colorStateList_unselect);
            isReset = false;
            show();
        }
    }

    private void displayPaymentRadioGroup(int checkedId) {

        if (checkedId == radioCreditButtonOrder.getId()) {
            radioCreditButtonOrder.setTextColor(Color.parseColor("#111cc4"));
            radioKakaopayButtonOrder.setTextColor(Color.parseColor("#C8000000"));
            radioMobileButtonOrder.setTextColor(Color.parseColor("#C8000000"));

            radioCreditButtonOrder.setTypeface(null, Typeface.BOLD);
            radioKakaopayButtonOrder.setTypeface(null, Typeface.NORMAL);
            radioMobileButtonOrder.setTypeface(null, Typeface.NORMAL);

            radioCreditButtonOrder.setButtonTintList(colorStateList_select);
            radioKakaopayButtonOrder.setButtonTintList(colorStateList_unselect);
            radioMobileButtonOrder.setButtonTintList(colorStateList_unselect);
        }

        if (checkedId == radioKakaopayButtonOrder.getId()) {
            radioKakaopayButtonOrder.setTextColor(Color.parseColor("#111cc4"));
            radioCreditButtonOrder.setTextColor(Color.parseColor("#C8000000"));
            radioMobileButtonOrder.setTextColor(Color.parseColor("#C8000000"));

            radioKakaopayButtonOrder.setTypeface(null, Typeface.BOLD);
            radioCreditButtonOrder.setTypeface(null, Typeface.NORMAL);
            radioMobileButtonOrder.setTypeface(null, Typeface.NORMAL);

            radioKakaopayButtonOrder.setButtonTintList(colorStateList_select);
            radioCreditButtonOrder.setButtonTintList(colorStateList_unselect);
            radioMobileButtonOrder.setButtonTintList(colorStateList_unselect);
        }

        if (checkedId == radioMobileButtonOrder.getId()) {
            radioMobileButtonOrder.setTextColor(Color.parseColor("#111cc4"));
            radioKakaopayButtonOrder.setTextColor(Color.parseColor("#C8000000"));
            radioCreditButtonOrder.setTextColor(Color.parseColor("#C8000000"));

            radioMobileButtonOrder.setTypeface(null, Typeface.BOLD);
            radioKakaopayButtonOrder.setTypeface(null, Typeface.NORMAL);
            radioCreditButtonOrder.setTypeface(null, Typeface.NORMAL);

            radioMobileButtonOrder.setButtonTintList(colorStateList_select);
            radioKakaopayButtonOrder.setButtonTintList(colorStateList_unselect);
            radioCreditButtonOrder.setButtonTintList(colorStateList_unselect);
        }
    }
}
