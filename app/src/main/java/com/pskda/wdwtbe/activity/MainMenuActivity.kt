package com.pskda.wdwtbe.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.pskda.wdwtbe.R

class MainMenuActivity : AppCompatActivity() {

    private var btnPlay : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        btnPlay = findViewById(R.id.btn_play)

        btnPlay?.setOnClickListener {
            val intent = Intent (this, PlayActivity::class.java)
            startActivity(intent)
        }
    }
}
