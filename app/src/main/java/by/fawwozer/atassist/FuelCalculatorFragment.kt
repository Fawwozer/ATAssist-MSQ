package by.fawwozer.atassist

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt

class FuelCalculatorFragment: Fragment() {
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		
		view.findViewById<EditText>(R.id.et_calculator_remain)
			.doOnTextChanged {_, _, _, _ -> calculate(view)}
		view.findViewById<EditText>(R.id.et_calculator_adjust)
			.doOnTextChanged {_, _, _, _ -> calculate(view)}
		view.findViewById<EditText>(R.id.et_calculator_depart)
			.doOnTextChanged {_, _, _, _ -> calculate(view)}
		view.findViewById<EditText>(R.id.et_calculator_density)
			.doOnTextChanged {_, _, _, _ -> calculate(view)}
	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_other_fuel_calculator, null)
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
			remain = view.findViewById<EditText>(R.id.et_calculator_remain).text.toString()
				.toInt()
			adjust = view.findViewById<EditText>(R.id.et_calculator_adjust).text.toString()
				.toInt()
			depart = view.findViewById<EditText>(R.id.et_calculator_depart).text.toString()
				.toInt()
			
			uplift = depart + adjust - remain
			total = depart + adjust
			
			view.findViewById<EditText>(R.id.et_calculator_uplift).text = uplift.toEditable()
			view.findViewById<EditText>(R.id.et_calculator_total).text = total.toEditable()
		} catch (_: Throwable) {
			return
		}
		try {
			density = view.findViewById<EditText>(R.id.et_calculator_density).text.toString()
				.toDouble()
			
			if (density > 0.0) {
				liters = ((depart + adjust - remain) / density)
				if ((((depart + adjust - remain) / density) * 10 % 10).toInt() == 5) {
					if (adjust > 0) {
						view.findViewById<EditText>(R.id.et_calculator_uplift).text = (uplift - 1).toEditable()
						view.findViewById<EditText>(R.id.et_calculator_total).text = (total - 1).toEditable()
						view.findViewById<EditText>(R.id.et_calculator_adjust).text = (adjust - 1).toEditable()
						view.findViewById<EditText>(R.id.et_calculator_liters).text = ((depart + adjust - 1 - remain) / density).roundToInt()
							.toEditable()
					} else {
						view.findViewById<EditText>(R.id.et_calculator_uplift).text = (uplift + 1).toEditable()
						view.findViewById<EditText>(R.id.et_calculator_total).text = (total + 1).toEditable()
						view.findViewById<EditText>(R.id.et_calculator_adjust).text = (adjust + 1).toEditable()
						view.findViewById<EditText>(R.id.et_calculator_liters).text = ((depart + adjust + 1 - remain) / density).roundToInt()
							.toEditable()
					}
				} else {
					view.findViewById<EditText>(R.id.et_calculator_uplift).text = uplift.toEditable()
					view.findViewById<EditText>(R.id.et_calculator_total).text = total.toEditable()
					view.findViewById<EditText>(R.id.et_calculator_liters).text = liters.roundToInt()
						.toEditable()
				}
			}
		} catch (_: Throwable) {
			return
		}
	}
	
	private fun Int.toEditable(): Editable = Editable.Factory.getInstance()
		.newEditable(this.toString())
}