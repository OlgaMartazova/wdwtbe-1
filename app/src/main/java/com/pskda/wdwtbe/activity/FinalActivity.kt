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
                    tvScore?.text = "Ты получил $score/100. \nОтлично"
                    tvText?.text = "Вы справились с экзаменом.\nСоветую Вам поступать на мехМат"
                }
                score > 70 -> {
                    tvScore?.text = "Ты получил $score/100. \nХорошо"
                    tvText?.text = "Чепуху несёте.\nНатянул вам на четверку"
                }
                score > 55 -> {
                    tvScore?.text = "Ты получил $score/100. \nУдовлетворительно"
                    tvText?.text = "Чепуху несёте"
                }
                else -> {
                    tvScore?.text = "Ты получил $score/100. \nЭкзамен не сдан"
                    tvText?.text = "Молодой человек,\nвы ничего не знаете.\n Вы отчислены"
                }
            }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
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
}