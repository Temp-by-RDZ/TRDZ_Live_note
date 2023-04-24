package com.trdz.live_note.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.trdz.live_note.R
import com.trdz.live_note.utility.KEY_OPTIONS
import com.trdz.live_note.utility.KEY_THEME
import com.trdz.live_note.utility.stopToast
import org.koin.android.ext.android.inject

class MainActivity: AppCompatActivity() {

	//region Injected
	private val navigation: Navigation by inject()

	//endregion

	//region Customization
	override fun onBackPressed() {
		val fragmentList = supportFragmentManager.fragments

		var handled = false
		for (f in fragmentList) {
			if (f is CustomOnBackPressed) {
				handled = f.onBackPressed()
				if (handled) {
					break
				}
			}
		}

		if (!handled) super.onBackPressed()
	}

	//endregion

	//region Base realization
	override fun onDestroy() {
		stopToast()
		super.onDestroy()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		themeSettings()
		setContentView(R.layout.activity_main)
		if (savedInstanceState == null) {
			Log.d("@@@", "Start program")
			navigation.add(supportFragmentManager, WindowStart(), false, R.id.container_fragment_primal)
		}
	}

	private fun themeSettings() {
		when (getSharedPreferences(KEY_OPTIONS, Context.MODE_PRIVATE).getInt(KEY_THEME, 0)) {
			0 -> setTheme(R.style.MyBaseTheme)
			1 -> setTheme(R.style.MyGoldTheme)
			2 -> setTheme(R.style.MyFiolTheme)
		}
	}

	//endregion

}