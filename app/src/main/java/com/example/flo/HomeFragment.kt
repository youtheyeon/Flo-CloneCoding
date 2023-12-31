package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment(), AlbumRVAdapter.CommunicationInterface {

    private lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //데이터 리스트 생성 더미 데이터
        albumDatas.apply {
            add(Album("MELTING POINT", "ZEROBASEONE(제로베이스원)", R.drawable.img_album_exp5).apply {
                songs = ArrayList<Song>().apply {
                    add(Song("MELTING POINT", "ZEROBASEONE(제로베이스원)", 0, 0, false, "", R.drawable.img_album_exp5))
                    add(Song("Take My Hand", "ZEROBASEONE(제로베이스원)", 0, 0, false, "", R.drawable.img_album_exp5))
                    add(Song("CRUSH (가시)", "ZEROBASEONE(제로베이스원)", 0, 0, false, "", R.drawable.img_album_exp5))
                    add(Song("Kidz Zone", "ZEROBASEONE(제로베이스원)", 0, 0, false, "", R.drawable.img_album_exp5))
                    add(Song("Good Night", "ZEROBASEONE(제로베이스원)", 0, 0, false, "", R.drawable.img_album_exp5))
                }
            })
            add(Album("YOUTH IN THE SHADE", "ZEROBASEONE(제로베이스원)", R.drawable.img_album_exp6))
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))

        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onPlayBtnClick(album: Album) {
                sendData(album)
            }
        } )

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }

    override fun sendData(album: Album) {
        val firstSong = album.songs?.getOrNull(0)
        if (firstSong != null) {
            if (activity is MainActivity) {
                val activity = activity as MainActivity
                activity.updateMainPlayer(firstSong)
            }
        }
    }

}