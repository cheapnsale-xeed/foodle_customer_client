package com.xeed.cheapnsale.holder;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xeed.cheapnsale.R;

public class ExpandableMenuListHolder extends RecyclerView.ViewHolder{
    public TextView itemName;

    public ExpandableMenuListHolder(View view) {
        super(view);
        itemName = (TextView) view.findViewById(R.id.menu_item_name);

    }
}
