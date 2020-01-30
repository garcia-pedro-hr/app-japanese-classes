package com.phgarcia.msjc.ui.modules

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.phgarcia.msjc.R
import com.phgarcia.msjc.databinding.ModulesFragmentBinding

class ModulesFragment : Fragment() {

    private val modulesAdapter = ModulesAdapter()

    private lateinit var viewModel: ModulesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<ModulesFragmentBinding>(inflater,
            R.layout.modules_fragment, container, false)
        binding.modulesList.adapter = modulesAdapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ModulesViewModel::class.java)
        viewModel.modulesLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { modulesAdapter.data = it }
        })
    }

}
