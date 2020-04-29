package by.fawwozer.atassist

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import by.fawwozer.atassist.Global.Companion.CHECK_DB_NAME
import by.fawwozer.atassist.Global.Companion.CHECK_DB_TABLE
import by.fawwozer.atassist.Global.Companion.CHECK_DB_VERSION

class CheckDB(context: Context): SQLiteOpenHelper(context, CHECK_DB_NAME, null, CHECK_DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE " + CHECK_DB_TABLE + "( " +
                    //KEY_LOGS_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
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