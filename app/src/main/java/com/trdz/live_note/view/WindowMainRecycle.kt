package com.trdz.live_note.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.trdz.live_note.R
import com.trdz.live_note.model.DataNote
import java.util.*
import kotlin.collections.HashMap

class WindowMainRecycle internal constructor(
	private val context: Context,
	private var titleList: List<String> = emptyList(),
	private var dataList: HashMap<String, List<DataNote>> = HashMap<String, List<DataNote>>(),
): BaseExpandableListAdapter() {

	fun setData( data:  HashMap<String, List<DataNote>>) {
		this.titleList = data.keys.toList()
		Collections.sort(titleList)
		this.dataList = data
		notifyDataSetChanged()
	}

	override fun getChildrenCount(listPosition: Int) = this.dataList[this.titleList[listPosition]]!!.size
	override fun getChild(listPosition: Int, expandedListPosition: Int)= this.dataList[this.titleList[listPosition]]!![expandedListPosition]
	override fun getChildId(listPosition: Int, expandedListPosition: Int)= expandedListPosition.toLong()

	override fun getGroupCount() = this.titleList.size
	override fun getGroup(listPosition: Int) = this.titleList[listPosition]
	override fun getGroupId(listPosition: Int) = listPosition.toLong()

	override fun getChildView(
		listPosition: Int,
		expandedListPosition: Int,
		isLastChild: Boolean,
		convertView: View?,
		parent: ViewGroup,
	): View {
		var view = convertView

		if (view == null) {
			val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
			view = layoutInflater.inflate(R.layout.element_sub_list_item, null)
		}

		val expandedListElement = getChild(listPosition, expandedListPosition)
		view!!.findViewById<TextView>(R.id.listView).text = expandedListElement.time
		view.findViewById<TextView>(R.id.numFirst).text = expandedListElement.first.toString()
		view.findViewById<TextView>(R.id.numSecond).text = expandedListElement.second.toString()
		return view
	}

	override fun getGroupView(
		listPosition: Int,
		isExpanded: Boolean,
		convertView: View?,
		parent: ViewGroup,
	): View {
		var view = convertView
		
		if (view == null) {
			val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
			view = layoutInflater.inflate(R.layout.element_list_item, null)
		}

		val listTitle = getGroup(listPosition)
		view!!.findViewById<TextView>(R.id.listView).text = listTitle
		return view
	}

	override fun hasStableIds(): Boolean {
		return false
	}

	override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
		return true
	}
}