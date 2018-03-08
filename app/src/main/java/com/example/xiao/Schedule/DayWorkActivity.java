package com.example.xiao.Schedule;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.xiao.Schedule.Adapter.AddListDapter;
import com.example.xiao.Schedule.util.Person;
import com.ldf.calendar.model.CalendarDate;

import org.litepal.crud.DataSupport;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import memberClass.Affair;

public class DayWorkActivity extends AppCompatActivity {
    List<Affair> personlist=new ArrayList<Affair>();
    RecyclerView recyclerView;
    Button button;
    AddListDapter suppliesaDapter;
    String username;
    CalendarDate date;
    ArrayList<Affair> worklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_work);
        initdate();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 新建一个事件 同样是跳转到编辑页面
                turntosetting();
            }
        });

        suppliesaDapter.setOnItemClickListener(new AddListDapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO 点击事件跳转到编辑页面
                turntosetting();
            }
        });
    }

    private void turntosetting() {

        Intent intent=new Intent(getApplicationContext(),SettingActivity.class);
        intent.putExtra("date",date);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    private void initdate(){
        button=(Button)findViewById(R.id.addwork);

        recyclerView = (RecyclerView)findViewById(R.id.thatdaywork);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        username=sharedPreferences.getString("username",null);
        Intent intent=getIntent();
        date=(CalendarDate)intent.getSerializableExtra("date");
        translatedate();
        suppliesaDapter=new AddListDapter(worklist);
        recyclerView.setAdapter(suppliesaDapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));

    }

    private void translatedate() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String startTime=new String();
        startTime=date.toString();
        try {
            java.util.Date startdate=sf.parse(startTime);
            startTime=dateFormat.format(startdate).toString();
            Log.d("今天的日期是",startTime);
            List<Affair> affairslist = DataSupport.findAll(Affair.class);
            worklist=new ArrayList<Affair>();
          //  List<Affair> affairslist = DataSupport.where("_startTime = ? ", "2018-2-6").find(Affair.class);
            for (Affair affair: affairslist){
                String datecursor;
                datecursor= dateFormat.format(affair.getStartTime()).toString();
                if (datecursor.equals(startTime)){
                 worklist.add(affair);
                }
            }
           /* for (Affair affair: worklist){
                Log.d("拥有的日期等于",affair.getStartTime().toString());
            }*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
