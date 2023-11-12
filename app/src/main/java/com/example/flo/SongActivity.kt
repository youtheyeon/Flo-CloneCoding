package com.example.flo

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //바인딩 초기화
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

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
            setPlayStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayStatus(false)
        }

        //반복버튼 이미지 전환하기
        binding.songRepeatIv.setOnClickListener {
            setRepeatStatus(false)
        }
        binding.songRepeatOnIv.setOnClickListener {
            setRepeatStatus(true)
        }

        //랜덤버튼 이미지 전환하기
        binding.songRandomIv.setOnClickListener {
            setMixStatus(false)
        }
        binding.songRandomOnIv.setOnClickListener {
            setMixStatus(true)
        }

    }

    private fun initSong() {
        if(intent.hasExtra("title") && intent.hasExtra("singer")) {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text=intent.getStringExtra("title")
        binding.songSingerNameTv.text=intent.getStringExtra("singer")
        binding.songStartTimeTv.text=String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text=String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        setPlayStatus(song.isPlaying)
    }


    private fun setPlayStatus(isPlaying : Boolean) {
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }
    }

    private fun setRepeatStatus(isRepeating : Boolean) {
        if(isRepeating) {
            binding.songRepeatIv.visibility = View.VISIBLE
            binding.songRepeatOnIv.visibility = View.GONE
        } else {
            binding.songRepeatIv.visibility = View.GONE
            binding.songRepeatOnIv.visibility = View.VISIBLE
        }
    }

    private fun setMixStatus(isMixing : Boolean) {
        if(isMixing) {
            binding.songRandomIv.visibility = View.VISIBLE
            binding.songRandomOnIv.visibility = View.GONE
        } else {
            binding.songRandomIv.visibility = View.GONE
            binding.songRandomOnIv.visibility = View.VISIBLE
        }
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true):Thread(){

        private var second : Int = 0
        private var mills : Float = 0f

        override fun run() {
            super.run()
            try {
                while (true){
                    if (second >= playTime){
                        break
                    }


                    if (isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime) * 100).toInt()
                        }

                        if (mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            } catch (e: InterruptedException){
                Log.d("Song","재생 스레드 오류 ${e.message}")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        setPlayStatus(false)
        song.second = ((binding.songProgressSb.progress * song.playTime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)

        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() //미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null // 미디어 플레이어 해제
    }
}

