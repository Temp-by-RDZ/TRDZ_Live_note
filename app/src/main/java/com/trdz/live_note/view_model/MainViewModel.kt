package com.trdz.live_note.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trdz.live_note.model.DataNote
import com.trdz.live_note.model.Repository
import com.trdz.live_note.model.RequestResult
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

/** Главная VM для сегмента Picture */
class MainViewModel(
	private val dataLive: SingleLiveData<StatusProcess>,
	private val repository: Repository,
): ViewModel(), ServerResponse {

	private val coroutineScope = CoroutineScope(
		Dispatchers.IO
				+ SupervisorJob()
				+ CoroutineExceptionHandler { _, throwable ->
			handleError(throwable)
		})

	private fun handleError(throwable: Throwable) {
		Log.w("@@@", "Prs - Coroutine dead $throwable")
	}

	private var jobs: Job? = null

	private var current: HashMap<String, List<DataNote>> = HashMap<String, List<DataNote>>()

	fun getData(): LiveData<StatusProcess> = dataLive

	/** Подготовка запроса*/
	fun initialize() {
		request()
	}

	fun addNew(first: Int, second: Int) {
		val list = if (current[getDate()] == null) mutableListOf()
		else current[getDate()]!!.toMutableList()
		list.add(DataNote(getTime(), first, second))
		current[getDate()] = list
		repository.save(current)
		success(RequestResult(200, current))
	}

	/** Работа с датой для запроса */
	private fun getDate(): String {
		val calendar = Calendar.getInstance()
		calendar.add(Calendar.DATE, 0)
		val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
		return dateFormat.format(calendar.time)
	}

	/** Работа с временем для запроса */
	private fun getTime(): String {
		val calendar = Calendar.getInstance()
		calendar.add(Calendar.DATE, 0)
		val dateFormat = SimpleDateFormat("kk:mm", Locale.getDefault())
		return dateFormat.format(calendar.time)
	}

	/** Выполнение запроса */
	private fun request() {
		Log.d("@@@", "Prs - Start loading")
		jobs?.cancel()
		jobs = coroutineScope.launch {
			val response = repository.connection()
			if (response.code in 200..299) {
				Log.d("@@@", "Prs - External load complete")
				success(response)
			}
			else {
				Log.w("@@@", "Prs - Failed internal load start external loading")
				fail(response.code, null)
			}
		}
	}

	/** Реакция MV на успех запроса */
	override fun success(data: RequestResult) {
		Log.d("@@@", "Mod - get success answer")
		current = data.data
		dataLive.postValue(StatusProcess.Success(data))
	}

	/** Реакция MV на ошибку запроса */
	override fun fail(code: Int, throwable: Throwable?) {
		Log.d("@@@", "Mod - request failed $code by $throwable")
		val message = throwable ?: Throwable("Unspecified Error")
		dataLive.postValue(StatusProcess.Error(code, message))
	}
}