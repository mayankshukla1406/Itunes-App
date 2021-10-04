package com.example.itunesapi.RoomDatabase

import androidx.lifecycle.LiveData

class MusicRepository(private val musicDao: MusicDao) {

    val readAllData: LiveData<List<MusicEntity>> = musicDao.getAllMusicList()

    suspend fun addMusic(musicEntity:MusicEntity){
        musicDao.insertMusic(musicEntity)
    }
}