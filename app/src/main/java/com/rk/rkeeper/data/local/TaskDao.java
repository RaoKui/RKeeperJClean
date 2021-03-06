package com.rk.rkeeper.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.rk.rkeeper.task.domain.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Tasks")
    List<Task> getTasks();

    @Query("SELECT * FROM Tasks WHERE entryid = :taskId")
    Task getTaskById(String taskId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Update
    int updateTask(Task task);

    @Query("UPDATE tasks SET completed = :completed WHERE entryid =:taskId")
    void updateCompleted(String taskId, boolean completed);

    @Query("DELETE FROM tasks  WHERE entryid = :taskId")
    void deleteTaskById(String taskId);

    @Query("DELETE  FROM tasks")
    void deleteTasks();

    @Query("DELETE FROM tasks WHERE completed=1")
    int deleteCompletedTasks();


}
