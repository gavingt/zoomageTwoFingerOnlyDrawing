package com.jsibbold.zoomage.example

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.jsibbold.zoomage.ZoomageView

class MainActivity : AppCompatActivity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private var demoView: ZoomageView? = null
    private var optionsView: View? = null
    private var optionsDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        demoView = findViewById(R.id.demoView)
        prepareOptions()
    }

    private fun prepareOptions() {
        optionsView = layoutInflater.inflate(R.layout.zoomage_options, null)
        setSwitch(R.id.zoomable, demoView!!.isZoomable)
        setSwitch(R.id.translatable, demoView!!.isTranslatable)
        setSwitch(R.id.animateOnReset, demoView!!.animateOnReset)
        setSwitch(R.id.autoCenter, demoView!!.autoCenter)
        setSwitch(R.id.restrictBounds, demoView!!.restrictBounds)
        optionsView!!.findViewById<View>(R.id.reset).setOnClickListener(this)
        optionsView!!.findViewById<View>(R.id.autoReset).setOnClickListener(this)
        optionsDialog = AlertDialog.Builder(this).setTitle("Zoomage Options")
            .setView(optionsView)
            .setPositiveButton("Close", null)
            .create()
    }

    private fun setSwitch(id: Int, state: Boolean) {
        val switchView = optionsView!!.findViewById<SwitchCompat>(id)
        switchView.setOnCheckedChangeListener(this)
        switchView.isChecked = state
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!optionsDialog!!.isShowing) {
            optionsDialog!!.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        val id = buttonView.id
        if (id == R.id.zoomable) {
            demoView!!.isZoomable = isChecked
        } else if (id == R.id.translatable) {
            demoView!!.isTranslatable = isChecked
        } else if (id == R.id.restrictBounds) {
            demoView!!.restrictBounds = isChecked
        } else if (id == R.id.animateOnReset) {
            demoView!!.animateOnReset = isChecked
        } else if (id == R.id.autoCenter) {
            demoView!!.autoCenter = isChecked
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.reset) {
            demoView!!.reset()
        } else {
            showResetOptions()
        }
    }

    private fun showResetOptions() {
        val options = arrayOf<CharSequence>("Under", "Over", "Always", "Never")
        val builder = AlertDialog.Builder(this)
        builder.setItems(options) { dialog, which -> demoView!!.autoResetMode = which }
        builder.create().show()
    }
}