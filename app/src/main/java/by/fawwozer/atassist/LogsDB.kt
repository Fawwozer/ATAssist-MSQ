package by.fawwozer.atassist

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_10
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_11
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_11_CONCENTRATION
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_11_TYPE
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_2
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_2_HYDRAULIC_ADD_SYSTEM_1
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_2_HYDRAULIC_ADD_SYSTEM_2
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_2_HYDRAULIC_ADD_SYSTEM_3
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_INFO_DENSITY
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_INFO_FUEL_CARD
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_INFO_FUEL_ORDER
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_INFO_REFUELER
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_INFO_TEMPERATURE
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_KG_ADD
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_KG_ADJUST
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_KG_DEPART
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_KG_REMAIN
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_KG_TOTAL
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_LB_ADD
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_LB_ADJUST
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_LB_DEPART
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_LB_REMAIN
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_3_LB_TOTAL
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_4
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_4_DOCUMENTS
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_4_FUEL
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_4_LIFE_VEST_SEAT
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_4_LIFE_VEST_SPARE
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_4_NOTES
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_4_OTHER
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_4_SEAT_BELTS
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_5
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_5_ENGINE_1_ADD
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_5_ENGINE_1_REMAIN
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_5_ENGINE_2_ADD
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_5_ENGINE_2_REMAIN
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6_M1_ADD
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6_M1_REMAIN
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6_M2_ADD
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6_M2_REMAIN
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6_M3_ADD
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6_M3_REMAIN
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6_M4_ADD
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6_M4_REMAIN
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6_N1_ADD
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6_N1_REMAIN
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6_N2_ADD
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_6_N2_REMAIN
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_9
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_AFML_9_WORKS
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_APU_CYCLES
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_APU_HOURS
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_APU_HOURS_ON_PLANE
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_APU_SERIAL_NUMBER
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_ARRIVAL_TIME
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_BELAVIA
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_CHECK
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_COMMERCIAL_BAGGAGE_END
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_COMMERCIAL_BAGGAGE_START
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_COMMERCIAL_DOORS_CLOSE
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_COMMERCIAL_PASSENGERS_END
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_COMMERCIAL_PASSENGERS_START
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_DEPARTURE_TIME
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_FLIGHT_DESTINATION
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_FLIGHT_NUMBER
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_ID
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_IS_DONE
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_PLANE
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_SORT
import by.fawwozer.atassist.Global.Companion.KEY_LOGS_STAND
import by.fawwozer.atassist.Global.Companion.LOGS_DB_NAME
import by.fawwozer.atassist.Global.Companion.LOGS_DB_TABLE
import by.fawwozer.atassist.Global.Companion.LOGS_DB_VERSION

class LogsDB: SQLiteOpenHelper(Global.appContext, LOGS_DB_NAME, null, LOGS_DB_VERSION) {
	override fun onCreate(db: SQLiteDatabase?) {
		db!!.execSQL(
			"CREATE TABLE " + LOGS_DB_TABLE + "( " +
					KEY_LOGS_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
					KEY_LOGS_SORT + " long, " +
					KEY_LOGS_FLIGHT_NUMBER + " text, " +
					KEY_LOGS_FLIGHT_DESTINATION + " text, " +
					KEY_LOGS_ARRIVAL_TIME + " long, " +
					KEY_LOGS_DEPARTURE_TIME + " long, " +
					KEY_LOGS_STAND + " text, " +
					KEY_LOGS_IS_DONE + " bit, " +
					KEY_LOGS_PLANE + " tinyint, " +
					KEY_LOGS_CHECK + " text, " +
					KEY_LOGS_BELAVIA + " bit, " +
					KEY_LOGS_AFML_2 + " bit, " +
					KEY_LOGS_AFML_2_HYDRAULIC_ADD_SYSTEM_1 + " real, " +
					KEY_LOGS_AFML_2_HYDRAULIC_ADD_SYSTEM_2 + " real, " +
					KEY_LOGS_AFML_2_HYDRAULIC_ADD_SYSTEM_3 + " real, " +
					KEY_LOGS_AFML_3 + " bit, " +
					KEY_LOGS_AFML_3_KG_REMAIN + " integer, " +
					KEY_LOGS_AFML_3_KG_ADD + " integer, " +
					KEY_LOGS_AFML_3_KG_TOTAL + " integer, " +
					KEY_LOGS_AFML_3_KG_ADJUST + " integer, " +
					KEY_LOGS_AFML_3_KG_DEPART + " integer, " +
					KEY_LOGS_AFML_3_LB_REMAIN + " integer, " +
					KEY_LOGS_AFML_3_LB_ADD + " integer, " +
					KEY_LOGS_AFML_3_LB_TOTAL + " integer, " +
					KEY_LOGS_AFML_3_LB_ADJUST + " integer, " +
					KEY_LOGS_AFML_3_LB_DEPART + " integer, " +
					KEY_LOGS_AFML_3_INFO_TEMPERATURE + " integer, " +
					KEY_LOGS_AFML_3_INFO_DENSITY + " real, " +
					KEY_LOGS_AFML_3_INFO_FUEL_CARD + " text, " +
					KEY_LOGS_AFML_3_INFO_REFUELER + " text, " +
					KEY_LOGS_AFML_3_INFO_FUEL_ORDER + " text, " +
					KEY_LOGS_AFML_4 + " long, " +
					KEY_LOGS_AFML_4_FUEL + " bit, " +
					KEY_LOGS_AFML_4_NOTES + " bit, " +
					KEY_LOGS_AFML_4_LIFE_VEST_SEAT + " bit, " +
					KEY_LOGS_AFML_4_LIFE_VEST_SPARE + " bit, " +
					KEY_LOGS_AFML_4_SEAT_BELTS + " bit, " +
					KEY_LOGS_AFML_4_DOCUMENTS + " bit, " +
					KEY_LOGS_AFML_4_OTHER + " bit, " +
					KEY_LOGS_AFML_5 + " bit, " +
					KEY_LOGS_AFML_5_ENGINE_1_REMAIN + " real, " +
					KEY_LOGS_AFML_5_ENGINE_1_ADD + " real, " +
					KEY_LOGS_AFML_5_ENGINE_2_REMAIN + " real, " +
					KEY_LOGS_AFML_5_ENGINE_2_ADD + " real, " +
					KEY_LOGS_AFML_6 + " long, " +
					KEY_LOGS_AFML_6_N1_REMAIN + " integer, " +
					KEY_LOGS_AFML_6_N2_REMAIN + " integer, " +
					KEY_LOGS_AFML_6_M1_REMAIN + " integer, " +
					KEY_LOGS_AFML_6_M2_REMAIN + " integer, " +
					KEY_LOGS_AFML_6_M3_REMAIN + " integer, " +
					KEY_LOGS_AFML_6_M4_REMAIN + " integer, " +
					KEY_LOGS_AFML_6_N1_ADD + " integer, " +
					KEY_LOGS_AFML_6_N2_ADD + " integer, " +
					KEY_LOGS_AFML_6_M1_ADD + " integer, " +
					KEY_LOGS_AFML_6_M2_ADD + " integer, " +
					KEY_LOGS_AFML_6_M3_ADD + " integer, " +
					KEY_LOGS_AFML_6_M4_ADD + " integer, " +
					KEY_LOGS_AFML_10 + " long, " +
					KEY_LOGS_AFML_11 + " long, " +
					KEY_LOGS_AFML_11_TYPE + " tinyint, " +
					KEY_LOGS_AFML_11_CONCENTRATION + " long, " +
					KEY_LOGS_COMMERCIAL_BAGGAGE_START + " long, " +
					KEY_LOGS_COMMERCIAL_BAGGAGE_END + " long, " +
					KEY_LOGS_COMMERCIAL_PASSENGERS_START + " long, " +
					KEY_LOGS_COMMERCIAL_PASSENGERS_END + " long, " +
					KEY_LOGS_COMMERCIAL_DOORS_CLOSE + " long, " +
					KEY_LOGS_APU_CYCLES + " real, " +
					KEY_LOGS_APU_HOURS + " real, " +
					KEY_LOGS_APU_HOURS_ON_PLANE + " real, " +
					KEY_LOGS_APU_SERIAL_NUMBER + " text, " +
					KEY_LOGS_AFML_9 + " long, " +
					KEY_LOGS_AFML_9_WORKS + " text" +
					");"
		)
		onUpgrade(db, 1, LOGS_DB_VERSION)
	}
	
	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
		when (oldVersion) {
			1 -> {
				Log.d("MY", "LogsDB/onUpgrade/oldVer = 1")
			}
			2 -> {
				Log.d("MY", "LogsDB/onUpgrade/oldVer = 2")
			}
			else -> {
				Log.d("MY", "LogsDB/onUpgrade/oldVer else")
			}
		}
	}
}