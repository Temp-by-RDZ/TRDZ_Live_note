package com.trdz.live_note.model

/** Интерфейс для основного репозитория */
interface Repository {
	suspend fun connection() : RequestResult
	fun save(data: HashMap<String, List<DataNote>>)
}