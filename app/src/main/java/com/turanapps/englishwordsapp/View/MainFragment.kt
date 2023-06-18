package com.turanapps.englishwordsapp.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.turanapps.englishwordsapp.ViewModel.MainViewModel
import com.turanapps.englishwordsapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonClicked()

    }

    private fun buttonClicked(){
        binding.buttonLearnedWordsList.setOnClickListener {
            toLists("learnedWordsList")
        }

        binding.buttonNotLearnedWordsList.setOnClickListener{
            toLists("notLearnedWordsList")
        }

        binding.buttonLearnedWord.setOnClickListener {
            toWord("learnedWord")
        }

        binding.buttonNotLearnedWord.setOnClickListener {
            toWord("notLearnedWord")
        }

    }

    private fun toLists(argumentValue: String){
        val action = MainFragmentDirections.actionMainFragmentToListWordsFragment(argumentValue)
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun toWord(argumentValue: String){
        val action = MainFragmentDirections.actionMainFragmentToWordFragment(argumentValue)
        Navigation.findNavController(requireView()).navigate(action)
    }

}