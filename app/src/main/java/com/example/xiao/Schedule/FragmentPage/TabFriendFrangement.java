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

public class TabFriendFrangement extends Fragment {
    private Button button01,button02;
    private Fragment fragment01;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_fragement, container, false);
        button01=(Button)view.findViewById(R.id.friendbt) ;
        button02 = (Button) view.findViewById(R.id.teambt);
        setview();

        return view;
    }
    private void setview() {
        FragmentManager FM = getFragmentManager();
        FragmentTransaction MfragmentTransaction =FM.beginTransaction();
        FriendFragment  f1 = new FriendFragment();
        MfragmentTransaction.replace(R.id.friendflayout,f1);
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

                FriendFragment  f1 = new FriendFragment();

                MfragmentTransaction.replace(R.id.friendflayout,f1);

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

                TeamFragment f2 = new TeamFragment();

                MfragmentTransactions.replace(R.id.friendflayout,f2);

                MfragmentTransactions.commit();
            }
        });



    }
}
