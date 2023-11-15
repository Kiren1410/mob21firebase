package com.kiren.mob21firebase.ui.login

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

import com.kiren.mob21firebase.ui.base.BaseFragment
import com.kiren.mob21firebase2.R
import com.kiren.mob21firebase2.databinding.FragmentLoginBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by viewModels()
    private lateinit var signInClient: GoogleSignInClient

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("debugging", "Result Code: ${result.resultCode}")

        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val acc = task.result!!
                Log.d("debugging", acc.displayName.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("debbuging", e.message.toString())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        signInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.run {
            tvSignUp.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginToRegister()
                navController.navigate(action)
            }

            btnSignIn.setOnClickListener {
                val email = tvEmail.text.toString()
                val pass = tvPassword.text.toString()
                viewModel.login(email, pass)
            }

            btnSignInWithGoogle.setOnClickListener{
                googleSignInLauncher.launch(signInClient.signInIntent)
            }
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.success.collect {
                val action = LoginFragmentDirections.toHome()
                navController.navigate(action)
            }
        }
        // Creates a login fragment
        // Initialize the Google Sign-In launcher

        lifecycleScope.launch {
            viewModel.greet.collect {
                binding.tvGreet.text = it
            }
        }
    }
    // when the google sign in finishes, lambda function is executed and authenticates with firebase



}


















