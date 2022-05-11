package by.fawwozer.atassist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import by.fawwozer.atassist.Global.Companion.STATE_DDA
import by.fawwozer.atassist.Global.Companion.STATE_DMI_A
import by.fawwozer.atassist.Global.Companion.STATE_DMI_B
import by.fawwozer.atassist.Global.Companion.STATE_DMI_C
import by.fawwozer.atassist.Global.Companion.STATE_DMI_D
import by.fawwozer.atassist.Global.Companion.STATE_DONE
import by.fawwozer.atassist.Global.Companion.STATE_INFO
import by.fawwozer.atassist.Global.Companion.STATE_INOP

class FleetAdapter(data: ArrayList<FleetDB.FleetData>, context: Context):
	ArrayAdapter<FleetDB.FleetData>(context, R.layout.adapter_fleet, data) {
	
	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		
		val newView: View
		val flData = getItem(position)
		
		val inflater = LayoutInflater.from(context)
		newView = inflater.inflate(R.layout.adapter_fleet, null)
		val root = convertView?.findViewById<RelativeLayout>(R.id.rl_fleet_root)
		val head = convertView?.findViewById<TextView>(R.id.tv_fleet_headline)
		val message = convertView?.findViewById<TextView>(R.id.tv_fleet_message)
		val dmiToGoDays = convertView?.findViewById<TextView>(R.id.tv_fleet_dmi_days)
		val date = convertView?.findViewById<TextView>(R.id.tv_fleet_date)
		val time = convertView?.findViewById<TextView>(R.id.tv_fleet_time)
		
		when (flData?.status) {
			STATE_DONE -> {}
			STATE_INFO -> {}
			STATE_DMI_A -> {}
			STATE_DMI_B -> {}
			STATE_DMI_C -> {}
			STATE_DMI_D -> {}
			STATE_DDA -> {}
			STATE_INOP -> {}
		}
		
		return newView
	}
}