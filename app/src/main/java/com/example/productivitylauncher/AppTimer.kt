package com.example.productivitylauncher

import android.os.CountDownTimer


class AppTimer(runningTime:Long) {

    private val startTimeInMillis: Long = runningTime

    private var mCountDownTimer: CountDownTimer? = null

    var mTimerRunning = false
    var enabled = true

    private var mTimeLeftInMillis = startTimeInMillis

    fun start() {
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
            }

            override fun onFinish() {
                mTimerRunning = false
                enabled = false
            }
        }.start()
        mTimerRunning = true

    }

    fun pause() {
        mCountDownTimer!!.cancel()
        mTimerRunning = false
    }

    private fun reset() {
        mTimeLeftInMillis = startTimeInMillis
    }
}