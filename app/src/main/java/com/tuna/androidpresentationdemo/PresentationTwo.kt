package com.tuna.androidpresentationdemo

import android.app.Presentation
import android.content.Context
import android.os.Bundle
import android.view.Display

class PresentationTwo(outerContext: Context?, display: Display?) :
    Presentation(outerContext, display) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presentation_two)
    }
}