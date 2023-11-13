package com.example.flo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentSavedSongBinding


class SavedSongFragment : Fragment() {
    lateinit var binding: FragmentSavedSongBinding
    private var songDatas = ArrayList<Song>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)

        //데이터 리스트 생성 더미 데이터
        songDatas.apply {
            add(Song("MELTING POINT", "ZEROBASEONE(제로베이스원)", 0, 0, false, "", R.drawable.img_album_exp5))
            add(Song("Take My Hand", "ZEROBASEONE(제로베이스원)", 0, 0, false, "", R.drawable.img_album_exp5))
            add(Song("CRUSH (가시)", "ZEROBASEONE(제로베이스원)", 0, 0, false, "", R.drawable.img_album_exp5))
            add(Song("Kidz Zone", "ZEROBASEONE(제로베이스원)", 0, 0, false, "", R.drawable.img_album_exp5))
            add(Song("Good Night", "ZEROBASEONE(제로베이스원)", 0, 0, false, "", R.drawable.img_album_exp5))
        }

        val savedSongRVAdapter = SavedSongRVAdapter(songDatas)
        binding.savedSongRv.adapter = savedSongRVAdapter
        binding.savedSongRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        savedSongRVAdapter.setMyItemClickListener(object: SavedSongRVAdapter.MyItemClickListener{
            override fun onRemoveItem(position: Int) {
                savedSongRVAdapter.removeItem(position)
            }

        } )

        return binding.root
    }
}