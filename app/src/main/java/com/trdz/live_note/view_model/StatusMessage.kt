package com.trdz.live_note.view_model

import com.trdz.live_note.model.RequestResult

/** Ответы VM для фрагментов: */
sealed class StatusProcess {
	object Load : StatusProcess()
	data class Saving(val data: String) : StatusProcess()
	data class Success(val data: RequestResult) : StatusProcess()
	data class Error(val code: Int, val error: Throwable) : StatusProcess()
}