package by.fawwozer.atassist

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import by.fawwozer.atassist.Global.Companion.CHECK_DB_NAME
import by.fawwozer.atassist.Global.Companion.CHECK_DB_TABLE
import by.fawwozer.atassist.Global.Companion.CHECK_DB_VERSION
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_ID
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_NAME
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_TYPE
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_AFML_2
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_AFML_3
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_AFML_4
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_AFML_5
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_AFML_6
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_AFML_10
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_AFML_11
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_COMMERCIAL
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_APU_DATA
import by.fawwozer.atassist.Global.Companion.KEY_CHEKC_ADDITIONAL_WORKS

class CheckDB(context: Context): SQLiteOpenHelper(context, CHECK_DB_NAME, null, CHECK_DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE " + CHECK_DB_TABLE + "( " +
                    KEY_CHEKC_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                    KEY_CHEKC_NAME + " text, " +
                    KEY_CHEKC_TYPE + " tinyint, " +
                    KEY_CHEKC_AFML_2 + " bit, " +
                    KEY_CHEKC_AFML_3 + " bit, " +
                    KEY_CHEKC_AFML_4 + " bit, " +
                    KEY_CHEKC_AFML_5 + " bit, " +
                    KEY_CHEKC_AFML_6 + " bit, " +
                    KEY_CHEKC_AFML_10 + " bit, " +
                    KEY_CHEKC_AFML_11 + " bit, " +
                    KEY_CHEKC_COMMERCIAL + " bit, " +
                    KEY_CHEKC_APU_DATA + " bit, " +
                    KEY_CHEKC_ADDITIONAL_WORKS + " text" +
                    ");"
        )
        onUpgrade(db,1, CHECK_DB_VERSION)
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