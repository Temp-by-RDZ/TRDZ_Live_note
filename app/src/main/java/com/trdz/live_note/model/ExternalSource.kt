package com.trdz.live_note.model

/** Интерфейс для Источников Данных */
interface ExternalSource {
	suspend fun load(): RequestResult
	fun save(data: HashMap<String, List<DataNote>>)
}