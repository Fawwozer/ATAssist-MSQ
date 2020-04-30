package by.fawwozer.atassist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import by.fawwozer.atassist.Global.Companion.PREFERENCE_FILE
import by.fawwozer.atassist.Global.Companion.PREFERENCE_LAST_BACKUP_TIME
import by.fawwozer.atassist.Global.Companion.PREFERENCE_LAST_RUN_VERSION
import by.fawwozer.atassist.Global.Companion.SETTING_BACKUP_CLOUD
import by.fawwozer.atassist.Global.Companion.SETTING_BACKUP_LOCAL
import by.fawwozer.atassist.Global.Companion.SETTING_BACKUP_TIME
import by.fawwozer.atassist.Global.Companion.SETTING_GENERAL_APPLICATION_THEME
import by.fawwozer.atassist.Global.Companion.SETTING_GENERAL_DELETE_LOGS
import by.fawwozer.atassist.Global.Companion.SETTING_GENERAL_SCHEDULE_CLEAR_TIME
import by.fawwozer.atassist.Global.Companion.SETTING_MAINTENANCE_HIDE_DEICE
import by.fawwozer.atassist.Global.Companion.SETTING_MAINTENANCE_KG_ROUND
import by.fawwozer.atassist.Global.Companion.SETTING_MAINTENANCE_LITERS_ROUND
import by.fawwozer.atassist.Global.Companion.SETTING_NOTIFICATION_ALLOW
import by.fawwozer.atassist.Global.Companion.SETTING_NOTIFICATION_FLEET
import by.fawwozer.atassist.Global.Companion.SETTING_NOTIFICATION_SCHEDULE
import dagger.BindsInstance
import kotlinx.android.synthetic.main.view_splash_screen.*
import java.util.concurrent.TimeUnit

class SplashScreen : AppCompatActivity() {

    lateinit var preference: SharedPreferences

    companion object{
        lateinit var instance: SplashScreen
    }

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MY", "SplashScreen/onCreate/Start")

        //применение настроек темы из SharedPreference

        preference = getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
        if (preference.contains(SETTING_GENERAL_APPLICATION_THEME)) {
            Log.d("MY", "SplashScreen/onCreate/preference has theme")
            when (preference.getInt(SETTING_GENERAL_APPLICATION_THEME, 0)) {
                0 -> setTheme(R.style.AppTheme_FullScreen_Light)
                1 -> setTheme(R.style.AppTheme_FullScreen_Dark)
            }
        } else {
            Log.d("MY", "SplashScreen/onCreate/preference hasn`t theme")
            setTheme(R.style.AppTheme_FullScreen_Light)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_splash_screen)
        window.decorView.apply {systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN}

        ///проверка версии android, если <25 загружается im_spinner в iv_splash_spinner
        ///если >25, то загружается im_spinner_animated

        if (Build.VERSION.SDK_INT < 25) {
            iv_splash_spinner.setImageResource(R.drawable.im_spinner)
            Log.d("MY", "SplashScreen/onCreate/SDK < 25")
        }
        else {
            iv_splash_spinner.setImageResource(R.drawable.im_spinner_animated)
            Log.d("MY", "SplashScreen/onCreate/SDK > 24")
        }

        //проверка на AnimatedVectorDrawable и запуск анимации

        val spinner = iv_splash_spinner.drawable
        if (spinner is AnimatedVectorDrawable) {
            Log.d("MY", "SplashScreen/onCreate/start spinner animation")
            spinner.start()
        }

        //запуск фонового процесса подготовки приложения

        val async = Async()
        async.execute()
        Log.d("MY", "SplashScreen/onCreate/Finish")
    }

    private class Async: AsyncTask<Void, Void, Void>() {
        lateinit var context: Context
        override fun doInBackground(vararg params: Void?): Void? {
            Log.d("MY", "SplashScreen/Async/doInBackground/Start")

            //проверка на версию приложенияб если нет указанной версии
            //то считается что это первый запуск приложения и работает
            //инициализация настроек и запрос разрешений

            val preference: SharedPreferences
            preference = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
            when (preference.getString(PREFERENCE_LAST_RUN_VERSION,"0")) {
                R.string.app_code.toString() -> {
                    Log.d("MY", "SplashScreen/Async/doInBackground/LAST_RUN_VERSION = 19")}
                else -> {
                    Log.d("MY", "SplashScreen/Async/doInBackground/LAST_RUN_VERSION else")
                    ///запросы разрешений

                    ///запись начальных настроек приложения

                    val editor = preference.edit()

                    editor.putInt(SETTING_GENERAL_APPLICATION_THEME,0)
                    editor.putInt(SETTING_MAINTENANCE_LITERS_ROUND,0)
                    editor.putInt(SETTING_BACKUP_TIME,0)

                    editor.putLong(SETTING_GENERAL_SCHEDULE_CLEAR_TIME,0)

                    editor.putBoolean(SETTING_GENERAL_DELETE_LOGS,false)
                    editor.putBoolean(SETTING_MAINTENANCE_KG_ROUND,false)
                    editor.putBoolean(SETTING_MAINTENANCE_HIDE_DEICE,false)
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
            //TimeUnit.SECONDS.sleep(10)
            Log.d("MY", "SplashScreen/Async/doInBackground/Finish")
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Log.d("MY", "SplashScreen/Async/onPostExecute")
            val intent = Intent(context, ATAssist::class.java).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
            instance.startActivity(intent)
            instance.finish()

        }

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("MY", "SplashScreen/Async/onPreExecute")
            context = Global.appContext
        }
    }
}
