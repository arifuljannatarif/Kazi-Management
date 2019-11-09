package com.example.kazimanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ShortlistAdapter extends RecyclerView.Adapter<ShortlistAdapter.viewholder> {
    Context mContext;
    long curtime;
    int tc=0;
    ArrayList<MarriageModel> mainlist,templist;
    public ShortlistAdapter(Context mContext) {
        this.mContext = mContext;
        mainlist=new ArrayList<>();
        templist=new ArrayList<>();
        curtime=System.currentTimeMillis();
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.cars_shortlist_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        MarriageModel model=getitem(position);
        holder.date.setText(gettime(model.getDate()));
        holder.serial.setText("SL  "+model.getSeriaNo()+" / "+model.getBookNo());
        holder.bride.setText(model.getBrideName());
        holder.groom.setText(model.getGroomName());
        if(DateUtils.isToday(model.getDate())) {
            holder.mainlayout.setBackgroundColor(Color.parseColor("#B95C5470"));//00818a  263859 62a388
            holder.status.setText(mContext.getResources().getString(R.string.tichmark));
        }
        else if(model.getDate()>curtime)
        {
            holder.mainlayout.setBackgroundColor(Color.parseColor("#8F4D4545"));
            holder.status.setText(mContext.getResources().getString(R.string.clock));
        }
        else {
            holder.mainlayout.setBackgroundColor(Color.parseColor("#8183142C"));
            holder.status.setText(mContext.getResources().getString(R.string.party));
        }
    }

    @Override
    public int getItemCount() {
        return templist.size();
    }

    public void clearall() {
        tc=0;
        templist.clear();
        mainlist.clear();
        notifyDataSetChanged();
    }

    public void additem(MarriageModel model,long day) {
        if(DateUtils.isToday(day))
        {

            templist.add(tc++,model);
            mainlist.add(tc-1,model);
        }
       else if (day>curtime){
            mainlist.add(tc,model);
            templist.add(tc,model);
        }else{
            mainlist.add(model);
            templist.add(model);
        }

    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView bride,groom,date,serial,status;
        LinearLayout mainlayout;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            //itemView.setAlpha((float) 0.8);
            mainlayout=itemView.findViewById(R.id.mainlayout);
            bride=itemView.findViewById(R.id.bride);
            groom=itemView.findViewById(R.id.groom);
            date=itemView.findViewById(R.id.m_date);
            serial=itemView.findViewById(R.id.serial);
            status=itemView.findViewById(R.id.status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,Add_new_details.class);
                    intent.putExtra("serializableextra",getitem(getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });
        }
    }
    public void filter(String query){
        templist.clear();
        notifyDataSetChanged();
        if(query==null || query.isEmpty())
        {
            templist.addAll(mainlist);
            notifyDataSetChanged();
        }
        else{
            query=query.toLowerCase();
            for(int i=0;i<mainlist.size();i++){
                MarriageModel model=mainlist.get(i);//getitem(i);
                if(model.getMobileNo().toLowerCase().contains(query) || model.getGroomName().toLowerCase().contains(query) || model.getBrideName().toLowerCase().contains(query) || model.getSeriaNo().toLowerCase().contains(query)
                        || model.getBookNo().toLowerCase().contains(query))templist.add(model);
            }
            notifyDataSetChanged();
        }
    }


    private MarriageModel getitem(int i) {
        return templist.get(i);
    }
    public static String gettime2(long time){
        try {
            return new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date(time));
        }catch (Exception e){
            return "invalid";
        }
    }
    public static String gettime(long time){
        try {
            return new SimpleDateFormat("dd.MM.yyyy").format(new Date(time));
        }catch (Exception e){
            return "invalid";
        }
    }
}
