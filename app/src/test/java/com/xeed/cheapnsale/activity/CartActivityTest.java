package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.TestApplication;
import com.xeed.cheapnsale.adapter.CartListAdapter;
import com.xeed.cheapnsale.holder.CartListChildHolder;
import com.xeed.cheapnsale.holder.CartListHeadHolder;
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
    private CartListAdapter cartListAdapter;
    private CartListHeadHolder headHolder;
    private CartListChildHolder childHolder;

    @Before
    public void setUp() throws Exception {
        Cart mockCart = ((TestApplication) RuntimeEnvironment.application).getCart();

        mockCart.addCartItem(new Menu("mock-1", "mockItem-1", 22000, 3, "mockSrc-1"));
        mockCart.addCartItem(new Menu("mock-2", "mockItem-2", 12000, 2, "mockSrc-2"));
        mockCart.addCartItem(new Menu("mock-3", "mockItem-3", 6000, 1, "mockSrc-3"));
        mockCart.addCartItem(new Menu("mock-4", "mockItem-4", 8800, 5, "mockSrc-4"));

        cartActivity = Robolectric.buildActivity(CartActivity.class).create().get();
        cartListAdapter = (CartListAdapter) cartActivity.recyclerCart.getAdapter();

        headHolder = (CartListHeadHolder) cartListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
        childHolder = (CartListChildHolder) cartListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);
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

    @Test
    public void whenItemPlusMinusButtonClick_thenItemCountChange() throws Exception {
        cartListAdapter.onBindViewHolder(headHolder, 0);

        assertThat(headHolder.textItemCountCart.getText()).isEqualTo("3");

        headHolder.imagePlusButtonCart.performClick();
        cartListAdapter.onBindViewHolder(headHolder, 0);
        assertThat(headHolder.textItemCountCart.getText()).isEqualTo("4");

        headHolder.imageMinusButtonCart.performClick();
        cartListAdapter.onBindViewHolder(headHolder, 0);
        assertThat(headHolder.textItemCountCart.getText()).isEqualTo("3");
    }

    @Test
    public void whenItemDeleteButtonClick_thenCartItemDelete() throws Exception {
        assertThat(cartListAdapter.getItemCount()).isEqualTo(5);
        cartListAdapter.onBindViewHolder(headHolder, 1);
        assertThat(headHolder.textItemNameCart.getText()).isEqualTo("mockItem-2");

        headHolder.imageDeleteButtonCart.performClick();
        assertThat(cartListAdapter.getItemCount()).isEqualTo(4);
        cartListAdapter.onBindViewHolder(headHolder, 1);

        assertThat(headHolder.textItemNameCart.getText()).isEqualTo("mockItem-3");
    }

    @Test
    public void whenAddMenuLayoutClick_thenFinishCartActivity() throws Exception {
        cartListAdapter.onBindViewHolder(childHolder, 4);
        childHolder.linearAddMenuCart.performClick();
        assertThat(cartActivity.isFinishing()).isTrue();
    }
}