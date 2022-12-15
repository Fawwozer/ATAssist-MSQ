package by.fawwozer.atassist

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_FLIGHT_DESTINATION
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_FLIGHT_NUMBER
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_ID
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_PLANE
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_STAND
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_STATUS
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_TIME_EXPECT
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_TIME_PLANED
import by.fawwozer.atassist.Global.Companion.KOBRA_DB_NAME
import by.fawwozer.atassist.Global.Companion.KOBRA_DB_TABLE_ARRIVAL
import by.fawwozer.atassist.Global.Companion.KOBRA_DB_TABLE_DEPARTURE
import by.fawwozer.atassist.Global.Companion.KOBRA_DB_VERSION

class KobraDB: SQLiteOpenHelper(Global.appContext, KOBRA_DB_NAME, null, KOBRA_DB_VERSION) {
	override fun onCreate(db: SQLiteDatabase?) {
		db!!.execSQL(
			"CREATE TABLE " + KOBRA_DB_TABLE_ARRIVAL + "( " +
					KEY_KOBRA_ID + " long, " +
					KEY_KOBRA_PLANE + " text, " +
					KEY_KOBRA_FLIGHT_NUMBER + " text, " +
					KEY_KOBRA_FLIGHT_DESTINATION + " text, " +
					KEY_KOBRA_TIME_PLANED + " long, " +
					KEY_KOBRA_TIME_EXPECT + " long, " +
					KEY_KOBRA_STAND + " text, " +
					KEY_KOBRA_STATUS + " text" +
					");"
		)
		db.execSQL(
			"CREATE TABLE " + KOBRA_DB_TABLE_DEPARTURE + "( " +
					KEY_KOBRA_ID + " long, " +
					KEY_KOBRA_PLANE + " text, " +
					KEY_KOBRA_FLIGHT_NUMBER + " text, " +
					KEY_KOBRA_FLIGHT_DESTINATION + " text, " +
					KEY_KOBRA_TIME_PLANED + " long, " +
					KEY_KOBRA_TIME_EXPECT + " long, " +
					KEY_KOBRA_STAND + " text, " +
					KEY_KOBRA_STATUS + " text" +
					");"
		)
		onUpgrade(db, 1, KOBRA_DB_VERSION)
	}
	
	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
		when (oldVersion) {
			1 -> {
				Log.d("MY", "KobraDB/onUpgrade/oldVer = 1")
			}
			2 -> {
				Log.d("MY", "KobraDB/onUpgrade/oldVer = 2")
			}
			else -> {
				Log.d("MY", "KobraDB/onUpgrade/oldVer else")
			}
		}
	}
}

