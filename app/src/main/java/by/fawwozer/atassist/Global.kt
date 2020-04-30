package by.fawwozer.atassist

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp

class Global: Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MY", "Global/onCreate/Start")

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

        Log.d("MY", "Global/onCreate/Finish")
    }

    companion object {

        ////GENERAL\\\\

        const val GENERAL_SCHEDULE_NOTIFICATION_CHANNEL_NAME = "Schedule Notification"
        const val GENERAL_SCHEDULE_NOTIFICATION_CHANNEL_ID = "scheduleNotification"
        const val GENERAL_SCHEDULE_NOTIFICATION_CHANNEL_CODE = 123

        const val GENERAL_FLEET_NOTIFICATION_CHANNEL_NAME = "Fleet Notification"
        const val GENERAL_FLEET_NOTIFICATION_CHANNEL_ID = "fleetNotification"
        const val GENERAL_FLEET_NOTIFICATION_CHANNEL_CODE = 456

        const val GENERAL_FIRST_STRING_SPLITTER = ",|,"
        const val GENERAL_SECOND_STRING_SPLITTER = ".|."

        ////PREFERENCES\\\\

        const val PREFERENCE_FILE = "app_preference"
        const val PREFERENCE_LAST_RUN_VERSION = "last_run"
        const val PREFERENCE_LAST_BACKUP_TIME = "last_backup"

        ////SETTINGS\\\\

        const val SETTING_GENERAL_APPLICATION_THEME = "theme"
        const val SETTING_GENERAL_SCHEDULE_CLEAR_TIME = "schedule_time"
        const val SETTING_GENERAL_DELETE_LOGS = "delete_logs"

        const val SETTING_NOTIFICATION_ALLOW = "allow_notification"
        const val SETTING_NOTIFICATION_SCHEDULE = "schedule_notification"
        const val SETTING_NOTIFICATION_FLEET = "fleet_notification"

        const val SETTING_MAINTENANCE_LITERS_ROUND = "liters_round"
        const val SETTING_MAINTENANCE_KG_ROUND = "kg_round"
        const val SETTING_MAINTENANCE_HIDE_DEICE = "hide_deice"

        const val SETTING_BACKUP_LOCAL = "local_backup"
        const val SETTING_BACKUP_TIME = "time_backup"
        const val SETTING_BACKUP_CLOUD = "cloud_backup"


        ////FIRESTORE\\\\

        const val FIRESTORE_COLLECTION_FLEET = "FleetDB"
        const val FIRESTORE_COLLECTION_PLANES = "PlaneDB"
        const val FIRESTORE_COLLECTION_CHECKS = "CheckDB"

        const val FIRESTORE_STATE_DONE = 0
        const val FIRESTORE_STATE_INFO = 5
        const val FIRESTORE_STATE_DMI_A = 10
        const val FIRESTORE_STATE_DMI_B = 11
        const val FIRESTORE_STATE_DMI_C = 12
        const val FIRESTORE_STATE_DMI_D = 13
        const val FIRESTORE_STATE_DDA = 20
        const val FIRESTORE_STATE_INOP = 25

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
        const val KEY_LOGS_AFML_6_N1_ADD  = "_AFML_6_N1_ADD"
        const val KEY_LOGS_AFML_6_N2_ADD  = "_AFML_6_N2_ADD"
        const val KEY_LOGS_AFML_6_M1_ADD  = "_AFML_6_M1_ADD"
        const val KEY_LOGS_AFML_6_M2_ADD  = "_AFML_6_M2_ADD"
        const val KEY_LOGS_AFML_6_M3_ADD  = "_AFML_6_M3_ADD"
        const val KEY_LOGS_AFML_6_M4_ADD  = "_AFML_6_M4_ADD"

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
        const val KEY_LOGS_AFML_9_WORK_00 = "_AFML_9_W_00"
        const val KEY_LOGS_AFML_9_WORK_01 = "_AFML_9_W_01"
        const val KEY_LOGS_AFML_9_WORK_02 = "_AFML_9_W_02"
        const val KEY_LOGS_AFML_9_WORK_03 = "_AFML_9_W_03"
        const val KEY_LOGS_AFML_9_WORK_04 = "_AFML_9_W_04"
        const val KEY_LOGS_AFML_9_WORK_05 = "_AFML_9_W_05"
        const val KEY_LOGS_AFML_9_WORK_06 = "_AFML_9_W_06"
        const val KEY_LOGS_AFML_9_WORK_07 = "_AFML_9_W_07"
        const val KEY_LOGS_AFML_9_WORK_08 = "_AFML_9_W_08"
        const val KEY_LOGS_AFML_9_WORK_09 = "_AFML_9_W_09"
        const val KEY_LOGS_AFML_9_WORK_10 = "_AFML_9_W_10"
        const val KEY_LOGS_AFML_9_WORK_11 = "_AFML_9_W_11"
        const val KEY_LOGS_AFML_9_WORK_12 = "_AFML_9_W_12"
        const val KEY_LOGS_AFML_9_WORK_13 = "_AFML_9_W_13"
        const val KEY_LOGS_AFML_9_WORK_14 = "_AFML_9_W_14"
        const val KEY_LOGS_AFML_9_WORK_15 = "_AFML_9_W_15"
        const val KEY_LOGS_AFML_9_WORK_16 = "_AFML_9_W_16"
        const val KEY_LOGS_AFML_9_WORK_17 = "_AFML_9_W_17"
        const val KEY_LOGS_AFML_9_WORK_18 = "_AFML_9_W_18"
        const val KEY_LOGS_AFML_9_WORK_19 = "_AFML_9_W_19"
        const val KEY_LOGS_AFML_9_WORK_20 = "_AFML_9_W_20"
        const val KEY_LOGS_AFML_9_WORK_21 = "_AFML_9_W_21"
        const val KEY_LOGS_AFML_9_WORK_22 = "_AFML_9_W_22"
        const val KEY_LOGS_AFML_9_WORK_23 = "_AFML_9_W_23"
        const val KEY_LOGS_AFML_9_WORK_24 = "_AFML_9_W_24"
        const val KEY_LOGS_AFML_9_WORK_25 = "_AFML_9_W_25"
        const val KEY_LOGS_AFML_9_WORK_26 = "_AFML_9_W_26"
        const val KEY_LOGS_AFML_9_WORK_27 = "_AFML_9_W_27"
        const val KEY_LOGS_AFML_9_WORK_28 = "_AFML_9_W_28"
        const val KEY_LOGS_AFML_9_WORK_29 = "_AFML_9_W_29"
        const val KEY_LOGS_AFML_9_WORK_30 = "_AFML_9_W_30"
        const val KEY_LOGS_AFML_9_WORK_31 = "_AFML_9_W_31"
        const val KEY_LOGS_AFML_9_WORK_32 = "_AFML_9_W_32"
        const val KEY_LOGS_AFML_9_WORK_33 = "_AFML_9_W_33"
        const val KEY_LOGS_AFML_9_WORK_34 = "_AFML_9_W_34"
        const val KEY_LOGS_AFML_9_WORK_35 = "_AFML_9_W_35"
        const val KEY_LOGS_AFML_9_WORK_36 = "_AFML_9_W_36"
        const val KEY_LOGS_AFML_9_WORK_37 = "_AFML_9_W_37"
        const val KEY_LOGS_AFML_9_WORK_38 = "_AFML_9_W_38"
        const val KEY_LOGS_AFML_9_WORK_39 = "_AFML_9_W_39"

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

        lateinit var instance: Global
            private set

        val appContext: Context
            get() {
                Log.d("MY", "Global/appContext")
                return instance.applicationContext
            }
    }

    init {
        instance = this
    }
}