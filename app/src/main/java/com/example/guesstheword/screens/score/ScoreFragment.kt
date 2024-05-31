package com.example.guesstheword.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.guesstheword.R
import com.example.guesstheword.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentScoreBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_score, container, false
        )
        // Get args using by navArgs property delegate
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)

        binding.scoreViewModel = viewModel
        binding.setLifecycleOwner(this)

//        viewModel.score.observe(viewLifecycleOwner) { newScore ->
//            binding.scoreText.text = newScore.toString()
//        }

//        binding.playAgainButton.setOnClickListener { viewModel.onPlayAgain() }

        viewModel.eventPlayAgain.observe(viewLifecycleOwner) { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainOnComplete()
            }
        }

        return binding.root
    }

//    private fun onPlayAgain() {
//        findNavController().navigate(ScoreFragmentDirections.actionRestart())
//    }

}