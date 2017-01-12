package com.xeed.cheapnsale.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xeed.cheapnsale.R;

public class ExpandableMenuListHolder extends RecyclerView.ViewHolder{
    public TextView itemName;

    public ExpandableMenuListHolder(View view) {
        super(view);
        itemName = (TextView) view.findViewById(R.id.menu_item_name);

        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (itemName != null) {
//                    Toast.makeText(view.getContext(), itemName.getText(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
