package com.bevstudio.wolfbooksapp.view.activity

import android.animation.AnimatorInflater
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bevstudio.wolfbooksapp.R
import com.bevstudio.wolfbooksapp.databinding.ActivitySplashScreenBinding
import com.bevstudio.wolfbooksapp.helper.Constant

class SplashScreenActivity : AppCompatActivity() {
    var ivLogo: ImageView? = null
    private var binding: ActivitySplashScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(
            layoutInflater
        )
        val view: View = binding!!.root
        ivLogo = binding!!.ivLogo
        val animator = AnimatorInflater.loadAnimator(this, R.animator.translate)
        animator.setTarget(ivLogo)
        animator.start()

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, NavigationActivity::class.java)
            startActivity(intent)
            finish()
        }, Constant.SPLASH_TIME_OUT.toLong())

        setContentView(view)
    }
}
