package com.turanapps.englishwordsapp.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.turanapps.englishwordsapp.Adapter.RecyclerAdapterListWords
import com.turanapps.englishwordsapp.Errors.ObserveErrors
import com.turanapps.englishwordsapp.Model.Word
import com.turanapps.englishwordsapp.ViewModel.ListWordsViewModel
import com.turanapps.englishwordsapp.databinding.FragmentListWordsBinding

class ListWordsFragment : Fragment() {

    private lateinit var binding: FragmentListWordsBinding

    private lateinit var viewModel: ListWordsViewModel

    private lateinit var forWhat: String

    private lateinit var wordsList: ArrayList<Word>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        forWhat = arguments?.getString("forWhat").toString()

        viewModel = ViewModelProvider(this).get(ListWordsViewModel::class.java)

        wordsList = viewModel.getDatasetFromRoomDB(forWhat)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListWordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObserveErrors.observe(viewLifecycleOwner, requireContext(), viewModel.readFromRoomDBErrorEntity)

        viewModel.isEmpty.observe(viewLifecycleOwner, Observer {
            if(it==true){
                binding.cardView.visibility = View.VISIBLE
                binding.recyclerViewWordsListFragment.visibility = View.GONE

                if(forWhat.equals("learnedWordsList")){
                    binding.learnedAllWordsTextView.visibility = View.GONE
                    binding.learnedNothingTextView.visibility = View.VISIBLE
                }
                else if(forWhat.equals("notLearnedWordsList")){
                    binding.learnedAllWordsTextView.visibility = View.VISIBLE
                    binding.learnedNothingTextView.visibility = View.GONE
                }
            }
            else{
                binding.cardView.visibility = View.GONE
                binding.recyclerViewWordsListFragment.visibility = View.VISIBLE

                val layoutManager = LinearLayoutManager(context)
                binding.recyclerViewWordsListFragment.layoutManager = layoutManager
                binding.recyclerViewWordsListFragment.adapter = RecyclerAdapterListWords(wordsList)
            }
        })

    }

}