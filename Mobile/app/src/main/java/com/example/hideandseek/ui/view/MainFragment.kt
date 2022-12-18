package com.example.hideandseek.ui.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.opengl.Visibility
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

        fun changeBtCaptureVisible(isOn: Boolean) {
            if (isOn) {
                btCaptureOn.visibility  = View.VISIBLE
                btCaptureOff.visibility = View.INVISIBLE
            } else {
                btCaptureOn.visibility  = View.INVISIBLE
                btCaptureOff.visibility = View.VISIBLE
            }
        }

        // スキルボタン
        val btSkillOn:        ImageView   = binding.btSkillOn
        val btSkillOff:       ImageView   = binding.btSkillOff
        val progressSkill:    ProgressBar = binding.progressSkill

        fun changeBtSkillVisible(isOn: Boolean) {
            if (isOn) {
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

        // 捕まったか確認するダイアログ
        val dialogCapture:    ImageView = binding.dialogCapture
        val ivDemonCapture:   ImageView = binding.dialogCaptureDemon
        val btCaptureYes:     ImageView = binding.btCaptureYes
        val btCaptureNo:      ImageView = binding.btCaptureNo

        fun changeCaptureDialogVisible(visibility: Int) {
            dialogCapture.visibility  = visibility
            ivDemonCapture.visibility = visibility
            btCaptureYes.visibility   = visibility
            btCaptureNo.visibility    = visibility
        }

        // 捕まった後のダイアログ
        val dialogCaptured:   ImageView = binding.dialogCaptured
        val ivDemonCaptured:  ImageView = binding.dialogCapturedDemon
        val ivNormalCaptured: ImageView = binding.dialogCapturedNormal
        val metalRod:         ImageView = binding.metalRod
        val btCapturedClose:  ImageView = binding.capturedClose

        fun changeAfterCaptureDialogVisible(visibility: Int) {
            dialogCaptured.visibility   = visibility
            ivDemonCaptured.visibility  = visibility
            ivNormalCaptured.visibility = visibility
            metalRod.visibility         = visibility
            btCapturedClose.visibility  = visibility
        }

        // 観戦中
        val ivWatching:       ImageView = binding.ivWatching

        // 捕まってステータスが捕まったになったら観戦モードになる
        fun changeStatusCaptured() {
            ivWatching.visibility   = View.VISIBLE
            changeBtCaptureVisible(false)
            btSkillOff.visibility   = View.VISIBLE
            btSkillOn.visibility    = View.INVISIBLE
        }

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

        fun changeClearDialogVisible(visibility: Int) {
            dialogClear.visibility      = visibility
            dialogClearUser1.visibility = visibility
            btClearClose.visibility     = visibility
        }

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

        fun changeResultDialogVisible(visibility: Int) {
            resultBack.visibility    = visibility
            tvResult.visibility      = visibility
            tvWinner.visibility      = visibility
            tvLoser.visibility       = visibility
            winnerUser1.visibility   = visibility
            winnerUser2.visibility   = visibility
            loserUser3.visibility    = visibility
            loserUser4.visibility    = visibility
            btResultClose.visibility = visibility
        }

        fun changeOtherResultDialog(visibility: Int) {
            ivTime.visibility         = visibility
            tvNow.visibility          = visibility
            tvLimit.visibility        = visibility
            tvRelativeTime.visibility = visibility
            tvLimitTime.visibility    = visibility
            user1Normal.visibility    = visibility
            user2Normal.visibility    = visibility
            user3Normal.visibility    = visibility
            user4Demon.visibility     = visibility
            user1Captured.visibility  = visibility
            btSkillOn.visibility      = visibility
            btSkillOff.visibility     = visibility
            progressSkill.visibility  = visibility

            dialogCapture.visibility  = visibility
            ivDemonCapture.visibility = visibility
            btCaptureYes.visibility   = visibility
            btCaptureNo.visibility    = visibility

            btCaptureOn.visibility      = visibility
            btCaptureOff.visibility     = visibility

            ivEye.visibility           = visibility
            tvTrap.visibility          = visibility
            trapDialogText.visibility  = visibility
            trapDialogDemon.visibility = visibility

            dialogClear.visibility      = visibility
            dialogClearUser1.visibility = visibility
            btClearClose.visibility     = visibility
        }


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

                // URLから画像を取得
                coroutineScope.launch {
                    val originalBitmap = viewModel.fetchMap(url)
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
                changeClearDialogVisible(View.VISIBLE)
            }
        }

        // クリアダイアログの閉じるを押した時
        btClearClose.setOnClickListener {
            // クリアダイアログを非表示
            changeClearDialogVisible(View.INVISIBLE)

            // Resultダイアログの表示
            changeResultDialogVisible(View.VISIBLE)

            // Result以外のものを非表示
            changeOtherResultDialog(View.INVISIBLE)
        }

        // Resultダイアログの閉じるを押した時の処理
        btResultClose.setOnClickListener {
            // Resultダイアログの非表示
            changeResultDialogVisible(View.INVISIBLE)

            // Result以外のものを表示
            changeOtherResultDialog(View.VISIBLE)
        }

        // 捕まったボタンが押された時の処理
        btCaptureOn.setOnClickListener {
            // ボタンを押された状態にする
            changeBtCaptureVisible(false)
            // 捕まったか確認するダイアログが出現
            changeCaptureDialogVisible(View.VISIBLE)

        }

        btCaptureNo.setOnClickListener {
            // ボタンを押されていない状態にする
            changeBtCaptureVisible(true)
            // 捕まったか確認するダイアログが消える
            changeCaptureDialogVisible(View.INVISIBLE)
        }

        btCaptureYes.setOnClickListener {
            // 捕まったか確認するダイアログが消える
            changeCaptureDialogVisible(View.INVISIBLE)

            // 捕まったダイアログが出る
            changeAfterCaptureDialogVisible(View.VISIBLE)
        }

        btCapturedClose.setOnClickListener {
            // 捕まったダイアログが消える
            changeAfterCaptureDialogVisible(View.INVISIBLE)

            // 観戦モードになる
            changeStatusCaptured()

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
            changeBtSkillVisible(it)
        }



        // Mapに画像をセット
        viewModel.map.observe(viewLifecycleOwner) {
            ivMap.setImageBitmap(it)
        }



        return root
    }
}
