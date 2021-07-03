package com.pskda.wdwtbe.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pskda.wdwtbe.R
import com.pskda.wdwtbe.model.Question
import com.pskda.wdwtbe.model.QuestionRepository

import java.util.*

class PlayActivity : AppCompatActivity() {

    private var btnAns1 : Button? = null
    private var btnAns2 : Button? = null
    private var btnAns3 : Button? = null
    private var btnAns4 : Button? = null
    private var tvQuestion : TextView? = null


    private var ans1Id : Int? = null
    private var ans2Id : Int? = null
    private var ans3Id : Int? = null
    private var ans4Id : Int? = null


    private val difficulty = arrayOf(1, 2, 3, 5, 7, 9, 10, 13)

    val questions = hashMapOf(
        1 to listOf(
            Question("name1", true, arrayOf("ans1", "ans2", "ans3", "ans4"), 0),
            Question("name2", true, arrayOf("ans1", "ans2", "ans3", "ans4"), 1),
            Question("name3", true, arrayOf("ans1", "ans2", "ans3", "ans4"), 1)
        ),
        2 to listOf(
        )
    )

    private var buttonsOfAnswer = listOf(btnAns1, btnAns2, btnAns3, btnAns4)
    private var indicesOfAnswer = emptyArray<Int>()

    private var counter : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findView()

        for (item in difficulty) {
            setQuestion(item)
            //подождать, таймер показывается

            // как-то понять, на какую именно кнопку нажали, ее индекс/id
            //click(нажатая кнопка)
            //нажатие раньше чем конец таймера
            //конец таймера - skip question
        }

    }

    private fun setQuestion(item : Int) {
        //кнопки становятся кликабельными
        //TODO QuestionRepository
        var question : Question? = questions.get(item)?.get(Random().nextInt(3))
        val type : Boolean? = question?.isExtra
        if (item == 1) {
            while (type == true) {
                question = questions.get(item)?.get(Random().nextInt(3))
            }
        }
        tvQuestion?.setText(question?.name)
        setContentView(tvQuestion)

        for (ind in buttonsOfAnswer.indices) {
            var index : Int = Random().nextInt(4)
            while (indicesOfAnswer.contains(index)) {
                index = Random().nextInt(4)
            }
            buttonsOfAnswer[ind]?.setText(question?.answers?.get(index))
            setContentView(buttonsOfAnswer[ind])
            indicesOfAnswer[ind] = index
        }
        val rightAns : Int? = question?.indexOfRightAnswer
        //TODO id of button
    }

    private fun click(btnAnswer : Button) {
        btnAnswer.setOnClickListener {
            //все кнопки не активные

        }
    }
    private fun findView() {
        btnAns1 = findViewById(R.id.btn_ans1)
        btnAns2 = findViewById(R.id.btn_ans2)
        btnAns3 = findViewById(R.id.btn_ans3)
        btnAns4 = findViewById(R.id.btn_ans4)
        tvQuestion = findViewById(R.id.tv_question)

    }

//    override fun onClick(view: View?) {
//        when(view?.id){
//            R.id.button1->{
//                // do some work here
//            }
//        }
//    }
}