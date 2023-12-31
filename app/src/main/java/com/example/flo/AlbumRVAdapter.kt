package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(album: Album)
        fun onPlayBtnClick(album: Album)
    }

    interface CommunicationInterface {
        fun sendData(album: Album)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        //뷰홀더 생성, 아이템 뷰 객체 생성
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        //뷰홀더에 데이터 바인딩
        holder.bind(albumList[position])
        //클릭이벤트 적용
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(albumList[position])
        }
        //Play 버튼 클릭이벤트
        holder.binding.itemAlbumPlayImgIv.setOnClickListener {
            mItemClickListener.onPlayBtnClick(albumList[position])
        }
    }

    override fun getItemCount(): Int = albumList.size
    inner class  ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }

}