package com.example.hideandseek.ui.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.hideandseek.databinding.FragmentMainBinding
import com.example.hideandseek.ui.viewmodel.MainFragmentViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import java.net.URL

class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val viewModel: MainFragmentViewModel by viewModels()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private var width : Int = 0
    private var height: Int = 0

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
        val ivMap = binding.ivMap

        // データベースからデータを持ってくる
        context?.let { viewModel.setAllUsersLive(it) }

        // データが更新されたら表示
        viewModel.allUsersLive.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                tvRelativeTime.text = it[it.size-1].relativeTime
            }

            // URLから画像を取得
            coroutineScope.launch {
                val originalDeferred = coroutineScope.async(Dispatchers.IO) {
                    getOriginalBitmap("https://maps.googleapis.com/maps/api/staticmap?center=${it[it.size-1].latitude},${it[it.size-1].longitude}&size=350x640&scale=1&zoom=18&key=AIzaSyA-cfLegBoleKaT2TbU5R4K1uRkzBR6vUQ&markers=color:red|${it[it.size-1].latitude},${it[it.size-1].longitude}")
                }
                val originalBitmap = originalDeferred.await()
                viewModel.setMap(originalBitmap)
            }
        }

        // Mapに画像をセット
        viewModel.map.observe(viewLifecycleOwner) {
            ivMap.setImageBitmap(it)

        }



        return root
    }

    fun getOriginalBitmap(url: String): Bitmap =
        URL(url).openStream().use {
            BitmapFactory.decodeStream(it)
        }
}
