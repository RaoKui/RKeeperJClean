package com.rk.rkeeper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rk.rkeeper.data.TaskRepository;
import com.rk.rkeeper.data.local.TaskLocalDataSource;
import com.rk.rkeeper.data.local.ToDoDatabase;
import com.rk.rkeeper.data.remote.TaskRemoteDataSource;
import com.rk.rkeeper.task.TasksPresenter;
import com.rk.rkeeper.task.domain.TaskFilterType;
import com.rk.rkeeper.task.domain.filter.FilterFactory;
import com.rk.rkeeper.task.usecase.GetTasks;
import com.rk.rkeeper.task.view.TaskFragment;
import com.rk.rkeeper.utils.ActivityUtils;
import com.rk.rkeeper.utils.AppExecutors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    private TasksPresenter mTasksPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        if (mNavigationView != null) {
            setUpDrawerContent(mNavigationView);
        }

        TaskFragment taskFragment =
                (TaskFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (taskFragment == null) {
            taskFragment = TaskFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), taskFragment, R.id.contentFrame);
        }

        mTasksPresenter = new TasksPresenter(
                Injection.provideUseCaseHandler(),
                taskFragment,
                Injection.provideGetTasks(getApplicationContext()),
                Injection.provideCompleteTasks(getApplicationContext())
        );

        if (savedInstanceState != null) {
            TaskFilterType currentFiltering = (TaskFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mTasksPresenter.setFiltering(currentFiltering);
        }


    }

    private void setUpDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                break;
                            case R.id.statistics_navigation_menu_item:
                                break;
                            default:
                                break;
                        }
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}
