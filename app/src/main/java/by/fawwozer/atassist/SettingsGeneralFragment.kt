package by.fawwozer.atassist

import android.app.AlertDialog
import android.content.Context
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
import by.fawwozer.atassist.Global.Companion.SETTING_GENERAL_APPLICATION_THEME
import by.fawwozer.atassist.Global.Companion.SETTING_GENERAL_DELETE_LOGS
import by.fawwozer.atassist.Global.Companion.SETTING_GENERAL_SCHEDULE_CLEAR_TIME
import by.fawwozer.atassist.Global.Companion.THEME_BELAVIA
import by.fawwozer.atassist.Global.Companion.THEME_DARK
import by.fawwozer.atassist.Global.Companion.THEME_LIGHT

class SettingsGeneralFragment: Fragment() {
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_settings_general,null)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		initPreference()
		
		//слушатели для пунктов настроек
		//смена темы через диалог
		val changeTheme = view.findViewById<RelativeLayout>(R.id.rl_general_theme)
		changeTheme!!.setOnClickListener{
			val context = Global.appContext
			val preference = context.getSharedPreferences(Global.PREFERENCE_FILE, Context.MODE_PRIVATE)
			val editor = preference.edit()
			val builder = AlertDialog.Builder(getContext());
			builder.setTitle("Choose theme")
				.setItems(R.array.settings_theme_array, DialogInterface.OnClickListener {dialogInterface, i ->
					when (i) {
						THEME_LIGHT -> {
							editor.putInt(SETTING_GENERAL_APPLICATION_THEME, THEME_LIGHT)
						}
						THEME_DARK -> {
							editor.putInt(SETTING_GENERAL_APPLICATION_THEME, THEME_DARK)
						}
						THEME_BELAVIA -> {
							editor.putInt(SETTING_GENERAL_APPLICATION_THEME, THEME_BELAVIA)
						}
					}
					editor.apply()
					dialogInterface.cancel()
					//перезапуск приложения
					val intent = activity?.baseContext?.packageManager?.getLaunchIntentForPackage(requireActivity().baseContext.packageName)
					intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
					startActivity(intent)
				})
				.setCancelable(true)
				.create()
				.show()
		}
		
		//ввод через диалог количества часов до удаления записи с экрана schedule
		val scheduleTime = view.findViewById<RelativeLayout>(R.id.rl_general_schedule_time)
		scheduleTime!!.setOnClickListener {
			val context = Global.appContext
			val preference = context.getSharedPreferences(Global.PREFERENCE_FILE, Context.MODE_PRIVATE)
			val editor = preference.edit()
			val v = LayoutInflater.from(context).inflate(R.layout.dialog_settings_input,null)
			val edit = v.findViewById<EditText>(R.id.et_dialog_input)
			edit.setText(preference.getInt(SETTING_GENERAL_SCHEDULE_CLEAR_TIME,14).toString())
			val builder = AlertDialog.Builder(getContext())
			builder
				.setView(v)
				.setPositiveButton("Ok", DialogInterface.OnClickListener {dialogInterface, _ ->
					if (edit.text != null) {
						editor.putInt(SETTING_GENERAL_SCHEDULE_CLEAR_TIME, edit.text.toString().toInt())
						editor.apply()
						dialogInterface.cancel()
						initPreference()
					} else return@OnClickListener
				})
				.setCancelable(true)
				.create()
				.show()
		}
		
		//запрет/разрешение на удаление логов
		val deleteLog = view.findViewById<RelativeLayout>(R.id.rl_general_delete_log)
		deleteLog!!.setOnClickListener{
			val context = Global.appContext
			val preference = context.getSharedPreferences(Global.PREFERENCE_FILE, Context.MODE_PRIVATE)
			val editor = preference.edit()
			editor.putBoolean(SETTING_GENERAL_DELETE_LOGS,!preference.getBoolean(SETTING_GENERAL_DELETE_LOGS,false))
			editor.apply()
			initPreference()
		}
		
		super.onViewCreated(view, savedInstanceState)
	}
	
	
	//инициализация настроек с отображением на экране
	private fun initPreference() {
		val context = Global.appContext
		val preference = context.getSharedPreferences(Global.PREFERENCE_FILE, Context.MODE_PRIVATE)
		
		when (preference.getInt(SETTING_GENERAL_APPLICATION_THEME, THEME_LIGHT)) {
			THEME_LIGHT -> {view?.findViewById<TextView>(R.id.tv_general_theme_summary)?.text = "Light"}
			THEME_DARK -> {view?.findViewById<TextView>(R.id.tv_general_theme_summary)?.text = "Dark"}
			THEME_BELAVIA -> {view?.findViewById<TextView>(R.id.tv_general_theme_summary)?.text = "Belavia"}
		}
		
		val t = preference.getInt(SETTING_GENERAL_SCHEDULE_CLEAR_TIME, 14)
		when (t) {
			0 -> view?.findViewById<TextView>(R.id.tv_general_schedule_time_summary)?.text = R.string.settings_general_schedule_time_summary_off.toString()
			1 -> view?.findViewById<TextView>(R.id.tv_general_schedule_time_summary)?.text = t.toString() + " hour"
			else -> view?.findViewById<TextView>(R.id.tv_general_schedule_time_summary)?.text = t.toString() + " hours"
		}
		
		if (preference.getBoolean(SETTING_GENERAL_DELETE_LOGS, false)) {
			view?.findViewById<ImageView>(R.id.iv_general_delete_log_icon)?.setImageResource(R.drawable.ic_toggle_on)
			view?.findViewById<TextView>(R.id.tv_general_delete_log_summary)?.text = resources.getString(R.string.settings_general_delete_logs_summary_on)
		}
		else{
			view?.findViewById<ImageView>(R.id.iv_general_delete_log_icon)?.setImageResource(R.drawable.ic_toggle_off)
			view?.findViewById<TextView>(R.id.tv_general_delete_log_summary)?.text = resources.getString(R.string.settings_general_delete_logs_summary_off)
		}
	}
}