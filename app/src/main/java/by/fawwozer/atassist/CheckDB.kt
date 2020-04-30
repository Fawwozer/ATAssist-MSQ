package by.fawwozer.atassist

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import by.fawwozer.atassist.Global.Companion.CHECK_DB_NAME
import by.fawwozer.atassist.Global.Companion.CHECK_DB_TABLE
import by.fawwozer.atassist.Global.Companion.CHECK_DB_VERSION
import by.fawwozer.atassist.Global.Companion.FIRESTORE_COLLECTION_CHECKS
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_ID
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_NAME
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_TYPE
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_2
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_3
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_4
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_5
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_6
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_10
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_11
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_COMMERCIAL
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_APU_DATA
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_ADDITIONAL_WORKS
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore

class CheckDB() : SQLiteOpenHelper(Global.appContext, CHECK_DB_NAME, null, CHECK_DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("MY", "CheckDB/onCreate/Start")
        db!!.execSQL(
            "CREATE TABLE " + CHECK_DB_TABLE + " ( " +
                    KEY_CHECK_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                    KEY_CHECK_NAME + " text, " +
                    KEY_CHECK_TYPE + " tinyint, " +
                    KEY_CHECK_AFML_2 + " bit, " +
                    KEY_CHECK_AFML_3 + " bit, " +
                    KEY_CHECK_AFML_4 + " bit, " +
                    KEY_CHECK_AFML_5 + " bit, " +
                    KEY_CHECK_AFML_6 + " bit, " +
                    KEY_CHECK_AFML_10 + " bit, " +
                    KEY_CHECK_AFML_11 + " bit, " +
                    KEY_CHECK_COMMERCIAL + " bit, " +
                    KEY_CHECK_APU_DATA + " bit, " +
                    KEY_CHECK_ADDITIONAL_WORKS + " text" +
                    ");"
        )
        onUpgrade(db, 1, CHECK_DB_VERSION)
        Log.d("MY", "CheckDB/onCreate/Finish")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("MY", "CheckDB/onUpgrade/Start")
        when (oldVersion) {
            1 -> {
                Log.d("MY", "CheckDB/onUpgrade/oldVer = 1")
            }
            2 -> {
                Log.d("MY", "CheckDB/onUpgrade/oldVer = 2")
            }
            else -> {
                Log.d("MY", "CheckDB/onUpgrade/oldVer else")
            }
        }
        Log.d("MY", "CheckDB/onUpgrade/Finish")
    }

    companion object {
        fun loadFromFireStore() {
            Log.d("MY", "CheckDB/loadFromFireStore/Start")
            val firestore = FirebaseFirestore.getInstance()
            Log.d("MY", "CheckDB/loadFromFireStore/firestore = " + firestore)
            firestore.collection(FIRESTORE_COLLECTION_CHECKS).get().addOnSuccessListener(
                OnSuccessListener { documents ->
                    if (documents != null) {
                        Log.d("MY", "CheckDB/loadFromFireStore/documents != null")
                        val checkDB = CheckDB()
                        val db = checkDB.writableDatabase
                        for (document in documents) {
                            Log.d("MY", "CheckDB/loadFromFireStore/document.id = " + document.id)
                            if (document.id != "##INFO##")
                                {
                                    val map: Map<String, Any> = document.data
                                    val cv = ContentValues()
                                    cv.put(KEY_CHECK_ID, map[KEY_CHECK_ID] as Long)
                                    cv.put(KEY_CHECK_NAME, map[KEY_CHECK_NAME] as String)
                                    cv.put(KEY_CHECK_TYPE, map[KEY_CHECK_TYPE] as String)
                                    cv.put(KEY_CHECK_AFML_2, map[KEY_CHECK_AFML_2] as Boolean)
                                    cv.put(KEY_CHECK_AFML_3, map[KEY_CHECK_AFML_3] as Boolean)
                                    cv.put(KEY_CHECK_AFML_4, map[KEY_CHECK_AFML_4] as Boolean)
                                    cv.put(KEY_CHECK_AFML_5, map[KEY_CHECK_AFML_5] as Boolean)
                                    cv.put(KEY_CHECK_AFML_6, map[KEY_CHECK_AFML_6] as Boolean)
                                    cv.put(KEY_CHECK_AFML_10, map[KEY_CHECK_AFML_10] as Boolean)
                                    cv.put(KEY_CHECK_AFML_11, map[KEY_CHECK_AFML_11] as Boolean)
                                    cv.put(KEY_CHECK_COMMERCIAL,map[KEY_CHECK_COMMERCIAL] as Boolean)
                                    cv.put(KEY_CHECK_APU_DATA, map[KEY_CHECK_APU_DATA] as Boolean)
                                    cv.put(KEY_CHECK_ADDITIONAL_WORKS,map[KEY_CHECK_ADDITIONAL_WORKS] as String)
                                    val cursor = db.query(CHECK_DB_TABLE,null,KEY_CHECK_ID + " = '" + map[KEY_CHECK_ID] + "'",null,null,null,null)
                                    if (cursor.moveToFirst()){
                                        db.update(CHECK_DB_TABLE, cv, KEY_CHECK_ID + " = '" + map[KEY_CHECK_ID] + "'", null)
                                        Log.d("MY","CheckDB/loadFromFireStore/Upgrade data in DB")
                                    }
                                    else {
                                        db.insert(CHECK_DB_TABLE, null, cv)
                                        Log.d("MY","CheckDB/loadFromFireStore/Create data in DB")
                                    }
                                    cursor.close()
                                }
                        }
                        db.close()
                    }
                }
            )
            Log.d("MY", "CheckDB/loadFromFireStore/Finish")
        }
    }
}