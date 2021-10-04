package com.example.itunesapi.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesapi.R
import com.example.itunesapi.RoomDatabase.MusicEntity
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class MusicDatabaseRoomAdapter(val context:Context): RecyclerView.Adapter<MusicDatabaseRoomAdapter.MusicRoomViewHolder>() {

    private var musicList = emptyList<MusicEntity>()

    class MusicRoomViewHolder(musicView: View) : RecyclerView.ViewHolder(musicView) {
        val wrapperType    : TextView = musicView.findViewById(R.id.txtWrapperType)
        val kind           : TextView = musicView.findViewById(R.id.txtKind)
        val trackID        : TextView = musicView.findViewById(R.id.txtTrackID)
        val artistName     : TextView = musicView.findViewById(R.id.txtArtistname)
        val trackName      : TextView = musicView.findViewById(R.id.txtTrackName)
        val iTunesExtra    : TextView = musicView.findViewById(R.id.txtItunesExtras)
        val country        : TextView = musicView.findViewById(R.id.txtCountry)
        val currency       : TextView = musicView.findViewById(R.id.txtCurrency)
        val trackUrl       : TextView = musicView.findViewById(R.id.txtTrackUrl)
        val artWork1       : ImageView = musicView.findViewById(R.id.imgartWork1)
        val artWork2       : ImageView = musicView.findViewById(R.id.imgartWork2)
        val previewUrl       : TextView = musicView.findViewById(R.id.txtTrackPreviewUrl)
        val musicContent   : CardView = musicView.findViewById(R.id.musicContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicRoomViewHolder {
        return MusicRoomViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.music_recyclerview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MusicRoomViewHolder, position: Int) {
        val list = musicList[position]
        holder.wrapperType.text = "Wrapper Type : ${list.wrapperType}"
        holder.kind.text        = "Kind : ${list.kind}"
        holder.trackID.text     = "Track ID : ${list.trackId}"
        holder.artistName.text  = "Artist name : ${list.artistName}"
        holder.trackName.text   = "Track Name : ${list.trackName}"
        holder.iTunesExtra.text = "Has Itunes Extras : ${list.hasITunesExtras}"
        holder.country.text     = "Country : ${list.country}"
        holder.currency.text    = "Currency : ${list.currency}"
        holder.trackUrl.text    = "Track Url : ${list.trackViewUrl}"
        holder.previewUrl.text  = "Track Preview Url :${list.previewUrl}"
        Picasso.get().load(list.artworkUrl30).into(holder.artWork1)
        Picasso.get().load(list.artworkUrl100).into(holder.artWork2)
        holder.musicContent.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(list.previewUrl))
            context.startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(musicList: List<MusicEntity>) {
        this.musicList = musicList
        notifyDataSetChanged()
    }
}