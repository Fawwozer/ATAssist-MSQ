package by.fawwozer.atassist

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import by.fawwozer.atassist.Global.Companion.AIRPORT_DB_NAME
import by.fawwozer.atassist.Global.Companion.AIRPORT_DB_TABLE
import by.fawwozer.atassist.Global.Companion.AIRPORT_DB_VERSION
import by.fawwozer.atassist.Global.Companion.KEY_AIRPORT_CODE
import by.fawwozer.atassist.Global.Companion.KEY_AIRPORT_NAME
import by.fawwozer.atassist.Global.Companion.appContext
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AirportDB : SQLiteOpenHelper(appContext, AIRPORT_DB_NAME, null, AIRPORT_DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE " + AIRPORT_DB_TABLE + "( " + Global.KEY_AIRPORT_ID + " integer PRIMARY KEY AUTOINCREMENT, " + Global.KEY_AIRPORT_NAME + " text, " + Global.KEY_AIRPORT_CODE + " text" + ");"
        )
        onUpgrade(db, 1, AIRPORT_DB_VERSION)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        when (oldVersion) {
            1 -> {
                Log.d("MY", "AirportDB/onUpgrade/oldVer = 1")
            }

            2 -> {
                Log.d("MY", "AirportDB/onUpgrade/oldVer = 2")
            }

            else -> {
                Log.d("MY", "AirportDB/onUpgrade/oldVer else")
            }
        }
    }

    companion object {
        fun loadFromFirestore() {
            Firebase.firestore.collection("AirportBase").get().addOnSuccessListener { result ->
                if (result != null) {

                    val db = AirportDB().writableDatabase

                    for (doc in result) {

                        val cv = ContentValues()

                        cv.put(KEY_AIRPORT_NAME, doc.get("_name").toString())
                        cv.put(KEY_AIRPORT_CODE, doc.get("_code").toString())

                        val cursor = db.query(
                            AIRPORT_DB_TABLE,
                            null,
                            "$KEY_AIRPORT_CODE = '${doc.get("_code").toString()}'",
                            null,
                            null,
                            null,
                            null
                        )

                        if (cursor.moveToFirst()) db.update(
                            AIRPORT_DB_TABLE,
                            cv,
                            "$KEY_AIRPORT_CODE = '${doc.get("_code").toString()}'",
                            null
                        )
                        else db.insert(AIRPORT_DB_TABLE, null, cv)

                        cursor.close()
                    }

                    db.close()
                }
            }
        }

        fun airportNameByCode(code: String): String {
            val cursor = AirportDB().writableDatabase.query(
                AIRPORT_DB_TABLE, null, "$KEY_AIRPORT_CODE  = '$code'", null, null, null, null
            )
            return if (cursor.moveToFirst()) cursor.getString(cursor.getColumnIndex((KEY_AIRPORT_NAME)))
            else code
        }
    }

}