package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.activity.StoreDetailActivity;
import com.xeed.cheapnsale.service.model.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent(RuntimeEnvironment.application, StoreDetailActivity.class);
        intent.putExtra("store", makeMockData());

        storeDetailActivity = Robolectric.buildActivity(StoreDetailActivity.class).withIntent(intent).create().get();

        backButton = (ImageView) storeDetailActivity.findViewById(R.id.image_back_button_store_detail);
        callLink = (TextView) storeDetailActivity.findViewById(R.id.text_call_button_store_detail);
        mapLink = (TextView) storeDetailActivity.findViewById(R.id.text_map_button_store_detail);
        title = (TextView) storeDetailActivity.findViewById(R.id.image_title_order_detail);
        storeTitle = (TextView) storeDetailActivity.findViewById(R.id.text_name_store_detail);
        storePaymentType = (TextView) storeDetailActivity.findViewById(R.id.text_payment_type_store_detail);
        storeMainImg = (ImageView) storeDetailActivity.findViewById(R.id.image_top_src_store_detail);
    }

    @Test
    public void whenBackButtonIsClicked_thenActivityIsFinish() throws Exception {

        backButton.performClick();

        assertThat(storeDetailActivity.isFinishing()).isTrue();
    }

    @Test
    public void whenHardwareBackButtonIsClicked_thenActivityIsFinish() throws Exception {
        storeDetailActivity.onBackPressed();
        assertThat(storeDetailActivity.isFinishing()).isTrue();
    }

    @Test
    public void whenActivityIsStart_thenShowOnlyBackButton() throws Exception {
        assertThat(backButton.getVisibility()).isEqualTo(View.VISIBLE);

        assertThat(callLink.getVisibility()).isEqualTo(View.GONE);
        assertThat(mapLink.getVisibility()).isEqualTo(View.GONE);
        assertThat(title.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    public void whenActivityIsStart_thenStoreTitleIsVisible() throws Exception {
        Bitmap mockBitmap = BitmapFactory.decodeResource(RuntimeEnvironment.application.getResources(), R.drawable.ico_map);

        storeDetailActivity.imageCallback.onBitmapLoaded(mockBitmap, null);

        assertThat(storeTitle.getText()).isEqualTo("가게이름");
        assertThat(storePaymentType.getText()).isEqualTo("바로결제");
        assertThat(storeMainImg.getDrawable()).isEqualTo(ContextCompat.getDrawable(RuntimeEnvironment.application, R.drawable.ico_map));
    }

    private Store makeMockData(){
        Store store = new Store();
        store.setName("가게이름");
        store.setPaymentType("바로결제");
        store.setMainImageUrl("http://image.jpg");
        return store;
    }
}