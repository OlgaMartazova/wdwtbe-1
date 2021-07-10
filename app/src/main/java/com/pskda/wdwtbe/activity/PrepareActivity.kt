package com.pskda.wdwtbe.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.pskda.wdwtbe.R
import com.pskda.wdwtbe.databinding.ActivityPlayBinding
import com.pskda.wdwtbe.databinding.ActivityPrepareBinding
import kotlin.random.Random


class PrepareActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var controller: NavController

    private var isDifficultyChosen: Boolean = false
    private var difficultyScore: Int = 0

    private val hardDifficulty = 6
    private val difficulty10 = 10
    private val difficulty25 = 25
    private val difficulty40 = 40
    private val difficulty50 = 50

    private lateinit var binding: ActivityPrepareBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrepareBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnDifficulty6.setOnClickListener(this)
        binding.btnDifficulty1025.setOnClickListener(this)
        binding.btnDifficulty2640.setOnClickListener(this)
        binding.btnDifficulty4150.setOnClickListener(this)

        binding.btnFiftyFifty.setOnClickListener(this)
        binding.btnCheatNotes.setOnClickListener(this)
        binding.btnGroupmateHelp.setOnClickListener(this)
        binding.btnArsiksHelp.setOnClickListener(this)

        controller = (supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment)
            .navController
    }

    override fun onClick(view: View?) {
        with (binding) {
            when (view?.id) {
                btnDifficulty6.id -> {
                    Log.d("KNOPKA", "Нажата кнопка выбора сложности 6")

                    if (!isDifficultyChosen) {
                        difficultyScore = hardDifficulty
                        isDifficultyChosen = true
                    }
                }

                btnDifficulty1025.id -> {
                    Log.d("KNOPKA", "Нажата кнопка выбора сложности 10 - 25")
                    if (!isDifficultyChosen) {
                        difficultyScore =
                            Random.nextInt(from = difficulty10, until = difficulty25 + 1)
                        isDifficultyChosen = true
                    }
                }

                btnDifficulty2640.id -> {
                    Log.d("KNOPKA", "Нажата кнопка выбора сложности 26 - 40")
                    if (!isDifficultyChosen) {
                        difficultyScore =
                            Random.nextInt(from = difficulty25 + 1, until = difficulty40 + 1)
                        isDifficultyChosen = true
                    }
                }

                btnDifficulty4150.id -> {
                    Log.d("KNOPKA", "Нажата кнопка выбора сложности 41 - 50")
                    if (!isDifficultyChosen) {
                        difficultyScore =
                            Random.nextInt(from = difficulty40 + 1, until = difficulty50 + 1)
                        isDifficultyChosen = true
                    }
                }

                btnFiftyFifty.id -> {
                    showDialog(getString(R.string._50_50), getString(R.string._50_50_descr))
                }

                btnCheatNotes.id -> {
                    showDialog(getString(R.string.cheat_note), getString(R.string.cheat_note_descr))
                }

                btnGroupmateHelp.id -> {
                    showDialog(
                        getString(R.string.groupmate_help),
                        getString(R.string.groupmate_help_descr)
                    )
                }

                btnArsiksHelp.id -> {
                    showDialog(getString(R.string.ars_help), getString(R.string.ars_help_descr))
                }
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
        val alertDialog =
            AlertDialog.Builder(this,  R.style.MyAlertDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(
                "OK")
                { dialogInterface, i -> dialogInterface.dismiss() }
                .create()

        alertDialog.window!!.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )

        alertDialog.show()

        hideSystemUI(alertDialog.window!!.decorView)

        alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI(decorView: View = window.decorView) {
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