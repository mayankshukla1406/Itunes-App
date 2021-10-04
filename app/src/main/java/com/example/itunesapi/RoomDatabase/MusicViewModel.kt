package com.example.itunesapi.RoomDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<MusicEntity>>
    val repository: MusicRepository

    init {
        val musicDao = MusicDatabase.getDatabase(application).musicDao()
        repository = MusicRepository(musicDao)
        readAllData = repository.readAllData
    }

    fun addMusicList(musicEntity: MusicEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMusic(musicEntity)
        }
    }
}