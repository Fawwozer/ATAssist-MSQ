package by.fawwozer.atassist

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import by.fawwozer.atassist.GlobalApp.Companion.PREFERENCE_FILE
import by.fawwozer.atassist.GlobalApp.Companion.PREFERENCE_LAST_BACKUP_TIME
import by.fawwozer.atassist.GlobalApp.Companion.PREFERENCE_LAST_RUN_VERSION
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_BACKUP_ALLOW
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_BACKUP_CLOUD
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_BACKUP_LOCAL
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_BACKUP_TIME
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_GENERAL_APPLICATION_THEME
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_GENERAL_DELETE_LOGS
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_GENERAL_SCHEDULE_CLEAR_TIME
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_MAINTENANCE_HIDE_DEICE
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_MAINTENANCE_KG_ROUND
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_MAINTENANCE_LITRES_ROUND
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_NOTIFICATION_ALLOW
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_NOTIFICATION_FLEET
import by.fawwozer.atassist.GlobalApp.Companion.SETTING_NOTIFICATION_SCHEDULE
import kotlinx.android.synthetic.main.view_splash_screen.*

class SplashScreen : AppCompatActivity() {

    lateinit var preference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        //применение настроек темы из SharedPreference

        preference = getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
        if (preference.contains(SETTING_GENERAL_APPLICATION_THEME)) {
            when (preference.getInt(SETTING_GENERAL_APPLICATION_THEME, 0)) {
                0 -> setTheme(R.style.AppTheme_FullScreen_Light)
                1 -> setTheme(R.style.AppTheme_FullScreen_Dark)
            }
        } else {
            setTheme(R.style.AppTheme_FullScreen_Light)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_splash_screen)
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        ///проверка версии android, если <25 загружается im_spinner в iv_splash_spinner
        ///если >25, то загружается im_spinner_animated

        if (Build.VERSION.SDK_INT < 25) iv_splash_spinner.setImageResource(R.drawable.im_spinner)
        else iv_splash_spinner.setImageResource(R.drawable.im_spinner_animated)

        //проверка на AnimatedVectorDrawable и запуск анимации

        val spinner = iv_splash_spinner.drawable
        if (spinner is AnimatedVectorDrawable) {
            spinner.start()
        }

        //запуск фонового процесса подготовки приложения

        val run = Runnable {
            when (preference.getString(PREFERENCE_LAST_RUN_VERSION,"0")) {
                R.string.app_code.toString() -> { }
                else -> {
                    ///запросы разрешений

                    ///запись начальных настроек приложения

                    val editor = preference.edit()

                    editor.putInt(SETTING_GENERAL_APPLICATION_THEME,0)
                    editor.putInt(SETTING_MAINTENANCE_LITRES_ROUND,0)
                    editor.putInt(SETTING_BACKUP_TIME,0)

                    editor.putLong(SETTING_GENERAL_SCHEDULE_CLEAR_TIME,0)

                    editor.putBoolean(SETTING_GENERAL_DELETE_LOGS,false)
                    editor.putBoolean(SETTING_MAINTENANCE_KG_ROUND,false)
                    editor.putBoolean(SETTING_MAINTENANCE_HIDE_DEICE,false)
                    editor.putBoolean(SETTING_BACKUP_ALLOW,false)
                    editor.putBoolean(SETTING_BACKUP_LOCAL,false)
                    editor.putBoolean(SETTING_BACKUP_CLOUD,false)
                    editor.putBoolean(SETTING_NOTIFICATION_ALLOW,false)
                    editor.putBoolean(SETTING_NOTIFICATION_SCHEDULE,false)
                    editor.putBoolean(SETTING_NOTIFICATION_FLEET,false)

                    editor.putString(PREFERENCE_LAST_RUN_VERSION, R.string.app_code.toString())
                    editor.putLong(PREFERENCE_LAST_BACKUP_TIME,0)

                    editor.apply()
                }
            }
        }
        run.run()
    }
}
