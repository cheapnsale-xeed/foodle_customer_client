package com.xeed.cheapnsale.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.model.Order;
import com.xeed.cheapnsale.util.DateUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MyOrderCurrentAdapter extends RecyclerView.Adapter<MyOrderCurrentAdapter.MyOrderCurrentHolder> {

    private Context context;
    private ArrayList<Order> myOrder;

    public MyOrderCurrentAdapter(Context context, ArrayList<Order> myOrder) {
        this.context = context;
        this.myOrder = myOrder;
    }

    @Override
    public MyOrderCurrentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ready_my_order, parent, false);

        return new MyOrderCurrentAdapter.MyOrderCurrentHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyOrderCurrentHolder holder, int position) {
        final Order order = myOrder.get(position);
        String menuContent;

        holder.picasso.load(order.getMenus().get(0).getMenuImg())
                .transform(new CropCircleTransformation())
                .into(holder.imageItemSrcMyOrder);
        if (order.getStatus().equals("DONE")) {
            holder.textStatusMyOrder.setText(R.string.ready_to_pickup);
            holder.textStatusMyOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_my_order_menu_done));
        } else if (order.getStatus().equals("PREPARE")) {
            holder.textStatusMyOrder.setText(R.string.now_prepare);
            holder.textStatusMyOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_my_order_menu));
        } else if (order.getStatus().equals("READY")) {
            holder.textStatusMyOrder.setText(R.string.ready_to_receipt);
            holder.textStatusMyOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_my_order_menu));
        }

        if (order.getMenus().size() > 1) {
            menuContent = order.getMenus().get(0).getMenuName() + " 외 " + String.valueOf(order.getMenus().size() - 1) + "개";
        } else {
            menuContent = order.getMenus().get(0).getMenuName();
        }
        holder.textItemNameMyOrder.setText(menuContent);
        holder.textStoreNameMyOrder.setText(order.getStoreName());
        holder.textPickupTimeMyOrder.setText(DateUtil.myOrderPickUpTime(order.getPickupTime()));

        // 데모용 클릭이벤트 (POS 프로세스가 추가되면 삭제 예정임)
        holder.imageItemSrcMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (order.getStatus().equals("DONE")) {
                    order.setStatus("READY");
                } else if (order.getStatus().equals("PREPARE")) {
                    order.setStatus("DONE");
                } else if (order.getStatus().equals("READY")) {
                    order.setStatus("PREPARE");
                }

                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return myOrder.size();
    }

    public void updateData(ArrayList<Order> myOrder) {
        this.myOrder = myOrder;
        this.notifyDataSetChanged();
    }

    public class MyOrderCurrentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_item_src_my_order)
        public ImageView imageItemSrcMyOrder;
        @BindView(R.id.text_status_my_order)
        public TextView textStatusMyOrder;
        @BindView(R.id.text_item_name_my_order)
        public TextView textItemNameMyOrder;
        @BindView(R.id.text_store_name_my_order)
        public TextView textStoreNameMyOrder;
        @BindView(R.id.text_pickup_time_my_order)
        public TextView textPickupTimeMyOrder;

        @Inject
        Picasso picasso;

        public MyOrderCurrentHolder(View itemView) {
            super(itemView);
            ((Application) itemView.getContext().getApplicationContext()).getApplicationComponent().inject(this);
            ButterKnife.bind(this, itemView);
        }
    }
}
