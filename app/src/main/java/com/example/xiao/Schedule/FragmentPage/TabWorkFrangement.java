package com.example.xiao.Schedule.FragmentPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xiao.Schedule.R;

/**
 * Created by xiaowentao85336773 on 2017/12/11.
 */

public class TabWorkFrangement extends Fragment {
    private Button button01,button02;
    private Fragment fragment01;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.work_fragment, container, false);
        button01=(Button)view.findViewById(R.id.workbt) ;
        button02 = (Button) view.findViewById(R.id.teamworkbt);
        setview();
        return view;
    }
    private void setview() {
        FragmentManager FM = getFragmentManager();

        FragmentTransaction MfragmentTransaction =FM.beginTransaction();

        WorkFragment  f1 = new WorkFragment();

        MfragmentTransaction.replace(R.id.workflayout,f1);

        MfragmentTransaction.commit();
        button02.setEnabled(true);
        button01.setEnabled(false);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button02.setEnabled(true);
                button01.setEnabled(false);
                FragmentManager FM = getFragmentManager();

                FragmentTransaction MfragmentTransaction =FM.beginTransaction();

                WorkFragment  f1 = new WorkFragment();

                MfragmentTransaction.replace(R.id.workflayout,f1);

                MfragmentTransaction.commit();
            }
        });

        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button01.setEnabled(true);
                button02.setEnabled(false);
                FragmentManager FMs = getFragmentManager();

                FragmentTransaction MfragmentTransactions = FMs.beginTransaction();

                TeamWorkFragment f2 = new TeamWorkFragment();

                MfragmentTransactions.replace(R.id.workflayout,f2);

                MfragmentTransactions.commit();
            }
        });
    }
}
