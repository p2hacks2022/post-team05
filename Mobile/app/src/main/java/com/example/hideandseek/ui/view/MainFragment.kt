package com.example.hideandseek.ui.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ImageView
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
import kotlin.math.log

class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val viewModel: MainFragmentViewModel by viewModels()

    private val locationArray = Array(60) {
        Array(3) {
            arrayOfNulls<Double>(2)
        }
    }

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

        // デモ用のリスト作成
        for (i in 0..59) {
            locationArray[i][0][0] = 41.84202707025747 + i*0.00001
            locationArray[i][0][1] = 140.7673718711624 + i*0.00001
            locationArray[i][1][0] = 41.84222707025747 + i*0.00001
            locationArray[i][1][1] = 140.7673718711624 + i*0.00001
            locationArray[i][2][0] = 41.84192707025747 + i*0.00001
            locationArray[i][2][1] = 140.7674718711624 + i*0.00001
        }
        
        // Viewの取得
        // 時間のテキスト
        val tvRelativeTime:   TextView  = binding.tvRelativeTime
        val tvLimitTime:      TextView  = binding.tvLimitTime
        // Map
        val ivMap:            ImageView = binding.ivMap
        // 捕まったボタン
        val btCaptureOn:      ImageView = binding.btCaptureOn
        val btCaptureOff:     ImageView = binding.btCaptureOff
        // スキルボタン
        val btSkillOn:        ImageView = binding.btSkillOn
        val btSkillOff:       ImageView = binding.btSkillOff
        // 捕まったか確認するダイアログ
        val dialogCapture:    ImageView = binding.dialogCapture
        val ivDemonCapture:   ImageView = binding.dialogCaptureDemon
        val btCaptureYes:     ImageView = binding.btCaptureYes
        val btCaptureNo:      ImageView = binding.btCaptureNo
        // 捕まった後のダイアログ
        val dialogCaptured:   ImageView = binding.dialogCaptured
        val ivDemonCaptured:  ImageView = binding.dialogCapturedDemon
        val ivNormalCaptured: ImageView = binding.dialogCapturedNormal
        val metalRod:         ImageView = binding.metalRod
        val btCapturedClose:  ImageView = binding.capturedClose
        // 観戦中
        val ivWatching:       ImageView = binding.ivWatching
        // User normal
        val user1Normal:      ImageView = binding.user1Normal
        val user2Normal:      ImageView = binding.user2Normal
        val user3Normal:      ImageView = binding.user3Normal
        // User demon
        val user4Demon:       ImageView = binding.user4Demon
        // User captured
        val user1Captured:    ImageView = binding.user1Captured

        // データベースからデータを持ってくる
        context?.let { viewModel.setAllUsersLive(it) }

        // データが更新されたら表示
        viewModel.allUsersLive.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                viewModel.setLimitTime(it[0].relativeTime)
                tvRelativeTime.text = it[it.size-1].relativeTime
                // URLから画像を取得
                var url = "https://maps.googleapis.com/maps/api/staticmap?center=${it[it.size-1].latitude},${it[it.size-1].longitude}&size=310x640&scale=1&zoom=18&key=AIzaSyA-cfLegBoleKaT2TbU5R4K1uRkzBR6vUQ&markers=color:red|${it[it.size-1].latitude},${it[it.size-1].longitude}"

                for (i in 0..2) {
                    url += "&markers=color:red|${locationArray[it[it.size-1].relativeTime.substring(6).toInt()][i][0]},${locationArray[it[it.size-1].relativeTime.substring(6).toInt()][i][1]}"
                }

                // URLから画像を取得
                coroutineScope.launch {
                    val originalDeferred = coroutineScope.async(Dispatchers.IO) {
                        getOriginalBitmap(url)
                    }
                    val originalBitmap = originalDeferred.await()
                    viewModel.setMap(originalBitmap)
                }
            }
        }

        viewModel.limitTime.observe(viewLifecycleOwner) {
            tvLimitTime.text = it
        }

        // 捕まったボタンが押された時の処理
        btCaptureOn.setOnClickListener {
            // ボタンを押された状態にする
            btCaptureOff.visibility   = View.VISIBLE
            // ダイアログが出現
            dialogCapture.visibility  = View.VISIBLE
            ivDemonCapture.visibility = View.VISIBLE
            btCaptureYes.visibility   = View.VISIBLE
            btCaptureNo.visibility    = View.VISIBLE

        }

        btCaptureNo.setOnClickListener {
            // ボタンを押されていない状態にする
            btCaptureOff.visibility   = View.INVISIBLE
            // ダイアログが消える
            dialogCapture.visibility  = View.INVISIBLE
            ivDemonCapture.visibility = View.INVISIBLE
            btCaptureYes.visibility   = View.INVISIBLE
            btCaptureNo.visibility    = View.INVISIBLE
        }

        btCaptureYes.setOnClickListener {
            // ダイアログが消える
            dialogCapture.visibility  = View.INVISIBLE
            ivDemonCapture.visibility = View.INVISIBLE
            btCaptureYes.visibility   = View.INVISIBLE
            btCaptureNo.visibility    = View.INVISIBLE

            // 捕まったダイアログが出る
            dialogCaptured.visibility   = View.VISIBLE
            ivDemonCaptured.visibility  = View.VISIBLE
            ivNormalCaptured.visibility = View.VISIBLE
            metalRod.visibility         = View.VISIBLE
            btCapturedClose.visibility  = View.VISIBLE
        }

        btCapturedClose.setOnClickListener {
            // 捕まったダイアログが消える
            dialogCaptured.visibility   = View.INVISIBLE
            ivDemonCaptured.visibility  = View.INVISIBLE
            ivNormalCaptured.visibility = View.INVISIBLE
            metalRod.visibility         = View.INVISIBLE
            btCapturedClose.visibility  = View.INVISIBLE

            // 観戦モードになる
            ivWatching.visibility   = View.VISIBLE
            btCaptureOff.visibility = View.VISIBLE
            btCaptureOn.visibility  = View.INVISIBLE
            btSkillOff.visibility   = View.VISIBLE
            btSkillOn.visibility    = View.INVISIBLE

            // ステータスが変わる
            user1Normal.visibility   = View.INVISIBLE
            user1Captured.visibility = View.VISIBLE
        }

        // 特定の時刻の位置情報を表示
//        viewModel.location.observe(viewLifecycleOwner) {
//            Log.d("TEST", it.toString())
//            if (it.isNotEmpty()) {
//                var url = "https://maps.googleapis.com/maps/api/staticmap?center=${it[0].latitude},${it[0].longitude}&size=350x640&scale=1&zoom=18&key=AIzaSyA-cfLegBoleKaT2TbU5R4K1uRkzBR6vUQ&markers=color:red|${it[0].latitude},${it[0].longitude}"
//
//                if (it.size > 1) {
//                    for (i in 1 until it.size) {
//                        url += "&markers=color:red|${it[i].latitude},${it[i].longitude}"
//                    }
//                }
//
//                // URLから画像を取得
//                coroutineScope.launch {
//                    val originalDeferred = coroutineScope.async(Dispatchers.IO) {
//                        getOriginalBitmap(url)
//                    }
//                    val originalBitmap = originalDeferred.await()
//                    viewModel.setMap(originalBitmap)
//                }
//            }
//        }

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
