package com.trdz.live_note.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trdz.live_note.R
import com.trdz.live_note.view.Navigation
import org.koin.dsl.module

val moduleMain = module {
	single<Navigation>() { Navigation(R.id.container_fragment_base) }

}


