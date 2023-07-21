package com.alphabet.audioplayer_113

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import com.alphabet.audioplayer_113.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var mp: MediaPlayer? = null
    val mediaRawPath = R.raw.kesariya
    val mediaUrlPath = "https://samplelib.com/lib/preview/mp3/sample-15s.mp3"
    val arrSongs = ArrayList<String>()
    var count = 0

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        arrSongs.apply {
            add(mediaUrlPath)
            add("https://samplelib.com/lib/preview/mp3/sample-12s.mp3")
        }

        playMusic(arrSongs[count])

        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromuser: Boolean) {
                if(fromuser){
                    mp!!.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })


        binding.btnNext.setOnClickListener {
            MediaPlayer.create(this, R.raw.click).start()
            if(mp!=null) {
                mp!!.stop()
                mp!!.reset()
                mp!!.release()
                mp = null

                if (count < arrSongs.size-1) {
                    count++
                    playMusic(arrSongs[count])
                    mp!!.start()
                }
            }

        }

        binding.btnPlay.setOnClickListener {
            MediaPlayer.create(this, R.raw.click).start()
            playMusic(arrSongs[count])
            mp!!.start()
        }

        binding.btnPause.setOnClickListener {
            MediaPlayer.create(this, R.raw.click).start()
            if(mp!=null){
                mp!!.pause()
            }
        }

        binding.btnStop.setOnClickListener {
            MediaPlayer.create(this, R.raw.click).start()
            if(mp!=null){
                mp!!.stop()
                mp!!.reset()
                mp!!.release()
                mp = null
            }
        }

        mp!!.setOnCompletionListener {
            if(mp!=null) {
                mp!!.stop()
                mp!!.reset()
                mp!!.release()
                mp = null

                if (count < arrSongs.size-1) {
                    count++
                    playMusic(arrSongs[count])
                    mp!!.start()
                }
            }
        }

    }

    fun playMusic(path : String){

        if(mp==null){
            mp = MediaPlayer.create(this, Uri.parse(path))
            initializeSeekBar()
            /*mp = MediaPlayer()
            mp!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                mp!!.setDataSource(this, Uri.parse(path))
                mp!!.prepare()
                initializeSeekBar()

            } catch (e: Exception){
                Log.d("Exception:", "${e.message}")
            }*/
        }





    }

    fun initializeSeekBar(){
        binding.seekBar.max = mp!!.duration

        Handler(Looper.myLooper()!!).postDelayed(object: Runnable{
            override fun run() {
                Handler(Looper.myLooper()!!).postDelayed(this, 1000)
                if(mp!=null){
                    binding.seekBar.progress = mp!!.currentPosition
                }
            }

        },0)
    }

    override fun onDestroy() {
        super.onDestroy()
        mp!!.stop()
        mp!!.release()
    }


}