package com.trdz.live_note.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trdz.live_note.model.Repository
import com.trdz.live_note.model.RepositoryExecutor

class ViewModelFactories(
	private val dataLive: SingleLiveData<StatusProcess>,
	private val repository: Repository,
): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T: ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
		MainViewModel::class.java -> MainViewModel(dataLive,repository)
		else -> throw IllegalArgumentException("Unknown ViewModel class")
	} as T

}