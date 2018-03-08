package com.example.xiao.Schedule.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.xiao.Schedule.R;
import com.example.xiao.Schedule.util.DateUtil;
import com.example.xiao.Schedule.util.Person;

import java.util.List;

import memberClass.Affair;

/**
 * Created by xiaowentao85336773 on 2017/4/24.
 */

public class AddListDapter extends RecyclerView.Adapter<AddListDapter.ViewHolder>{
    private List<Affair> mSupplieslist;
    private Context mcontext;
    String conment;
    static  class  ViewHolder extends RecyclerView.ViewHolder {
        View suppliesview;
        ImageView Suppliesimage;
        TextView todotv;
        TextView date_tv;
        TextView type_tv;
        TextView lv_tv;
        TextView starttime_tv;
        TextView endtime_tv;
        Button add;
        Button deleted;
        Button settop;
        LinearLayout ll_content;
        public ViewHolder(View view)
        {
            super(view);
            todotv=(TextView)view.findViewById(R.id.workview_tv);
            ll_content=(LinearLayout)view.findViewById(R.id.ll_content);
            deleted=(Button)view.findViewById(R.id.btn_delete);
            settop=(Button)view.findViewById(R.id.btn_zd) ;
            date_tv=(TextView)view.findViewById(R.id.date_tv);
            type_tv=(TextView)view.findViewById(R.id.type_tv);
            endtime_tv=(TextView)view.findViewById(R.id.endtime_tv);
            starttime_tv=(TextView)view.findViewById(R.id.starttime_tv);
            lv_tv=(TextView)view.findViewById(R.id.lv_tv);
            suppliesview=view;
        }


    }
    public AddListDapter(List<Affair> suppliesList){
        mSupplieslist=suppliesList;
    }
    @Override
    public AddListDapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext==null){
            mcontext=parent.getContext();
        }
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int poision=holder.getAdapterPosition();
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v, poision);
                }
            }
        });
        holder.deleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 删除事件
                mSupplieslist.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),mSupplieslist.size());

            }
        });
        /* holder.settop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"删除了："+position,Toast.LENGTH_SHORT).show();
            }
        });*/

        holder.suppliesview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return  holder;
    }

    @Override
    public void onBindViewHolder(AddListDapter.ViewHolder holder, int position) {
        Affair supplies=mSupplieslist.get(position);
        holder.todotv.setText(supplies.getName());
        String type="类型："+String.valueOf(supplies.getType());
        String lv="等级："+String.valueOf(supplies.getLevel());
        holder.type_tv.setText(type);
        holder.date_tv.setText(DateUtil.translatedate(supplies.getEndTime()));
        holder.lv_tv.setText(lv);

        //   holder.itemView.setTag(mSupplieslist.get(position));
    }

    @Override
    public int getItemCount() {
        return mSupplieslist.size();
    }
    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void addData(int position,Affair person) {

        mSupplieslist.add(position, person);
        notifyItemInserted(position);
        notifyItemRangeChanged(position,mSupplieslist.size());
    }
}
