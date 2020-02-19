package com.phgarcia.msjc.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.phgarcia.msjc.R
import com.phgarcia.msjc.databinding.MainFragmentBinding
import com.phgarcia.msjc.extensions.runLayoutAnimation
import com.phgarcia.msjc.models.Lesson
import com.phgarcia.msjc.models.Module
import com.phgarcia.msjc.ui.main.adapters.LessonsAdapter
import com.phgarcia.msjc.ui.main.adapters.ModulesAdapter
import com.phgarcia.msjc.ui.main.listeners.IOnItemClickListener
import com.phgarcia.msjc.ui.main.listeners.BackdropAnimationController
import timber.log.Timber

class MainFragment : Fragment() {

    private lateinit var backdropAnimationController: BackdropAnimationController
    private lateinit var viewModel: MainViewModel

    private val modulesAdapter =
        ModulesAdapter(object: IOnItemClickListener<Module> {
            override fun onItemClicked(obj: Module, pos: Int) {
                viewModel.selectModule(obj)
                viewModel.storeLastSelectedPosition(pos)
                backdropAnimationController.hide()
            }
        })

    private val lessonsAdapter =
        LessonsAdapter(object: IOnItemClickListener<Lesson> {
            override fun onItemClicked(obj: Lesson, pos: Int) {
                Timber.d("Selected lesson #${obj.lessonNumber}")
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DataBindingUtil.inflate<MainFragmentBinding>(inflater, R.layout.main_fragment,
            container, false).apply {
            backdropAnimationController = BackdropAnimationController(activity!!, backdropForeground,
                openIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_menu),
                closeIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_close))

            (activity as AppCompatActivity).setSupportActionBar(appBar)
            appBar.setNavigationOnClickListener(backdropAnimationController.navIconClickListener)

            initModulesRecyclerView(this)
            initLessonsRecyclerView(this)
            initViewModel(this)
            return root
        }
    }

    private fun initViewModel(binding: MainFragmentBinding) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java).apply {
            moduleList.observe(viewLifecycleOwner, Observer {
                it?.let {
                    modulesAdapter.items = it
                    modulesAdapter.select(viewModel.retrieveLastSelectedPosition())
                }
            })
            lessonList.observe(viewLifecycleOwner, Observer {
                it?.let {
                    lessonsAdapter.items = it
                    binding.lessonList.runLayoutAnimation()
                }
            })
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initModulesRecyclerView(binding: MainFragmentBinding) =
        with(binding.modulesList) {
            clipToPadding = false
            clipChildren = false
            adapter = modulesAdapter
        }

    private fun initLessonsRecyclerView(binding: MainFragmentBinding) =
        with(binding.lessonList) {
            clipToPadding = false
            clipChildren = false
            adapter = lessonsAdapter
            setOnScrollChangeListener { _, _, _, _, _ ->
                // selected is set because it is an easy attribute and not used otherwise
                binding.backdropForegroundHeader.isSelected = canScrollVertically(-1)
            }
        }

}
