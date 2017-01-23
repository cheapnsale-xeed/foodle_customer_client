package com.xeed.cheapnsale;

import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xeed.cheapnsale.activity.MapActivity;
import com.xeed.cheapnsale.vo.Cart;
import com.xeed.cheapnsale.vo.CartItem;

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

        mockCart.addCartItem(new CartItem("mock-1", "mockItem-1", 22000, 3, "mockSrc-1"));
        mockCart.addCartItem(new CartItem("mock-2", "mockItem-2", 12000, 2, "mockSrc-2"));
        mockCart.addCartItem(new CartItem("mock-3", "mockItem-3", 6000, 1, "mockSrc-3"));
        mockCart.addCartItem(new CartItem("mock-4", "mockItem-4", 8800, 5, "mockSrc-4"));

        cartActivity = Robolectric.buildActivity(CartActivity.class).create().get();
    }

    @Test
    public void whenOnClickBackButton_thenActivityIsFinish() throws Exception {
        ImageButton backButton = (ImageButton) cartActivity.findViewById(R.id.main_toolbar_back_button);
        backButton.performClick();

        assertThat(cartActivity.isFinishing()).isTrue();
    }

    @Test
    public void whenClickFooterOrderButton_thenStartOrderActivity() throws Exception {
        cartActivity.findViewById(R.id.cart_footer_order_button).performClick();

        Intent expectedIntent = new Intent(cartActivity, OrderActivity.class);
        Intent actualIntent = shadowOf(cartActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
    }

    @Test
    public void whenActivityIsStart_thenCartTotalPriceAndTotalCountIsCorrect() throws Exception {
        assertThat(((TextView)cartActivity.findViewById(R.id.cart_footer_order_info_count)).getText()).isEqualTo("(11)");
        assertThat(((TextView)cartActivity.findViewById(R.id.cart_footer_order_info_price)).getText()).isEqualTo("140,000원");
    }
}