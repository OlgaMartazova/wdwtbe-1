package com.pskda.wdwtbe.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.pskda.wdwtbe.R

class RulesTextViewFragment : Fragment(R.layout.fragment_rules_textview) {

    private var imgBtnNext: ImageButton? = null
    private var imgBtnPrev: ImageButton? = null
    private var tvRules: TextView? = null

    private var rulesStrings: List<String> = emptyList()
    private var counter: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findView(view)

        rulesStrings = listOf(
            getString(R.string.rules_part1),
            getString(R.string.rules_part2),
            getString(R.string.rules_part3),
            getString(R.string.rules_part4)
        )

        tvRules?.text = rulesStrings[counter]
        counter++

        initListeners()
    }

    private fun initListeners() {
        imgBtnPrev?.setOnClickListener() {
            counter--
            if (counter >= 0 && counter < rulesStrings.size) {
                tvRules?.text = rulesStrings[counter]
            } else {
                counter++
            }
        }

        imgBtnNext?.setOnClickListener() {
            counter++
            if (counter < rulesStrings.size && counter >= 0) {
                tvRules?.text = rulesStrings[counter]
            } else {
                counter--
            }
        }
    }

    private fun findView(view: View) {
        imgBtnNext = view.findViewById(R.id.img_btn_next)
        imgBtnPrev = view.findViewById(R.id.img_btn_prev)
        tvRules = view.findViewById(R.id.tv_rules)
    }
}