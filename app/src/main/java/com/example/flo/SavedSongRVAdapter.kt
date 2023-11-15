package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSavedSongBinding

class SavedSongRVAdapter(private val songList: ArrayList<Song>): RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onRemoveItem(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    fun removeItem(position: Int){
        songList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedSongRVAdapter.ViewHolder {
        val binding: ItemSavedSongBinding = ItemSavedSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedSongRVAdapter.ViewHolder, position: Int) {
        holder.bind(songList[position])
        holder.binding.itemSongMoreBtn.setOnClickListener{
            mItemClickListener.onRemoveItem(position)
        }
    }

    override fun getItemCount(): Int = songList.size

    inner class  ViewHolder(val binding: ItemSavedSongBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(song: Song){
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongCoverImgIv.setImageResource(song.coverImg!!)
        }
    }

}