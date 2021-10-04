package com.example.itunesapi.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMusic(musicEntity: MusicEntity)

    @Delete
    fun deleteMusic(musicEntity: MusicEntity)

    @Query("SELECT * FROM  musiclist")
    fun getAllMusicList():LiveData<List<MusicEntity>>
}