package by.fawwozer.atassist

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.fawwozer.atassist.Global.Companion.MAIN_FRAGMENT_FLEET
import by.fawwozer.atassist.Global.Companion.MAIN_FRAGMENT_KOBRA
import by.fawwozer.atassist.Global.Companion.MAIN_FRAGMENT_SCHEDULE
import by.fawwozer.atassist.Global.Companion.THEME_BELAVIA
import by.fawwozer.atassist.Global.Companion.THEME_DARK
import by.fawwozer.atassist.Global.Companion.THEME_LIGHT
import by.fawwozer.atassist.Global.Companion.appContext
import by.fawwozer.atassist.Global.Companion.appTheme
import by.fawwozer.atassist.Global.Companion.deleteLog
import by.fawwozer.atassist.Global.Companion.scheduleTime
import by.fawwozer.atassist.Global.Companion.startupScreen

class SettingsGeneralFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings_general, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPreference()

        //слушатели для пунктов настроек
        //смена темы через диалог
        val changeTheme = view.findViewById<RelativeLayout>(R.id.rl_general_theme)
        changeTheme!!.setOnClickListener {
            val builder = AlertDialog.Builder(context);
            builder.setTitle("Choose theme")
                .setItems(R.array.settings_theme_array) { dialogInterface, i ->
                    appTheme = i
                    dialogInterface.cancel()
                    //перезапуск приложения
                    val intent = activity?.baseContext?.packageManager?.getLaunchIntentForPackage(
                        requireActivity().baseContext.packageName
                    )
                    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    if (intent != null) {
                        startActivity(intent)
                    }
                }.setCancelable(true).create().show()
        }

        val changeScreen = view.findViewById<RelativeLayout>(R.id.rl_general_screen)
        changeScreen!!.setOnClickListener {
            val builder = AlertDialog.Builder(context);
            builder.setTitle("Choose startup screen")
                .setItems(R.array.settings_screen_array) { dialogInterface, i ->
                    startupScreen = i
                    dialogInterface.cancel()
                    initPreference()
                }.setCancelable(true).create().show()
        }

        //ввод через диалог количества часов до удаления записи с экрана schedule
        val changeScheduleTime = view.findViewById<RelativeLayout>(R.id.rl_general_schedule_time)
        changeScheduleTime!!.setOnClickListener {
            val v = LayoutInflater.from(context).inflate(R.layout.dialog_settings_input, null)
            val edit = v.findViewById<EditText>(R.id.et_dialog_input)
            edit.setText(scheduleTime.toString())
            val builder = AlertDialog.Builder(getContext())
            builder.setView(v)
                .setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, _ ->
                    if (edit.text != null) {
                        scheduleTime = edit.text.toString().toInt()
                        dialogInterface.cancel()
                        initPreference()
                    }
                    else return@OnClickListener
                }).setCancelable(true).create().show()
        }

        //запрет/разрешение на удаление логов
        val changeDeleteLog = view.findViewById<RelativeLayout>(R.id.rl_general_delete_log)
        changeDeleteLog!!.setOnClickListener {
            deleteLog = !deleteLog
            initPreference()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    //инициализация настроек с отображением на экране
    private fun initPreference() {

        when (appTheme) {
            THEME_LIGHT -> {
                view?.findViewById<TextView>(R.id.tv_general_theme_summary)?.text = "Light"
            }

            THEME_DARK -> {
                view?.findViewById<TextView>(R.id.tv_general_theme_summary)?.text = "Dark"
            }

            THEME_BELAVIA -> {
                view?.findViewById<TextView>(R.id.tv_general_theme_summary)?.text = "Belavia"
            }
        }

        when (startupScreen) {
            MAIN_FRAGMENT_SCHEDULE -> {
                view?.findViewById<TextView>(R.id.tv_general_screen_summary)?.text = getString(R.string.main_menu_schedule)
            }

            MAIN_FRAGMENT_FLEET -> {
                view?.findViewById<TextView>(R.id.tv_general_screen_summary)?.text = getString(R.string.main_menu_fleet_info)
            }

            MAIN_FRAGMENT_KOBRA -> {
                view?.findViewById<TextView>(R.id.tv_general_screen_summary)?.text = getString(R.string.main_menu_umms_kobra)
            }
        }

        when (scheduleTime) {
            0 -> view?.findViewById<TextView>(R.id.tv_general_schedule_time_summary)?.text =
                R.string.settings_general_schedule_time_summary_off.toString()
            1 -> view?.findViewById<TextView>(R.id.tv_general_schedule_time_summary)?.setText(scheduleTime.toString() + " hour")

            else -> view?.findViewById<TextView>(R.id.tv_general_schedule_time_summary)?.text =
                scheduleTime.toString() + " hours"
        }

        if (deleteLog) {
            view?.findViewById<ImageView>(R.id.iv_general_delete_log_icon)
                ?.setImageResource(R.drawable.ic_toggle_on)
            view?.findViewById<TextView>(R.id.tv_general_delete_log_summary)?.text =
                resources.getString(R.string.settings_general_delete_logs_summary_on)
        }
        else {
            view?.findViewById<ImageView>(R.id.iv_general_delete_log_icon)
                ?.setImageResource(R.drawable.ic_toggle_off)
            view?.findViewById<TextView>(R.id.tv_general_delete_log_summary)?.text =
                resources.getString(R.string.settings_general_delete_logs_summary_off)
        }
    }
}