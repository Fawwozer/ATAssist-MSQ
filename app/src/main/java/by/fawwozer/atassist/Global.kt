package by.fawwozer.atassist

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Global : Application() {

    override fun onCreate() {
        super.onCreate()

        ///инициализация Firebase Analytics

        FirebaseApp.initializeApp(this)

        ///создание баз данных

        val logsDB = LogsDB()
        val checkDB = CheckDB()
        val planeDB = PlaneDB()
        val fleetDB = FleetDB()

        val dblogs = logsDB.writableDatabase
        val dbcheck = checkDB.writableDatabase
        val dbplane = planeDB.writableDatabase
        val dbfleet = fleetDB.writableDatabase
        dblogs.close()
        dbcheck.close()
        dbplane.close()
        dbfleet.close()

        //загрузка из FireStore

        CheckDB.loadFromFireStore()
        PlaneDB.loadFromFireStore()
        FleetDB.loadFromFireStore()
    }

    companion object {

        ////GENERAL\\\\

        const val SCHEDULE_NOTIFICATION_CHANNEL_NAME = "Schedule Notification"
        const val SCHEDULE_NOTIFICATION_CHANNEL_ID = "scheduleNotification"
        const val SCHEDULE_NOTIFICATION_CHANNEL_CODE = 13

        const val FLEET_NOTIFICATION_CHANNEL_NAME = "Fleet Notification"
        const val FLEET_NOTIFICATION_CHANNEL_ID = "fleetNotification"
        const val FLEET_NOTIFICATION_CHANNEL_CODE = 666

        const val MAIN_TEXT_SPLITTER = ",|,"
        const val SECOND_TEXT_SPLITTER = ".|."

        const val BOEING_737_CL = 0
        const val BOEING_737_NG = 1
        const val BOEING_737_MX = 2
        const val EMBRAER_170 = 5
        const val EMBRAER_190 = 6
        const val EMBRAER_290 = 7

        const val CHECK_AA = 0
        const val CHECK_TR = 1
        const val CHECK_BD = 2

        const val TIME_ZONE_UTC = "UTC"

        const val TIME_PICKER_INTERVAL = 5

        ////MAIN ACTIVITY\\\\

        const val MAIN_ITEM_SCHEDULE = R.id.mi_schedule
        const val MAIN_ITEM_FLEET = R.id.mi_fleet
        const val MAIN_ITEM_KOBRA = R.id.mi_kobra
        const val MAIN_ITEM_SETTINGS = R.id.mi_settings

        const val MAIN_FRAGMENT_SCHEDULE = R.id.fragment_schedule
        const val MAIN_FRAGMENT_FLEET = R.id.fragment_fleet_info
        const val MAIN_FRAGMENT_KOBRA = R.id.fragment_kobra
        const val MAIN_FRAGMENT_SETTINGS = R.id.fragment_settings

        ////SETTINGS ACTIVITY\\\\

        const val SETTINGS_ITEM_GENERAL = R.id.rl_settings_general
        const val SETTINGS_ITEM_MAINTENANCE = R.id.rl_settings_maintenance
        const val SETTINGS_ITEM_BACKUP = R.id.rl_settings_backup
        const val SETTINGS_ITEM_NOTIFICATION = R.id.rl_settings_notification
        const val SETTINGS_ITEM_LOGS = R.id.rl_settings_logs
        const val SETTINGS_ITEM_ABOUT = R.id.rl_settings_about

        const val SETTINGS_SCREEN_GENERAL = R.id.fragment_settings_general
        const val SETTINGS_SCREEN_MAINTENANCE = R.id.fragment_settings_maintenance
        const val SETTINGS_SCREEN_BACKUP = R.id.fragment_settings_backup
        const val SETTINGS_SCREEN_NOTIFICATION = R.id.fragment_settings_notification
        const val SETTINGS_SCREEN_LOGS = R.id.fragment_settings_logs
        const val SETTINGS_SCREEN_ABOUT = R.id.fragment_settings_about

        ////OTHER SCREENS\\\
        const val OTHER_SCREEN_FUEL_CALCULATOR = R.id.fragment_other_fuel_calculator
        const val OTHER_SCREEN_MSQ_INFO = R.id.fragment_other_msq_info
        const val OTHER_ITEM_FUEL_CALCULATOR = R.id.toolbar_main_calculator
        const val OTHER_ITEM_MSQ_INFO = R.id.toolbar_main_jeppesen
        const val OTHER_KOBRA_RELOAD = R.id.toolbar_main_reload

        ////PREFERENCES\\\\

        const val PREFERENCE_FILE = "by.fawwozer.atassist_preferences"
        const val PREFERENCE_LAST_RUN_VERSION = "last_run"
        const val PREFERENCE_LAST_BACKUP_TIME = "last_backup"
        const val PREFERENCE_LOGIN_TOKEN = "login_token"

        ////SETTINGS\\\\

        const val SETTING_GENERAL_APPLICATION_THEME = "theme"
        const val SETTING_GENERAL_SCHEDULE_CLEAR_TIME = "schedule_time"
        const val SETTING_GENERAL_DELETE_LOGS = "delete_logs"
        const val SETTING_GENERAL_FIRST_SCREEN = "default_screen"

        const val SETTING_NOTIFICATION_ALLOW = "allow_notification"
        const val SETTING_NOTIFICATION_SCHEDULE = "schedule_notification"
        const val SETTING_NOTIFICATION_FLEET = "fleet_notification"

        const val SETTING_MAINTENANCE_LITERS_ROUND = "liters_round"
        const val SETTING_MAINTENANCE_KG_ROUND = "kg_round"
        const val SETTING_MAINTENANCE_HIDE_DEICE = "hide_deice"

        const val SETTING_BACKUP_LOCAL = "local_backup"
        const val SETTING_BACKUP_TIME = "time_backup"
        const val SETTING_BACKUP_CLOUD = "cloud_backup"

        const val SETTING_KOBRA_LOGIN = "kobra_login"
        const val SETTING_KOBRA_PASSWORD = "kobra_password"

        ////FIRESTORE\\\\

        const val FIRESTORE_COLLECTION_FLEET = "FleetBase"
        const val FIRESTORE_COLLECTION_PLANES = "PlanesBase"
        const val FIRESTORE_COLLECTION_CHECKS = "ChecksBase"

        const val STATE_DONE = 0
        const val STATE_INFO = 5
        const val STATE_DMI_A = 10
        const val STATE_DMI_B = 11
        const val STATE_DMI_C = 12
        const val STATE_DMI_D = 13
        const val STATE_DDA = 20
        const val STATE_INOP = 25

        ////Logs DB\\\\

        const val LOGS_DB_NAME = "LogsDB.db"
        const val LOGS_DB_VERSION = 1
        const val LOGS_DB_TABLE = "_LG"

        const val KEY_LOGS_ID = "_ID"
        const val KEY_LOGS_SORT = "_SORT"
        const val KEY_LOGS_FLIGHT_NUMBER = "_FL_NUM"
        const val KEY_LOGS_FLIGHT_DESTINATION = "_FL_DEST"
        const val KEY_LOGS_ARRIVAL_TIME = "_ARR_TIME"
        const val KEY_LOGS_DEPARTURE_TIME = "_DEP_TIME"
        const val KEY_LOGS_STAND = "_STAND"
        const val KEY_LOGS_IS_DONE = "_DONE"
        const val KEY_LOGS_PLANE = "_PLANE_ID"
        const val KEY_LOGS_CHECK = "_CHECK_ID"
        const val KEY_LOGS_BELAVIA = "_BELAVIA"

        const val KEY_LOGS_AFML_2 = "_AFML_2"
        const val KEY_LOGS_AFML_2_HYDRAULIC_ADD_SYSTEM_1 = "_AFML_2_HYDR_1"
        const val KEY_LOGS_AFML_2_HYDRAULIC_ADD_SYSTEM_2 = "_AFML_2_HYDR_2"
        const val KEY_LOGS_AFML_2_HYDRAULIC_ADD_SYSTEM_3 = "_AFML_2_HYDR_3"

        const val KEY_LOGS_AFML_3 = "_AFML_3"
        const val KEY_LOGS_AFML_3_KG_REMAIN = "_AFML_3_KG_REM"
        const val KEY_LOGS_AFML_3_KG_ADD = "_AFML_3_KG_ADD"
        const val KEY_LOGS_AFML_3_KG_TOTAL = "_AFML_3_KG_TOT"
        const val KEY_LOGS_AFML_3_KG_ADJUST = "_AFML_3_KG_ADJ"
        const val KEY_LOGS_AFML_3_KG_DEPART = "_AFML_3_KG_DEP"
        const val KEY_LOGS_AFML_3_LB_REMAIN = "_AFML_3_LB_REM"
        const val KEY_LOGS_AFML_3_LB_ADD = "_AFML_3_LB_ADD"
        const val KEY_LOGS_AFML_3_LB_TOTAL = "_AFML_3_LB_TOT"
        const val KEY_LOGS_AFML_3_LB_ADJUST = "_AFML_3_LB_ADJ"
        const val KEY_LOGS_AFML_3_LB_DEPART = "_AFML_3_LB_DEP"
        const val KEY_LOGS_AFML_3_INFO_TEMPERATURE = "_AFML_3_I_TEM"
        const val KEY_LOGS_AFML_3_INFO_DENSITY = "_AFML_3_I_DENS"
        const val KEY_LOGS_AFML_3_INFO_FUEL_CARD = "_AFML_3_I_FCA"
        const val KEY_LOGS_AFML_3_INFO_REFUELER = "_AFML_3_I_REF"
        const val KEY_LOGS_AFML_3_INFO_FUEL_ORDER = "_AFML_3_I_FOR"

        const val KEY_LOGS_AFML_4 = "_AFML_4"
        const val KEY_LOGS_AFML_4_FUEL = "_AFML_4_FUEL"
        const val KEY_LOGS_AFML_4_NOTES = "_AFML_4_NOTE"
        const val KEY_LOGS_AFML_4_LIFE_VEST_SEAT = "_AFML_4_LVSEAT"
        const val KEY_LOGS_AFML_4_LIFE_VEST_SPARE = "_AFML_4_LVSPARE"
        const val KEY_LOGS_AFML_4_SEAT_BELTS = "_AFML_4_BELT"
        const val KEY_LOGS_AFML_4_DOCUMENTS = "_AFML_4_DOC"
        const val KEY_LOGS_AFML_4_OTHER = "_AFML_4_OTHER"

        const val KEY_LOGS_AFML_5 = "_AFML_5"
        const val KEY_LOGS_AFML_5_ENGINE_1_REMAIN = "_AFML_5_E1_REM"
        const val KEY_LOGS_AFML_5_ENGINE_1_ADD = "_AFML_5_E1_ADD"
        const val KEY_LOGS_AFML_5_ENGINE_2_REMAIN = "_AFML_5_E2_REM"
        const val KEY_LOGS_AFML_5_ENGINE_2_ADD = "_AFML_5_E2_ADD"

        const val KEY_LOGS_AFML_6 = "_AFML_6"
        const val KEY_LOGS_AFML_6_N1_REMAIN = "_AFML_6_N1_REM"
        const val KEY_LOGS_AFML_6_N2_REMAIN = "_AFML_6_N2_REM"
        const val KEY_LOGS_AFML_6_M1_REMAIN = "_AFML_6_M1_REM"
        const val KEY_LOGS_AFML_6_M2_REMAIN = "_AFML_6_M2_REM"
        const val KEY_LOGS_AFML_6_M3_REMAIN = "_AFML_6_M3_REM"
        const val KEY_LOGS_AFML_6_M4_REMAIN = "_AFML_6_M4_REM"
        const val KEY_LOGS_AFML_6_N1_ADD = "_AFML_6_N1_ADD"
        const val KEY_LOGS_AFML_6_N2_ADD = "_AFML_6_N2_ADD"
        const val KEY_LOGS_AFML_6_M1_ADD = "_AFML_6_M1_ADD"
        const val KEY_LOGS_AFML_6_M2_ADD = "_AFML_6_M2_ADD"
        const val KEY_LOGS_AFML_6_M3_ADD = "_AFML_6_M3_ADD"
        const val KEY_LOGS_AFML_6_M4_ADD = "_AFML_6_M4_ADD"

        const val KEY_LOGS_AFML_10 = "_AFML_10"

        const val KEY_LOGS_AFML_11 = "_AFML_11"
        const val KEY_LOGS_AFML_11_TYPE = "_AFML_11_TYPE"
        const val KEY_LOGS_AFML_11_CONCENTRATION = "_AFML_11_CONC"

        const val KEY_LOGS_COMMERCIAL_BAGGAGE_START = "_COM_BAG_START"
        const val KEY_LOGS_COMMERCIAL_BAGGAGE_END = "_COM_BAG_END"
        const val KEY_LOGS_COMMERCIAL_PASSENGERS_START = "_COM_PAX_START"
        const val KEY_LOGS_COMMERCIAL_PASSENGERS_END = "_COM_PAX_END"
        const val KEY_LOGS_COMMERCIAL_DOORS_CLOSE = "_COM_DOOR"

        const val KEY_LOGS_APU_CYCLES = "_APU_CYCLES"
        const val KEY_LOGS_APU_HOURS = "_APU_HOURS"
        const val KEY_LOGS_APU_HOURS_ON_PLANE = "_APU_ON_PLANE"
        const val KEY_LOGS_APU_SERIAL_NUMBER = "_APU_S_N"

        const val KEY_LOGS_AFML_9 = "_AFML_9"
        const val KEY_LOGS_AFML_9_WORKS = "_AFML_9_W"

        ////Plane DB\\\\

        const val PLANE_DB_NAME = "PlaneDB.db"
        const val PLANE_DB_VERSION = 1
        const val PLANE_DB_TABLE = "_PL"

        const val KEY_PLANE_ID = "_ID"
        const val KEY_PLANE_NAME = "_NAME"
        const val KEY_PLANE_NAME_KOBRA = "_KOBRA"
        const val KEY_PLANE_NAME_TYPE = "_NAME_TYPE"
        const val KEY_PLANE_TYPE = "_TYPE"
        const val KEY_PLANE_FUEL = "_FUEL"
        const val KEY_PLANE_OIL = "_OIL"
        const val KEY_PLANE_OIL_STEP = "_OIL_DIFF"
        const val KEY_PLANE_HYDRAULIC = "_HYDR"
        const val KEY_PLANE_HYDRAULIC_STEP = "_HYDR_DIFF"
        const val KEY_PLANE_ENABLED = "_ENABLE"

        ////Check DB\\\\

        const val CHECK_DB_NAME = "CheckDB.db"
        const val CHECK_DB_VERSION = 1
        const val CHECK_DB_TABLE = "_CH"

        const val KEY_CHECK_ID = "_ID"
        const val KEY_CHECK_NAME = "_NAME"
        const val KEY_CHECK_TYPE = "_TYPE"
        const val KEY_CHECK_AFML_2 = "_AFML_2"
        const val KEY_CHECK_AFML_3 = "_AFML_3"
        const val KEY_CHECK_AFML_4 = "_AFML_4"
        const val KEY_CHECK_AFML_5 = "_AFML_5"
        const val KEY_CHECK_AFML_6 = "_AFML_6"
        const val KEY_CHECK_AFML_10 = "_AFML_10"
        const val KEY_CHECK_AFML_11 = "_AFML_11"
        const val KEY_CHECK_COMMERCIAL = "_COMMER"
        const val KEY_CHECK_APU_DATA = "_APU"
        const val KEY_CHECK_ADDITIONAL_WORKS = "_ADD_WORK"

        ////Fleet Info DB\\\\

        const val FLEET_DB_NAME = "FleetDB.db"
        const val FLEET_DB_VERSION = 1
        const val FLEET_DB_TABLE = "_IN"

        const val KEY_FLEET_ID = "_ID"
        const val KEY_FLEET_PLANE = "_PLANE"
        const val KEY_FLEET_STATUS = "_STATUS"
        const val KEY_FLEET_DATE = "_DATE"
        const val KEY_FLEET_TILL = "_TILL"
        const val KEY_FLEET_HEADER = "_HEAD"
        const val KEY_FLEET_MESSAGE = "_MESSAGE"

        ////Kobra DB\\\\

        const val KOBRA_DB_NAME = "KobraDB.db"
        const val KOBRA_DB_VERSION = 1
        const val KOBRA_DB_TABLE_ARRIVAL = "_AR"
        const val KOBRA_DB_TABLE_DEPARTURE = "_DP"

        const val KEY_KOBRA_ID = "_ID"
        const val KEY_KOBRA_PLANE = "_PLANE"
        const val KEY_KOBRA_FLIGHT_NUMBER = "_FL_NUM"
        const val KEY_KOBRA_FLIGHT_DESTINATION = "_FL_DEST"
        const val KEY_KOBRA_TIME_PLANED = "_TIME_PLANED"
        const val KEY_KOBRA_TIME_EXPECT = "_TIME_EXPECT"
        const val KEY_KOBRA_STAND = "_STAND"
        const val KEY_KOBRA_STATUS = "_STATUS"

        ////Airport DB\\\\

        const val AIRPORT_DB_NAME = "AirportDB.db"
        const val AIRPORT_DB_VERSION = 1
        const val AIRPORT_DB_TABLE = "_AP"

        const val KEY_AIRPORT_ID = "_ID"
        const val KEY_AIRPORT_CODE = "_CODE"
        const val KEY_AIRPORT_NAME = "_NAME"

        const val FORWARD_DIRECTION = true
        const val BACKWARD_DIRECTION = false

        const val THEME_LIGHT = 0
        const val THEME_DARK = 1
        const val THEME_BELAVIA = 2

        const val KOBRA_STATUS_IN_FLIGHT = "В ПУТИ"
        const val KOBRA_STATUS_ARRIVED_1 = "СОВЕРШИЛ ПОСАДКУ"
        const val KOBRA_STATUS_ARRIVED_2 = "ПРИБЫЛ"
        const val KOBRA_STATUS_IN_WORK_1 = "РЕГИСТРАЦИЯ"
        const val KOBRA_STATUS_IN_WORK_2 = "РЕГИСТРАЦИЯ ЗАКОНЧЕНА"
        const val KOBRA_STATUS_IN_WORK_3 = "ПОСАДКА"
        const val KOBRA_STATUS_IN_WORK_4 = "ПОСАДКА ОКОНЧЕНА"
        const val KOBRA_STATUS_IN_WORK_5 = "ОТПРАВЛЯЕТСЯ"
        const val KOBRA_STATUS_DEPARTED = "ВЫЛЕТЕЛ"
        const val KOBRA_STATUS_DELAYED = "ЗАДЕРЖАН"
        const val KOBRA_STATUS_IN_FUTURE = ""

        const val ROUND_LITERS_TO_1 = "1"
        const val ROUND_LITERS_TO_5 = "5"
        const val ROUND_LITERS_TO_10 = "10"
        const val ROUND_LITERS_TO_25 = "25"
        const val ROUND_LITERS_TO_50 = "50"

        lateinit var instance: Global
            private set

        ///Values

        val appContext: Context
            get() {
                return instance.applicationContext
            }

        val appPreference: SharedPreferences
            get() {
                return appContext.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
            }

        ///Variables
        ///Preferences
        ///General settings

        var appTheme: Int = THEME_LIGHT
            get() {
                return if (appPreference.contains("appTheme")) {
                    appPreference.getInt("appTheme", THEME_LIGHT)
                }
                else {
                    appPreference.edit().putInt("appTheme", THEME_LIGHT).apply()
                    THEME_LIGHT
                }
            }
            set(value) {
                appPreference.edit().putInt("appTheme", value).apply()
                field = value
            }

        var scheduleTime: Int = 14
            get() {
                return if (appPreference.contains("scheduleTime")) {
                    appPreference.getInt("scheduleTime", 14)
                }
                else {
                    appPreference.edit().putInt("scheduleTime", 14).apply()
                    14
                }
            }
            set(value) {
                appPreference.edit().putInt("scheduleTime", value).apply()
                field = value
            }

        var deleteLog: Boolean = false
            get() {
                return if (appPreference.contains("deleteLog")) {
                    appPreference.getBoolean("deleteLog", false)
                }
                else {
                    appPreference.edit().putBoolean("deleteLog", false).apply()
                    false
                }
            }
            set(value) {
                appPreference.edit().putBoolean("deleteLog", value).apply()
                field = value
            }

        var startupScreen: Int = MAIN_FRAGMENT_KOBRA
            get() {
                return if (appPreference.contains("startupScreen")) {
                    appPreference.getInt("startupScreen", MAIN_FRAGMENT_KOBRA)
                }
                else {
                    appPreference.edit().putInt("startupScreen", MAIN_FRAGMENT_KOBRA).apply()
                    MAIN_FRAGMENT_KOBRA
                }
            }
            set(value) {
                when (value) {
                    0 -> {
                        appPreference.edit().putInt("startupScreen", MAIN_FRAGMENT_SCHEDULE).apply()
                        field = MAIN_FRAGMENT_SCHEDULE
                    }

                    1 -> {
                        appPreference.edit().putInt("startupScreen", MAIN_FRAGMENT_FLEET).apply()
                        field = MAIN_FRAGMENT_FLEET
                    }

                    2 -> {
                        appPreference.edit().putInt("startupScreen", MAIN_FRAGMENT_KOBRA).apply()
                        field = MAIN_FRAGMENT_KOBRA
                    }
                }
            }

        ///Variables

        var kobraLastUpdate: Long = 0
            get() {
                return if (appPreference.contains("kobraLastUpdate")) {
                    appPreference.getLong("kobraLastUpdate", 0)
                }
                else {
                    appPreference.edit().putLong("kobraLastUpdate", 0).apply()
                    0
                }
            }
            set(value) {
                appPreference.edit().putLong("kobraLastUpdate", value).apply()
                field = value
            }
    }

    init {
        instance = this
    }
}