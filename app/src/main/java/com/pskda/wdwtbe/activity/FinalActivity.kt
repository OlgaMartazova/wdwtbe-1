package com.pskda.wdwtbe.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pskda.wdwtbe.databinding.ActivityFinalBinding

class FinalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnRestart.setOnClickListener {
            val intent = Intent (this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        binding.btnOut.setOnClickListener {
            this.finish()
        }

        val result = intent.getIntExtra("Score", 0)
        with (binding) {
            when {
                result > 85 -> {
                    tvResult.text = "Ты получил $result/100. \nОтлично"
                    tvText.text = "Вы справились с экзаменом.\nСоветую Вам поступать на мехМат"
                }
                result > 70 -> {
                    tvResult.text = "Ты получил $result/100. \nХорошо"
                    tvText.text = "Чепуху несёте.\nНатянул вам на четверку"
                }
                result > 55 -> {
                    tvResult.text = "Ты получил $result/100. \nУдовлетворительно"
                    tvText.text = "Чепуху несёте"
                }
                else -> {
                    tvResult.text = "Ты получил $result/100. \nЭкзамен не сдан"
                    tvText.text = "Молодой человек,\nвы ничего не знаете.\nВы отчислены"
                }
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

    override fun onStop() {
        super.onStop()
        this.finish()
    }
}