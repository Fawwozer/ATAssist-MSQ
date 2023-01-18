package by.fawwozer.atassist

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import by.fawwozer.atassist.Global.Companion.BACKWARD_DIRECTION
import by.fawwozer.atassist.Global.Companion.CHECK_AA
import by.fawwozer.atassist.Global.Companion.CHECK_BD
import by.fawwozer.atassist.Global.Companion.CHECK_TR
import by.fawwozer.atassist.Global.Companion.FORWARD_DIRECTION
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_ARRIVAL_TIME
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_BELAVIA
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_CHECK
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_DEPARTURE_TIME
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_FLIGHT_DESTINATION
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_FLIGHT_NUMBER
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_ID
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_IS_DONE
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_PLANE
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_SORT
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_STAND
import by.fawwozer.atassist.Global.Companion.LOGS_DB_TABLE
import by.fawwozer.atassist.Global.Companion.MAIN_FRAGMENT_FLEET
import by.fawwozer.atassist.Global.Companion.MAIN_FRAGMENT_KOBRA
import by.fawwozer.atassist.Global.Companion.MAIN_FRAGMENT_SCHEDULE
import by.fawwozer.atassist.Global.Companion.MAIN_FRAGMENT_SETTINGS
import by.fawwozer.atassist.Global.Companion.MAIN_ITEM_FLEET
import by.fawwozer.atassist.Global.Companion.MAIN_ITEM_KOBRA
import by.fawwozer.atassist.Global.Companion.MAIN_ITEM_SCHEDULE
import by.fawwozer.atassist.Global.Companion.MAIN_ITEM_SETTINGS
import by.fawwozer.atassist.Global.Companion.MAIN_TEXT_SPLITTER
import by.fawwozer.atassist.Global.Companion.OTHER_ITEM_FUEL_CALCULATOR
import by.fawwozer.atassist.Global.Companion.OTHER_ITEM_MSQ_INFO
import by.fawwozer.atassist.Global.Companion.OTHER_KOBRA_RELOAD
import by.fawwozer.atassist.Global.Companion.OTHER_SCREEN_FUEL_CALCULATOR
import by.fawwozer.atassist.Global.Companion.OTHER_SCREEN_MSQ_INFO
import by.fawwozer.atassist.Global.Companion.SETTINGS_ITEM_ABOUT
import by.fawwozer.atassist.Global.Companion.SETTINGS_ITEM_BACKUP
import by.fawwozer.atassist.Global.Companion.SETTINGS_ITEM_GENERAL
import by.fawwozer.atassist.Global.Companion.SETTINGS_ITEM_LOGS
import by.fawwozer.atassist.Global.Companion.SETTINGS_ITEM_MAINTENANCE
import by.fawwozer.atassist.Global.Companion.SETTINGS_ITEM_NOTIFICATION
import by.fawwozer.atassist.Global.Companion.SETTINGS_SCREEN_ABOUT
import by.fawwozer.atassist.Global.Companion.SETTINGS_SCREEN_BACKUP
import by.fawwozer.atassist.Global.Companion.SETTINGS_SCREEN_GENERAL
import by.fawwozer.atassist.Global.Companion.SETTINGS_SCREEN_LOGS
import by.fawwozer.atassist.Global.Companion.SETTINGS_SCREEN_MAINTENANCE
import by.fawwozer.atassist.Global.Companion.SETTINGS_SCREEN_NOTIFICATION
import by.fawwozer.atassist.Global.Companion.THEME_BELAVIA
import by.fawwozer.atassist.Global.Companion.THEME_DARK
import by.fawwozer.atassist.Global.Companion.THEME_LIGHT
import by.fawwozer.atassist.Global.Companion.TIME_PICKER_INTERVAL
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class ATAssist: AppCompatActivity() {
	
	//переменные истории открытия вкладок
	var selectedScreen = MAIN_FRAGMENT_KOBRA
	private var previousScreen = MAIN_FRAGMENT_KOBRA
	private var prePreviousScreen = MAIN_FRAGMENT_KOBRA
	private var defaultScreen = MAIN_FRAGMENT_KOBRA
	
	override fun onCreate(savedInstanceState: Bundle?) {
		//применение темы из настроек
		val preference = getSharedPreferences(Global.PREFERENCE_FILE, Context.MODE_PRIVATE)
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
		if (preference.contains(Global.SETTING_GENERAL_APPLICATION_THEME)) {
			when (preference.getInt(Global.SETTING_GENERAL_APPLICATION_THEME, THEME_LIGHT)) {
				THEME_LIGHT -> setTheme(R.style.AppTheme_Light)
				THEME_DARK -> setTheme(R.style.AppTheme_Dark)
				THEME_BELAVIA -> setTheme(R.style.AppTheme_Belavia)
			}
		} else {
			setTheme(R.style.AppTheme_Light)
		}
		super.onCreate(savedInstanceState)
		setContentView(R.layout.view_main)
		//setContentView(R.layout.test)
		
		findViewById<BottomNavigationView>(R.id.bnv_main).setOnNavigationItemSelectedListener {item: MenuItem ->
			//переключение экранов через BottomNavigationView
			return@setOnNavigationItemSelectedListener when (item.itemId) {
				MAIN_ITEM_SCHEDULE -> {
					showScreen(MAIN_FRAGMENT_SCHEDULE, FORWARD_DIRECTION)
					true
				}
				MAIN_ITEM_FLEET -> {
					showScreen(MAIN_FRAGMENT_FLEET, FORWARD_DIRECTION)
					true
				}
				MAIN_ITEM_KOBRA -> {
					showScreen(MAIN_FRAGMENT_KOBRA, FORWARD_DIRECTION)
					true
				}
				MAIN_ITEM_SETTINGS -> {
					showScreen(MAIN_FRAGMENT_SETTINGS, FORWARD_DIRECTION)
					true
				}
				else -> false
			}
		}
		
		//показ первой вкладки при запуске
		showFirstScreen(defaultScreen)
	}
	
	override fun onStart() {
		super.onStart()
		GetKobraSPP.run(this)
	}
	
	override fun onBackPressed() {
		//возвращение по истории открытия
		if (selectedScreen != defaultScreen) {
			showScreen(previousScreen, BACKWARD_DIRECTION)    //показ предыдущей вкладки
		} else {
			if (previousScreen != defaultScreen) {
				showScreen(previousScreen, BACKWARD_DIRECTION)   //показ предыдущей вкладки
			} else {
				super.onBackPressed() //выход из приложения при достижении настроеной начальной вкладки
			}
		}
	}
	
	fun showScreen(id: Int, direction: Boolean) {
		if (selectedScreen != id) {//проверка на то что вкладка ещё не показана
			val mainFragmentSchedule = supportFragmentManager.findFragmentById(MAIN_FRAGMENT_SCHEDULE)
			val mainFragmentFleet = supportFragmentManager.findFragmentById(MAIN_FRAGMENT_FLEET)
			val mainFragmentKobra = supportFragmentManager.findFragmentById(MAIN_FRAGMENT_KOBRA)
			val mainFragmentSettings = supportFragmentManager.findFragmentById(MAIN_FRAGMENT_SETTINGS)
			
			val settingsFragmentGeneral = supportFragmentManager.findFragmentById(SETTINGS_SCREEN_GENERAL)
			val settingsFragmentMaintenance = supportFragmentManager.findFragmentById(SETTINGS_SCREEN_MAINTENANCE)
			val settingsFragmentBackUp = supportFragmentManager.findFragmentById(SETTINGS_SCREEN_BACKUP)
			val settingsFragmentNotification = supportFragmentManager.findFragmentById(SETTINGS_SCREEN_NOTIFICATION)
			val settingsFragmentLogs = supportFragmentManager.findFragmentById(SETTINGS_SCREEN_LOGS)
			val settingsFragmentAbout = supportFragmentManager.findFragmentById(SETTINGS_SCREEN_ABOUT)
			
			val otherFragmentFuel = supportFragmentManager.findFragmentById(OTHER_SCREEN_FUEL_CALCULATOR)
			val otherFragmentMSQ = supportFragmentManager.findFragmentById(OTHER_SCREEN_MSQ_INFO)
			
			//перезапись значиний истории вкладок
			if (direction == FORWARD_DIRECTION) {//при движении вперед
				prePreviousScreen = previousScreen
				previousScreen = selectedScreen
			} else {//при движении назад по истории
				previousScreen = prePreviousScreen
				prePreviousScreen = defaultScreen
			}
			selectedScreen = id
			
			//скрытие всех вкладок
			supportFragmentManager
				.beginTransaction()
				.hide(mainFragmentSchedule!!)
				.hide(mainFragmentFleet!!)
				.hide(mainFragmentKobra!!)
				.hide(mainFragmentSettings!!)
				.hide(settingsFragmentGeneral!!)
				.hide(settingsFragmentMaintenance!!)
				.hide(settingsFragmentBackUp!!)
				.hide(settingsFragmentNotification!!)
				.hide(settingsFragmentLogs!!)
				.hide(settingsFragmentAbout!!)
				.hide(otherFragmentFuel!!)
				.hide(otherFragmentMSQ!!)
				.commit()
			
			//показ выбранной вкладки
			when (id) {
				MAIN_FRAGMENT_SCHEDULE -> {
					findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.main_menu_schedule)
					findViewById<BottomNavigationView>(R.id.bnv_main).selectedItemId = MAIN_ITEM_SCHEDULE
					findViewById<BottomAppBar>(R.id.bab_main).visibility = View.VISIBLE
					findViewById<FloatingActionButton>(R.id.fab_main).show()
					findViewById<FloatingActionButton>(R.id.fab_main).setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_plus))
					findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.VISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.VISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
					supportFragmentManager
						.beginTransaction()
						.show(mainFragmentSchedule)
						.commit()
				}
				MAIN_FRAGMENT_FLEET -> {
					findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.main_menu_fleet_info)
					findViewById<BottomNavigationView>(R.id.bnv_main).selectedItemId = MAIN_ITEM_FLEET
					findViewById<BottomAppBar>(R.id.bab_main).visibility = View.VISIBLE
					findViewById<FloatingActionButton>(R.id.fab_main).show()
					findViewById<FloatingActionButton>(R.id.fab_main).setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_plus))
					findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.VISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.VISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
					supportFragmentManager
						.beginTransaction()
						.show(mainFragmentFleet)
						.commit()
				}
				MAIN_FRAGMENT_KOBRA -> {
					findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.main_menu_umms_kobra)
					findViewById<BottomNavigationView>(R.id.bnv_main).selectedItemId = MAIN_ITEM_KOBRA
					findViewById<BottomAppBar>(R.id.bab_main).visibility = View.VISIBLE
					findViewById<FloatingActionButton>(R.id.fab_main).show()
					findViewById<FloatingActionButton>(R.id.fab_main).setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_sync))
					findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.VISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.VISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
					supportFragmentManager
						.beginTransaction()
						.replace(R.id.fragment_kobra, MainKobraFragment())
						.show(mainFragmentKobra)
						.commit()
				}
				MAIN_FRAGMENT_SETTINGS -> {
					findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.main_menu_settings)
					findViewById<BottomNavigationView>(R.id.bnv_main).selectedItemId = MAIN_ITEM_SETTINGS
					findViewById<BottomAppBar>(R.id.bab_main).visibility = View.VISIBLE
					findViewById<FloatingActionButton>(R.id.fab_main).hide()
					findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
					supportFragmentManager
						.beginTransaction()
						.show(mainFragmentSettings)
						.commit()
				}
				SETTINGS_SCREEN_GENERAL -> {
					findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.settings_menu_title_general)
					findViewById<BottomAppBar>(R.id.bab_main).visibility = View.GONE
					findViewById<FloatingActionButton>(R.id.fab_main).hide()
					findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
					supportFragmentManager
						.beginTransaction()
						.show(settingsFragmentGeneral)
						.commit()
				}
				SETTINGS_SCREEN_MAINTENANCE -> {
					findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.settings_menu_title_maintenance)
					findViewById<BottomAppBar>(R.id.bab_main).visibility = View.GONE
					findViewById<FloatingActionButton>(R.id.fab_main).hide()
					findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
					supportFragmentManager
						.beginTransaction()
						.show(settingsFragmentMaintenance)
						.commit()
				}
				SETTINGS_SCREEN_BACKUP -> {
					findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.settings_menu_title_backup)
					findViewById<BottomAppBar>(R.id.bab_main).visibility = View.GONE
					findViewById<FloatingActionButton>(R.id.fab_main).hide()
					findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
					supportFragmentManager
						.beginTransaction()
						.show(settingsFragmentBackUp)
						.commit()
				}
				SETTINGS_SCREEN_NOTIFICATION -> {
					findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.settings_menu_title_notification)
					findViewById<BottomAppBar>(R.id.bab_main).visibility = View.GONE
					findViewById<FloatingActionButton>(R.id.fab_main).hide()
					findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
					supportFragmentManager
						.beginTransaction()
						.show(settingsFragmentNotification)
						.commit()
				}
				SETTINGS_SCREEN_LOGS -> {
					findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.settings_menu_title_logs)
					findViewById<BottomAppBar>(R.id.bab_main).visibility = View.GONE
					findViewById<FloatingActionButton>(R.id.fab_main).hide()
					findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
					supportFragmentManager
						.beginTransaction()
						.show(settingsFragmentLogs)
						.commit()
				}
				SETTINGS_SCREEN_ABOUT -> {
					findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.settings_menu_title_about)
					findViewById<BottomAppBar>(R.id.bab_main).visibility = View.GONE
					findViewById<FloatingActionButton>(R.id.fab_main).hide()
					findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.INVISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
					supportFragmentManager
						.beginTransaction()
						.show(settingsFragmentAbout)
						.commit()
				}
				OTHER_SCREEN_FUEL_CALCULATOR -> {
					findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.other_screen_fuel_calculator)
					findViewById<BottomAppBar>(R.id.bab_main).visibility = View.GONE
					findViewById<FloatingActionButton>(R.id.fab_main).hide()
					findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.VISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.VISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.VISIBLE
					supportFragmentManager
						.beginTransaction()
						.show(otherFragmentFuel)
						.commit()
				}
				OTHER_SCREEN_MSQ_INFO -> {
					findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.other_screen_msq_info)
					findViewById<BottomAppBar>(R.id.bab_main).visibility = View.GONE
					findViewById<FloatingActionButton>(R.id.fab_main).hide()
					findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.VISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.VISIBLE
					findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
					supportFragmentManager
						.beginTransaction()
						.show(otherFragmentMSQ)
						.commit()
				}
			}
			/*
			//temporary\\
			findViewById<BottomAppBar>(R.id.bab_main).visibility = View.GONE
			findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.INVISIBLE
			findViewById<FloatingActionButton>(R.id.fab_main).hide()
			*/
		}
	}
	
	private fun Context.showFirstScreen(id: Int) {
		val mainFragmentSchedule = supportFragmentManager.findFragmentById(MAIN_FRAGMENT_SCHEDULE)
		val mainFragmentFleet = supportFragmentManager.findFragmentById(MAIN_FRAGMENT_FLEET)
		val mainFragmentKobra = supportFragmentManager.findFragmentById(MAIN_FRAGMENT_KOBRA)
		val mainFragmentSettings = supportFragmentManager.findFragmentById(MAIN_FRAGMENT_SETTINGS)
		
		val settingsFragmentGeneral = supportFragmentManager.findFragmentById(SETTINGS_SCREEN_GENERAL)
		val settingsFragmentMaintenance = supportFragmentManager.findFragmentById(SETTINGS_SCREEN_MAINTENANCE)
		val settingsFragmentBackUp = supportFragmentManager.findFragmentById(SETTINGS_SCREEN_BACKUP)
		val settingsFragmentNotification = supportFragmentManager.findFragmentById(SETTINGS_SCREEN_NOTIFICATION)
		val settingsFragmentLogs = supportFragmentManager.findFragmentById(SETTINGS_SCREEN_LOGS)
		val settingsFragmentAbout = supportFragmentManager.findFragmentById(SETTINGS_SCREEN_ABOUT)
		
		val otherFragmentFuel = supportFragmentManager.findFragmentById(OTHER_SCREEN_FUEL_CALCULATOR)
		val otherFragmentMSQ = supportFragmentManager.findFragmentById(OTHER_SCREEN_MSQ_INFO)
		
		supportFragmentManager
			.beginTransaction()
			.hide(mainFragmentSchedule!!)
			.hide(mainFragmentFleet!!)
			.hide(mainFragmentKobra!!)
			.hide(mainFragmentSettings!!)
			.hide(settingsFragmentGeneral!!)
			.hide(settingsFragmentMaintenance!!)
			.hide(settingsFragmentBackUp!!)
			.hide(settingsFragmentNotification!!)
			.hide(settingsFragmentLogs!!)
			.hide(settingsFragmentAbout!!)
			.hide(otherFragmentFuel!!)
			.hide(otherFragmentMSQ!!)
			.commit()
		
		when (id) {
			MAIN_FRAGMENT_SCHEDULE -> {
				findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.main_menu_schedule)
				findViewById<BottomNavigationView>(R.id.bnv_main).selectedItemId = MAIN_ITEM_SCHEDULE
				findViewById<FloatingActionButton>(R.id.fab_main).setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_plus))
				findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.VISIBLE
				findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.VISIBLE
				findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
				supportFragmentManager
					.beginTransaction()
					.show(mainFragmentSchedule)
					.commit()
			}
			MAIN_FRAGMENT_FLEET -> {
				findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.main_menu_fleet_info)
				findViewById<BottomNavigationView>(R.id.bnv_main).selectedItemId = MAIN_ITEM_FLEET
				findViewById<FloatingActionButton>(R.id.fab_main).setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_plus))
				findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.VISIBLE
				findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.VISIBLE
				findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
				supportFragmentManager
					.beginTransaction()
					.show(mainFragmentFleet)
					.commit()
			}
			MAIN_FRAGMENT_KOBRA -> {
				findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.main_menu_umms_kobra)
				findViewById<BottomNavigationView>(R.id.bnv_main).selectedItemId = MAIN_ITEM_KOBRA
				findViewById<FloatingActionButton>(R.id.fab_main).setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_sync))
				findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.VISIBLE
				findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.VISIBLE
				findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
				supportFragmentManager
					.beginTransaction()
					.show(mainFragmentKobra)
					.commit()
			}
			else -> {
				findViewById<TextView>(R.id.toolbar_main_title).text = getString(R.string.main_menu_umms_kobra)
				findViewById<BottomNavigationView>(R.id.bnv_main).selectedItemId = MAIN_ITEM_KOBRA
				findViewById<ImageButton>(R.id.toolbar_main_jeppesen).visibility = View.VISIBLE
				findViewById<ImageButton>(R.id.toolbar_main_calculator).visibility = View.VISIBLE
				findViewById<ImageButton>(R.id.toolbar_main_reload).visibility = View.INVISIBLE
				supportFragmentManager
					.beginTransaction()
					.show(mainFragmentKobra)
					.commit()
			}
		}
		selectedScreen = id
		previousScreen = id
		prePreviousScreen = id
	}
	
	fun navigationOnClick(view: View) {
		when (view.id) {
			SETTINGS_ITEM_GENERAL -> {
				showScreen(SETTINGS_SCREEN_GENERAL, FORWARD_DIRECTION)
			}
			SETTINGS_ITEM_MAINTENANCE -> {
				showScreen(SETTINGS_SCREEN_MAINTENANCE, FORWARD_DIRECTION)
			}
			SETTINGS_ITEM_BACKUP -> {
				showScreen(SETTINGS_SCREEN_BACKUP, FORWARD_DIRECTION)
			}
			SETTINGS_ITEM_NOTIFICATION -> {
				showScreen(SETTINGS_SCREEN_NOTIFICATION, FORWARD_DIRECTION)
			}
			SETTINGS_ITEM_LOGS -> {
				showScreen(SETTINGS_SCREEN_LOGS, FORWARD_DIRECTION)
			}
			SETTINGS_ITEM_ABOUT -> {
				showScreen(SETTINGS_SCREEN_ABOUT, FORWARD_DIRECTION)
			}
			OTHER_ITEM_FUEL_CALCULATOR -> {
				showScreen(OTHER_SCREEN_FUEL_CALCULATOR, FORWARD_DIRECTION)
			}
			OTHER_ITEM_MSQ_INFO -> {
				//TODO("Create MSQ Info screen for enable")
				//showScreen(OTHER_SCREEN_MSQ_INFO, FORWARD_DIRECTION)
			}
			OTHER_KOBRA_RELOAD -> {
				when (selectedScreen) {
					MAIN_FRAGMENT_SCHEDULE -> GetKobraSPP.run(this)
					OTHER_SCREEN_FUEL_CALCULATOR -> (supportFragmentManager.findFragmentById(R.id.fragment_other_fuel_calculator) as FuelCalculatorFragment).clear()
				}
			}
		}
	}
	
	fun floatBTNOnClick(view: View) {
		when (selectedScreen) {
			MAIN_FRAGMENT_SCHEDULE -> createScheduleEntry(-1, "-1", -1, "", 0, 0, -1)
			MAIN_FRAGMENT_KOBRA -> GetKobraSPP.run(this)
			MAIN_FRAGMENT_FLEET -> {}//TODO("Implement creating fleet info note")
		}
	}
	
	fun createScheduleEntry(planeId: Int, checksIds: String, flightN: Int, city: String, arr: Long, dep: Long, logId: Long) {
		
		// создание констант
		val context = this
		val start = Calendar.getInstance().timeInMillis
		
		val scheduleEntryData = ScheduleEntryData()
		scheduleEntryData.planeID = planeId
		scheduleEntryData.checksIDs = checksIds
		scheduleEntryData.flightN = flightN
		scheduleEntryData.city = city
		scheduleEntryData.logID = logId
		scheduleEntryData.arrTime = arr
		scheduleEntryData.depTime = dep
		
		//создание диалогового окна и поиск View  в нем
		val v = LayoutInflater.from(this)
			.inflate(R.layout.dialog_belavia, null)
		
		val spPlane = v.findViewById<Spinner>(R.id.sp_d_belavia_aircraft)
		val spCheck = v.findViewById<Spinner>(R.id.sp_d_belavia_check)
		val tvArrTime = v.findViewById<TextView>(R.id.tv_d_belavia_land)
		val tvDepTime = v.findViewById<TextView>(R.id.tv_d_belavia_takeoff)
		val btAddCheck = v.findViewById<Button>(R.id.bt_d_belavia_add)
		val btRemoveCheck1 = v.findViewById<Button>(R.id.bt_d_belavia_remove_1)
		val btRemoveCheck2 = v.findViewById<Button>(R.id.bt_d_belavia_remove_2)
		val btRemoveCheck3 = v.findViewById<Button>(R.id.bt_d_belavia_remove_3)
		
		//настройка массивов данных самолетов и чеков
		val pda = PLaneDataArray()
		val cda = CheckDataArray()
		pda.createForEnabled()
		cda.createForType(pda.getType(planeId))
		
		//настройка выпадающих списков
		val spPlaneAdapter = ArrayAdapter(context, R.layout.adapter_spinner, pda.getPlaneNameWithTypeList())
		var spCheckAdapter = ArrayAdapter(context, R.layout.adapter_spinner, cda.getChecksList())
		spPlane.adapter = spPlaneAdapter
		spCheck.adapter = spCheckAdapter
		
		Log.d("MY", "First run")
		initiateScheduleEntryDialogView(v, scheduleEntryData)
		
		//обработчик выбота самолета
		spPlane.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				if (Calendar.getInstance().timeInMillis - start > 1000) {
					if (position != 0) { //выбран самолет
						cda.createForType(pda.getType(pda.getPlaneID(position)))
						if (pda.getType(scheduleEntryData.planeID) != pda.getType(pda.getPlaneID(position))) {
							if (spCheck.selectedItemId != 0.toLong()) {
								val checksIDs = scheduleEntryData.checksIDs.split(MAIN_TEXT_SPLITTER)
									.toMutableList()
								scheduleEntryData.checksIDs = checksIDs[0]
							} else scheduleEntryData.checksIDs = "-1"
						}
						spCheckAdapter = ArrayAdapter(context, R.layout.adapter_spinner, cda.getChecksList())
						spCheck.adapter = spCheckAdapter
						scheduleEntryData.planeID = pda.getPlaneID(position)
						initiateScheduleEntryDialogView(v, scheduleEntryData)
					} else { //не выбран самолет
						Log.d("MY", "Plane not selected")
						cda.createForType(-1) // создание пустого массива чеков для выпадающего списка
						spCheckAdapter = ArrayAdapter(context, R.layout.adapter_spinner, cda.getChecksList())
						spCheck.adapter = spCheckAdapter
						spCheck.setSelection(0)
						scheduleEntryData.planeID = -1
						scheduleEntryData.checksIDs = "-1"
						initiateScheduleEntryDialogView(v, scheduleEntryData)
					}
				}
			}
			
			override fun onNothingSelected(p0: AdapterView<*>?) {}
		}
		//обработчик выбора чека
		spCheck.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				if (Calendar.getInstance().timeInMillis - start > 1000) {
					val checksIDs = scheduleEntryData.checksIDs.split(MAIN_TEXT_SPLITTER)
						.toMutableList()
					if (position != 0) {
						cda.createForType(pda.getType(pda.getPlaneID(spPlane.selectedItemPosition)))
						checksIDs[0] = cda.getCheckID(position)
							.toString()
						var checks = ""
						var remove = -1
						for ((a, check) in checksIDs.withIndex()) {
							if (check == cda.getCheckID(position)
									.toString() && a != 0
							) remove = a
						}
						if (remove != -1) checksIDs.removeAt(remove)
						for ((b, check) in checksIDs.withIndex()) {
							checks += if (b == 0) check
							else MAIN_TEXT_SPLITTER + check
						}
						scheduleEntryData.checksIDs = checks
						Log.d("MY", "Check selected")
						initiateScheduleEntryDialogView(v, scheduleEntryData)
					} else {
						if (Calendar.getInstance().timeInMillis - start > 1000) {
							checksIDs.clear()
							checksIDs += "-1"
							scheduleEntryData.checksIDs = "-1"
							scheduleEntryData.arrTime = 0
							scheduleEntryData.depTime = 0
							Log.d("MY", "Check choose selected")
							initiateScheduleEntryDialogView(v, scheduleEntryData)
						}
					}
				}
			}
			
			override fun onNothingSelected(p0: AdapterView<*>?) {}
		}
		
		//обработчики выбора времени прилета/вылета
		val clickListener = View.OnClickListener {
			val timePickerDialog = LayoutInflater.from(context)
				.inflate(R.layout.dialog_time_picker, null)
			val timePicker = timePickerDialog.findViewById<TimePicker>(R.id.tp_d_time_picker)
			timePicker.setIs24HourView(true)
			timePicker.hour = 12
			timePicker.minute = 0
			setMinuteInterval(timePicker)
			val builder = AlertDialog.Builder(context)
			builder.setCancelable(true)
				.setView(timePickerDialog)
				.setPositiveButton("Ok") {dialog, _ ->
					val time = Calendar.getInstance()
					val current = Calendar.getInstance()
					time.set(Calendar.HOUR_OF_DAY, timePicker.hour)
					time.set(Calendar.MINUTE, timePicker.minute * TIME_PICKER_INTERVAL)
					time.set(Calendar.SECOND, 0)
					time.set(Calendar.MILLISECOND, 0)
					when (it.id) {
						R.id.tv_d_belavia_land -> {
							current.add(Calendar.HOUR_OF_DAY, -2)
							if (time.before(current)) time.add(Calendar.DAY_OF_YEAR, 1)
							scheduleEntryData.arrTime = time.timeInMillis
						}
						R.id.tv_d_belavia_takeoff -> {
							if (time.before(current)) time.add(Calendar.DAY_OF_YEAR, 1)
							scheduleEntryData.depTime = time.timeInMillis
						}
					}
					Log.d("MY", "Time picker")
					initiateScheduleEntryDialogView(v, scheduleEntryData)
					dialog.cancel()
				}
				.create()
				.show()
		}
		tvArrTime.setOnClickListener(clickListener)
		tvDepTime.setOnClickListener(clickListener)
		
		//обработчик нажатия кнопки добавления чека
		btAddCheck.setOnClickListener {
			val pMenu = PopupMenu(context, it)
			val checksIDs = scheduleEntryData.checksIDs.split(MAIN_TEXT_SPLITTER)
				.toMutableList()
			cda.createForType(pda.getType(pda.getPlaneID(spPlane.selectedItemPosition)))
			val checksNames = cda.getChecksList()
			//создание выпадающего меню возможных для добавления чеков
			for ((a, checkName) in checksNames.withIndex()) {
				var skip = false
				//проверка чека на использование, если используется пропускаем
				for (checkID in checksIDs) {
					if (cda.getCheckID(a) == checkID.toInt()) skip = true
				}
				//не добавляются AA,TR,BD
				if (!(cda.getCheckID(a) < 10 || skip || a == spCheck.selectedItemPosition)) {
					pMenu.menu.add(1, cda.getCheckID(a), a, checkName)
				}
			}
			pMenu.setOnMenuItemClickListener {mItem ->
				if (checksIDs.size < 4) {
					cda.createForType(pda.getType(pda.getPlaneID(spPlane.selectedItemPosition)))
					checksIDs += "${mItem.itemId}"
				}
				var checks = ""
				for ((a, check) in checksIDs.withIndex()) {
					checks += if (a == 0) check
					else MAIN_TEXT_SPLITTER + check
				}
				scheduleEntryData.checksIDs = checks
				Log.d("MY", "Add check")
				initiateScheduleEntryDialogView(v, scheduleEntryData)
				false
			}
			pMenu.show()
		}
		
		//обработчик нажатия кнопок удаления дабавленых чеков
		val removeClick = View.OnClickListener {
			val checksIDs = scheduleEntryData.checksIDs.split(MAIN_TEXT_SPLITTER)
				.toMutableList()
			when (it.id) {
				R.id.bt_d_belavia_remove_1 -> checksIDs.removeAt(1)
				R.id.bt_d_belavia_remove_2 -> checksIDs.removeAt(2)
				R.id.bt_d_belavia_remove_3 -> checksIDs.removeAt(3)
			}
			var checks = ""
			for ((a, check) in checksIDs.withIndex()) {
				checks += if (a == 0) check
				else MAIN_TEXT_SPLITTER + check
			}
			scheduleEntryData.checksIDs = checks
			Log.d("MY", "Remove check")
			initiateScheduleEntryDialogView(v, scheduleEntryData)
		}
		btRemoveCheck1.setOnClickListener(removeClick)
		btRemoveCheck2.setOnClickListener(removeClick)
		btRemoveCheck3.setOnClickListener(removeClick)
		
		val builder = AlertDialog.Builder(context)
		builder
			.setView(v)
			.setCancelable(true)
			.setPositiveButton(if (logId == (-1).toLong()) {
				"Ok"
			} else {
				"Edit"
			}, DialogInterface.OnClickListener
			{_, _ ->
				if (v.findViewById<EditText>(R.id.et_d_belavia_flight).text.isNotEmpty()) scheduleEntryData.flightN = v.findViewById<EditText>(R.id.et_d_belavia_flight).text.toString()
					.toInt()
				if (v.findViewById<EditText>(R.id.et_d_belavia_destination).text.isNotEmpty()) scheduleEntryData.city = v.findViewById<EditText>(R.id.et_d_belavia_destination).text.toString()
				var sort: Long
				val checksIDs = scheduleEntryData.checksIDs.split(MAIN_TEXT_SPLITTER)
					.toMutableList()
				sort = when {
					scheduleEntryData.arrTime != 0.toLong() -> scheduleEntryData.arrTime - checksIDs[0].toLong()
					scheduleEntryData.depTime != 0.toLong() -> scheduleEntryData.depTime - checksIDs[0].toLong()
					else -> Long.MAX_VALUE
				}
				val cv = ContentValues()
				cv.put(KEY_LOGS_PLANE, scheduleEntryData.planeID)
				cv.put(KEY_LOGS_CHECK, scheduleEntryData.checksIDs)
				cv.put(KEY_LOGS_FLIGHT_NUMBER, scheduleEntryData.flightN)
				cv.put(KEY_LOGS_FLIGHT_DESTINATION, scheduleEntryData.city)
				cv.put(KEY_LOGS_ARRIVAL_TIME, scheduleEntryData.arrTime)
				cv.put(KEY_LOGS_DEPARTURE_TIME, scheduleEntryData.depTime)
				cv.put(KEY_LOGS_SORT, sort)
				cv.put(KEY_LOGS_BELAVIA, true)
				cv.put(KEY_LOGS_IS_DONE, false)
				cv.put(KEY_LOGS_STAND, -1)
				
				if (spPlane.selectedItemPosition == 0) return@OnClickListener
				if (spCheck.selectedItemPosition == 0) return@OnClickListener
				if (spCheck.selectedItemPosition == 1 && (scheduleEntryData.arrTime == 0.toLong() || v.findViewById<EditText>(R.id.et_d_belavia_destination).text.isEmpty() || v.findViewById<EditText>(R.id.et_d_belavia_flight).text.isEmpty())) return@OnClickListener
				if (spCheck.selectedItemPosition == 2 && (scheduleEntryData.arrTime == 0.toLong() || scheduleEntryData.depTime == 0.toLong() || v.findViewById<EditText>(R.id.et_d_belavia_destination).text.isEmpty() || v.findViewById<EditText>(R.id.et_d_belavia_flight).text.isEmpty())) return@OnClickListener
				if (spCheck.selectedItemPosition == 3 && (scheduleEntryData.depTime == 0.toLong() || v.findViewById<EditText>(R.id.et_d_belavia_destination).text.isEmpty() || v.findViewById<EditText>(R.id.et_d_belavia_flight).text.isEmpty())) return@OnClickListener
				
				val logsDB = LogsDB().writableDatabase
				if (logId == (-1).toLong()) logsDB.insert(LOGS_DB_TABLE, null, cv)
				else logsDB.update(LOGS_DB_TABLE, cv, "${KEY_LOGS_ID}=${logId}", null)
				logsDB.close()
				return@OnClickListener
			})
			.create()
			.show()
	}
	
	private fun setMinuteInterval(tp: TimePicker) {
		val minutePicker = tp.findViewById<NumberPicker>(Resources.getSystem()
			.getIdentifier("minute", "id", "android"))
		minutePicker.minValue = 0
		minutePicker.maxValue = (60 / TIME_PICKER_INTERVAL) - 1
		var minutes = emptyArray<String>()
		var i = 0
		do {
			minutes += if (i < 10) "0$i"
			else "$i"
			i += TIME_PICKER_INTERVAL
		} while (i < 60)
		minutePicker.displayedValues = minutes
	}
	
	private fun initiateScheduleEntryDialogView(view: View, scheduleEntryData: ScheduleEntryData) {
		
		//поиск элементов диалогового окна
		Log.d("MY", "ScheduleEntryData: ${scheduleEntryData.planeID}, ${scheduleEntryData.checksIDs}, ${scheduleEntryData.flightN}, ${scheduleEntryData.city}, ${scheduleEntryData.logID}, ${scheduleEntryData.arrTime}, ${scheduleEntryData.depTime}")
		val spPlane = view.findViewById<Spinner>(R.id.sp_d_belavia_aircraft)
		val spCheck = view.findViewById<Spinner>(R.id.sp_d_belavia_check)
		val tvAddCheck1 = view.findViewById<TextView>(R.id.tv_d_belavia_add_check_1)
		val tvAddCheck2 = view.findViewById<TextView>(R.id.tv_d_belavia_add_check_2)
		val tvAddCheck3 = view.findViewById<TextView>(R.id.tv_d_belavia_add_check_3)
		val tvArrTime = view.findViewById<TextView>(R.id.tv_d_belavia_land)
		val tvDepTime = view.findViewById<TextView>(R.id.tv_d_belavia_takeoff)
		val tvFlightN = view.findViewById<TextView>(R.id.tv_d_belavia_flight)
		val ivArrTime = view.findViewById<ImageView>(R.id.iv_d_belavia_land)
		val ivDepTime = view.findViewById<ImageView>(R.id.iv_d_belavia_takeoff)
		val etFlightN = view.findViewById<EditText>(R.id.et_d_belavia_flight)
		val etCity = view.findViewById<EditText>(R.id.et_d_belavia_destination)
		val sSpacer = view.findViewById<Space>(R.id.s_d_belavia_spacer)
		val btAddCheck = view.findViewById<Button>(R.id.bt_d_belavia_add)
		val btRemoveCheck1 = view.findViewById<Button>(R.id.bt_d_belavia_remove_1)
		val btRemoveCheck2 = view.findViewById<Button>(R.id.bt_d_belavia_remove_2)
		val btRemoveCheck3 = view.findViewById<Button>(R.id.bt_d_belavia_remove_3)
		
		//создание постоянных
		val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
		val pda = PLaneDataArray()
		val cda = CheckDataArray()
		val checksIDs = scheduleEntryData.checksIDs.split(MAIN_TEXT_SPLITTER)
		val arrTime = Calendar.getInstance(TimeZone.getDefault())
		arrTime.timeInMillis = scheduleEntryData.arrTime
		val depTime = Calendar.getInstance(TimeZone.getDefault())
		depTime.timeInMillis = scheduleEntryData.depTime
		pda.createForEnabled()
		cda.createForType(pda.getType(scheduleEntryData.planeID))
		
		//отображение данных из ScheduleEntryData
		spPlane.setSelection(pda.getPosition(scheduleEntryData.planeID))
		spCheck.setSelection(cda.getPosition(checksIDs[0].toInt()))
		if (scheduleEntryData.arrTime != 0.toLong()) tvArrTime.text = timeFormat.format(arrTime.time)
		else tvArrTime.text = "12:00"
		if (scheduleEntryData.depTime != 0.toLong()) tvDepTime.text = timeFormat.format(depTime.time)
		else tvDepTime.text = "12:00"
		if (etFlightN.text.isEmpty()) {
			if (scheduleEntryData.flightN != -1) etFlightN.setText("${scheduleEntryData.flightN}")
			else etFlightN.setText("")
		}
		if (etCity.text.isEmpty()) etCity.setText(scheduleEntryData.city)
		
		// влияние plane, check на вид окна
		if (spCheck.selectedItemPosition != 0) {
			cda.createForType(pda.getType(pda.getPlaneID(spPlane.selectedItemPosition)))
			when (cda.getCheckID(spCheck.selectedItemPosition)) {
				CHECK_AA -> {
					btAddCheck.visibility = View.VISIBLE
					tvArrTime.visibility = View.VISIBLE
					ivArrTime.visibility = View.VISIBLE
					tvDepTime.visibility = View.GONE
					ivDepTime.visibility = View.GONE
					tvFlightN.visibility = View.VISIBLE
					etFlightN.visibility = View.VISIBLE
					etCity.visibility = View.VISIBLE
					etCity.hint = getString(R.string.dialog_belavia_city_hint)
					sSpacer.visibility = View.VISIBLE
				}
				CHECK_TR -> {
					btAddCheck.visibility = View.VISIBLE
					tvArrTime.visibility = View.VISIBLE
					ivArrTime.visibility = View.VISIBLE
					tvDepTime.visibility = View.VISIBLE
					ivDepTime.visibility = View.VISIBLE
					tvFlightN.visibility = View.VISIBLE
					etFlightN.visibility = View.VISIBLE
					etCity.visibility = View.VISIBLE
					etCity.hint = getString(R.string.dialog_belavia_city_hint)
					sSpacer.visibility = View.VISIBLE
				}
				CHECK_BD -> {
					btAddCheck.visibility = View.VISIBLE
					tvArrTime.visibility = View.GONE
					ivArrTime.visibility = View.GONE
					tvDepTime.visibility = View.VISIBLE
					ivDepTime.visibility = View.VISIBLE
					tvFlightN.visibility = View.VISIBLE
					etFlightN.visibility = View.VISIBLE
					etCity.visibility = View.VISIBLE
					etCity.hint = getString(R.string.dialog_belavia_city_hint)
					sSpacer.visibility = View.VISIBLE
				}
				else -> {
					btAddCheck.visibility = View.VISIBLE
					tvArrTime.visibility = View.VISIBLE
					ivArrTime.visibility = View.VISIBLE
					tvDepTime.visibility = View.VISIBLE
					ivDepTime.visibility = View.VISIBLE
					tvFlightN.visibility = View.GONE
					etFlightN.visibility = View.GONE
					etCity.visibility = View.VISIBLE
					etCity.hint = getString(R.string.dialog_belavia_city_hint_note)
					sSpacer.visibility = View.VISIBLE
				}
			}
		} else {
			btAddCheck.visibility = View.GONE
			tvArrTime.visibility = View.GONE
			ivArrTime.visibility = View.GONE
			tvDepTime.visibility = View.GONE
			ivDepTime.visibility = View.GONE
			tvFlightN.visibility = View.GONE
			etFlightN.visibility = View.GONE
			etCity.visibility = View.GONE
			etCity.hint = getString(R.string.dialog_belavia_city_hint)
			sSpacer.visibility = View.GONE
		}
		
		for ((a, checkID) in checksIDs.withIndex()) {
			when (a) {
				0 -> {
					tvAddCheck1.visibility = View.GONE
					btRemoveCheck1.visibility = View.GONE
					tvAddCheck2.visibility = View.GONE
					btRemoveCheck2.visibility = View.GONE
					tvAddCheck3.visibility = View.GONE
					btRemoveCheck3.visibility = View.GONE
				}
				1 -> {
					tvAddCheck1.text = cda.getCheckName(checkID.toInt())
					tvAddCheck1.visibility = View.VISIBLE
					btRemoveCheck1.visibility = View.VISIBLE
					tvAddCheck2.visibility = View.GONE
					btRemoveCheck2.visibility = View.GONE
					tvAddCheck3.visibility = View.GONE
					btRemoveCheck3.visibility = View.GONE
				}
				2 -> {
					tvAddCheck2.text = cda.getCheckName(checkID.toInt())
					tvAddCheck1.visibility = View.VISIBLE
					btRemoveCheck1.visibility = View.VISIBLE
					tvAddCheck2.visibility = View.VISIBLE
					btRemoveCheck2.visibility = View.VISIBLE
					tvAddCheck3.visibility = View.GONE
					btRemoveCheck3.visibility = View.GONE
				}
				3 -> {
					tvAddCheck3.text = cda.getCheckName(checkID.toInt())
					tvAddCheck1.visibility = View.VISIBLE
					btRemoveCheck1.visibility = View.VISIBLE
					tvAddCheck2.visibility = View.VISIBLE
					btRemoveCheck2.visibility = View.VISIBLE
					tvAddCheck3.visibility = View.VISIBLE
					btRemoveCheck3.visibility = View.VISIBLE
				}
			}
		}
	}
	
	class ScheduleEntryData {
		var planeID: Int = -1
		var checksIDs: String = "-1"
		var flightN: Int = -1
		var city: String = ""
		var arrTime: Long = 0
		var depTime: Long = 0
		var logID: Long = -1
	}

//// вынести в отдельный класс\\\\
////Settings screens fragments\\\\
	
	class SettingsMaintenance: Fragment() {
		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			return inflater.inflate(R.layout.fragment_settings_maintenance, null)
		}
	}
	
	class SettingsBackUp: Fragment() {
		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			return inflater.inflate(R.layout.fragment_settings_backup, null)
		}
	}
	
	class SettingsNotification: Fragment() {
		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			return inflater.inflate(R.layout.fragment_settings_notification, null)
		}
	}
	
	class LogsFragment: Fragment() {
		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			return inflater.inflate(R.layout.fragment_settings_logs, null)
		}
	}
	
	class AboutFragment: Fragment() {
		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			return inflater.inflate(R.layout.fragment_settings_about, null)
		}
	}

////Other screens fragments\\\\
	
	class MSQInfoFragment: Fragment() {
		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			return inflater.inflate(R.layout.fragment_other_msq_info, null)
		}
	}
}
