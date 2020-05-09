package by.fawwozer.atassist

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
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
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.view_main.*

class ATAssist : AppCompatActivity() {

    var selectedNavigationMenuItem = 0
    var previousNavigationMenuItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        val preference = getSharedPreferences(Global.PREFERENCE_FILE, Context.MODE_PRIVATE)
        if (preference.contains(Global.SETTING_GENERAL_APPLICATION_THEME)) {
            when (preference.getInt(Global.SETTING_GENERAL_APPLICATION_THEME, 0)) {
                0 -> setTheme(R.style.AppTheme_Light)
                1 -> setTheme(R.style.AppTheme_Dark)
            }
        } else {
            setTheme(R.style.AppTheme_Light)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_main)

        val scheduleFragment = supportFragmentManager.findFragmentById(R.id.fragment_schedule)
        val fleetInfoFragment = supportFragmentManager.findFragmentById(R.id.fragment_fleet_info)
        val fuelCalcFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_fuel_calculator)
        val kobraFragment = supportFragmentManager.findFragmentById(R.id.fragment_kobra)
        val msqInfoFragment = supportFragmentManager.findFragmentById(R.id.fragment_msq_info)
        val logsFragment = supportFragmentManager.findFragmentById(R.id.fragment_logs)

        supportFragmentManager
            .beginTransaction()
            .show(scheduleFragment!!)
            .hide(fleetInfoFragment!!)
            .hide(fuelCalcFragment!!)
            .hide(kobraFragment!!)
            .hide(msqInfoFragment!!)
            .hide(logsFragment!!)
            .commit()

        setMenuItemSelected(0)
    }

    override fun onBackPressed() {
        if (selectedNavigationMenuItem != 0) {
            val scheduleFragment = supportFragmentManager.findFragmentById(R.id.fragment_schedule)
            val fleetInfoFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_fleet_info)
            val fuelCalcFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_fuel_calculator)
            val kobraFragment = supportFragmentManager.findFragmentById(R.id.fragment_kobra)
            val msqInfoFragment = supportFragmentManager.findFragmentById(R.id.fragment_msq_info)
            val logsFragment = supportFragmentManager.findFragmentById(R.id.fragment_logs)
            supportFragmentManager
                .beginTransaction()
                .hide(scheduleFragment!!)
                .hide(fleetInfoFragment!!)
                .hide(fuelCalcFragment!!)
                .hide(kobraFragment!!)
                .hide(msqInfoFragment!!)
                .hide(logsFragment!!)
                .commit()
            when (previousNavigationMenuItem) {
                0 -> {
                    supportFragmentManager
                        .beginTransaction()
                        .show(scheduleFragment!!)
                        .commit()
                    setMenuItemSelected(0)
                    selectedNavigationMenuItem = 0
                }
                1 -> {
                    supportFragmentManager
                        .beginTransaction()
                        .show(fleetInfoFragment!!)
                        .commit()
                    setMenuItemSelected(1)
                    selectedNavigationMenuItem = 1
                }
                2 -> {
                    supportFragmentManager
                        .beginTransaction()
                        .show(fuelCalcFragment!!)
                        .commit()
                    setMenuItemSelected(2)
                    selectedNavigationMenuItem = 2
                }
                3 -> {
                    supportFragmentManager
                        .beginTransaction()
                        .show(kobraFragment!!)
                        .commit()
                    setMenuItemSelected(3)
                    selectedNavigationMenuItem = 3
                }
                4 -> {
                    supportFragmentManager
                        .beginTransaction()
                        .show(msqInfoFragment!!)
                        .commit()
                    setMenuItemSelected(4)
                    selectedNavigationMenuItem = 4
                }
                5 -> {
                    supportFragmentManager
                        .beginTransaction()
                        .show(logsFragment!!)
                        .commit()
                    setMenuItemSelected(5)
                    selectedNavigationMenuItem = 5
                }
            }
            previousNavigationMenuItem = 0
        } else super.onBackPressed()
    }

    fun navigationMenuClick(mi: MenuItem) {
        Log.d("MY", "${mi.itemId} item clicked")
        val scheduleFragment = supportFragmentManager.findFragmentById(R.id.fragment_schedule)
        val fleetInfoFragment = supportFragmentManager.findFragmentById(R.id.fragment_fleet_info)
        val fuelCalcFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_fuel_calculator)
        val kobraFragment = supportFragmentManager.findFragmentById(R.id.fragment_kobra)
        val msqInfoFragment = supportFragmentManager.findFragmentById(R.id.fragment_msq_info)
        val logsFragment = supportFragmentManager.findFragmentById(R.id.fragment_logs)

        dl_main.closeDrawer(GravityCompat.START)

        when (mi.itemId) {
            R.id.mi_schedule -> {
                if (selectedNavigationMenuItem != 0) {
                    supportFragmentManager
                        .beginTransaction()
                        .show(scheduleFragment!!)
                        .hide(fleetInfoFragment!!)
                        .hide(fuelCalcFragment!!)
                        .hide(kobraFragment!!)
                        .hide(msqInfoFragment!!)
                        .hide(logsFragment!!)
                        .commit()
                    setMenuItemSelected(0)
                }
            }
            R.id.mi_fleet -> {
                if (selectedNavigationMenuItem != 1) {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(scheduleFragment!!)
                        .show(fleetInfoFragment!!)
                        .hide(fuelCalcFragment!!)
                        .hide(kobraFragment!!)
                        .hide(msqInfoFragment!!)
                        .hide(logsFragment!!)
                        .commit()
                    setMenuItemSelected(1)
                }
            }
            R.id.mi_fuel -> {
                if (selectedNavigationMenuItem != 2) {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(scheduleFragment!!)
                        .hide(fleetInfoFragment!!)
                        .show(fuelCalcFragment!!)
                        .hide(kobraFragment!!)
                        .hide(msqInfoFragment!!)
                        .hide(logsFragment!!)
                        .commit()
                    setMenuItemSelected(2)
                }
            }
            R.id.mi_kobra -> {
                if (selectedNavigationMenuItem != 3) {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(scheduleFragment!!)
                        .hide(fleetInfoFragment!!)
                        .hide(fuelCalcFragment!!)
                        .show(kobraFragment!!)
                        .hide(msqInfoFragment!!)
                        .hide(logsFragment!!)
                        .commit()
                    setMenuItemSelected(3)
                }
            }
            R.id.mi_msq_info -> {
                if (selectedNavigationMenuItem != 4) {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(scheduleFragment!!)
                        .hide(fleetInfoFragment!!)
                        .hide(fuelCalcFragment!!)
                        .hide(kobraFragment!!)
                        .show(msqInfoFragment!!)
                        .hide(logsFragment!!)
                        .commit()
                    setMenuItemSelected(4)
                }
            }
            R.id.mi_logs -> {
                if (selectedNavigationMenuItem != 5) {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(scheduleFragment!!)
                        .hide(fleetInfoFragment!!)
                        .hide(fuelCalcFragment!!)
                        .hide(kobraFragment!!)
                        .hide(msqInfoFragment!!)
                        .show(logsFragment!!)
                        .commit()
                    setMenuItemSelected(5)
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    fun Context.setMenuItemSelected(menuID: Int) {
        Log.d("MY", "MenuID: $menuID")
        previousNavigationMenuItem = selectedNavigationMenuItem
        selectedNavigationMenuItem = menuID
        when (menuID)
        {
            0 -> toolbar_main_title.text = getString(R.string.main_menu_schedule)
            1 -> toolbar_main_title.text = getString(R.string.main_menu_fleet_info)
            2 -> toolbar_main_title.text = getString(R.string.main_menu_fuel_calculator)
            3 -> toolbar_main_title.text = getString(R.string.main_menu_umms_kobra)
            4 -> toolbar_main_title.text = getString(R.string.main_menu_umms_info)
            5 -> toolbar_main_title.text = getString(R.string.main_menu_logs)
        }
        val colorNotSelected = getColorFromAttr(R.attr.navMenuTextColor)
        val colorSelected = getColorFromAttr(R.attr.navMenuTextColorSelected)
        val size = nv_main.menu.size()
        var i = 0;
        do {
            val menuItem = nv_main.menu.getItem(i)
            val spannableString = SpannableString(menuItem.title.toString())
            if (i == menuID) spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, colorSelected)), 0, spannableString.length, 0)
            else spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, colorNotSelected)), 0, spannableString.length, 0)
            menuItem.title = spannableString
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
