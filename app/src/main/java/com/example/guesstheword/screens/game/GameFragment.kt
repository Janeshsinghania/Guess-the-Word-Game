package com.example.guesstheword.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.guesstheword.R
import com.example.guesstheword.databinding.FragmentGameBinding
import java.util.regex.Pattern

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel  //connecting xml file with viemodel

//        binding.correctButton.setOnClickListener {
//            viewModel.onCorrect()
//        }
//
//        binding.skipButton.setOnClickListener {
//            viewModel.onSkip()
//        }

        binding.setLifecycleOwner(this)
//        viewModel.word.observe(viewLifecycleOwner) { newWord ->
//            binding.wordText.text = newWord
//        }

//        viewModel.score.observe(viewLifecycleOwner) { newScore ->
//            binding.scoreText.text = newScore.toString()
//        }

//        viewModel.currentTime.observe(viewLifecycleOwner) { newTime ->
//            binding.timerText.text = DateUtils.formatElapsedTime(newTime)   //format date
//        }

        viewModel.eventGameFinish.observe(viewLifecycleOwner) { isFinished ->
            if (isFinished) {
                val currentScore = viewModel.score.value ?: 0
                val action = GameFragmentDirections.actionGameToScore(currentScore)
                findNavController().navigate(action)
                viewModel.onGameFinishComplete() //to reset the game
            }
        }

        viewModel.eventBuzz.observe(viewLifecycleOwner){buzzType->
            if(buzzType!=GameViewModel.BuzzType.NO_BUZZ){
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        }

        return binding.root
    }

    private fun buzz(pattern:LongArray){
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                buzzer.vibrate(pattern,-1)
            }else{
                buzzer.vibrate(pattern,-1)
            }
        }
    }

}