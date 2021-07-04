package com.pskda.wdwtbe.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

    private val diff = arrayOf(1, 2, 3, 5, 7, 9, 10, 13)
    private var curDifficulty: Int = 0
    private var curDifficultyIndex: Int = 0

    private var rightBtnIndex: Int = 0

    private var curQuestionExtra = false

    private var buttonsOfAnswer: List<Button?> = emptyList()
    private var buttonsOfAnswerId: List<Int> = emptyList()
    private var indicesOfAnswer = arrayOf(-1, -1, -1, -1)

    private var scoreCnt: Int = 0

    val questions = hashMapOf(
        1 to listOf(
            Question("name1", false, arrayOf("ans1", "ans2", "ans3", "ans4"), 0),
            Question("name2", false, arrayOf("ans1", "ans2", "ans3", "ans4"), 1),
            Question("name3", false, arrayOf("ans1", "ans2", "ans3", "ans4"), 1)
        ),
        2 to listOf(
            Question("name4", true, arrayOf("ans1", "ans2", "ans3", "ans4"), 2),
            Question("name5", true, arrayOf("ans1", "ans2", "ans3", "ans4"), 3),
            Question("name6", true, arrayOf("ans1", "ans2", "ans3", "ans4"), 3)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_play)
        findView()

        btnAns1!!.setOnClickListener(this)
        btnAns2!!.setOnClickListener(this)
        btnAns3!!.setOnClickListener(this)
        btnAns4!!.setOnClickListener(this)

        tvType?.text = getString(R.string.extra_string)

        setQuestion(diff[curDifficultyIndex])
    }

    private fun setQuestion(item: Int) {
        curDifficulty = item
        for (btn in buttonsOfAnswer){
            btn?.isClickable = true
            btn?.setBackgroundColor(0)
        } //кнопки становятся кликабельными, цвета обнуляются
        //TODO QuestionRepository

        var question: Question? = questions[item]?.get(Random().nextInt(3))
        val type: Boolean? = question?.isExtra

        if (item == 1) {
            while (type == true) {
                question = questions[item]?.get(Random().nextInt(3))
            }
        }

        curQuestionExtra = type!!

        if (type) tvType?.visibility = VISIBLE
        else tvType?.visibility = GONE

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
        val rightBtn: Button? = buttonsOfAnswer[question.indexOfRightAnswer]

        rightBtn?.text = "Это верная кнопка"
    }

    override fun onClick(v: View?) {
        Log.d("KNOPKA", "Нажата кнопка")
        for (btn in buttonsOfAnswer) btn?.isClickable = false
        //TODO время для просмотра ответа
        when (v?.id) {
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
                if(curQuestionExtra){
                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = Intent(this, FinalActivity::class.java)
                        intent.putExtra("Score",0)
                        startActivity(intent)
                    }, 3000)
                }else{
                    nextQuestion()
                }
            }
        }
    }

    private fun nextQuestion() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (curDifficultyIndex < 1) {  // В этом if'e мы идем пока у нас есть вопросы (цифра это (кол-во вопросов - 1)), если вопросы закончились,
                // переключаемся на конечный экран с баллами
                curDifficultyIndex += 1
                indicesOfAnswer = arrayOf(-1, -1, -1, -1)
                setQuestion(diff[curDifficultyIndex])
            } else {
                val intent = Intent(this, FinalActivity::class.java)
                intent.putExtra("Score",scoreCnt)
                startActivity(intent)
            }
        }, 3000)
    }

    private fun findView() {
        btnAns1 = findViewById(R.id.btn_ans1)
        btnAns2 = findViewById(R.id.btn_ans2)
        btnAns3 = findViewById(R.id.btn_ans3)
        btnAns4 = findViewById(R.id.btn_ans4)

        tvQuestion = findViewById(R.id.tv_question)
        tvScore = findViewById(R.id.tv_score)
        tvType = findViewById(R.id.tv_type)

        buttonsOfAnswer = listOf(btnAns1, btnAns2, btnAns3, btnAns4)
        buttonsOfAnswerId = listOf(R.id.btn_ans1, R.id.btn_ans2, R.id.btn_ans3, R.id.btn_ans4)
    }
}