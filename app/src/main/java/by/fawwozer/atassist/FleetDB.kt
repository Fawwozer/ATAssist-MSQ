package by.fawwozer.atassist

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import by.fawwozer.atassist.Global.Companion.FIRESTORE_COLLECTION_FLEET
import by.fawwozer.atassist.Global.Companion.FLEET_DB_NAME
import by.fawwozer.atassist.Global.Companion.FLEET_DB_TABLE
import by.fawwozer.atassist.Global.Companion.FLEET_DB_VERSION
import by.fawwozer.atassist.Global.Companion.KEY_FLEET_ID
import by.fawwozer.atassist.Global.Companion.KEY_FLEET_PLANE
import by.fawwozer.atassist.Global.Companion.KEY_FLEET_STATUS
import by.fawwozer.atassist.Global.Companion.KEY_FLEET_DATE
import by.fawwozer.atassist.Global.Companion.KEY_FLEET_TILL
import by.fawwozer.atassist.Global.Companion.KEY_FLEET_HEADER
import by.fawwozer.atassist.Global.Companion.KEY_FLEET_MESSAGE
import com.google.firebase.firestore.FirebaseFirestore

class FleetDB() : SQLiteOpenHelper(Global.appContext, FLEET_DB_NAME, null, FLEET_DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE " + FLEET_DB_TABLE + " ( " +
                    KEY_FLEET_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                    KEY_FLEET_PLANE + " integer, " +
                    KEY_FLEET_STATUS + " integer, " +
                    KEY_FLEET_DATE + " long, " +
                    KEY_FLEET_TILL + " long, " +
                    KEY_FLEET_HEADER + " text, " +
                    KEY_FLEET_MESSAGE + " text" +
                    ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        when (oldVersion) {
            1 -> {
                Log.d("MY", "FleetDB/onUpgrade/oldVer = 1")
            }
            2 -> {
                Log.d("MY", "FleetDB/onUpgrade/oldVer = 2")
            }
            else -> {
                Log.d("MY", "FleetDB/onUpgrade/oldVer else")
            }
        }
    }

    companion object {
        fun loadFromFireStore() {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection(FIRESTORE_COLLECTION_FLEET)
                .addSnapshotListener { documents, error ->
                    if (error != null) {
                        Log.d("MY", "FleetDB/loadFromFireStore/addSnapshotListener/Error $error")
                        return@addSnapshotListener
                    }

                    if (documents != null) {
                        val fleetDB = FleetDB()
                        val db = fleetDB.writableDatabase
                        for (document in documents) {
                            if (document.id != "##INFO##") {
                                val map: Map<String, Any> = document.data
                                val cv = ContentValues()
                                cv.put(KEY_FLEET_ID, map[KEY_FLEET_ID] as Long)
                                cv.put(KEY_FLEET_PLANE, map[KEY_FLEET_PLANE] as Long)
                                cv.put(KEY_FLEET_STATUS, map[KEY_FLEET_STATUS] as Long)
                                cv.put(KEY_FLEET_DATE, map[KEY_FLEET_DATE] as Long)
                                cv.put(KEY_FLEET_TILL, map[KEY_FLEET_TILL] as Long)
                                cv.put(KEY_FLEET_HEADER, map[KEY_FLEET_HEADER] as String)
                                cv.put(KEY_FLEET_MESSAGE, map[KEY_FLEET_MESSAGE] as String)
                                val cursor = db.query(FLEET_DB_TABLE, null, KEY_FLEET_ID + " = '" + map[KEY_FLEET_ID] + "'", null, null, null, null)
                                if (cursor.moveToFirst()) db.update(FLEET_DB_TABLE, cv, KEY_FLEET_ID + " = '" + map[KEY_FLEET_ID] + "'", null)
                                else db.insert(FLEET_DB_TABLE, null, cv)
                                cursor.close()
                            }
                        }
                        db.close()

                        //показать оповещение

                        if (documents.size() > 1) {
                            //изменилось несколько записей
                        } else {
                            //изменилась одна запись
                        }
                    }
                }
        }
    }
}