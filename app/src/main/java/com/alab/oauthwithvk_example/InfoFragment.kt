package com.alab.oauthwithvk_example

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alab.oauthwithvk_example.databinding.InfoFragmentBinding

/**
 * Представляет фрагмент 'Инфо'.
 */
class InfoFragment : Fragment() {
    private val _viewModel: InfoViewModel by viewModels()
    private var _binding: InfoFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            lifecycleOwner = this@InfoFragment.viewLifecycleOwner
            vm = _viewModel
            tvFriends.movementMethod = ScrollingMovementMethod()
            logout.setOnClickListener {
                App.application.accountService.token = null
                App.application.accountService.userId = null
                findNavController().navigate(R.id.action_InfoFragment_to_AuthFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}