package by.fawwozer.atassist

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceActivity
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.view_main.*

class ATAssist: AppCompatActivity() {
	
	//переменные истории открытия вкладок
	private var selectedNavigationMenuItem = 0
	private var previousNavigationMenuItem = 0
	
	override fun onCreate(savedInstanceState: Bundle?) {
		//применение темы из настроек
		val preference = getSharedPreferences(Global.PREFERENCE_FILE, Context.MODE_PRIVATE)
		if (preference.contains(Global.SETTING_GENERAL_APPLICATION_THEME)) {
			when (preference.getString(Global.SETTING_GENERAL_APPLICATION_THEME, "0")) {
				"0" -> setTheme(R.style.AppTheme_Light)
				"1" -> setTheme(R.style.AppTheme_Dark)
			}
		} else {
			setTheme(R.style.AppTheme_Light)
		}
		super.onCreate(savedInstanceState)
		setContentView(R.layout.view_main)
		
		setMenuItemSelected(0)    //выбор начальной вкладки
	}
	
	override fun onBackPressed() {
		//возвращение по истории открытия
		if (selectedNavigationMenuItem != 0) {
			setMenuItemSelected(previousNavigationMenuItem)    //показ предыдущей вкладки
			previousNavigationMenuItem = 0    //сброс истории вкладок на 0 // на настроеную начальную вкладку
		} else {
			if (previousNavigationMenuItem != 0) {
				setMenuItemSelected(previousNavigationMenuItem)    //показ предыдущей вкладки
				previousNavigationMenuItem = 0    //сброс истории вкладок на 0 // на настроеную начальную вкладку
			} else {
				super.onBackPressed() //выход из приложения при достижении настроеной начальной вкладки
			}
		}
	}
	
	fun navigationMenuClick(mi: MenuItem) {
		//показ выбраной вкладки
		when (mi.itemId) {
			R.id.mi_schedule -> if (selectedNavigationMenuItem != 0) setMenuItemSelected(0)
			R.id.mi_fleet -> if (selectedNavigationMenuItem != 1) setMenuItemSelected(1)
			R.id.mi_kobra -> if (selectedNavigationMenuItem != 2) setMenuItemSelected(2)
			R.id.mi_msq_info -> if (selectedNavigationMenuItem != 3) setMenuItemSelected(3)
			R.id.mi_logs -> if (selectedNavigationMenuItem != 4) setMenuItemSelected(4)
		}
	}
	
	@SuppressLint("ResourceType")
	fun Context.setMenuItemSelected(menuID: Int) {
		val scheduleFragment = supportFragmentManager.findFragmentById(R.id.fragment_schedule)
		val fleetInfoFragment = supportFragmentManager.findFragmentById(R.id.fragment_fleet_info)
		val fuelCalcFragment = supportFragmentManager.findFragmentById(R.id.fragment_fuel_calculator)
		val kobraFragment = supportFragmentManager.findFragmentById(R.id.fragment_kobra)
		val msqInfoFragment = supportFragmentManager.findFragmentById(R.id.fragment_msq_info)
		val logsFragment = supportFragmentManager.findFragmentById(R.id.fragment_logs)
		
		//скрытие всех вкладок
		supportFragmentManager
			.beginTransaction()
			.hide(scheduleFragment!!)
			.hide(fleetInfoFragment!!)
			.hide(fuelCalcFragment!!)
			.hide(kobraFragment!!)
			.hide(msqInfoFragment!!)
			.hide(logsFragment!!)
			.commit()
		
		//внесение изменений в историю открытия вкладок
		previousNavigationMenuItem = selectedNavigationMenuItem
		selectedNavigationMenuItem = menuID
		
		//показ выбраной вкладки
		when (menuID) {
			0 -> {
				toolbar_main_title.text = getString(R.string.main_menu_schedule)
				supportFragmentManager
					.beginTransaction()
					.show(scheduleFragment)
					.commit()
			}
			1 -> {
				toolbar_main_title.text = getString(R.string.main_menu_fleet_info)
				supportFragmentManager
					.beginTransaction()
					.show(fleetInfoFragment)
					.commit()
			}
			2 -> {
				toolbar_main_title.text = getString(R.string.main_menu_umms_kobra)
				supportFragmentManager
					.beginTransaction()
					.show(kobraFragment)
					.commit()
			}
			3 -> {
				toolbar_main_title.text = getString(R.string.main_menu_umms_info)
				supportFragmentManager
					.beginTransaction()
					.show(msqInfoFragment)
					.commit()
			}
			4 -> {
				toolbar_main_title.text = getString(R.string.main_menu_logs)
				supportFragmentManager
					.beginTransaction()
					.show(logsFragment)
					.commit()
			}
		}
		
		//изменение цвета иконки и текста меню для выбраной вкладки
		val colorNotSelected = getColorFromAttr(R.attr.navMenuTextColor)
		val colorSelected = getColorFromAttr(R.attr.navMenuTextColorSelected)
		val size = bab_main.menu.size()
		var i = 0
		do {
			val menuItem = bab_main.menu.getItem(i)
			
			val spannableString = SpannableString(menuItem.title.toString())
			if (i == menuID) spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, colorSelected)), 0, spannableString.length, 0)
			else spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, colorNotSelected)), 0, spannableString.length, 0)
			menuItem.title = spannableString
			
			val drawable = menuItem.icon
			if (i == menuID) DrawableCompat.setTint(drawable, ContextCompat.getColor(this, colorSelected))
			else DrawableCompat.setTint(drawable, ContextCompat.getColor(this, colorNotSelected))
			i++
		} while (i < size)
	}
	
	@ColorInt
	fun Context.getColorFromAttr(@AttrRes attribute: Int) =
		TypedValue().let {
			theme.resolveAttribute(attribute, it, false); it.data
		}
	
	class ScheduleFragment: Fragment() {
		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			return inflater.inflate(R.layout.fragment_schedule, null)
		}
	}
	
	class FleetInfoFragment: Fragment() {
		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			return inflater.inflate(R.layout.fragment_fleet_info, null)
		}
	}
	
	class FuelCalculatorFragment: Fragment() {
		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			return inflater.inflate(R.layout.fragment_fuel_calculator, null)
		}
	}
	
	class KobraFragment: Fragment() {
		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			return inflater.inflate(R.layout.fragment_kobra, null)
		}
	}
	
	class MSQInfoFragment: Fragment() {
		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			return inflater.inflate(R.layout.fragment_msq_info, null)
		}
	}
	
	class LogsFragment: Fragment() {
		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			return inflater.inflate(R.layout.fragment_logs, null)
		}
	}
}
