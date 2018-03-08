package com.example.xiao.Schedule.FragmentPage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.xiao.Schedule.Bean.Affairdata;
import com.example.xiao.Schedule.DayWorkActivity;
import com.example.xiao.Schedule.R;
import com.example.xiao.Schedule.View.CustomDayView;
import com.example.xiao.Schedule.View.ThemeDayView;
import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;


import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import javax.xml.datatype.DatatypeFactory;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import memberClass.Affair;
import memberClass.MainAct;


/**
 * Created by xiaowentao85336773 on 2017/12/11.
 */

public class ClanderFrangement extends Fragment {
    TextView tvYear;
    TextView tvMonth;
    TextView backToday;
    CoordinatorLayout content;
    MonthPager monthPager;
    RecyclerView rvToDoList;
    TextView scrollSwitch;
    TextView themeSwitch;
    TextView nextMonthBtn;
    TextView lastMonthBtn;
    Affair affair;
    private String username;

    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private ArrayList<Affair> affairArrayList = new ArrayList<Affair>();
    private OnSelectDateListener onSelectDateListener;
    private Affairdata affairdata;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    public boolean isInitiated;
    private CalendarDate currentDate;
    private boolean initiated = false;
    // private CustomCalendar cal;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rili_fragment, container, false);
        if (initiated==false) {
           // Log.e("aaaaa", "日历被初始化");
            initiated=true;
            SharedPreferences sharedPreferences= getActivity().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
            username=sharedPreferences.getString("username",null);
            io.reactivex.Observable.create(new ObservableOnSubscribe<ArrayList<Affair>>() {

                @Override
                public void subscribe(ObservableEmitter<ArrayList<Affair>> e) throws Exception {
                    DataSupport.deleteAll(Affair.class);

                 affairArrayList= MainAct.affairQuery(username);
                 e.onNext(affairArrayList);
                // Log.d("收到了","aaaaaaaaaaaaa"+Thread.currentThread());
                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ArrayList<Affair>>() {
                        @Override
                        public void accept(ArrayList<Affair> affairArrayList2) throws Exception {
                            //TODO 存入数据库
                            SQLiteDatabase db = Connector.getDatabase();
                            DataSupport.saveAll(affairArrayList2);
                            affairArrayList= (ArrayList<Affair>) DataSupport.findAll(Affair.class);
                        /*   for (Affair affair1:affairArrayList){
                               Log.d("aaaaaaaaaaa",affair1.getName());
                           }*/
                        initMarkData();

                        }

                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                           Log.d("发生了错误","wanle  quan wan le ");
                        }
                    });

        }
       monthPager=(MonthPager)view.findViewById(R.id.calendar_view) ;
       // initCalendarView();
        content = (CoordinatorLayout)view.findViewById(R.id.content);
        monthPager = (MonthPager) view.findViewById(R.id.calendar_view);
        monthPager.setViewHeight(Utils.dpi2px(getContext(), 270));
        tvYear = (TextView) view.findViewById(R.id.show_year_view);
        tvMonth = (TextView) view.findViewById(R.id.show_month_view);
        backToday = (TextView) view.findViewById(R.id.back_today_button);
        scrollSwitch = (TextView) view.findViewById(R.id.scroll_switch);
        themeSwitch = (TextView) view.findViewById(R.id.theme_switch);
        nextMonthBtn = (TextView) view.findViewById(R.id.next_month);
        lastMonthBtn = (TextView) view.findViewById(R.id.last_month);
        rvToDoList = (RecyclerView) view.findViewById(R.id.list);
        rvToDoList.setHasFixedSize(true);
        //这里用线性显示 类似于listview
        initCurrentDate();
        initCalendarView();
        initToolbarClickListener();

        return view;
} /**
     * 初始化对应功能的listener
     *
     * @return void
     */
    private void initToolbarClickListener() {
        backToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBackToDayBtn();
            }
        });
     /*   scrollSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK) {
                    Utils.scrollTo(content, rvToDoList, monthPager.getViewHeight(), 200);
                    calendarAdapter.switchToMonth();
                } else {
                    Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
                    calendarAdapter.switchToWeek(monthPager.getRowIndex());
                }
            }
        });*/
        themeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshSelectBackground();
            }
        });
        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);
            }
        });
        lastMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);
            }
        });
    }
    /**
     * 初始化currentDate
     *
     * @return void
     */
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        tvYear.setText(currentDate.getYear() + "年");
        tvMonth.setText(currentDate.getMonth() + "");
    }

    /**
     * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
     */
    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(getContext(), R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                getContext(),
                onSelectDateListener,
                CalendarAttr.CalendarType.MONTH,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);
       // initMarkData();
        initMonthPager();
    }
    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                //  TODO 点击日期进入事件列表
                String a= String.valueOf(date.getDay());
                Log.d("aaa",String.valueOf(date.getDay()));
                Intent intent=new Intent(getActivity(),DayWorkActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
                //your code
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示上一个月 ， 1表示下一个月
                monthPager.selectOtherMonth(offset);
            }
        };
    }
    private void initMarkData() {
        HashMap markData = new HashMap<>();
        //0表示红点，1表示灰点
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-M-d");
        for (Affair affair1:affairArrayList){
            String startTime=new String();
            startTime = sf.format(affair1.getStartTime()).toString();
            Log.d("---------------------",startTime);
           markData.put(startTime, "0");
          //  markData.put("2018-2-9", "0");
        }


        calendarAdapter.setMarkData(markData);
    }
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    tvYear.setText(date.getYear() + "年");
                    tvMonth.setText(date.getMonth() + "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void onClickBackToDayBtn() {
        refreshMonthPager();
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
        tvYear.setText(today.getYear() + "年");
        tvMonth.setText(today.getMonth() + "");
    }

    private void refreshSelectBackground() {
        ThemeDayView themeDayView = new ThemeDayView(getContext(), R.layout.custom_day_focus);
        calendarAdapter.setCustomDayRenderer(themeDayView);
        calendarAdapter.notifyDataSetChanged();
        calendarAdapter.notifyDataChanged(new CalendarDate());
    }
    public void onDestroy() {
        Log.d("bbbbbbbbb","销毁成功");

        super.onDestroy();
    }



}

