package com.xeed.cheapnsale;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StoreDetailActivityTest {

    private StoreDetailActivity storeDetailActivity;
    private ImageButton backButton;
    private TextView callLink;
    private TextView mapLink;
    private TextView title;

    @Before
    public void setUp() throws Exception {
        storeDetailActivity = Robolectric.buildActivity(StoreDetailActivity.class).create().get();

        backButton = (ImageButton) storeDetailActivity.findViewById(R.id.toolbar_back_button);
        callLink = (TextView) storeDetailActivity.findViewById(R.id.toolbar_call_link);
        mapLink = (TextView) storeDetailActivity.findViewById(R.id.toolbar_map_link);
        title = (TextView) storeDetailActivity.findViewById(R.id.toolbar_order_text);
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

}