package com.xeed.cheapnsale;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

// todo : 임시 생성. 탭에 붙으면 삭제
public class ListSelectedTempActivity extends AppCompatActivity {

    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_selected_temp);

    }

}
