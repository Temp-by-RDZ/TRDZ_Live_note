package com.trdz.live_note.model

/** Ответ от Запроса в NASA EpicPicture,MarsRoverPicture,PictureOfTheDay */
data class RequestResult(
	val code: Int, 						//Код ответа на запрос
	val data: HashMap<String, List<DataNote>>
)
