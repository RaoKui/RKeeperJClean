package com.rk.rkeeper.task.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rk.rkeeper.R;
import com.rk.rkeeper.UseCaseHandler;
import com.rk.rkeeper.UseCaseThreadPoolScheduler;
import com.rk.rkeeper.data.TaskRepository;
import com.rk.rkeeper.data.local.TaskLocalDataSource;
import com.rk.rkeeper.data.local.ToDoDatabase;
import com.rk.rkeeper.data.remote.TaskRemoteDataSource;
import com.rk.rkeeper.task.AddTaskContract;
import com.rk.rkeeper.task.AddTaskPresenter;
import com.rk.rkeeper.task.usecase.SaveTask;
import com.rk.rkeeper.utils.AppExecutors;

import java.security.PrivateKey;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddTaskActivity extends AppCompatActivity implements AddTaskContract.View {

    private AddTaskContract.Presenter mPresenter;

    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.et_description)
    EditText mEtDescription;
    @BindView(R.id.btn_add)
    Button mBtnSave;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);

        ToDoDatabase toDoDatabase = ToDoDatabase.getInstance(this);
        mPresenter = new AddTaskPresenter(new UseCaseHandler(new UseCaseThreadPoolScheduler()),
                null,
                this,
                new SaveTask(TaskRepository.getInstance(TaskRemoteDataSource.getInstance(),
                        TaskLocalDataSource.getInstance(new AppExecutors(), toDoDatabase.taskDao()))),
                false
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @OnClick(R.id.btn_add)
    public void saveTask() {
        mPresenter.saveTask(mEtTitle.getText().toString(), mEtDescription.getText().toString());
    }

    @Override
    public void setPresenter(AddTaskContract.Presenter presenter) {
//        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void showEmptyTaskError() {
        Toast.makeText(this, "内容不能为空！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTasksList() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void setTitle(String title) {
        mEtTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        mEtDescription.setText(description);
    }

    @Override
    public void isActive() {

    }
}
