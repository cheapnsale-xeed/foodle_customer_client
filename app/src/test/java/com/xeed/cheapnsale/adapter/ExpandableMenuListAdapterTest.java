package com.xeed.cheapnsale.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.holder.ExpandableMenuChildHolder;
import com.xeed.cheapnsale.holder.ExpandableMenuListHolder;
import com.xeed.cheapnsale.vo.CartItem;
import com.xeed.cheapnsale.vo.MenuItems;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExpandableMenuListAdapterTest {

    private ExpandableMenuListAdapter expandableMenuListAdapter;
    private ExpandableMenuListHolder headerHolder;

    @Before
    public void setUp() throws Exception {
        expandableMenuListAdapter = new ExpandableMenuListAdapter(RuntimeEnvironment.application, makeMockList());
        headerHolder = (ExpandableMenuListHolder) expandableMenuListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
    }

    @Test
    public void whenAdapterStarts_thenHolderHaveRightItems() throws Exception {
        expandableMenuListAdapter.onBindViewHolder(headerHolder, 0);

        assertThat(headerHolder.itemName.getText()).isEqualTo("Item = 0");
        assertThat(headerHolder.itemPrice.getText()).isEqualTo("22,000원");
    }

    @Test
    public void whenHeaderHolderClicked_thenShowChildHolderBelowHeadHolder() throws Exception {
        expandableMenuListAdapter.onBindViewHolder(headerHolder, 0);
        headerHolder.itemView.performClick();

        assertThat(expandableMenuListAdapter.getItemCount()).isEqualTo(4);

        ExpandableMenuChildHolder childHolder = (ExpandableMenuChildHolder)expandableMenuListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);
        expandableMenuListAdapter.onBindViewHolder(childHolder, 1);

        assertThat(childHolder.itemMinus.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(childHolder.itemTotalPriceText.getText()).isEqualTo("22,000원");
    }

    @Test
    public void whenChildHolderPlusAndMinusButtonClicked_thenItemCountIsChange() throws Exception {
        expandableMenuListAdapter.onBindViewHolder(headerHolder, 0);
        headerHolder.itemView.performClick();

        assertThat(expandableMenuListAdapter.getItemCount()).isEqualTo(4);

        ExpandableMenuChildHolder childHolder = (ExpandableMenuChildHolder)expandableMenuListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);
        expandableMenuListAdapter.onBindViewHolder(childHolder, 1);

        childHolder.itemPlus.performClick();
        assertThat(childHolder.itemCountText.getText()).isEqualTo("2");
        assertThat(childHolder.itemTotalPriceText.getText()).isEqualTo("44,000원");

        childHolder.itemMinus.performClick();
        assertThat(childHolder.itemCountText.getText()).isEqualTo("1");
        assertThat(childHolder.itemTotalPriceText.getText()).isEqualTo("22,000원");

        childHolder.itemMinus.performClick();
        assertThat(childHolder.itemCountText.getText()).isEqualTo("1");
        assertThat(childHolder.itemTotalPriceText.getText()).isEqualTo("22,000원");

    }

    @Test
    public void whenHeaderHolderClicked_thenCheckAddCartButton() throws Exception {
        ExpandableMenuChildHolder childHolder = (ExpandableMenuChildHolder)expandableMenuListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);

        CartItem cartItem = new CartItem();
        cartItem.setMenuId("111");
        cartItem.setMenuName("만두");
        cartItem.setPrice(5000);

        Application app = ((Application)childHolder.itemView.getContext().getApplicationContext());

        app.getCart().setStoreId("store_1");
        app.getCart().addCartItem(cartItem);

        expandableMenuListAdapter.onBindViewHolder(headerHolder, 0);
        headerHolder.itemView.performClick();
        expandableMenuListAdapter.onBindViewHolder(childHolder, 1);

        assertThat(childHolder.itemOrderNowButton.getVisibility()).isEqualTo(View.GONE);
    }

    private List<MenuItems> makeMockList() {
        List<MenuItems> list = new ArrayList<>();
        for (int i = 0; i < 3; i ++) {
            MenuItems item = new MenuItems(0, "Item = " + i, 22000, "");
            list.add(item);
        }

        return list;
    }
}