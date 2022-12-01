package com.tuna.androidpresentationdemo

import android.app.AlertDialog
import android.app.Presentation
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Display
import android.widget.Button
import android.widget.TextView


class PresentationOne(outerContext: Context?, display: Display?) :
    Presentation(outerContext, display) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presentation_one)

        val tvPresentation = findViewById<TextView>(R.id.text)
        val btnOk = findViewById<Button>(R.id.click)
        val btnCancel = findViewById<Button>(R.id.dismiss)

        tvPresentation.text = "If the secondary screen supports touch, you can click to try the result"

        btnOk.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Dialog")
                .setMessage("Prsentation Click Test")
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    tvPresentation.text = "That's amazing"
                }.create().show()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}