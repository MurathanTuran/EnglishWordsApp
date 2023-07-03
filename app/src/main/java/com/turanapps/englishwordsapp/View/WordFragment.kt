package com.turanapps.englishwordsapp.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.turanapps.englishwordsapp.Errors.ObserveErrors
import com.turanapps.englishwordsapp.Model.Word
import com.turanapps.englishwordsapp.ViewModel.WordViewModel
import com.turanapps.englishwordsapp.databinding.FragmentWordBinding

class WordFragment : Fragment() {

    private lateinit var binding: FragmentWordBinding

    private lateinit var viewModel: WordViewModel

    private lateinit var forWhat: String

    private var isEnglishVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        forWhat = arguments?.getString("forWhat").toString()

        viewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        viewModel.getDataFromRoomDB(forWhat)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObserveErrors.observe(viewLifecycleOwner, requireContext(), viewModel.readFromRoomDBErrorEntity, viewModel.updateWordErrorEntity)

        viewModel.word.observe(viewLifecycleOwner, Observer {
            if(it==null){
                binding.learnedAllWordsTextView.visibility = View.GONE
                binding.learnedNothingTextView.visibility = View.GONE
                binding.englishWordTextView.visibility = View.GONE
                binding.turkishWordTextView.visibility = View.GONE
                binding.cancelButton.visibility = View.GONE
                binding.doneButton.visibility = View.GONE
                binding.refreshButton.visibility = View.GONE

                if(forWhat.equals("learnedWord")){
                    binding.infoCardView.visibility = View.VISIBLE
                    binding.learnedNothingTextView.visibility = View.VISIBLE

                    binding.learnedAllWordsTextView.visibility = View.GONE
                    binding.cardView.visibility = View.GONE
                }
                else if(forWhat.equals("notLearnedWord")){
                    binding.infoCardView.visibility = View.VISIBLE
                    binding.learnedAllWordsTextView.visibility = View.VISIBLE

                    binding.learnedNothingTextView.visibility = View.GONE
                    binding.cardView.visibility = View.GONE
                }
            }
            else{

                binding.englishWordTextView.text = it.english
                binding.turkishWordTextView.text = it.turkish

                if(!it.learned){
                    binding.cancelButton.visibility = View.GONE
                    binding.doneButton.visibility = View.VISIBLE
                    binding.refreshButton.visibility = View.VISIBLE
                    doneButtonClicked(it)
                    refreshButtonClicked()
                }
                else if(it.learned){
                    binding.doneButton.visibility = View.GONE
                    binding.cancelButton.visibility = View.VISIBLE
                    binding.refreshButton.visibility = View.VISIBLE
                    cancelButtonClicked(it)
                    refreshButtonClicked()
                }

                binding.cardView.setOnClickListener {
                    binding.cardView.animate().rotationYBy(180f).setDuration(300).withEndAction {
                        binding.cardView.rotationY = 0f
                    }.start()

                    if (isEnglishVisible) {
                        rotateToTurkishSide()
                        isEnglishVisible = false
                    } else {
                        rotateToEnglishSide()
                        isEnglishVisible = true
                    }

                }

            }
        })

    }

    private fun refreshButtonClicked(){
        binding.refreshButton.setOnClickListener {
            viewModel.getDataFromRoomDB(forWhat)
            rotateToDefaultSide()
        }
    }

    private fun doneButtonClicked(word: Word){
        binding.doneButton.setOnClickListener {
            word.learned = true
            viewModel.updateWord(word, forWhat)
            rotateToDefaultSide()
        }
    }

    private fun cancelButtonClicked(word: Word){
        binding.cancelButton.setOnClickListener {
            word.learned = false
            viewModel.updateWord(word, forWhat)
            rotateToDefaultSide()
        }
    }

    private fun rotateToEnglishSide(){
        binding.turkishWordTextView.visibility = View.GONE
        binding.turkishWordTextView.rotationY = 180f
        binding.englishWordTextView.visibility = View.VISIBLE
        binding.englishWordTextView.rotationY = 0f
    }

    private fun rotateToTurkishSide(){
        binding.englishWordTextView.visibility = View.GONE
        binding.englishWordTextView.rotationY = 180f
        binding.turkishWordTextView.visibility = View.VISIBLE
        binding.turkishWordTextView.rotationY = 0f
    }

    private fun rotateToDefaultSide() {
        if (!isEnglishVisible) {
            rotateToEnglishSide()
            isEnglishVisible = true
        }
    }

}