package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss()
        }

        //Toast 메시지 팝업창
        binding.songLalacLayout.setOnClickListener {
            Toast.makeText(activity, "LILAC",Toast.LENGTH_SHORT).show()
        }

        //앨범 데이터 받아오기
        binding.albumMusicTitleTv.text=arguments?.getString("title")
        binding.albumSingerNameTv.text=arguments?.getString("singer")

        return binding.root
    }

}