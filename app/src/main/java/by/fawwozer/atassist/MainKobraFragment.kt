package by.fawwozer.atassist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_FLIGHT_DESTINATION
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_FLIGHT_NUMBER
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_PLANE
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_STAND
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_STATUS
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_TIME_EXPECT
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_TIME_PLANED
import by.fawwozer.atassist.Global.Companion.KOBRA_DB_TABLE_ARRIVAL
import by.fawwozer.atassist.Global.Companion.KOBRA_DB_TABLE_DEPARTURE
import by.fawwozer.atassist.Global.Companion.KOBRA_STATUS_ARRIVED_1
import by.fawwozer.atassist.Global.Companion.KOBRA_STATUS_ARRIVED_2
import by.fawwozer.atassist.Global.Companion.KOBRA_STATUS_DELAYED
import by.fawwozer.atassist.Global.Companion.KOBRA_STATUS_DEPARTED
import by.fawwozer.atassist.Global.Companion.KOBRA_STATUS_IN_FLIGHT
import by.fawwozer.atassist.Global.Companion.KOBRA_STATUS_IN_FUTURE
import by.fawwozer.atassist.Global.Companion.KOBRA_STATUS_IN_WORK_1
import by.fawwozer.atassist.Global.Companion.KOBRA_STATUS_IN_WORK_2
import by.fawwozer.atassist.Global.Companion.KOBRA_STATUS_IN_WORK_3
import by.fawwozer.atassist.Global.Companion.KOBRA_STATUS_IN_WORK_4
import by.fawwozer.atassist.Global.Companion.KOBRA_STATUS_IN_WORK_5
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.*

class MainKobraFragment: Fragment(R.layout.fragment_main_kobra) {
	
	private lateinit var kobraPagerAdapter: KobraPagerAdapter
	private lateinit var viewPager: ViewPager2
	private lateinit var tabLayout: TabLayout
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		Log.d("MY", "MainKobraFragment onViewCreated")
		kobraPagerAdapter = KobraPagerAdapter(this)
		tabLayout = view.findViewById(R.id.tab_kobra)
		viewPager = view.findViewById(R.id.pager_kobra)
		viewPager.adapter = kobraPagerAdapter
		TabLayoutMediator(tabLayout, viewPager) {tab, position ->
			when (position) {
				0 -> tab.text = "Arrival"
				1 -> tab.text = "Departure"
			}
		}.attach()
	}
}

class KobraPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
	override fun getItemCount(): Int = 2
	override fun createFragment(position: Int): Fragment {
		val fragment = KobraPagerFragment()
		fragment.arguments = Bundle().apply {
			putInt(ARG, position)
		}
		return fragment
	}
}

private const val ARG = "object"

class KobraPagerFragment: Fragment() {
	//обработка созданой страніцы
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		arguments?.takeIf {it.containsKey(ARG)}
			?.apply {
				
				val kobraData = mutableListOf<KobraAdapter.KobraData>()
				
				when (getInt(ARG)) {
					0 -> {
						kobraData.clear()
						val db = KobraDB().writableDatabase
						//TODO("Change past hours view by value from preference")
						val cur = db.query(KOBRA_DB_TABLE_ARRIVAL, null, KEY_KOBRA_TIME_PLANED + ">" + (Calendar.getInstance().timeInMillis - 12 * 60 * 60 * 1000).toString(), null, null, null, KEY_KOBRA_TIME_PLANED)
						if (cur.moveToFirst()) {
							do {
								kobraData.add(KobraAdapter.KobraData(cur.getString(cur.getColumnIndex(KEY_KOBRA_PLANE)), cur.getString(cur.getColumnIndex(KEY_KOBRA_FLIGHT_NUMBER)), cur.getString(cur.getColumnIndex(KEY_KOBRA_FLIGHT_DESTINATION)), cur.getString(cur.getColumnIndex(KEY_KOBRA_STAND)), cur.getString(cur.getColumnIndex(KEY_KOBRA_STATUS)), cur.getLong(cur.getColumnIndex(KEY_KOBRA_TIME_PLANED)), cur.getLong(cur.getColumnIndex(KEY_KOBRA_TIME_EXPECT))))
							} while (cur.moveToNext())
						}
						cur.close()
						db.close()
						
						val recyclerView: RecyclerView = view.findViewById(R.id.rv_kobra_root)
						recyclerView.layoutManager = LinearLayoutManager(activity)
						recyclerView.adapter = KobraAdapter(kobraData)
					}
					1 -> {
						kobraData.clear()
						val db = KobraDB().writableDatabase
						//TODO("Change past hours view by value from preference")
						val cur = db.query(KOBRA_DB_TABLE_DEPARTURE, null, KEY_KOBRA_TIME_PLANED + ">" + (Calendar.getInstance().timeInMillis - 12 * 60 * 60 * 1000).toString(), null, null, null, KEY_KOBRA_TIME_PLANED)
						if (cur.moveToFirst()) {
							do {
								kobraData.add(KobraAdapter.KobraData(cur.getString(cur.getColumnIndex(KEY_KOBRA_PLANE)), cur.getString(cur.getColumnIndex(KEY_KOBRA_FLIGHT_NUMBER)), cur.getString(cur.getColumnIndex(KEY_KOBRA_FLIGHT_DESTINATION)), cur.getString(cur.getColumnIndex(KEY_KOBRA_STAND)), cur.getString(cur.getColumnIndex(KEY_KOBRA_STATUS)), cur.getLong(cur.getColumnIndex(KEY_KOBRA_TIME_PLANED)), cur.getLong(cur.getColumnIndex(KEY_KOBRA_TIME_EXPECT))))
							} while (cur.moveToNext())
						}
						cur.close()
						db.close()
						
						val recyclerView: RecyclerView = view.findViewById(R.id.rv_kobra_root)
						recyclerView.layoutManager = LinearLayoutManager(activity)
						recyclerView.adapter = KobraAdapter(kobraData)
					}
				}
			}
	}
	
	//созданіе страніцы
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_kobra, container, false)
	}
}

class KobraAdapter(private val kobraData: List<KobraData>):
	RecyclerView.Adapter<KobraAdapter.KobraViewHolder>() {
	
	class KobraViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
		val rl_root: RelativeLayout = itemView.findViewById(R.id.rl_kobra_root)
		val tv_plane: TextView = itemView.findViewById(R.id.tv_kobra_plane)
		val tv_flight: TextView = itemView.findViewById(R.id.tv_kobra_flight)
		val tv_dectination: TextView = itemView.findViewById(R.id.tv_kobra_detination)
		val tv_time_planed: TextView = itemView.findViewById(R.id.tv_kobra_time_schedule)
		val tv_time_expect: TextView = itemView.findViewById(R.id.tv_kobra_time_expect)
		val tv_stand: TextView = itemView.findViewById(R.id.tv_kobra_stand)
	}
	
	data class KobraData(
		val plane: String, val flight: String, val destination: String, val stand: String, val status: String, val time_planed: Long, val time_expect: Long
	)
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KobraViewHolder {
		val itemView = LayoutInflater.from(parent.context)
			.inflate(R.layout.adapter_kobra, parent, false)
		return KobraViewHolder(itemView)
	}
	
	override fun onBindViewHolder(holder: KobraViewHolder, position: Int) {
		val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
		holder.tv_plane.text = kobraData[position].plane
		holder.tv_flight.text = kobraData[position].flight
		holder.tv_dectination.text = kobraData[position].destination
		holder.tv_stand.text = kobraData[position].stand
		
		val calendar = Calendar.getInstance()
		
		if (kobraData[position].time_expect != 0.toLong()) {
			calendar.timeInMillis = kobraData[position].time_expect
			holder.tv_time_expect.text = formatter.format(calendar.time)
		} else holder.tv_time_expect.text = ""
		
		if (kobraData[position].time_planed != 0.toLong()) {
			calendar.timeInMillis = kobraData[position].time_planed
			holder.tv_time_planed.text = formatter.format(calendar.time)
		} else holder.tv_time_planed.text = ""
		
		when (kobraData[position].status) {
			KOBRA_STATUS_ARRIVED_1 -> holder.rl_root.setBackgroundResource(R.drawable.bg_green)
			KOBRA_STATUS_ARRIVED_2 -> holder.rl_root.setBackgroundResource(R.drawable.bg_green)
			KOBRA_STATUS_DEPARTED -> holder.rl_root.setBackgroundResource(R.drawable.bg_green)
			KOBRA_STATUS_IN_WORK_1 -> holder.rl_root.setBackgroundResource(R.drawable.bg_blue)
			KOBRA_STATUS_IN_WORK_2 -> holder.rl_root.setBackgroundResource(R.drawable.bg_blue)
			KOBRA_STATUS_IN_WORK_3 -> holder.rl_root.setBackgroundResource(R.drawable.bg_blue)
			KOBRA_STATUS_IN_WORK_4 -> holder.rl_root.setBackgroundResource(R.drawable.bg_blue)
			KOBRA_STATUS_IN_WORK_5 -> holder.rl_root.setBackgroundResource(R.drawable.bg_blue)
			KOBRA_STATUS_IN_FLIGHT -> holder.rl_root.setBackgroundResource(R.drawable.bg_blue)
			KOBRA_STATUS_IN_FUTURE -> holder.rl_root.setBackgroundResource(R.drawable.bg_clear)
			KOBRA_STATUS_DELAYED -> holder.rl_root.setBackgroundResource(R.drawable.bg_yellow)
			else -> holder.rl_root.setBackgroundResource(R.drawable.bg_clear)
		}
	}
	
	override fun getItemCount() = kobraData.size
}