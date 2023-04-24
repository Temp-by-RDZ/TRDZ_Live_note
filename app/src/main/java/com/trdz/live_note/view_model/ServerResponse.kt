package com.trdz.live_note.view_model

import com.trdz.live_note.model.RequestResult

/** Ожидаемые действия VM на обращения */
interface ServerResponse {
	fun success(data: RequestResult)
	fun fail(code: Int, throwable: Throwable?)
}