package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.TestApplication;
import com.xeed.cheapnsale.service.model.Cart;
import com.xeed.cheapnsale.service.model.Menu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CartActivityTest {

    private CartActivity cartActivity;

    @Before
    public void setUp() throws Exception {
        Cart mockCart = ((TestApplication) RuntimeEnvironment.application).getCart();

        mockCart.addCartItem(new Menu("mock-1", "mockItem-1", 22000, 3, "mockSrc-1"));
        mockCart.addCartItem(new Menu("mock-2", "mockItem-2", 12000, 2, "mockSrc-2"));
        mockCart.addCartItem(new Menu("mock-3", "mockItem-3", 6000, 1, "mockSrc-3"));
        mockCart.addCartItem(new Menu("mock-4", "mockItem-4", 8800, 5, "mockSrc-4"));

        cartActivity = Robolectric.buildActivity(CartActivity.class).create().get();
    }

    @Test
    public void whenOnClickBackButton_thenActivityIsFinish() throws Exception {
        ImageView backButton = (ImageView) cartActivity.findViewById(R.id.image_back_button_cart);
        backButton.performClick();

        assertThat(cartActivity.isFinishing()).isTrue();
    }

    @Test
    public void whenClickFooterOrderButton_thenStartOrderActivity() throws Exception {
        cartActivity.findViewById(R.id.text_order_button_footer).performClick();

        Intent expectedIntent = new Intent(cartActivity, OrderActivity.class);
        Intent actualIntent = shadowOf(cartActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
    }

    @Test
    public void whenActivityIsStart_thenCartTotalPriceAndTotalCountIsCorrect() throws Exception {
        assertThat(((TextView)cartActivity.findViewById(R.id.text_item_count_footer)).getText()).isEqualTo("(11)");
        assertThat(((TextView)cartActivity.findViewById(R.id.text_total_price_footer)).getText()).isEqualTo("140,000원");
    }
}