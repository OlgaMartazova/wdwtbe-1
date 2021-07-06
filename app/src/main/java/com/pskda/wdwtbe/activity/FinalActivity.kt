package com.pskda.wdwtbe.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pskda.wdwtbe.R

class FinalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_final)

        val tvFinal: TextView = findViewById(R.id.tv_score)

        val text = getString(R.string.final_score_string, intent.getIntExtra("Score", 0))

        tvFinal.text = text
        toggleFullScreen()
    }

    fun toggleFullScreen() {
        if (window.decorView.systemUiVisibility == View.SYSTEM_UI_FLAG_VISIBLE) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
        this.finish()
    }
    
    override fun onStop() {
        super.onStop()
        this.finish()
    }

    override fun onResume() {
        super.onResume()
        toggleFullScreen()
    }
}