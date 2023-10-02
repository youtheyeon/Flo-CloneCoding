package com.example.flo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater) //바인딩 초기화
        setContentView(binding.root)

        //Activity 종료하기
        binding.songDownIb.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(MainActivity.STRING_INTENT_KEY, binding.songMusicTitleTv.text.toString())
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        //재생버튼 이미지 전환하기
        binding.songMiniplayerIv.setOnClickListener {
            setPlayStatus(false)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayStatus(true)
        }

        //미니플레이어에서 제목, 가수 데이터 받아서 바인딩
        if(intent.hasExtra("title") && intent.hasExtra("singer")) {
            binding.songMusicTitleTv.text=intent.getStringExtra("title")
            binding.songSingerNameTv.text=intent.getStringExtra("singer")
        }


    }

    fun setPlayStatus(isPalying : Boolean) {
        if(isPalying) {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        } else {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }
}

