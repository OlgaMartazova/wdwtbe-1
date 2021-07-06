package com.pskda.wdwtbe.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.pskda.wdwtbe.R
import kotlin.random.Random

class PrepareActivity : AppCompatActivity(), View.OnClickListener  {

    private var btnRules: Button? = null

    private var btnDifficulty6: Button? = null
    private var btnDifficulty1025: Button? = null
    private var btnDifficulty2640: Button? = null
    private var btnDifficulty4150: Button? = null

    private var isDifficultyChosen: Boolean = false
    private var difficultyScore: Int = 0

    private val hardDifficulty = 6
    private val difficulty10 = 10
    private val difficulty25 = 25
    private val difficulty40 = 40
    private val difficulty50 = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prepare)

        findView()

        btnRules!!.setOnClickListener(this)
        btnDifficulty6!!.setOnClickListener(this)
        btnDifficulty1025!!.setOnClickListener(this)
        btnDifficulty2640!!.setOnClickListener(this)
        btnDifficulty4150!!.setOnClickListener(this)
    }

    private fun findView() {
        btnRules = findViewById(R.id.btn_rules)
        btnDifficulty6 = findViewById(R.id.btn_difficulty_6)
        btnDifficulty1025 = findViewById(R.id.btn_difficulty_10_25)
        btnDifficulty2640 = findViewById(R.id.btn_difficulty_26_40)
        btnDifficulty4150 = findViewById(R.id.btn_difficulty_41_50)
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            btnRules?.id ->  {
                showMessage("правила игры!")
                TODO("fragments? dialogs?")
                //btnRules?.visibility = GONE
            }

            btnDifficulty6?.id -> {
                Log.d("KNOPKA", "Нажата кнопка выбора сложности 6")

                if (!isDifficultyChosen) {
                    difficultyScore = hardDifficulty
                    isDifficultyChosen = true
                }
            }

            btnDifficulty1025?.id -> {
                Log.d("KNOPKA", "Нажата кнопка выбора сложности 10 - 25")
                if (!isDifficultyChosen) {
                    difficultyScore = Random.nextInt(from = difficulty10, until = difficulty25 + 1)
                    isDifficultyChosen = true
                }
            }

            btnDifficulty2640?.id -> {
                Log.d("KNOPKA", "Нажата кнопка выбора сложности 26 - 40")
                if (!isDifficultyChosen) {
                    difficultyScore = Random.nextInt(from = difficulty25 + 1, until = difficulty40 + 1)
                    isDifficultyChosen = true
                }
            }

            btnDifficulty4150?.id -> {
                Log.d("KNOPKA", "Нажата кнопка выбора сложности 41 - 50")
                if (!isDifficultyChosen) {
                    difficultyScore = Random.nextInt(from = difficulty40 + 1, until = difficulty50 + 1)
                    isDifficultyChosen = true
                }
            }

        }

        if (isDifficultyChosen) {
            val intent = Intent (this, PlayActivity::class.java)
            intent.putExtra("Difficulty", difficultyScore)
            startActivity(intent)
        }

    }
}