package com.xeed.cheapnsale.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.holder.ExpandableMenuListHolder;
import com.xeed.cheapnsale.vo.MenuItems;

import java.util.List;

public class ExpandableMenuListAdapter extends RecyclerView.Adapter<ExpandableMenuListHolder> {

    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<MenuItems> list;

    public ExpandableMenuListAdapter(List<MenuItems> list) {
        this.list = list;
    }

    @Override
    public ExpandableMenuListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu_list, parent, false);

        switch (viewType) {
            case HEADER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_menu_list, parent, false);
                return new ExpandableMenuListHolder(view);
            case CHILD:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.selected_menu_item_fragment, parent, false);
                return new ExpandableMenuListHolder(view);
            default:
                return new ExpandableMenuListHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ExpandableMenuListHolder holder, final int position) {
        switch (holder.getItemViewType()){
            case HEADER:
                String txt = list.get(position).getItemName();
                holder.itemName.setText(txt);
                break;
            case CHILD:
                break;
            default:
                break;
        }

        final ExpandableMenuListHolder itemController = holder;
        itemController.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (itemController.itemName != null) {
                    System.out.println("position : " + position);

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
