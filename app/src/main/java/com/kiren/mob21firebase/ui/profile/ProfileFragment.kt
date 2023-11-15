package com.kiren.mob21firebase.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.kiren.mob21firebase.ui.base.BaseFragment
import com.kiren.mob21firebase.ui.tabContainer.TabContainerFragmentDirections
import com.kiren.mob21firebase2.R
import com.kiren.mob21firebase2.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val viewModel: ProfileViewModel by viewModels()
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickMedia = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                viewModel.updateProfilePic(uri)
            } else {
                Log.d("PhotoPicker", "No Media Detected")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {


        super.setupUIComponents()

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }

//        binding.icEditProfile.setOnClickListener{
//            pickMedia.launch(PickVisualMediaRequest(
//                ActivityResultContracts.PickVisualMedia.ImageOnly))
//        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.user.collect {
                binding.tvEmail.text = it.email
                binding.tvUsername.text = it.name

            }
        }

        lifecycleScope.launch {
            viewModel.profileUri.collect{
                Glide.with(requireContext())
                    .load(it)
                    .placeholder(R.drawable.img_1)
                    .into(binding.ivImage)
            }
        }


        lifecycleScope.launch {
            viewModel.finish.collect {
                val action = TabContainerFragmentDirections.toLogin()
                navController.navigate(action)
            }
        }
    }


}