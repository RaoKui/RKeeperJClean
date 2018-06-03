package com.rk.rkeeper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rk.rkeeper.data.TaskRepository;
import com.rk.rkeeper.data.local.TaskLocalDataSource;
import com.rk.rkeeper.data.local.ToDoDatabase;
import com.rk.rkeeper.data.remote.TaskRemoteDataSource;
import com.rk.rkeeper.task.domain.filter.FilterFactory;
import com.rk.rkeeper.task.usecase.GetTasks;
import com.rk.rkeeper.task.usecase.SaveTask;
import com.rk.rkeeper.utils.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {
    public static TaskRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        ToDoDatabase toDoDatabase = ToDoDatabase.getInstance(context);
        return TaskRepository.getInstance(TaskRemoteDataSource.getInstance(),
                TaskLocalDataSource.getInstance(new AppExecutors(),
                        toDoDatabase.taskDao()));
    }

    public static GetTasks provideGetTasks(@NonNull Context context) {
        return new GetTasks(provideTasksRepository(context), new FilterFactory());
    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

//    public static GetTask provideGetTask(Context context) {
//        return new GetTask(Injection.provideTasksRepository(context));
//    }

    public static SaveTask provideSaveTask(Context context) {
        return new SaveTask(Injection.provideTasksRepository(context));
    }

//    public static CompleteTask provideCompleteTasks(Context context) {
//        return new CompleteTask(Injection.provideTasksRepository(context));
//    }
//
//    public static ActivateTask provideActivateTask(Context context) {
//        return new ActivateTask(Injection.provideTasksRepository(context));
//    }
//
//    public static ClearCompleteTasks provideClearCompleteTasks(Context context) {
//        return new ClearCompleteTasks(Injection.provideTasksRepository(context));
//    }
//
//    public static DeleteTask provideDeleteTask(Context context) {
//        return new DeleteTask(Injection.provideTasksRepository(context));
//    }
//
//    public static GetStatistics provideGetStatistics(Context context) {
//        return new GetStatistics(Injection.provideTasksRepository(context));
//    }
}
