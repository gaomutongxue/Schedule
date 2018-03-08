package com.example.xiao.Schedule;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.xiao.Schedule.FragmentPage.ClanderFrangement;
import com.example.xiao.Schedule.FragmentPage.TabWorkFrangement;
import com.example.xiao.Schedule.FragmentPage.TabFriendFrangement;
import com.example.xiao.Schedule.util.Tab;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import sqlClient.OkGohtttp;

public class ShowActivity extends AppCompatActivity {
private FragmentTabHost mTabHost;
   // Intent intent2=new Intent(this,DayWorkActivity.class);
    private List<Tab> list = new ArrayList<>(5);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        /**
         * 网络请求
         */
        OkGo.getInstance().init(getApplication());
        try {
            OkGohtttp.getaffair("http://120.79.223.146:8080/schedule/main"+"?username="+"root");
        }catch (Exception e) {
            e.printStackTrace();
        }
    //    supportRequestWindowFeature(Window.FEATURE_NO_TITLE);


        initTab();
    }
    private void initTab() {
        Tab tab1 = new Tab(R.drawable.tab_category_selector, "home", "日历", ClanderFrangement.class);
        Tab tab2 = new Tab(R.drawable.tab_category_selector, "hot", "好友", TabFriendFrangement.class);
        Tab tab3 = new Tab(R.drawable.tab_category_selector, "category", "事件", TabWorkFrangement.class);


        list.add(tab1);
        list.add(tab2);
        list.add(tab3);
      //  list.add(tab4);
      //  list.add(tab5);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (Tab tab : list) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tab.getTag());
            tabSpec.setIndicator(buildView(tab));
            mTabHost.addTab(tabSpec, tab.getmFragment(), null);
        }
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTabByTag(tab1.getTag());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.mainmenu);
        toolbar.setTitle("Schedule");
        toolbar.setNavigationIcon(R.drawable.touxiang);
       toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_out:
                        SharedPreferences mySharedPreferences= getSharedPreferences("userinfo",
                                Activity.MODE_PRIVATE);
                        //实例化SharedPreferences.Editor对象
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        editor.clear();
                        Intent intent=new Intent(getApplication(),LoginActivity.class);
                        startActivity(intent);break;

                }

                return true;
            }
        });

    }



    private View buildView(Tab tab) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        ImageView tabImg = (ImageView) view.findViewById(R.id.tab_img);
        TextView text = (TextView) view.findViewById(R.id.tab_text);
        tabImg.setImageResource(tab.getIcon());
        text.setText(tab.getText());
        return view;
    }









}


