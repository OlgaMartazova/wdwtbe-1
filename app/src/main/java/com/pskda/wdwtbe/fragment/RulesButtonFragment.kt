package com.pskda.wdwtbe.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pskda.wdwtbe.R

class RulesButtonFragment : Fragment(R.layout.fragment_rules_button) {

    private var btnRules: Button? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findView(view)

        btnRules?.setOnClickListener {
            findNavController().navigate(R.id.action_rulesButtonFragment_to_rulesTextViewFragment)
        }
    }

    private fun findView(view: View) {
        btnRules = view.findViewById(R.id.btn_rules)
    }
}