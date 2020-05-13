package by.fawwozer.atassist

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceActivity
import android.util.Log
import androidx.preference.PreferenceFragment

class Settings: PreferenceActivity() {
	
	var fragments = emptyArray<String>()
	override fun onBuildHeaders(target: MutableList<Header>?) {
		val preference = getSharedPreferences(Global.PREFERENCE_FILE, Context.MODE_PRIVATE)
		Log.d("MY", "Theme: " + preference.getString(Global.SETTING_GENERAL_APPLICATION_THEME, "10"))
		if (preference.contains(Global.SETTING_GENERAL_APPLICATION_THEME)) {
			when (preference.getString(Global.SETTING_GENERAL_APPLICATION_THEME, "0")) {
				"0" -> setTheme(R.style.AppTheme_Light)
				"1" -> setTheme(R.style.AppTheme_Dark)
			}
		} else {
			setTheme(R.style.AppTheme_Dark)
		}
		loadHeadersFromResource(R.xml.settings_headers, target)
		for (header in target!!) {
			fragments += header.fragment
		}
	}
	
	override fun isValidFragment(fragmentName: String?): Boolean {
		return fragments.contains(fragmentName)
	}
	
	class GeneralSettingsFragment: PreferenceFragment() {
		override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
			setPreferencesFromResource(R.xml.preference_general, rootKey)
		}
	}
	
	class MaintenanceSettingsFragment: PreferenceFragment() {
		override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
			setPreferencesFromResource(R.xml.preference_maintenance, rootKey)
		}
	}
}
