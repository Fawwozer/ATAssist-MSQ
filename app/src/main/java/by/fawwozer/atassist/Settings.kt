package by.fawwozer.atassist

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Settings : AppCompatActivity() {

    lateinit var preference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {preference = getSharedPreferences(Global.PREFERENCE_FILE, Context.MODE_PRIVATE)
        if (preference.contains(Global.SETTING_GENERAL_APPLICATION_THEME)) {
            when (preference.getInt(Global.SETTING_GENERAL_APPLICATION_THEME, 0)) {
                0 -> setTheme(R.style.AppTheme_FullScreen_Light)
                1 -> setTheme(R.style.AppTheme_FullScreen_Dark)
            }
        } else {
            setTheme(R.style.AppTheme_Light)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_settings)


    }
}
