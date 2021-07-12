package com.pskda.wdwtbe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.pskda.wdwtbe.R
import com.pskda.wdwtbe.databinding.FragmentRulesTextviewBinding

class RulesTextViewFragment : Fragment() {

    private var rulesStrings: List<String> = emptyList()
    private var counter: Int = 0

    private var _binding: FragmentRulesTextviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRulesTextviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rulesStrings = listOf(
            getString(R.string.rules_part1),
            getString(R.string.rules_part2),
            getString(R.string.rules_part3),
            getString(R.string.rules_part4)
        )

        binding.imgBtnPrev.visibility = INVISIBLE
        binding.tvRules.text = rulesStrings[counter]
        initListeners()
    }

    private fun initListeners() {
        binding.imgBtnPrev.setOnClickListener {
            binding.imgBtnNext.visibility = VISIBLE
            if (counter > 0 && counter < rulesStrings.size) {
                counter--
                binding.tvRules.text = rulesStrings[counter]
            }
            if (counter == 0) {
                binding.imgBtnPrev.visibility = INVISIBLE
            }
        }

        binding.imgBtnNext.setOnClickListener {
            binding.imgBtnPrev.visibility = VISIBLE
            if (counter < rulesStrings.size - 1 && counter >= 0) {
                counter++
                binding.tvRules.text = rulesStrings[counter]
            }
            if (counter == rulesStrings.size - 1) {
                binding.imgBtnNext.visibility = INVISIBLE
            }
        }
    }
}