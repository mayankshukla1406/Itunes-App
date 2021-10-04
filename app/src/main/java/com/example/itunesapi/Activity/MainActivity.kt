package com.example.itunesapi.Activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesapi.Adapter.MusicAdapter
import com.example.itunesapi.Adapter.MusicDatabaseRoomAdapter
import com.example.itunesapi.Models.musicModel
import com.example.itunesapi.R
import com.example.itunesapi.RoomDatabase.MusicDatabase
import com.example.itunesapi.RoomDatabase.MusicEntity
import com.example.itunesapi.RoomDatabase.MusicViewModel
import com.example.itunesapi.retrofit.iTunesInstance
import retrofit2.HttpException
import java.io.IOException


class MainActivity : AppCompatActivity(),androidx.appcompat.widget.SearchView.OnQueryTextListener {
    lateinit var musicViewModel      : MusicViewModel
    private lateinit var logo                : ImageView
    private lateinit var musicRecyclerView   : RecyclerView
    private lateinit var musicLayoutManager  : RecyclerView.LayoutManager
    private lateinit var musicAdapter        : MusicAdapter
    private lateinit var progressBar         : ProgressBar
    private lateinit var searchTerm          : String
    private lateinit var addData             : Button
    private lateinit var db                  : MusicDatabase
    private lateinit var musicroomRecyclerView    : RecyclerView
    private lateinit var musicroomAdapter         : MusicDatabaseRoomAdapter
    private lateinit var musicroomLayoutManager   : RecyclerView.LayoutManager
    lateinit var modal                       : List<musicModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        musicViewModel      = ViewModelProvider(this).get(MusicViewModel::class.java)
        progressBar         = findViewById(R.id.progressBar)
        addData             = findViewById(R.id.btAdd)
        logo                = findViewById(R.id.imgLogo)
        musicRecyclerView   = findViewById(R.id.recyclerView)
        musicLayoutManager  = GridLayoutManager(this, 1)
        musicroomRecyclerView = findViewById(R.id.recyclerViewRoom)
        musicroomLayoutManager = GridLayoutManager(this,1)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menubar, menu)
        val search = menu?.findItem(R.id.menuSearch)
        val searchView = search?.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(queryMusic: String?): Boolean {
        if (queryMusic != null) {
            searchTerm = queryMusic
            if (searchTerm.isNotEmpty()) {
                Log.d(
                    "TAG", searchTerm
                )
                search()
            }
        }
        return true
    }

    override fun onQueryTextChange(queryMusic: String?): Boolean {
        if (queryMusic != null) {
            searchTerm = queryMusic
            Log.d("SearchValue", searchTerm)
            if (searchTerm.isNotEmpty()) {
                search()
            }
        }
        return true
    }

    private fun search() {
        Log.d(
            "searchinisdne", searchTerm
        )
        // Do the GET request and get response
        lifecycleScope.launchWhenCreated {
            val response = try{
                iTunesInstance.api.getResult(searchTerm)
            }
            catch (e: IOException)
            {
                Log.e(
                    "Check your internet","Internet issue"
                )
                musicroomAdapter = MusicDatabaseRoomAdapter(this@MainActivity)
                musicroomRecyclerView.adapter = musicroomAdapter
                musicroomRecyclerView.layoutManager = musicroomLayoutManager
                musicViewModel.readAllData.observe(this@MainActivity, { musiclist ->
                    musicroomAdapter.setData(musiclist)
                })
                return@launchWhenCreated
            }
            catch (e:HttpException)
            {
                Log.e(
                    "https", "Http Issue"
                )
                return@launchWhenCreated
            }
            if (response.isSuccessful&&response.body()!=null) {
                addData.visibility = View.VISIBLE
                modal = response.body()!!.results
                addData.setOnClickListener{
                    insertData()
                }
                Log.d("All Value: ", modal.toString())
                musicAdapter = MusicAdapter(this@MainActivity,modal)
                musicRecyclerView.adapter = musicAdapter
                musicRecyclerView.layoutManager = musicLayoutManager
                musicRecyclerView.addItemDecoration(DividerItemDecoration(musicRecyclerView.context,(musicLayoutManager as GridLayoutManager).orientation))
            }
        }
    }
    private fun insertData() {
        for(i in 1..20) {
            val musicList = MusicEntity(
                modal[i].wrapperType,
                modal[i].kind,
                modal[i].trackId,
                modal[i].artistName,
                modal[i].trackName,
                modal[i].trackCensoredName,
                modal[i].trackViewUrl,
                modal[i].previewUrl,
                modal[i].artworkUrl30,
                modal[i].artworkUrl60,
                modal[i].artworkUrl100,
                modal[i].collectionPrice,
                modal[i].trackPrice,
                modal[i].trackRentalPrice,
                modal[i].collectionHdPrice,
                modal[i].trackHdPrice,
                modal[i].trackHdRentalPrice,
                modal[i].releaseDate,
                modal[i].collectionExplicitness,
                modal[i].trackExplicitness,
                modal[i].trackTimeMillis,
                modal[i].country,
                modal[i].currency,
                modal[i].primaryGenreName,
                modal[i].contentAdvisoryRating,
                modal[i].shortDescription,
                modal[i].longDescription,
                modal[i].hasITunesExtras
            )
            musicViewModel.addMusicList(musicList)
        }
    }
}