package com.rk.rkeeper.task.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.rk.rkeeper.R;
import com.rk.rkeeper.task.domain.Task;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.Holder> {

    private List<Task> mDataList;

    private Context mContext;


    public TasksAdapter(@NonNull Context context) {
        mDataList = new ArrayList<>();
        checkNotNull(context);
        mContext = context;
    }

    public void setData(@NonNull List<Task> dataList) {
        checkNotNull(dataList);
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Task task = mDataList.get(position);
        holder.mTvTitle.setText(task.getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        TextView mTvTitle;
        CheckBox mCheck;

        public Holder(View itemView) {
            super(itemView);
            mCheck = itemView.findViewById(R.id.checkbox);
            mTvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
