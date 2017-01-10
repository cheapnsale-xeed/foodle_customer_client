package com.xeed.cheapnsale.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xeed.cheapnsale.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MenuListFragment extends Fragment {

    RecyclerView recyclerView;

    // todo : 추후 dynamoDB 데이터 가져오는 부분을 inject 하여 테스트시 mock 으로 바꿔줘야 한다
    @Inject
    List<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.tab_menu_list_fragment, container, false);

        list = new ArrayList<>();
        for (int i = 0; i < 20; i ++) {
            list.add("Item = " + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MenuListAdapter(list));
        recyclerView.addItemDecoration(new MenuListDecorator());

        return recyclerView;
    }

    private class MenuListAdapter extends RecyclerView.Adapter<MenuListHolder> {
        List<String> list;

        public MenuListAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public MenuListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_menu_list, parent, false);

            return new MenuListHolder(view);
        }

        @Override
        public void onBindViewHolder(MenuListHolder holder, int position) {
            String txt = list.get(position);
            holder.titleView.setText(txt);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class MenuListHolder extends RecyclerView.ViewHolder {

        public TextView titleView;
        public MenuListHolder(View root) {
            super(root);
            titleView = (TextView) root.findViewById(R.id.menu_item_name);
        }
    }


    private class MenuListDecorator extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }
}
