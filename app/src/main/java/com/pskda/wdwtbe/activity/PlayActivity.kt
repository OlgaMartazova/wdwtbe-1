package com.pskda.wdwtbe.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.WindowManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.pskda.wdwtbe.R
import com.pskda.wdwtbe.model.Question
import com.pskda.wdwtbe.model.QuestionRepository

import java.util.*

class PlayActivity : AppCompatActivity(), View.OnClickListener {

    private var btnAns1: Button? = null
    private var btnAns2: Button? = null
    private var btnAns3: Button? = null
    private var btnAns4: Button? = null

    private var tvQuestion: TextView? = null
    private var tvScore: TextView? = null
    private var tvType: TextView? = null

    private var progressBarHorizontal: ProgressBar? = null

    private var btnHelp1: Button? = null
    private var btnHelp2: Button? = null
    private var btnHelp3: Button? = null
    private var btnHelp4: Button? = null

    //private val diff = arrayOf(1, 2, 3, 5, 7, 9, 10, 13)
    private val diff = arrayOf(1, 2)
    private var curDifficulty: Int = 0
    private var curDifficultyIndex: Int = 0

    private var rightBtnIndex: Int = 0

    private var startMills: Long = 30000

    private var curQuestionExtra = false

    private var buttonsOfAnswer: List<Button?> = emptyList()
    private var buttonsOfAnswerId: List<Int> = emptyList()
    private var indicesOfAnswer = arrayOf(-1, -1, -1, -1)

    private var buttonsOfHelp: List<Button?> = emptyList()
    private var buttonsOfHelpId: List<Int> = emptyList()
    private var isHelpUsed = arrayOf(false, false, false, false)


    private var scoreCnt: Int = 0

    private var helpCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_play)
        findView()
        toggleFullScreen()

        btnAns1!!.setOnClickListener(this)
        btnAns2!!.setOnClickListener(this)
        btnAns3!!.setOnClickListener(this)
        btnAns4!!.setOnClickListener(this)

        btnHelp1!!.setOnClickListener(this)
        btnHelp2!!.setOnClickListener(this)
        btnHelp3!!.setOnClickListener(this)
        btnHelp4!!.setOnClickListener(this)

        tvType?.text = getString(R.string.extra_string)

        scoreCnt = intent?.extras?.getInt("Difficulty")!!

        setQuestion(diff[curDifficultyIndex])
    }

    private fun setQuestion(item: Int) {
        curDifficulty = item
        for (btn in buttonsOfAnswer) {
            btn?.visibility = VISIBLE
            btn?.isClickable = true
            btn?.setBackgroundColor(0)
        } //кнопки становятся кликабельными, цвета обнуляются

        var question: Question? = QuestionRepository.questions[item]?.get(Random().nextInt(3))
        var type: Boolean? = question?.isExtra

        if (item == 1) {
            while (type == true) {
                question = QuestionRepository.questions[item]?.get(Random().nextInt(3))
            }
        }
        // Арсик использован - теперь все вопросы ДОП
        if (isHelpUsed[3]) {
            while (type == false) {
                question = QuestionRepository.questions[item]?.get(Random().nextInt(3))
                if (question != null) {
                    type = question.isExtra
                }
            }
        }

        curQuestionExtra = type!!

        if (type) {
            tvType?.visibility = VISIBLE
            startMills = 10000
            timer.start()
        } else {
            tvType?.visibility = GONE
            startMills = 30000
            timer.start()
        }

        for (ind in isHelpUsed.indices) {
            // Использованные подсказки не видны
            if (isHelpUsed[ind]) buttonsOfHelp[ind]?.visibility = GONE
            else buttonsOfHelp[ind]?.visibility = VISIBLE
        }

        tvQuestion?.setText(question?.name).toString()
        tvScore?.text = scoreCnt.toString()

        for (ind in buttonsOfAnswer.indices) {
            var index: Int = Random().nextInt(4)

            while (indicesOfAnswer.contains(index)) {
                index = Random().nextInt(4)
            }

            buttonsOfAnswer[ind]?.setText(question?.answers?.get(index)).toString()
            indicesOfAnswer[ind] = index
        }
        rightBtnIndex = question!!.indexOfRightAnswer
        buttonsOfAnswer[rightBtnIndex]?.text = "Это верная кнопка"
    }

    private var timer = object : CountDownTimer(startMills, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            progressBarHorizontal!!.setProgress((millisUntilFinished).toInt(), true)
        }

        override fun onFinish() {
            progressBarHorizontal!!.setProgress(0, true)
            for (btn in buttonsOfAnswer) btn?.isClickable = false
            buttonsOfAnswer[rightBtnIndex]!!.setBackgroundColor(resources.getColor(R.color.colorGood))
            nextQuestion()
        }
    }



    override fun onClick(v: View?) {
        Log.d("KNOPKA", "Нажата кнопка")
        for (btn in buttonsOfAnswer) btn?.isClickable = false
        when (v?.id) {

            // 50/50
            buttonsOfHelpId[0] -> {
                v.isClickable = false
                // За игру можно использовать 3 из 4 подсказок
                if (curQuestionExtra) {
                    helpBlocked()
                } else {
                    if (helpCount == 3) {
                        helpFinished()
                    } else {
                        isHelpUsed[0] = true
                        helpCount++
                        helpFiftyFifty()
                    }
                }
            }
            // шпаргалка
            buttonsOfHelpId[1] -> {
                v.isClickable = false
                if (curQuestionExtra) {
                    helpBlocked()
                } else {
                    if (helpCount == 3) {
                        helpFinished()
                    } else {
                        isHelpUsed[1] = true
                        helpCount++
                        helpCheating()
                    }
                }
            }
            // помощь одногруппника
            buttonsOfHelpId[2] -> {
                v.isClickable = false
                if (curQuestionExtra) {
                    helpBlocked()
                } else {
                    if (helpCount == 3) {
                        helpFinished()
                    } else {
                        isHelpUsed[2] = true
                        helpCount++
                        helpClassmate()
                    }
                }
            }
            // помощь арсика
            buttonsOfHelpId[3] -> {
                v.isClickable = false
                if (curQuestionExtra) {
                    helpBlocked()
                } else {
                    if (helpCount == 3) {
                        helpFinished()
                    } else {
                        isHelpUsed[3] = true
                        helpCount++
                        helpArsik()
                    }
                }
            }
            // Логика для верного ответа
            buttonsOfAnswerId[rightBtnIndex] -> {
                Log.d("KNOPKA", "Нажата верная кнопка")
                v.setBackgroundColor(resources.getColor(R.color.colorGood))
                scoreCnt += curDifficulty // добавляем к текущим баллам сложность
                tvScore?.text = scoreCnt.toString() // показываем баллы
                nextQuestion()
            }
            // Логика для неверного ответа
            else -> {
                Log.d("KNOPKA", "Нажата неверная кнопка")
                v!!.setBackgroundColor(resources.getColor(R.color.colorBad))
                buttonsOfAnswer[rightBtnIndex]!!.setBackgroundColor(resources.getColor(R.color.colorGood))
                nextQuestion()
            }
        }
    }

    private fun nextQuestion() {
        timer.cancel()
        Handler(Looper.getMainLooper()).postDelayed({
            if (curQuestionExtra) {
                val intent = Intent(this, FinalActivity::class.java)
                intent.putExtra("Score", 0)
                startActivity(intent)
            }
            if (curDifficultyIndex < diff.size - 1) {  // В этом if'e мы идем пока у нас есть вопросы (цифра это (кол-во вопросов - 1)), если вопросы закончились,
                // переключаемся на конечный экран с баллами
                curDifficultyIndex += 1
                indicesOfAnswer = arrayOf(-1, -1, -1, -1)
                setQuestion(diff[curDifficultyIndex])
            } else {
                val intent = Intent(this, FinalActivity::class.java)
                intent.putExtra("Score", scoreCnt)
                startActivity(intent)
            }
        }, 3000)
    }

    private fun helpFinished() {
        showMessage("Все доступные подсказки использованы")
        for (btn in buttonsOfAnswer) btn?.isClickable = true
    }

    private fun helpBlocked() {
        showMessage("На ДОП вопросе подсказки недоступны")
        for (btn in buttonsOfAnswer) btn?.isClickable = true
    }

    private fun helpFiftyFifty() {
        val success: Int = Random().nextInt(2)
        // подсказка не сработала
        if (success == 0) {
            showMessage("Подсказка не сработала")
            for (btn in buttonsOfAnswer) btn?.isClickable = true
        } // подсказка сработала
        else {
            // Выбрать рандомный неправильный ответ, который останется
            var index: Int = Random().nextInt(3)
            while (index == rightBtnIndex) {
                index = Random().nextInt(3)
            }
            // 2 других неправильных ответа убрать
            for (ind in buttonsOfAnswer.indices) {
                if (ind != index && ind != rightBtnIndex) {
                    buttonsOfAnswer[ind]?.visibility = GONE
                }
            }
            buttonsOfAnswer[index]?.isClickable = true
            buttonsOfAnswer[rightBtnIndex]?.isClickable = true
        }

    }

    private fun helpCheating() {
        val randomDiff: Int = diff[Random().nextInt(diff.size)]
        val randomQuestion: Question? =
            QuestionRepository.questions[randomDiff]?.get(Random().nextInt(3))
        val rightAnswer: String? = randomQuestion?.answers?.get(randomQuestion.indexOfRightAnswer)
        if (rightAnswer != null) {
            showMessage(rightAnswer)
        }
        for (btn in buttonsOfAnswer) btn?.isClickable = true
    }

    private fun helpClassmate() {
        val randomAnswerInd: Int = Random().nextInt(4)
        buttonsOfAnswer[rightBtnIndex]?.setBackgroundColor(resources.getColor(R.color.colorGood))
        if (randomAnswerInd == rightBtnIndex) {
            scoreCnt += curDifficulty // добавляем к текущим баллам сложность
            tvScore?.text = scoreCnt.toString() // показываем баллы
        } else {
            buttonsOfAnswer[randomAnswerInd]?.setBackgroundColor(resources.getColor(R.color.colorBad))
        }
        nextQuestion()
    }

    private fun helpArsik() {
        buttonsOfAnswer[rightBtnIndex]?.setBackgroundColor(resources.getColor(R.color.colorGood))
        scoreCnt += curDifficulty // добавляем к текущим баллам сложность
        tvScore?.text = scoreCnt.toString() // показываем баллы
        nextQuestion()
    }

    private fun showMessage(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

    private fun findView() {
        btnAns1 = findViewById(R.id.btn_ans1)
        btnAns2 = findViewById(R.id.btn_ans2)
        btnAns3 = findViewById(R.id.btn_ans3)
        btnAns4 = findViewById(R.id.btn_ans4)

        tvQuestion = findViewById(R.id.tv_question)
        tvScore = findViewById(R.id.tv_score)
        tvType = findViewById(R.id.tv_type)

        progressBarHorizontal = findViewById(R.id.progressBarHorizontal)

        btnHelp1 = findViewById(R.id.btn_help1)
        btnHelp2 = findViewById(R.id.btn_help2)
        btnHelp3 = findViewById(R.id.btn_help3)
        btnHelp4 = findViewById(R.id.btn_help4)

        buttonsOfAnswer = listOf(btnAns1, btnAns2, btnAns3, btnAns4)
        buttonsOfAnswerId = listOf(R.id.btn_ans1, R.id.btn_ans2, R.id.btn_ans3, R.id.btn_ans4)

        buttonsOfHelp = listOf(btnHelp1, btnHelp2, btnHelp3, btnHelp4)
        buttonsOfHelpId = listOf(R.id.btn_help1, R.id.btn_help2, R.id.btn_help3, R.id.btn_help4)
    }

    private fun toggleFullScreen() {
        if (window.decorView.systemUiVisibility == SYSTEM_UI_FLAG_VISIBLE) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            window.decorView.systemUiVisibility =
                SYSTEM_UI_FLAG_HIDE_NAVIGATION or SYSTEM_UI_FLAG_IMMERSIVE_STICKY or SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    override fun onResume() {
        super.onResume()
        toggleFullScreen()
    }

    override fun onStop() {
        super.onStop()
        this.finish()
    }
    
    // Готовая функция для диалога, можно использовать,
    // потом ее вызвать в setClickListener() {showDialog()}

//    private fun showDialog() {
//        AlertDialog.Builder(this)
//            .setTitle("Title")
//            .setMessage("Message")
//            .setPositiveButton("использовать") { dialog, _ ->
//                dialog.dismiss()
//            }
//            .setNegativeButton("выйти") { dialog, _ ->
//                dialog.dismiss()
//            }
//            .show()
//    }

}