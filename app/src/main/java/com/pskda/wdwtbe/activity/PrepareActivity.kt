package com.pskda.wdwtbe.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.pskda.wdwtbe.R
import kotlin.random.Random

class PrepareActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var controller: NavController

    private var btnDifficulty6: Button? = null
    private var btnDifficulty1025: Button? = null
    private var btnDifficulty2640: Button? = null
    private var btnDifficulty4150: Button? = null

    private var imgBtn50_50: ImageButton? = null
    private var imgBtnCheatNote: ImageButton? = null
    private var imgBtnGroupmateHelp: ImageButton? = null
    private var imgBtnArsiksHelp: ImageButton? = null

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

        btnDifficulty6!!.setOnClickListener(this)
        btnDifficulty1025!!.setOnClickListener(this)
        btnDifficulty2640!!.setOnClickListener(this)
        btnDifficulty4150!!.setOnClickListener(this)

        imgBtn50_50!!.setOnClickListener(this)
        imgBtnCheatNote!!.setOnClickListener(this)
        imgBtnGroupmateHelp!!.setOnClickListener(this)
        imgBtnArsiksHelp!!.setOnClickListener(this)

        controller = (supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment)
            .navController
    }

    private fun findView() {
        btnDifficulty6 = findViewById(R.id.btn_difficulty_6)
        btnDifficulty1025 = findViewById(R.id.btn_difficulty_10_25)
        btnDifficulty2640 = findViewById(R.id.btn_difficulty_26_40)
        btnDifficulty4150 = findViewById(R.id.btn_difficulty_41_50)

        imgBtn50_50 = findViewById(R.id.img_btn_fifty_fifty)
        imgBtnCheatNote = findViewById(R.id.img_btn_cheat_notes)
        imgBtnGroupmateHelp = findViewById(R.id.img_btn_groupmate_help)
        imgBtnArsiksHelp = findViewById(R.id.img_btn_arsiks_help)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
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

            imgBtn50_50?.id -> {
                showDialog(getString(R.string._50_50), getString(R.string._50_50_descr))
            }

            imgBtnCheatNote?.id -> {
                showDialog(getString(R.string.cheat_note), getString(R.string.cheat_note_descr))
            }

            imgBtnGroupmateHelp?.id -> {
                showDialog(getString(R.string.groupmate_help), getString(R.string.groupmate_help_descr))
            }

            imgBtnArsiksHelp?.id -> {
                showDialog(getString(R.string.ars_help), getString(R.string.ars_help_descr))
            }

        }

        if (isDifficultyChosen) {
            val intent = Intent (this, PlayActivity::class.java)
            intent.putExtra("Difficulty", difficultyScore)
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean = controller.navigateUp()

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
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