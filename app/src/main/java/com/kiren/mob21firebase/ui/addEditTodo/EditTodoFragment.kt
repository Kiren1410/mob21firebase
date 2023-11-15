package com.kiren.mob21firebase.ui.addEditTodo

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.kiren.mob21firebase.ui.addEditTodo.viewModel.EditTodoViewModel
import com.kiren.mob21firebase2.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditTodoFragment: BaseAddEditTodoFragment() {
    override val viewModel: EditTodoViewModel by viewModels()
    val args: EditTodoFragmentArgs by navArgs()

    override fun setupUIComponents() {
        super.setupUIComponents()
        binding.btnSubmit.text = getString(R.string.update)
        viewModel.getTodo(args.todoId)
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.todo.collect{
                binding.tvTitle.setText(it.title)
                binding.tvDesc.setText(it.desc)
            }
        }
    }

}