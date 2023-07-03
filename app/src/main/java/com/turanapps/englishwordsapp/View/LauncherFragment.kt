package com.turanapps.englishwordsapp.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.turanapps.englishwordsapp.Errors.ObserveErrors
import com.turanapps.englishwordsapp.R
import com.turanapps.englishwordsapp.ViewModel.LauncherViewModel

class LauncherFragment : Fragment() {

    private lateinit var viewModel: LauncherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LauncherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.whatToDo()

        ObserveErrors.observe(viewLifecycleOwner, requireContext(), viewModel.readJsonErrorEntity, viewModel.writeToRoomDBErrorEntity)

        viewModel.toMainFragmentB.observe(viewLifecycleOwner, Observer {
            if(it==true){
                Navigation.findNavController(requireView()).navigate(R.id.action_launcherFragment_to_mainFragment)
                viewModel.toMainFragmentB.value = false
            }
        })

    }

}