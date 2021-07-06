package com.pskda.wdwtbe.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pskda.wdwtbe.R

class FinalActivity : AppCompatActivity() {

    private var btnRestart: ImageView? = null
    private var btnOut: ImageView? = null
    private var tvScore: TextView? = null
    private var tvText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_final)

        btnRestart = findViewById(R.id.btn_restart)
        btnOut = findViewById(R.id.btn_out)
        tvScore = findViewById(R.id.tv_result)
        tvText = findViewById(R.id.tv_text)

        btnRestart?.setOnClickListener {
            val intent = Intent (this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        btnOut?.setOnClickListener {
            this.finish()
        }


        val score = intent.getIntExtra("Score", 0)
        Log.d("FINAL", score.toString())
        when {
                score > 85 -> {
                    tvScore?.text = "Ты получил $score / 100. Отлично"
                    tvText?.text = "Вы справились с экзаменом. Советую Вам поступать на мехМат"
                }
                score > 70 -> {
                    tvScore?.text = "Ты получил $score / 100. Хорошо"
                    tvText?.text = "Чепуху несёте. Натянул вам на четверку"
                }
                score > 55 -> {
                    tvScore?.text = "Ты получил $score / 100. Удовлетворительно"
                    tvText?.text = "Чепуху несёте"
                }
                else -> {
                    tvScore?.text = "Ты получил $score / 100. Экзамен не сдан"
                    tvText?.text = "Молодой человек, вы ничего не знаете. Вы отчислены"
                }
            }

        toggleFullScreen()
    }


    private fun toggleFullScreen() {
        if (window.decorView.systemUiVisibility == View.SYSTEM_UI_FLAG_VISIBLE) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        val intent = Intent(this, MainMenuActivity::class.java)
//        startActivity(intent)
//        this.finish()
//    }

    override fun onStop() {
        super.onStop()
        this.finish()
    }

    override fun onResume() {
        super.onResume()
        toggleFullScreen()
    }
}