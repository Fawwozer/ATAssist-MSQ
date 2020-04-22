package by.fawwozer.atassist

import android.app.Application
import android.content.Context

abstract class GlobalApp : Application() {

    companion object {

        ////GENERAL\\\\

        public val GENERAL_SCHEDULE_NOTIFICATION_CHANNEL_NAME = "Schedule Notification"
        public val GENERAL_SCHEDULE_NOTIFICATION_CHANNEL_ID = "scheduleNotification"
        public val GENERAL_SCHEDULE_NOTIFICATION_CHANNEL_CODE = 123

        public val GENERAL_FLEET_NOTIFICATION_CHANNEL_NAME = "Fleet Notification"
        public val GENERAL_FLEET_NOTIFICATION_CHANNEL_ID = "fleetNotification"
        public val GENERAL_FLEET_NOTIFICATION_CHANNEL_CODE = 456

        ////PREFERENCES\\\\

        public val PREFERENCE_FILE = "app_preference"
        public val PREFERENCE_LAST_RUN_VERSION = "last_run"
        public val PREFERENCE_LAST_BACKUP_TIME = "last_backup"

        ////SETTINGS\\\\

        public val SETTING_GENERAL_APPLICATION_THEME = "theme"
        public val SETTING_GENERAL_SCHEDULE_CLEAR_TIME = "schedule_time"
        public val SETTING_GENERAL_DELETE_LOGS = "delete_logs"

        public val SETTING_MAINTENANCE_LITRES_ROUND = "litres_round"
        public val SETTING_MAINTENANCE_KG_ROUND = "kg_round"
        public val SETTING_MAINTENANCE_HIDE_DEICE = "hide_deice"

        public val SETTING_BACKUP_ALLOW = "allow_backup"
        public val SETTING_BACKUP_TIME = "time_backup"
        public val SETTING_BACKUP_LOCAL = "local_backup"
        public val SETTING_BACKUP_CLOUD = "cloud_backup"

        public val SETTING_NOTIFICATION_ALLOW = "allow_notification"
        public val SETTING_NOTIFICATION_SCHEDULE = "schedule_notification"
        public val SETTING_NOTIFICATION_FLEET = "fleet_notification"


        ////FIRESTORE\\\\

        public val FIRESTORE_COLLECTION_FLEET = "fleet_info"
        public val FIRESTORE_COLLECTION_PLANES = "planes"

        public val FIRESTORE_STATE_DONE = 0;
        public val FIRESTORE_STATE_INFO = 5;
        public val FIRESTORE_STATE_DMI_A = 10;
        public val FIRESTORE_STATE_DMI_B = 11;
        public val FIRESTORE_STATE_DMI_C = 12;
        public val FIRESTORE_STATE_DMI_D = 13;
        public val FIRESTORE_STATE_DDA = 20;
        public val FIRESTORE_STATE_INOP = 25;

        lateinit var instance: GlobalApp
            private set

        val appContext: Context
            get() {
                return instance.applicationContext
            }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }
}