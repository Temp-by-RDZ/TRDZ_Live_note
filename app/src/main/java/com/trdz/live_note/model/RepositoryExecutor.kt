package com.trdz.live_note.model

import android.util.Log

class RepositoryExecutor(private val externalSource: ExternalSource): Repository {
	/** Отправка запроса NASA EpicPicture,MarsRoverPicture,PictureOfTheDay */
	override suspend fun connection(): RequestResult {
		Log.d("@@@", "Rep - start connection")
		return externalSource.load()
	}

	override fun save(data: HashMap<String, List<DataNote>>) {
		externalSource.save(data)
	}

}
