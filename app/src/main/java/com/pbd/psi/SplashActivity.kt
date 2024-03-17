package com.pbd.psi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.pbd.psi.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    private lateinit var sunAnim : Animation
    private lateinit var cloud1Anim : Animation
    private lateinit var cloud2Anim : Animation
    private lateinit var titleAnim : Animation
    private lateinit var sun : ImageView
    private lateinit var cloud1 : ImageView
    private lateinit var cloud2 : ImageView
    private lateinit var title : TextView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sunAnim = AnimationUtils.loadAnimation(this,R.anim.sun_anim)
        cloud1Anim = AnimationUtils.loadAnimation(this,R.anim.cloud1)
        cloud2Anim = AnimationUtils.loadAnimation(this,R.anim.cloud2)
        titleAnim = AnimationUtils.loadAnimation(this,R.anim.title)

        sun = findViewById(R.id.sun)
        cloud1 = findViewById(R.id.cloud1)
        cloud2 = findViewById(R.id.cloud2)
        title = findViewById(R.id.title)

        cloud1.startAnimation(cloud2Anim)
        cloud2.startAnimation(cloud1Anim)

        Handler(Looper.getMainLooper()).postDelayed({
            sun.startAnimation(sunAnim)
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }, 1000)
        }, 1500)
    }
}