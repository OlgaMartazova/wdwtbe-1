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
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.pskda.wdwtbe.R
import com.pskda.wdwtbe.databinding.ActivityPlayBinding
import com.pskda.wdwtbe.model.Question
import com.pskda.wdwtbe.model.QuestionRepository

import java.util.*

class PlayActivity : AppCompatActivity(), OnClickListener {

    private val diff = arrayOf(1, 2, 3, 5, 7, 9, 10, 13)
    private var curDifficulty: Int = 0
    private var curDifficultyIndex: Int = 0
    private var scoreCnt: Int = 0
    private var scoreDiff: Int = 0

    private var startMills: Long = 30000

    private var curQuestionExtra = false

    private var buttonsOfAnswer: List<Button?> = emptyList()
    private var buttonsOfAnswerId: List<Int> = emptyList()
    private var indicesOfAnswer = arrayOf(-1, -1, -1, -1)
    private var rightBtnIndex: Int = 0

    private var buttonsOfHelp: List<Button?> = emptyList()
    private var buttonsOfHelpId: List<Int> = emptyList()
    private var isHelpUsed = arrayOf(false, false, false, false)
    private var helpCount: Int = 0

    private lateinit var binding: ActivityPlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with (binding) {
            buttonsOfAnswer = listOf(btnAns1, btnAns2, btnAns3, btnAns4)
        buttonsOfAnswerId = listOf(R.id.btn_ans1, R.id.btn_ans2, R.id.btn_ans3, R.id.btn_ans4)
        buttonsOfHelp = listOf(btnHelp1, btnHelp2, btnHelp3, btnHelp4)
        buttonsOfHelpId = listOf(R.id.btn_help1, R.id.btn_help2, R.id.btn_help3, R.id.btn_help4)
        }

        binding.btnAns1.setOnClickListener(this)
        binding.btnAns2.setOnClickListener(this)
        binding.btnAns3.setOnClickListener(this)
        binding.btnAns4.setOnClickListener(this)

        binding.btnHelp1.setOnClickListener(this)
        binding.btnHelp2.setOnClickListener(this)
        binding.btnHelp3.setOnClickListener(this)
        binding.btnHelp4.setOnClickListener(this)

        binding.tvType.text = getString(R.string.extra_string)

        scoreDiff = intent?.extras?.getInt("Difficulty")!!
        scoreCnt += scoreDiff

        setQuestion(diff[curDifficultyIndex])
    }

    private fun setQuestion(item: Int) {

        for (helpInd in isHelpUsed.indices) {
            if (!isHelpUsed[helpInd]) {
                buttonsOfHelp[helpInd]?.isClickable = true
            }
        }
        curDifficulty = item
        for (btn in buttonsOfAnswer) {
            btn?.visibility = VISIBLE
            btn?.isClickable = true
            btn?.setBackgroundColor(0)
        } //кнопки становятся кликабельными, цвета обнуляются

        var question: Question? = QuestionRepository.questions.getValue(item)[Random().nextInt(3)]
        var type: Boolean? = question?.isExtra

        //не нужно, потому что в item=1 нет вопросов с пометкой ДОП
//        if (item == 1) {
//            while (type == true) {
//                question = QuestionRepository.questions.getValue(item)[Random().nextInt(3)]
//            }
//        }
        // Арсик использован - теперь все вопросы ДОП
        if (isHelpUsed[3]) {
            while (type == false) {
                question = QuestionRepository.questions.getValue(item)[Random().nextInt(3)]
                type = question.isExtra
            }
        }

        curQuestionExtra = type!!

        if (type) {
            binding.tvType.visibility = VISIBLE
            binding.tvType.setTextColor(resources.getColor(R.color.warning))
            startMills = 10000
            timer.start()
        } else {
            binding.tvType.visibility = GONE
            startMills = 30000
            timer.start()
        }

        for (ind in isHelpUsed.indices) {
            // Использованные подсказки не видны
            if (isHelpUsed[ind]) buttonsOfHelp[ind]?.visibility = GONE
            else buttonsOfHelp[ind]?.visibility = VISIBLE
        }

        binding.tvQuestion.setText(question?.name).toString()
        binding.tvScore.text = scoreCnt.toString()

        for (ind in buttonsOfAnswer.indices) {
            var index: Int = Random().nextInt(4)

            while (indicesOfAnswer.contains(index)) {
                index = Random().nextInt(4)
            }

            buttonsOfAnswer[ind]?.setText(question?.answers?.get(index)).toString()
            indicesOfAnswer[ind] = index
        }
        for (ind in indicesOfAnswer.indices) {
            if (indicesOfAnswer[ind] == question!!.indexOfRightAnswer) {
                rightBtnIndex = ind
            }
        }
        //это не верная строчка, потому что ответы даются кнопкам рандомно
        //rightBtnIndex = question!!.indexOfRightAnswer
        //buttonsOfAnswer[rightBtnIndex]?.text = "Это верная кнопка"
    }

    private var timer = object : CountDownTimer(startMills, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            binding.progressBarHorizontal.setProgress((millisUntilFinished).toInt(), true)
        }

        override fun onFinish() {
            binding.progressBarHorizontal.setProgress(0, true)
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
                    when {
                        helpCount == 3 -> {
                            helpFinished()
                        }
                        curDifficultyIndex == 7 -> {
                            helparsikLast()
                        }
                        else -> {
                            isHelpUsed[3] = true
                            helpCount++
                            helpArsik()
                        }
                    }
                }
            }
            // Логика для верного ответа
            buttonsOfAnswerId[rightBtnIndex] -> {
                Log.d("KNOPKA", "Нажата верная кнопка")
                for (helpInd in isHelpUsed.indices) {
                    if (!isHelpUsed[helpInd]) {
                        buttonsOfHelp[helpInd]?.isClickable = false
                    }
                }
                v.setBackgroundColor(resources.getColor(R.color.colorGood))
                scoreCnt += curDifficulty // добавляем к текущим баллам сложность
                Log.d("RIGHT ANS SCORE", scoreCnt.toString())
                binding.tvScore.text = scoreCnt.toString() // показываем баллы
                nextQuestion()
            }
            // Логика для неверного ответа
            else -> {
                Log.d("KNOPKA", "Нажата неверная кнопка")
                Log.d("WRONG ANS SCORE", scoreCnt.toString())
                for (helpInd in isHelpUsed.indices) {
                    if (!isHelpUsed[helpInd]) {
                        buttonsOfHelp[helpInd]?.isClickable = false
                    }
                }
                v!!.setBackgroundColor(resources.getColor(R.color.colorBad))
                buttonsOfAnswer[rightBtnIndex]!!.setBackgroundColor(resources.getColor(R.color.colorGood))
                if (curQuestionExtra) {
                    timer.cancel()
                    val intent = Intent(this, FinalActivity::class.java)
                    intent.putExtra("Score", scoreDiff)
                    startActivity(intent)
                }else nextQuestion()
            }
        }
    }

    private fun nextQuestion() {
        timer.cancel()
        Handler(Looper.getMainLooper()).postDelayed({
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
        val randomQuestion: Question =
            QuestionRepository.questions.getValue(randomDiff)[Random().nextInt(3)]
        val rightAnswer: String = randomQuestion.answers[randomQuestion.indexOfRightAnswer]
        showMessage(rightAnswer)
        for (btn in buttonsOfAnswer) btn?.isClickable = true
    }

    private fun helpClassmate() {
        val randomAnswerInd: Int = Random().nextInt(4)
        buttonsOfAnswer[rightBtnIndex]?.setBackgroundColor(resources.getColor(R.color.colorGood))
        if (randomAnswerInd == rightBtnIndex) {
            scoreCnt += curDifficulty // добавляем к текущим баллам сложность
            binding.tvScore.text = scoreCnt.toString() // показываем баллы
        } else {
            buttonsOfAnswer[randomAnswerInd]?.setBackgroundColor(resources.getColor(R.color.colorBad))
        }
        nextQuestion()
    }

    private fun helpArsik() {
        buttonsOfAnswer[rightBtnIndex]?.setBackgroundColor(resources.getColor(R.color.colorGood))
        scoreCnt += curDifficulty // добавляем к текущим баллам сложность
        binding.tvScore.text = scoreCnt.toString() // показываем баллы
        nextQuestion()
    }

    private fun helparsikLast() {
        showMessage("Это последний вопрос. Молодой человек, думайте сами.")
        for (btn in buttonsOfAnswer) btn?.isClickable = true
    }

    private fun showMessage(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (SYSTEM_UI_FLAG_IMMERSIVE
                or SYSTEM_UI_FLAG_LAYOUT_STABLE
                or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun onStop() {
        super.onStop()
        this.finish()
    }
}
