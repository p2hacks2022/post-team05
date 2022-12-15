package com.example.hideandseek.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.hideandseek.databinding.FragmentMainBinding
import com.example.hideandseek.ui.viewmodel.MainFragmentViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val viewModel: MainFragmentViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Viewの取得
        val tvRelativeTime: TextView = binding.tvRelativeTime

        context?.let { viewModel.fetchRelativeTime(it) }

        context?.let { viewModel.setAllUsersLive(it) }

        viewModel.allUsersLive.observe(viewLifecycleOwner) {
            tvRelativeTime.text = it.toString()
        }
//        lifecycleScope.launch {
//            repeatOnLifecycle(lifecycle.State.STARTED) {
//                viewModel.uiState.collect {
//                    tvRelativeTime.text = it.allUsers.toString()
//                }
//            }
//        }

//        viewModel.allUsers.observe(viewLifecycleOwner, Observer {
//            viewModel.fetchRelativeTime()
//            tvRelativeTime.text = it.toString()
//        })

        return root
    }
}

