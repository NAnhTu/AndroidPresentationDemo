package com.tuna.androidpresentationdemo

import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.media.MediaRouter
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Display
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var presentationOne: PresentationOne? = null
    private var presentationTwo: PresentationTwo? = null
    private var mediaRouter
            : MediaRouter? = null
    private var displayManager
            : DisplayManager? = null

    private fun checkPermission() {
            //SYSTEM_ALERT_WINDOW权限申请
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.data = Uri.parse("package:$packageName") //不加会显示所有可能的app
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    //            startActivityForResult(intent, 1)
                startActivityIntent.launch(intent)
            } else {
                //TODO do something you need
            }
    }



    var startActivityIntent: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
         ){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        val btnOne = findViewById<Button>(R.id.btn_type1)
        val btnTwo = findViewById<Button>(R.id.btn_type2)
        val btnThree = findViewById<Button>(R.id.btn_type3)
        val btnReset = findViewById<Button>(R.id.btn_back)

        btnOne.setOnClickListener {
            mediaRouter = getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter
            val localRouteInfo = mediaRouter!!.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_AUDIO)
            val display = localRouteInfo?.presentationDisplay
            if (display != null) {
                showPresentation(display)
            } else {
                Toast.makeText(this@MainActivity, "Does not support split screen", Toast.LENGTH_SHORT).show()
            }
        }

        btnTwo.setOnClickListener {
            displayManager = getSystemService(DISPLAY_SERVICE) as DisplayManager
            val arrayOfDisplay =
                displayManager!!.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION)
            if (arrayOfDisplay.isNotEmpty()) {
                showPresentation(arrayOfDisplay[0]) //Take the first split screen to use
            } else {
                Toast.makeText(this@MainActivity, "Does not support split screen", Toast.LENGTH_SHORT).show()
            }
        }

        btnThree.setOnClickListener {
            displayManager = getSystemService(DISPLAY_SERVICE) as DisplayManager
            val presentationDisplays: Array<Display> = displayManager!!.displays
            if (presentationDisplays.size > 1) {
                presentationTwo = PresentationTwo(applicationContext, presentationDisplays[1])
                presentationTwo!!.window?.setType(2037) //TYPE_SYSTEM_ALERT / TYPE_PHONE
                presentationTwo!!.show()
            } else {
                Toast.makeText(this@MainActivity, "Does not support split screen", Toast.LENGTH_SHORT).show()
            }
        }

        btnReset.setOnClickListener {
            if (presentationOne != null) {
                presentationOne!!.dismiss();
                presentationOne = null;
            }
            if (presentationTwo != null) {
                presentationTwo!!.dismiss();
                presentationTwo = null;
            }
        }
    }
    private fun showPresentation(display: Display) {
        presentationOne = null
        presentationOne = PresentationOne(applicationContext, display)
        presentationOne!!.window
            ?.setType(2037) //TYPE_SYSTEM_ALERT / TYPE_PHONE
        presentationOne!!.show()
    }
}