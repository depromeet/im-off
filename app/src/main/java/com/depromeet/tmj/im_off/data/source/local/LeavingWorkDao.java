package com.depromeet.tmj.im_off.data.source.local;

import com.depromeet.tmj.im_off.data.LeavingWork;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LeavingWorkDao {

    @Query("SELECT * FROM leavingworks")
    List<LeavingWork> getLeavingWorks();

    @Query("SELECT * FROM leavingworks WHERE entryid = :leavingWorkId")
    LeavingWork getLeavingWorkById(String leavingWorkId);

    @Query("SELECT count(*) FROM leavingworks WHERE iskaltoe = 1")
    int getKaltoeCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLeavingWork(LeavingWork leavingWork);

    @Update
    int updateLeavingWork(LeavingWork leavingWork);
}
