package com.trdz.live_note

import android.app.Application
import com.trdz.live_note.di.moduleMain
import com.trdz.live_note.di.moduleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@MyApp)
			modules(listOf(moduleMain, moduleViewModel))
		}
	}
}