package com.xeed.cheapnsale.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.holder.ExpandableMenuChildHolder;
import com.xeed.cheapnsale.holder.ExpandableMenuListHolder;
import com.xeed.cheapnsale.vo.MenuItems;

import java.util.List;

public class ExpandableMenuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<MenuItems> list;

    public ExpandableMenuListAdapter(List<MenuItems> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item_menu_list_child, parent, false);

        switch (viewType) {
            case HEADER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_item_menu_list_child, parent, false);
                return new ExpandableMenuListHolder(view);
            case CHILD:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_selected_menu_item_child, parent, false);
                return new ExpandableMenuChildHolder(view);
            default:
                return new ExpandableMenuListHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(getItemViewType(position) == HEADER){
            String txt = list.get(position).getItemName();

            final ExpandableMenuListHolder expandableMenuListHolder = (ExpandableMenuListHolder) holder;
            expandableMenuListHolder.itemName.setText(txt);
            expandableMenuListHolder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (expandableMenuListHolder.itemName != null) {
                        int childPos = getChildPos();

                        if (childPos != -1) {
                            list.remove(childPos);
                        }

                        if (position != childPos-1) {
                            if (position < childPos || childPos == -1) {
                                childPos = position + 1;
                            } else {
                                childPos = position;
                            }

                            MenuItems newItem = new MenuItems(CHILD);
                            list.add(childPos, newItem);
                        }

                        notifyDataSetChanged();
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    public int getChildPos(){
        for (int i = 0; i < getItemCount(); i ++) {
            if (list.get(i).getType() == CHILD) {
                return i;
            }
        }

        return -1;
    }
}
