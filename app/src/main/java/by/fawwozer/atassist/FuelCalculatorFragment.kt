package by.fawwozer.atassist

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt

class FuelCalculatorFragment: Fragment(R.layout.fragment_other_fuel_calculator) {
	private lateinit var _remain: EditText
	private lateinit var _uplift: EditText
	private lateinit var _total: EditText
	private lateinit var _adjust: EditText
	private lateinit var _depart: EditText
	private lateinit var _density: EditText
	private lateinit var _liters: TextView
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		
		_remain = view.findViewById(R.id.et_calculator_remain)
		_uplift = view.findViewById(R.id.et_calculator_uplift)
		_total = view.findViewById(R.id.et_calculator_total)
		_adjust = view.findViewById(R.id.et_calculator_adjust)
		_depart = view.findViewById(R.id.et_calculator_depart)
		_density = view.findViewById(R.id.et_calculator_density)
		_liters = view.findViewById(R.id.et_calculator_liters)
		
		_remain.doOnTextChanged {_, _, _, _ -> calculate(view)}
		_adjust.doOnTextChanged {_, _, _, _ -> calculate(view)}
		_depart.doOnTextChanged {_, _, _, _ -> calculate(view)}
		_density.doOnTextChanged {_, _, _, _ -> calculate(view)}
	}
	
	fun clear() {
		_liters.text = ""
		_remain.setText("")
		_uplift.setText("")
		_total.setText("")
		_depart.setText("")
		_density.setText("")
		_adjust.setText("0")
	}
	
	private fun calculate(view: View) {
		val remain: Int
		val uplift: Int
		val total: Int
		val adjust: Int
		val depart: Int
		val density: Double
		val liters: Double
		try {
			remain = this._remain.text.toString()
				.toInt()
			adjust = _adjust.text.toString()
				.toInt()
			depart = _depart.text.toString()
				.toInt()
			
			uplift = depart + adjust - remain
			total = depart + adjust
			
			this._uplift.text = uplift.toEditable()
			_total.text = total.toEditable()
		} catch (_: Throwable) {
			return
		}
		try {
			density = _density.text.toString()
				.toDouble()
			if (density > 0.0) {
				liters = ((depart + adjust - remain) / density)
				if ((((depart + adjust - remain) / density) * 10 % 10).toInt() == 5) {
					if (adjust > 0) {
						this._uplift.text = (uplift - 1).toEditable()
						_total.text = (total - 1).toEditable()
						_adjust.text = (adjust - 1).toEditable()
						_liters.text = ((depart + adjust - 1 - remain) / density).roundToInt()
							.toEditable()
					} else {
						this._uplift.text = (uplift + 1).toEditable()
						_total.text = (total + 1).toEditable()
						_adjust.text = (adjust + 1).toEditable()
						_liters.text = ((depart + adjust + 1 - remain) / density).roundToInt()
							.toEditable()
					}
				} else {
					this._uplift.text = uplift.toEditable()
					_total.text = total.toEditable()
					_liters.text = liters.roundToInt()
						.toEditable()
				}
			}
			//TODO("Implement round liters to value from preference")
		} catch (_: Throwable) {
			return
		}
	}
	
	private fun Int.toEditable(): Editable = Editable.Factory.getInstance()
		.newEditable(this.toString())
}