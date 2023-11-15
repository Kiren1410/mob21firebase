package com.kiren.mob21firebase.ui.addEditTodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kiren.mob21firebase.ui.base.BaseFragment
import com.kiren.mob21firebase.ui.addEditTodo.viewModel.BaseAddEditTodoViewModel
import com.kiren.mob21firebase2.databinding.FragmentBaseAddEditTodoBinding
import kotlinx.coroutines.launch


abstract class BaseAddEditTodoFragment : BaseFragment<FragmentBaseAddEditTodoBinding>() {

    abstract override val viewModel: BaseAddEditTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBaseAddEditTodoBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        binding.btnSubmit.setOnClickListener {
            val title = binding.tvTitle.text.toString()
            val desc = binding.tvDesc.text.toString()
            viewModel.submit(title, desc)
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.finish.collect{
                navController.popBackStack()
            }
        }
    }


}