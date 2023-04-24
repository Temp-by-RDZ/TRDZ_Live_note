package com.trdz.live_note.utility

import android.app.Activity
import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.trdz.live_note.R

/** Перечень всех вспомогательных функций */

//region SnackBar

fun View.showSnackBar(message: Int, length: Int = Snackbar.LENGTH_LONG) {
	showSnackBar(resources.getString(message), length)
}

fun View.showSnackBar(message: String, length: Int = Snackbar.LENGTH_LONG) {
	Snackbar.make(this, message, length).show()
}

inline fun View.showSnackBar(message: Int, length: Int = Snackbar.LENGTH_LONG, action: Snackbar.() -> Unit) {
	showSnackBar(resources.getString(message), length, action)
}

inline fun View.showSnackBar(message: String, length: Int = Snackbar.LENGTH_LONG, action: Snackbar.() -> Unit) {
	Snackbar.make(this, message, length).apply {
		action()
		show()
	}
}

fun Snackbar.action(action: Int, color: Int? = null, listener: (View) -> Unit) {
	action(view.resources.getString(action), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
	setAction(action, listener)
	color?.let { setActionTextColor(color) }
}
//endregion

//region Keyboard

fun Fragment.hideKeyboard() {
	view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
	hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
	val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
	inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
/* Showers:
	1)target.isIconified = false
	2)target.requestFocus()
	val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
	3)imm.showSoftInput(target, InputMethodManager.SHOW_IMPLICIT)
	4)imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
 */

//endregion

//region Pictures !!!REQUIRED COIL!!!
fun ImageView.loadSvg(url: String) {
	val imageLoader = ImageLoader.Builder(this.context)
		.componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
		.build()
	val request = ImageRequest.Builder(this.context)
		.crossfade(true)
		.crossfade(500)
		.data(url)
		.target(this)
		.build()
	imageLoader.enqueue(request)
}
//endregion

//region Toasts
private var toast: Toast? = null

fun Any.stopToast() {
	toast?.cancel()
}

fun Any.showToast(context: Context, text: String?, length: Int = Toast.LENGTH_SHORT) {
	toast?.cancel()
	toast = Toast.makeText(context, text, length)
	toast?.show()
}
//endregion

//region Animation
fun FragmentTransaction.animate(effect: String) {
	when (effect) {
		EFFECT_RISE -> setCustomAnimations(
			R.anim.slide_up,
			R.anim.slide_down,
		)
		EFFECT_FADE -> setCustomAnimations(
			R.anim.fade_in,
			R.anim.fade_out,
		)
		EFFECT_SLIDE -> setCustomAnimations(
			R.anim.slide_in,
			R.anim.slide_out,
		)
		EFFECT_DROP -> setCustomAnimations(
			R.anim.move_from_up,
			R.anim.fade_out,
		)
		EFFECT_MOVED -> setCustomAnimations(
			R.anim.move_from_up,
			R.anim.slide_down,
		)
		EFFECT_MOVEL -> setCustomAnimations(
			R.anim.slide_in,
			R.anim.move_to_left,
		)
		EFFECT_MOVER -> setCustomAnimations(
			R.anim.move_from_left,
			R.anim.slide_out,
		)
		EFFECT_MOVEU -> setCustomAnimations(
			R.anim.slide_up,
			R.anim.move_to_up,
		)
	}
}
//endregion

//region Bottom Navigation

fun BottomNavigationView.getSelectedItem(): Int {
	val menu: Menu = this.menu
	for (i in 0 until this.menu.size()) {
		val menuItem: MenuItem = menu.getItem(i)
		if (menuItem.isChecked) return menuItem.itemId
	}
	return 0
}
//endregion