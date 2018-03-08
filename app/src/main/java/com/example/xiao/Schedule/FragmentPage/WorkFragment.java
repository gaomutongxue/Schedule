package com.example.xiao.Schedule.FragmentPage;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xiao.Schedule.Adapter.AddListDapter;
import com.example.xiao.Schedule.R;
import com.example.xiao.Schedule.util.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowentao85336773 on 2018/2/2.
 */

public class WorkFragment extends android.support.v4.app.Fragment {
    List<Person> personlist=new ArrayList<Person>();
    RecyclerView recyclerView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_work_fragment, container, false);
        Person p1=new Person();
        p1.setText("doraemon");

        Person p2=new Person();
        p2.setText("doraemon");

        Person p3=new Person();
        p3.setText("doraemon");

        personlist.add(p1);
        personlist.add(p2);
        personlist.add(p3);
        for (int i=0;i<20;i++){
            personlist.add(p3);
        }
        recyclerView = (RecyclerView)view.findViewById(R.id.testrv4);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
       // AddListDapter suppliesaDapter=new AddListDapter(personlist);
       // recyclerView.setAdapter(suppliesaDapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
        return view;

    }
}
