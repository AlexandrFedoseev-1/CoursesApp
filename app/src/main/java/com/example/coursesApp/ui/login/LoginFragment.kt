package com.example.coursesApp.ui.login

import android.R.attr.end
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coursesApp.R
import com.example.coursesApp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val viewModel by lazy { LoginViewModel() }
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTextWatchers()
        observeViewModel()

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
        }

        binding.vkBtn.setOnClickListener {
            openUrl(getString(R.string.url_vk))
        }

        binding.okBtn.setOnClickListener {
            openUrl(getString(R.string.url_ok))
        }
    }

    private fun openUrl(url: String) {
        try {
            val i = Intent(Intent.ACTION_VIEW, url.toUri())
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
        } catch (e: Exception) {
        }
    }

    private fun setupTextWatchers() {
        // Фильтр: разрешить только латиницу, цифры и спецсимволы email
        binding.emailEt.filters = arrayOf(InputFilter { source, start, end, _, _, _ ->
            for (i in start until end) {
                val c = source[i]
                if (c in 'а'..'я' || c in 'А'..'Я') {
                    return@InputFilter ""
                }
            }
            null
        })

        binding.emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.onEmailChanged(s.toString())
            }
        })

        binding.passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.onPasswordChanged(s.toString())
            }
        })
    }

    private fun observeViewModel() {
        viewModel.isFormValid.observe(viewLifecycleOwner) { isValid ->
            binding.loginBtn.isEnabled = isValid
        }
    }


    companion object {
        fun newInstance() = LoginFragment()
    }
}