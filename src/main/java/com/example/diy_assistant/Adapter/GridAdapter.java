package com.example.diy_assistant.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.diy_assistant.Bean.ScheduleBean;
import com.example.diy_assistant.R;
import com.xx.roundprogressbar.RoundProgressBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by GeeGumb on 18:44 2019/5/9
 */
public class GridAdapter extends BaseAdapter {
    private Context context;
    private List<ScheduleBean> scheduleBeanList;
    private Callback callback;

    public GridAdapter(Context context, List<ScheduleBean> scheduleBeanList, Callback callback) {
        this.context = context;
        this.scheduleBeanList = scheduleBeanList;
        this.callback = callback;
    }


    @Override
    public int getCount() {
        return scheduleBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return scheduleBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, null, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        String title = scheduleBeanList.get(position).getTitle();
        String content = scheduleBeanList.get(position).getDesc();

        viewHolder.tv_title.setText(cut(title, 5));
        viewHolder.tv_desc.setText(cut(content, 24));

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date fromTime = scheduleBeanList.get(position).getFromTime();
        Date dateNow = new Date();
        Date deadline = scheduleBeanList.get(position).getDeadline();
        long passedTime = dateNow.getTime() - fromTime.getTime();
        long wholeTime = deadline.getTime() - fromTime.getTime();
        double progress = ((double) (passedTime) / (double) (wholeTime)) * 100;

        viewHolder.tv_time_from.setText(format.format(fromTime));
        viewHolder.tv_ddl.setText(format.format(deadline));

        viewHolder.progressBar.setCurrentProgress(progress > 100 ? 0 : 100 - progress);

        convertView.setOnLongClickListener(v -> {
            callback.onLongClick(position);
            return true;
        });
        convertView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("content", content);
            bundle.putString("fromTime", format.format(fromTime));
            bundle.putString("deadline", format.format(deadline));
            callback.onClick(bundle);
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_desc;
        TextView tv_time_from;
        TextView tv_ddl;
        RoundProgressBar progressBar;

        public ViewHolder(View view) {
            tv_title = view.findViewById(R.id.tv_title);
            tv_desc = view.findViewById(R.id.tv_desc);
            tv_time_from = view.findViewById(R.id.tv_startTime);
            tv_ddl = view.findViewById(R.id.tv_endTime);
            progressBar = view.findViewById(R.id.progress_circular);
        }
    }

    public interface Callback {
        void onLongClick(int position);

        void onClick(Bundle bundle);
    }

    private String cut(String content, int length) {
        int contentLength = content.getBytes().length / 3;

        if (contentLength <= length) {
            return content;
        } else {
            String result = content.substring(0, length - 1) + "..";
            return result;
        }
    }

}
