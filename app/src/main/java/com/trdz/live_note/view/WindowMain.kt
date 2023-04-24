package com.trdz.live_note.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.trdz.live_note.databinding.FragmentWindowMainBinding
import com.trdz.live_note.utility.KEY_FINSTANCE
import com.trdz.live_note.view_model.MainViewModel
import com.trdz.live_note.view_model.StatusProcess
import com.trdz.live_note.view_model.ViewModelFactories
import kotlinx.android.synthetic.main.fragment_window_main.*
import org.koin.android.ext.android.inject

class WindowMain: Fragment() {

	//region Injected
	private val navigation: Navigation by inject()
	private val factory: ViewModelFactories by inject()

	private val viewModel: MainViewModel by viewModels {
		factory
	}

	//endregion

	//region Elements
	private lateinit var adapter: WindowMainRecycle
	private var _binding: FragmentWindowMainBinding? = null
	private val binding get() = _binding!!
	var isFirst = false

	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			isFirst = it.getBoolean(KEY_FINSTANCE)
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowMainBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		prepInitialize()
		initialize()
	}

	private fun prepInitialize() {
		setViewModel()
	}

	private fun setViewModel() {
		val observer = Observer<StatusProcess> { renderData(it) }
		viewModel.getData().observe(viewLifecycleOwner, observer)
	}

	//endregion

	//region Main functional

	private fun initialize() {
		binds()
		viewModel.initialize()
		}

	private fun binds() = with(binding) {
		plus_imageview.setOnClickListener {
			edit.visibility = if (edit.visibility != View.VISIBLE ) View.VISIBLE else View.GONE
		}
		ok.setOnClickListener { viewModel.addNew(numFirst.text.toString().toInt(),numSecond.text.toString().toInt()) }
		adapter = WindowMainRecycle(requireContext())
		expendableList.setAdapter(adapter)
		}

	private fun renderData(material: StatusProcess) {
		when (material) {
			StatusProcess.Load -> {}
			is StatusProcess.Error -> {}
			is StatusProcess.Success -> {
				adapter.setData(material.data.data)
			}
			else -> {}
		}
	}

	//endregion

	companion object {
		@JvmStatic
		fun newInstance(first_instance: Boolean) =
			WindowMain().apply {
				arguments = Bundle().apply {
					putBoolean(KEY_FINSTANCE, first_instance)
				}
			}
	}

}