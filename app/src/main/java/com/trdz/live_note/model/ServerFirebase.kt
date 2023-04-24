package com.trdz.live_note.model

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.trdz.live_note.utility.*
import kotlinx.coroutines.tasks.await

/** Получение данных*/
class ServerFirebase(val db: FirebaseFirestore): ExternalSource {

	override suspend fun load(): RequestResult {
		val data = db.collection(PACKAGE_ID).get()
		data.await()
		return response(data.result)
	}

	private fun response(data: QuerySnapshot):RequestResult {
		val result: HashMap<String, List<DataNote>> = HashMap()
		data.documents.forEach { document ->
			val list = if (result[document.get(PACKAGE_DAY)]==null) mutableListOf()
			else result[document.get(PACKAGE_DAY)]!!.toMutableList()
			list.add(DataNote(document.get(PACKAGE_TIME) as String,(document.get(PACKAGE_FIRST) as Long).toInt(),(document.get(PACKAGE_SECOND) as Long).toInt()))
			result[document.get(PACKAGE_DAY) as String] = list
		}
		return RequestResult(200,result)
	}

	override fun save(data: HashMap<String, List<DataNote>>) {
		data.forEach { (key, value) ->
			value.forEach { element ->
				db.collection(PACKAGE_ID).add(hashMapOf(
					PACKAGE_DAY to key,
					PACKAGE_TIME to element.time,
					PACKAGE_FIRST to element.first,
					PACKAGE_SECOND to element.second,
				))
			}
		}
	}

}
