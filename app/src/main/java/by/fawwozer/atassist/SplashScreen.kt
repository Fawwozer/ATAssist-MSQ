package by.fawwozer.atassist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.fawwozer.atassist.Global.Companion.PREFERENCE_FILE
import by.fawwozer.atassist.Global.Companion.PREFERENCE_LAST_BACKUP_TIME
import by.fawwozer.atassist.Global.Companion.PREFERENCE_LAST_RUN_VERSION
import by.fawwozer.atassist.Global.Companion.SETTING_BACKUP_CLOUD
import by.fawwozer.atassist.Global.Companion.SETTING_BACKUP_LOCAL
import by.fawwozer.atassist.Global.Companion.SETTING_BACKUP_TIME
import by.fawwozer.atassist.Global.Companion.SETTING_GENERAL_APPLICATION_THEME
import by.fawwozer.atassist.Global.Companion.SETTING_GENERAL_DELETE_LOGS
import by.fawwozer.atassist.Global.Companion.SETTING_GENERAL_SCHEDULE_CLEAR_TIME
import by.fawwozer.atassist.Global.Companion.SETTING_KOBRA_LOGIN
import by.fawwozer.atassist.Global.Companion.SETTING_KOBRA_PASSWORD
import by.fawwozer.atassist.Global.Companion.SETTING_MAINTENANCE_HIDE_DEICE
import by.fawwozer.atassist.Global.Companion.SETTING_MAINTENANCE_KG_ROUND
import by.fawwozer.atassist.Global.Companion.SETTING_MAINTENANCE_LITERS_ROUND
import by.fawwozer.atassist.Global.Companion.SETTING_NOTIFICATION_ALLOW
import by.fawwozer.atassist.Global.Companion.SETTING_NOTIFICATION_FLEET
import by.fawwozer.atassist.Global.Companion.SETTING_NOTIFICATION_SCHEDULE
import by.fawwozer.atassist.Global.Companion.THEME_BELAVIA
import by.fawwozer.atassist.Global.Companion.THEME_DARK
import by.fawwozer.atassist.Global.Companion.THEME_LIGHT
import by.fawwozer.atassist.Global.Companion.appTheme
import by.fawwozer.atassist.Global.Companion.kobraLastUpdate
import by.fawwozer.atassist.Global.Companion.scheduleTime
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {

    lateinit var preference: SharedPreferences

    companion object {
        lateinit var instance: SplashScreen
    }

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        //применение настроек темы

        when (appTheme) {
            THEME_LIGHT -> setTheme(R.style.AppTheme_FullScreen_Light)
            THEME_DARK -> setTheme(R.style.AppTheme_FullScreen_Dark)
            THEME_BELAVIA -> setTheme(R.style.AppTheme_FullScreen_Belavia)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_splash_screen)

        MainScope().launch {

            //запуск анимаций на экране

            findViewById<ImageView>(R.id.iv_splash_spinner_outer).startAnimation(
                AnimationUtils.loadAnimation(
                    Global.appContext,
                    R.anim.spinner_rotate_cw
                )
            )
            findViewById<ImageView>(R.id.iv_splash_spinner_inner).startAnimation(
                AnimationUtils.loadAnimation(
                    Global.appContext,
                    R.anim.spinner_rotate_ccw
                )
            )

            //проверка входа
            if (Firebase.auth.currentUser == null) Firebase.auth.signInAnonymously().addOnCompleteListener { task->
                if (task.isSuccessful) Log.d("MY","Login complete")
                else Log.d("MY","Login failed")
            }

            //обновление базы аэропортов
            AirportDB.loadFromFirestore()

            //проверка на версию приложения, если нет указанной версии
            //то считается что это первый запуск приложения

            val preference =
                Global.appContext.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
            when (preference.getInt(PREFERENCE_LAST_RUN_VERSION, 0)) {
                R.string.app_code -> {
                }

                else -> {                    ///запросы разрешений

                }
            }

            //обновление кобры

            if (System.currentTimeMillis() - kobraLastUpdate > scheduleTime * 60 * 60 * 1000) {
                try {
                    withContext(Dispatchers.IO) { GetKobraSPP.get() }
                    kobraLastUpdate = System.currentTimeMillis()
                } catch (error: Exception) {
                    Log.d("MY", "Coroutine failed: $error")
                    Toast.makeText(Global.appContext, "Kobra update fail", Toast.LENGTH_SHORT)
                        .show()
                    kobraLastUpdate = 0
                }
            }

            //запуск основного окна

            val intent = Intent(
                Global.appContext,
                ATAssist::class.java
            ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
            instance.startActivity(intent)
            instance.finish()
        }
    }
}
