package com.rk.rkeeper.task.view;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        final Task task = mDataList.get(position);
        holder.mTvTitle.setText(task.getTitle());
        if (task.isCompleted()) {
            holder.mCheck.setVisibility(View.INVISIBLE);
            holder.mTvTitle.setTextColor(mContext.getResources().getColor(R.color.colorNine));
            holder.mTvTitle.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.mCheck.setVisibility(View.VISIBLE);
            holder.mTvTitle.setTextColor(mContext.getResources().getColor(R.color.colorThree));

        }

        holder.mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mTaskItemListener != null) {
                        mTaskItemListener.onTaskCheck(task);
                    }
                }
            }
        });
    }

    private TaskItemListener mTaskItemListener;

    public void setTaskItemListener(@NonNull TaskItemListener taskItemListener) {
        checkNotNull(taskItemListener, "taskItemListener cannot be null@");
        this.mTaskItemListener = taskItemListener;
    }

    public interface TaskItemListener {
        void onTaskClick(Task task);

        void onTaskCheck(Task task);

        void onTaskUncheck(Task task);
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
