package com.xeed.cheapnsale.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.TestApplication;
import com.xeed.cheapnsale.service.model.Cart;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.service.model.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StoreDetailActivityTest {

    private StoreDetailActivity storeDetailActivity;
    private ImageView backButton;
    private TextView callLink;
    private TextView mapLink;
    private TextView title;
    private TextView storeTitle;
    private TextView storePaymentType;
    private ImageView storeMainImg;
    private TextView textArrivalTimeStoreDetail;
    private View viewVerticalBarStoreDetail;
    private TextView noButton;
    private TextView yesButton;
    private TextView address;
    private TextView runningTime;

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent(RuntimeEnvironment.application, StoreDetailActivity.class);
        intent.putExtra("store", makeMockData());

        storeDetailActivity = Robolectric.buildActivity(StoreDetailActivity.class).withIntent(intent).create().get();

        backButton = (ImageView) storeDetailActivity.findViewById(R.id.image_back_button_store_detail);
        callLink = (TextView) storeDetailActivity.findViewById(R.id.text_call_button_store_detail);
        mapLink = (TextView) storeDetailActivity.findViewById(R.id.text_map_button_store_detail);
        title = (TextView) storeDetailActivity.findViewById(R.id.text_title_order_detail);
        storeTitle = (TextView) storeDetailActivity.findViewById(R.id.text_name_store_detail);
        storeMainImg = (ImageView) storeDetailActivity.findViewById(R.id.image_top_src_store_detail);
        address = (TextView) storeDetailActivity.findViewById(R.id.text_address_store_detail);
        runningTime = (TextView) storeDetailActivity.findViewById(R.id.text_running_time_store_detail);

        Cart mockCart = ((TestApplication) RuntimeEnvironment.application).getCart();

        mockCart.addCartItem(new Menu("mock-1", "mockItem-1", 22000, 3, "mockSrc-1"));
        mockCart.addCartItem(new Menu("mock-2", "mockItem-2", 12000, 2, "mockSrc-2"));

    }

    @Test
    public void whenActivityIsStart_thenShowOnlyBackButton() throws Exception {
        assertThat(backButton.getVisibility()).isEqualTo(View.VISIBLE);

        assertThat(callLink.getVisibility()).isEqualTo(View.GONE);
        assertThat(mapLink.getVisibility()).isEqualTo(View.GONE);
        assertThat(title.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    public void whenActivityIsStart_thenStoreAddressIsVisible() throws Exception {
        Store store = makeMockData();
       assertThat(address.getText().toString()).isEqualTo(store.getAddress());
    }

    @Test
    public void whenActivityIsStart_thenStoreRunningTimeIsVisible() throws Exception {
        Store store = makeMockData();
        assertThat(runningTime.getText().toString()).isEqualTo(store.getOpenTime() + " - " + store.getCloseTime() + " (" + storeDetailActivity.getResources().getString(R.string.txt_order_end_time) + " " + store.getEndTime() + ")");
    }

    @Test
    public void whenPressBackButton_thenShowCancelConfirmDialog() throws Exception {
        backButton.performClick();
        MaterialDialog orderCancelConfirmlDialog = (MaterialDialog) ShadowDialog.getLatestDialog();
        assertThat(orderCancelConfirmlDialog.isShowing()).isTrue();
    }

    @Test
    public void whenPressNoButtonInDialog_thenDismissDialog() throws Exception {
        backButton.performClick();
        MaterialDialog orderCancelConfirmlDialog = (MaterialDialog) ShadowDialog.getLatestDialog();
        noButton = (TextView) orderCancelConfirmlDialog.getView().findViewById(R.id.button_cancel_confirm_dialog_no);
        noButton.performClick();
        assertThat(orderCancelConfirmlDialog.isShowing()).isFalse();
    }

    @Test
    public void whenPressYesButtonInDialog_thenFinishStoreDetailActivity() throws Exception {
        backButton.performClick();
        MaterialDialog orderCancelConfirmlDialog = (MaterialDialog) ShadowDialog.getLatestDialog();
        yesButton = (TextView) orderCancelConfirmlDialog.getView().findViewById(R.id.button_cancel_confirm_dialog_yes);
        yesButton.performClick();

        assertThat(storeDetailActivity.isFinishing()).isTrue();
    }

    @Test
    public void whenClickMapLinkButton_thenStartStoreDetailMapActivity() throws Exception {
        mapLink.performClick();

        Intent expectedIntent = new Intent(storeDetailActivity, StoreDetailMapActivity.class);
        Intent actualIntent = shadowOf(storeDetailActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
    }

    private Store makeMockData(){
        Store store = new Store();
        store.setName("가게이름");
        store.setPaymentType("바로결제");
        store.setMainImageUrl("http://image.jpg");
        store.setDistanceToStore(456);
        store.setAddress("서울시 송파구 방이동 25번지");
        store.setOpenTime("08:00");
        store.setCloseTime("22:00");
        store.setEndTime("21:00");
        return store;
    }
}