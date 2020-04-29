package by.fawwozer.atassist

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp

class Global: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

    companion object {

        ////GENERAL\\\\

        val GENERAL_SCHEDULE_NOTIFICATION_CHANNEL_NAME = "Schedule Notification"
        val GENERAL_SCHEDULE_NOTIFICATION_CHANNEL_ID = "scheduleNotification"
        val GENERAL_SCHEDULE_NOTIFICATION_CHANNEL_CODE = 123

        val GENERAL_FLEET_NOTIFICATION_CHANNEL_NAME = "Fleet Notification"
        val GENERAL_FLEET_NOTIFICATION_CHANNEL_ID = "fleetNotification"
        val GENERAL_FLEET_NOTIFICATION_CHANNEL_CODE = 456

        ////PREFERENCES\\\\

        val PREFERENCE_FILE = "app_preference"
        val PREFERENCE_LAST_RUN_VERSION = "last_run"
        val PREFERENCE_LAST_BACKUP_TIME = "last_backup"

        ////SETTINGS\\\\

        val SETTING_GENERAL_APPLICATION_THEME = "theme"
        val SETTING_GENERAL_SCHEDULE_CLEAR_TIME = "schedule_time"
        val SETTING_GENERAL_DELETE_LOGS = "delete_logs"

        val SETTING_NOTIFICATION_ALLOW = "allow_notification"
        val SETTING_NOTIFICATION_SCHEDULE = "schedule_notification"
        val SETTING_NOTIFICATION_FLEET = "fleet_notification"

        val SETTING_MAINTENANCE_LITERS_ROUND = "liters_round"
        val SETTING_MAINTENANCE_KG_ROUND = "kg_round"
        val SETTING_MAINTENANCE_HIDE_DEICE = "hide_deice"

        val SETTING_BACKUP_LOCAL = "local_backup"
        val SETTING_BACKUP_TIME = "time_backup"
        val SETTING_BACKUP_CLOUD = "cloud_backup"


        ////FIRESTORE\\\\

        val FIRESTORE_COLLECTION_FLEET = "fleet_info"
        val FIRESTORE_COLLECTION_PLANES = "planes"

        val FIRESTORE_STATE_DONE = 0;
        val FIRESTORE_STATE_INFO = 5;
        val FIRESTORE_STATE_DMI_A = 10;
        val FIRESTORE_STATE_DMI_B = 11;
        val FIRESTORE_STATE_DMI_C = 12;
        val FIRESTORE_STATE_DMI_D = 13;
        val FIRESTORE_STATE_DDA = 20;
        val FIRESTORE_STATE_INOP = 25;

        ////Logs DB\\\\

        val LOGS_DB_NAME = "LogsDB.db"
        val LOGS_DB_VERSION = 1
        val LOGS_DB_TABLE = "logs"

        val KEY_LOGS_ID = "_ID"
        val KEY_LOGS_SORT = "_SORT"
        val KEY_LOGS_FLIGHT_NUMBER = "_FL_NUM"
        val KEY_LOGS_FLIGHT_DESTINATION = "_FL_DEST"
        val KEY_LOGS_ARRIVAL_TIME = "_ARR_TIME"
        val KEY_LOGS_DEPARTURE_TIME = "_DEP_TIME"
        val KEY_LOGS_STAND = "_STAND"
        val KEY_LOGS_IS_DONE = "_DONE"
        val KEY_LOGS_PLANE = "_PLANE_ID"
        val KEY_LOGS_CHECK = "_CHECK_ID"

        val KEY_LOGS_AFML_2 = "_AFML_2"
        val KEY_LOGS_AFML_2_HYDROULIC_ADD_SYSTEM_1 = "_AFML_2_HYDR_1"
        val KEY_LOGS_AFML_2_HYDROULIC_ADD_SYSTEM_2 = "_AFML_2_HYDR_2"
        val KEY_LOGS_AFML_2_HYDROULIC_ADD_SYSTEM_3 = "_AFML_2_HYDR_3"

        val KEY_LOGS_AFML_3 = "_AFML_3"
        val KEY_LOGS_AFML_3_KG_REMAIN = "_AFML_3_KG_REM"
        val KEY_LOGS_AFML_3_KG_ADD = "_AFML_3_KG_ADD"
        val KEY_LOGS_AFML_3_KG_TOTAL = "_AFML_3_KG_TOT"
        val KEY_LOGS_AFML_3_KG_ADJUST = "_AFML_3_KG_ADJ"
        val KEY_LOGS_AFML_3_KG_DEPART = "_AFML_3_KG_DEP"
        val KEY_LOGS_AFML_3_LB_REMAIN = "_AFML_3_LB_REM"
        val KEY_LOGS_AFML_3_LB_ADD = "_AFML_3_LB_ADD"
        val KEY_LOGS_AFML_3_LB_TOTAL = "_AFML_3_LB_TOT"
        val KEY_LOGS_AFML_3_LB_ADJUST = "_AFML_3_LB_ADJ"
        val KEY_LOGS_AFML_3_LB_DEPART = "_AFML_3_LB_DEP"
        val KEY_LOGS_AFML_3_INFO_TEMPERATURE = "_AFML_3_I_TEM"
        val KEY_LOGS_AFML_3_INFO_DENSITY = "_AFML_3_I_DENS"
        val KEY_LOGS_AFML_3_INFO_FUEL_CARD = "_AFML_3_I_FCA"
        val KEY_LOGS_AFML_3_INFO_REFUELER = "_AFML_3_I_REF"
        val KEY_LOGS_AFML_3_INFO_FUEL_ORDER = "_AFML_3_I_FOR"

        val KEY_LOGS_AFML_4 = "_AFML_4"
        val KEY_LOGS_AFML_4_FUEL = "_AFML_4_FUEL"
        val KEY_LOGS_AFML_4_NOTES = "_AFML_4_NOTE"
        val KEY_LOGS_AFML_4_LIFE_VEST_SEAT = "_AFML_4_LVSEAT"
        val KEY_LOGS_AFML_4_LIFE_VEST_SPARE = "_AFML_4_LVSPARE"
        val KEY_LOGS_AFML_4_SEAT_BELTS = "_AFML_4_BELT"
        val KEY_LOGS_AFML_4_DOCUMENTS = "_AFML_4_DOC"
        val KEY_LOGS_AFML_4_OTHER = "_AFML_4_OTHER"

        val KEY_LOGS_AFML_5 = "_AFML_5"
        val KEY_LOGS_AFML_5_ENGINE_1_REMAIN = "_AFML_5_E1_REM"
        val KEY_LOGS_AFML_5_ENGINE_1_ADD = "_AFML_5_E1_ADD"
        val KEY_LOGS_AFML_5_ENGINE_2_REMAIN = "_AFML_5_E2_REM"
        val KEY_LOGS_AFML_5_ENGINE_2_ADD = "_AFML_5_E2_ADD"

        val KEY_LOGS_AFML_6 = "_AFML_6"
        val KEY_LOGS_AFML_6_N1_REMAIN = "_AFML_6_N1_REM"
        val KEY_LOGS_AFML_6_N2_REMAIN = "_AFML_6_N2_REM"
        val KEY_LOGS_AFML_6_M1_REMAIN = "_AFML_6_M1_REM"
        val KEY_LOGS_AFML_6_M2_REMAIN = "_AFML_6_M2_REM"
        val KEY_LOGS_AFML_6_M3_REMAIN = "_AFML_6_M3_REM"
        val KEY_LOGS_AFML_6_M4_REMAIN = "_AFML_6_M4_REM"
        val KEY_LOGS_AFML_6_N1_ADD  = "_AFML_6_N1_ADD"
        val KEY_LOGS_AFML_6_N2_ADD  = "_AFML_6_N2_ADD"
        val KEY_LOGS_AFML_6_M1_ADD  = "_AFML_6_M1_ADD"
        val KEY_LOGS_AFML_6_M2_ADD  = "_AFML_6_M2_ADD"
        val KEY_LOGS_AFML_6_M3_ADD  = "_AFML_6_M3_ADD"
        val KEY_LOGS_AFML_6_M4_ADD  = "_AFML_6_M4_ADD"

        val KEY_LOGS_AFML_10 = "_AFML_10"

        val KEY_LOGS_AFML_11 = "_AFML_11"
        val KEY_LOGS_AFML_11_TYPE = "_AFML_11_TYPE"
        val KEY_LOGS_AFML_11_CONCENTRATION = "_AFML_11_CONC"

        val KEY_LOGS_COMERCIAL_BAGGAGE_START = "_COM_BAG_START"
        val KEY_LOGS_COMERCIAL_BAGGAGE_END = "_COM_BAG_END"
        val KEY_LOGS_COMERCIAL_PASSANGERS_START = "_COM_PAX_START"
        val KEY_LOGS_COMERCIAL_PASSANGERS_END = "_COM_PAX_END"
        val KEY_LOGS_COMERCIAL_DOORS_CLOSE = "_COM_DOOR"

        val KEY_LOGS_APU_CYCLES = "_APU_CYCLES"
        val KEY_LOGS_APU_HOURS = "_APU_HOURS"
        val KEY_LOGS_APU_HOURS_ON_PLANE = "_APU_ON_PLANE"
        val KEY_LOGS_APU_SERIAL_NUMBER = "_APU_S_N"

        val KEY_LOGS_AFML_9 = "_AFML_9"
        val KEY_LOGS_AFML_9_WORK_00 = "_AFML_9_W_00"
        val KEY_LOGS_AFML_9_WORK_01 = "_AFML_9_W_01"
        val KEY_LOGS_AFML_9_WORK_02 = "_AFML_9_W_02"
        val KEY_LOGS_AFML_9_WORK_03 = "_AFML_9_W_03"
        val KEY_LOGS_AFML_9_WORK_04 = "_AFML_9_W_04"
        val KEY_LOGS_AFML_9_WORK_05 = "_AFML_9_W_05"
        val KEY_LOGS_AFML_9_WORK_06 = "_AFML_9_W_06"
        val KEY_LOGS_AFML_9_WORK_07 = "_AFML_9_W_07"
        val KEY_LOGS_AFML_9_WORK_08 = "_AFML_9_W_08"
        val KEY_LOGS_AFML_9_WORK_09 = "_AFML_9_W_09"
        val KEY_LOGS_AFML_9_WORK_10 = "_AFML_9_W_10"
        val KEY_LOGS_AFML_9_WORK_11 = "_AFML_9_W_11"
        val KEY_LOGS_AFML_9_WORK_12 = "_AFML_9_W_12"
        val KEY_LOGS_AFML_9_WORK_13 = "_AFML_9_W_13"
        val KEY_LOGS_AFML_9_WORK_14 = "_AFML_9_W_14"
        val KEY_LOGS_AFML_9_WORK_15 = "_AFML_9_W_15"
        val KEY_LOGS_AFML_9_WORK_16 = "_AFML_9_W_16"
        val KEY_LOGS_AFML_9_WORK_17 = "_AFML_9_W_17"
        val KEY_LOGS_AFML_9_WORK_18 = "_AFML_9_W_18"
        val KEY_LOGS_AFML_9_WORK_19 = "_AFML_9_W_19"
        val KEY_LOGS_AFML_9_WORK_20 = "_AFML_9_W_20"
        val KEY_LOGS_AFML_9_WORK_21 = "_AFML_9_W_21"
        val KEY_LOGS_AFML_9_WORK_22 = "_AFML_9_W_22"
        val KEY_LOGS_AFML_9_WORK_23 = "_AFML_9_W_23"
        val KEY_LOGS_AFML_9_WORK_24 = "_AFML_9_W_24"
        val KEY_LOGS_AFML_9_WORK_25 = "_AFML_9_W_25"
        val KEY_LOGS_AFML_9_WORK_26 = "_AFML_9_W_26"
        val KEY_LOGS_AFML_9_WORK_27 = "_AFML_9_W_27"
        val KEY_LOGS_AFML_9_WORK_28 = "_AFML_9_W_28"
        val KEY_LOGS_AFML_9_WORK_29 = "_AFML_9_W_29"
        val KEY_LOGS_AFML_9_WORK_30 = "_AFML_9_W_30"
        val KEY_LOGS_AFML_9_WORK_31 = "_AFML_9_W_31"
        val KEY_LOGS_AFML_9_WORK_32 = "_AFML_9_W_32"
        val KEY_LOGS_AFML_9_WORK_33 = "_AFML_9_W_33"
        val KEY_LOGS_AFML_9_WORK_34 = "_AFML_9_W_34"
        val KEY_LOGS_AFML_9_WORK_35 = "_AFML_9_W_35"
        val KEY_LOGS_AFML_9_WORK_36 = "_AFML_9_W_36"
        val KEY_LOGS_AFML_9_WORK_37 = "_AFML_9_W_37"
        val KEY_LOGS_AFML_9_WORK_38 = "_AFML_9_W_38"
        val KEY_LOGS_AFML_9_WORK_39 = "_AFML_9_W_39"

        ////Plane DB\\\\
        val PLANE_DB_NAME = "PlaneDB.db"
        val PLANE_DB_VERSION = 1
        val PLANE_DB_TABLE = "plane"

        ////Check DB\\\\
        val CHECK_DB_NAME = "CheckDB.db"
        val CHECK_DB_VERSION = 1
        val CHECK_DB_TABLE = "check"

        ////Fleet Info DB\\\\
        val FLEET_DB_NAME = "FleetDB.db"
        val FLEET_DB_VERSION = 1
        val FLEET_DB_TABLE = "info"

        lateinit var instance: Global
            private set

        val appContext: Context
            get() {
                return instance.applicationContext
            }
    }

    init {
        instance = this
    }
}