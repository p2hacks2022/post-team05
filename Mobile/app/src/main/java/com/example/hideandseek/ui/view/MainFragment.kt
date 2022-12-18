package com.example.hideandseek.ui.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
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

    // latitude, longitudeのリスト
    private val locationArray = Array(240) {
        Array(3) {
            arrayOfNulls<Double>(2)
        }
    }

    private val statusArray = Array(240) {
        Array(3) {
            arrayOfNulls<Int>(2)
        }
    }

    private val trapArray = Array(10) {
        arrayOfNulls<Double>(2)
    }

    private val skillTime = arrayOfNulls<String>(10)

    private var trapNumber = 0

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // デモ用のデータのセットアップ
        viewModel.setUpDemoList(locationArray, statusArray)
        
        // Viewの取得
        // 時間表示の場所
        val ivTime:           ImageView = binding.ivTime
        val tvNow:            TextView  = binding.tvNow
        val tvLimit:          TextView  = binding.tvLimit
        val tvRelativeTime:   TextView  = binding.tvRelativeTime
        val tvLimitTime:      TextView  = binding.tvLimitTime
        // Map
        val ivMap:            ImageView = binding.ivMap
        // 捕まったボタン
        val btCaptureOn:      ImageView = binding.btCaptureOn
        val btCaptureOff:     ImageView = binding.btCaptureOff
        // スキルボタン
        val btSkillOn:        ImageView   = binding.btSkillOn
        val btSkillOff:       ImageView   = binding.btSkillOff
        val progressSkill:    ProgressBar = binding.progressSkill
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
        // 罠にかかったとき
        val ivEye:            ImageView = binding.ivEye
        val tvTrap:           TextView  = binding.tvTrap
        val trapDialogText:   ImageView = binding.textOniTrap
        val trapDialogDemon:  ImageView = binding.ivOniTrap
        // クリアしたとき
        val dialogClear:      ImageView = binding.dialogClear
        val dialogClearUser1: ImageView = binding.dialogClearUser1
        val btClearClose:     ImageView = binding.clearClose
        // Result画面
        val resultBack:       ImageView = binding.resultBack
        val tvResult:         TextView  = binding.tvResult
        val tvWinner:         TextView  = binding.tvWinner
        val tvLoser:          TextView  = binding.tvLoser
        val winnerUser1:      ImageView = binding.winnerUser1
        val winnerUser2:      ImageView = binding.winnerUser2
        val loserUser3:       ImageView = binding.loserUser3
        val loserUser4:       ImageView = binding.loserUser4
        val btResultClose:    ImageView = binding.btResultClose

        // データベースからデータを持ってくる
        context?.let { viewModel.setAllUsersLive(it) }

        // データが更新されたら表示
        viewModel.allUsersLive.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                viewModel.setLimitTime(it[0].relativeTime)
                tvRelativeTime.text = it[it.size-1].relativeTime
                viewModel.limitTime.observe(viewLifecycleOwner) { limitTime ->
                    viewModel.compareTime(it[it.size-1].relativeTime, limitTime)
                }
                // URLから画像を取得
                var url = "https://maps.googleapis.com/maps/api/staticmap" +
                        "?center=${it[it.size-1].latitude},${it[it.size-1].longitude}" +
                        "&size=310x640&scale=1" +
                        "&zoom=18" +
                        "&key=AIzaSyA-cfLegBoleKaT2TbU5R4K1uRkzBR6vUQ" +
                        "&markers=icon:https://onl.tw/FkXBADq|${it[it.size-1].latitude},${it[it.size-1].longitude}"

                // 他のユーザーの位置情報
                for (i in 0..2) {
                    var iconUrl = "https://onl.tw/3n6JcpK"
                    if (statusArray[it[it.size-1].relativeTime.substring(6).toInt()*2][i][0] == 1) {
                        iconUrl = "https://onl.tw/nPGwaP9"
                    }
                    url += "&markers=icon:" + iconUrl + "|${locationArray[it[it.size-1].relativeTime.substring(6).toInt()*2][i][0]},${locationArray[it[it.size-1].relativeTime.substring(6).toInt()][i][1]}"
                }

                // trapの位置情報
                if (trapNumber > 0) {
                    for (i in 0 until trapNumber) {
                        url += "&markers=icon:https://onl.tw/CxjsiH1|${trapArray[i][0]},${trapArray[i][1]}"
                    }
                    skillTime[trapNumber-1]?.let { it1 ->
                        viewModel.compareSkillTime(it[it.size-1].relativeTime,
                            it1
                        )
                        progressSkill.progress = viewModel.howProgressSkillTime(it[it.size-1].relativeTime,
                            it1
                        )
                    }
                }

                // statusの変更
//                for (i in 0..2) {
//                    if (statusArray[it[it.size-1].relativeTime.substring(6).toInt()*2][i][0] == 1) {
//
//                    }
//                }

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

        viewModel.isOverLimitTime.observe(viewLifecycleOwner) {
            if (it) {
                // クリアダイアログを表示
                dialogClear.visibility      = View.VISIBLE
                dialogClearUser1.visibility = View.VISIBLE
                btClearClose.visibility     = View.VISIBLE
            }
        }

        // クリアダイアログの閉じるを押した時
        btClearClose.setOnClickListener {
            // クリアダイアログを非表示
            dialogClear.visibility      = View.INVISIBLE
            dialogClearUser1.visibility = View.INVISIBLE
            btClearClose.visibility     = View.INVISIBLE

            // Result画面の表示
            resultBack.visibility    = View.VISIBLE
            tvResult.visibility      = View.VISIBLE
            tvWinner.visibility      = View.VISIBLE
            tvLoser.visibility       = View.VISIBLE
            winnerUser1.visibility   = View.VISIBLE
            winnerUser2.visibility   = View.VISIBLE
            loserUser3.visibility    = View.VISIBLE
            loserUser4.visibility    = View.VISIBLE
            btResultClose.visibility = View.VISIBLE
            // Result以外のものを非表示
            ivTime.visibility         = View.INVISIBLE
            tvNow.visibility          = View.INVISIBLE
            tvLimit.visibility        = View.INVISIBLE
            tvRelativeTime.visibility = View.INVISIBLE
            tvLimitTime.visibility    = View.INVISIBLE
            user1Normal.visibility    = View.INVISIBLE
            user2Normal.visibility    = View.INVISIBLE
            user3Normal.visibility    = View.INVISIBLE
            user4Demon.visibility     = View.INVISIBLE
            btCaptureOn.visibility    = View.INVISIBLE
            btSkillOn.visibility      = View.INVISIBLE
        }

        // Result画面の閉じるを押した時の処理
        btResultClose.setOnClickListener {
            // Result画面の非表示
            resultBack.visibility    = View.INVISIBLE
            tvResult.visibility      = View.INVISIBLE
            tvWinner.visibility      = View.INVISIBLE
            tvLoser.visibility       = View.INVISIBLE
            winnerUser1.visibility   = View.INVISIBLE
            winnerUser2.visibility   = View.INVISIBLE
            loserUser3.visibility    = View.INVISIBLE
            loserUser4.visibility    = View.INVISIBLE
            btResultClose.visibility = View.INVISIBLE

            // Result以外のものを表示
            ivTime.visibility         = View.VISIBLE
            tvNow.visibility          = View.VISIBLE
            tvLimit.visibility        = View.VISIBLE
            tvRelativeTime.visibility = View.VISIBLE
            tvLimitTime.visibility    = View.VISIBLE
            user1Normal.visibility    = View.VISIBLE
            user2Normal.visibility    = View.VISIBLE
            user3Normal.visibility    = View.VISIBLE
            user4Demon.visibility     = View.VISIBLE
            btCaptureOn.visibility    = View.VISIBLE
            btSkillOn.visibility      = View.VISIBLE
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

        // skillボタンが押された時の処理
        btSkillOn.setOnClickListener {
            viewModel.allUsersLive.observe(viewLifecycleOwner) {
                trapArray[trapNumber][0] = it[it.size-1].latitude
                trapArray[trapNumber][1] = it[it.size-1].longitude
                skillTime[trapNumber]    = it[it.size-1].relativeTime
            }
            viewModel.setIsOverSkillTime(false)
            trapNumber += 1
        }

        viewModel.isOverSkillTime.observe(viewLifecycleOwner) {
            if (it) {
                btSkillOn.visibility     = View.VISIBLE
                btSkillOff.visibility    = View.INVISIBLE
                progressSkill.visibility = View.INVISIBLE
            } else {
                btSkillOn.visibility     = View.INVISIBLE
                btSkillOff.visibility    = View.VISIBLE
                progressSkill.visibility = View.VISIBLE

                progressSkill.max = 60
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
