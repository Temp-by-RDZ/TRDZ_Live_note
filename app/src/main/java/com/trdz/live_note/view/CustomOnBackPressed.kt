package com.trdz.live_note.view

/** Интерфейс для  фрагментов которым требуется нестандартная рреакция на кнопку "назад" */
interface CustomOnBackPressed {
	fun onBackPressed(): Boolean
}