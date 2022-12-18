package com.example.hideandseek.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hideandseek.R
import com.example.hideandseek.data.datasource.local.User
import com.example.hideandseek.data.datasource.local.UserRoomDatabase
import com.example.hideandseek.databinding.ActivityMainBinding
import com.example.hideandseek.ui.viewmodel.MainActivityViewModel
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.net.URL
import java.time.LocalTime


class MainActivity : AppCompatActivity() {

    // 直近の現在地情報を取得するためのクライアント
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // 位置情報の設定をするためのパラメータを格納するRequest
    private lateinit var locationRequest: LocationRequest

    // 現在地を更新するためのコールバック
    private lateinit var locationCallback: LocationCallback

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Viewの取得
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_main
            )
        )

        supportActionBar?.hide()

        // BottomNavigationのセットアップ
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // 位置情報の権限をリクエスト
        // 正確な位置情報、おおよその位置情報どちらを許可しますか？というダイアログが出る。
        // どちらか選んで許可すれば、選ばれたもの、許可されなければ権限はなし
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted
                } else -> {
                    // No location access granted
                }
            }
        }

        // リクエストを送る
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))

        // 位置情報を取得する
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // 位置情報の権限があるか確認する
        checkLocationPermission()

        // 直近の位置情報を取得
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null
                if (location != null) {
                    // 相対時間の初期化
                    viewModel.setUpRelativeTime(LocalTime.now())
                    // Roomデータベースの初期化
                    viewModel.deleteAll(applicationContext)
                    postCalculatedRelativeTime(location)
                }
            }

        // 位置情報リクエストの設定
        locationRequest = LocationRequest.Builder(1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        // 現在の位置情報を取得する
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        // 位置情報更新コールバックを定義する
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                for (location in locationResult.locations) {
                    postCalculatedRelativeTime(location)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // 位置情報の更新を停止する
        stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    // 相対時間を計算し、Roomにinsertする関数
    private fun postCalculatedRelativeTime(location: Location) {
        // ずれとを計算
        val gap = viewModel.calculateGap(location)
        // 相対時間を計算
        viewModel.calculateRelativeTime(gap)
        // Roomに相対時間と座標を送る
        viewModel.insert(location, applicationContext)
    }

    // 位置情報の権限があるかどうかを確認する関数
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
    }

    // 位置情報の更新をする関数
    private fun startLocationUpdates() {
        // 位置情報の権限があるか確認する
        checkLocationPermission()
        // 位置情報の更新
        fusedLocationClient.requestLocationUpdates(locationRequest,
        locationCallback,
            Looper.getMainLooper()
            )
    }

    // 位置情報の更新を止める関数
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}