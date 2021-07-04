package com.pskda.wdwtbe.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pskda.wdwtbe.R

class FinalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_final)

        val tv_final: TextView = findViewById(R.id.tv_score)

        val text = getString(R.string.final_score_string, intent.getIntExtra("Score",0))

        tv_final.text = text
    }
}