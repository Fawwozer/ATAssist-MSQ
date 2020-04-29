package by.fawwozer.atassist

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import by.fawwozer.atassist.Global.Companion.PLANE_DB_NAME
import by.fawwozer.atassist.Global.Companion.PLANE_DB_TABLE
import by.fawwozer.atassist.Global.Companion.PLANE_DB_VERSION
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_ID
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_NAME
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_NAME_KOBRA
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_NAME_TYPE
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_TYPE
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_FUEL
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_OIL
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_OIL_STEP
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_HYDRAULIC
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_HYDRAULIC_STEP
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_ENABLED

class PlaneDB() : SQLiteOpenHelper(Global.appContext, PLANE_DB_NAME, null, PLANE_DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE " + PLANE_DB_TABLE + "( " +
                    KEY_PLANE_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                    KEY_PLANE_NAME + " text, " +
                    KEY_PLANE_NAME_KOBRA + " text, " +
                    KEY_PLANE_NAME_TYPE + " text, " +
                    KEY_PLANE_TYPE + " tinyint, " +
                    KEY_PLANE_FUEL + " tinyint, " +
                    KEY_PLANE_OIL + " tinyint, " +
                    KEY_PLANE_OIL_STEP + " real, " +
                    KEY_PLANE_HYDRAULIC + " tinyint, " +
                    KEY_PLANE_HYDRAULIC_STEP + " real, " +
                    KEY_PLANE_ENABLED + " bit" +
                    ");"
        )
        onUpgrade(db,1, PLANE_DB_VERSION)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        when (oldVersion) {
            1 -> {
            }
            2 -> {
            }
            else -> {
            }
        }
    }
}