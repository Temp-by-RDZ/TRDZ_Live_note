package com.trdz.live_note.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.trdz.live_note.model.ExternalSource
import com.trdz.live_note.model.Repository
import com.trdz.live_note.model.RepositoryExecutor
import com.trdz.live_note.model.ServerFirebase
import com.trdz.live_note.view_model.SingleLiveData
import com.trdz.live_note.view_model.StatusProcess
import com.trdz.live_note.view_model.ViewModelFactories
import org.koin.dsl.module

val moduleViewModel = module {
	single<FirebaseFirestore> { Firebase.firestore}
	single<ExternalSource> { ServerFirebase( db = get() ) }
	single<Repository>() { RepositoryExecutor( externalSource = get() ) }
	factory<SingleLiveData<StatusProcess>>() { SingleLiveData() }
	single<ViewModelFactories>() {
		ViewModelFactories(
			dataLive = get(),
			repository = get(),
		)
	}
}


