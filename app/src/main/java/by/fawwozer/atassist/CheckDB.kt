package by.fawwozer.atassist

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import by.fawwozer.atassist.Global.Companion.CHECK_DB_NAME
import by.fawwozer.atassist.Global.Companion.CHECK_DB_TABLE
import by.fawwozer.atassist.Global.Companion.CHECK_DB_VERSION
import by.fawwozer.atassist.Global.Companion.FIRESTORE_COLLECTION_CHECKS
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore

class CheckDB() : SQLiteOpenHelper(Global.appContext, CHECK_DB_NAME, null, CHECK_DB_VERSION) {
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
        onUpgrade(db, 1, CHECK_DB_VERSION)
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

    companion object {
        public fun loadFromFireStore() {
            val firebase = FirebaseFirestore.getInstance()
            firebase.collection(FIRESTORE_COLLECTION_CHECKS).get().addOnSuccessListener(
                OnSuccessListener { documents ->
                    if (documents != null) {
                        val checkDB = CheckDB()
                        val db = checkDB.writableDatabase
                        for (document in documents) {
                            if (document.id != "##INFO##")
                                run {
                                    val map: Map<String, Any> = document.data
                                    val cv = ContentValues()
                                    cv.put(KEY_CHEKC_ID, map[KEY_CHEKC_ID] as String)
                                    cv.put(KEY_CHEKC_NAME, map[KEY_CHEKC_NAME] as String)
                                    cv.put(KEY_CHEKC_TYPE, map[KEY_CHEKC_TYPE] as String)
                                    cv.put(KEY_CHEKC_AFML_2, map[KEY_CHEKC_AFML_2] as Boolean)
                                    cv.put(KEY_CHEKC_AFML_3, map[KEY_CHEKC_AFML_3] as Boolean)
                                    cv.put(KEY_CHEKC_AFML_4, map[KEY_CHEKC_AFML_4] as Boolean)
                                    cv.put(KEY_CHEKC_AFML_5, map[KEY_CHEKC_AFML_5] as Boolean)
                                    cv.put(KEY_CHEKC_AFML_6, map[KEY_CHEKC_AFML_6] as Boolean)
                                    cv.put(KEY_CHEKC_AFML_10, map[KEY_CHEKC_AFML_10] as Boolean)
                                    cv.put(KEY_CHEKC_AFML_11, map[KEY_CHEKC_AFML_11] as Boolean)
                                    cv.put(
                                        KEY_CHEKC_COMMERCIAL,
                                        map[KEY_CHEKC_COMMERCIAL] as Boolean
                                    )
                                    cv.put(KEY_CHEKC_APU_DATA, map[KEY_CHEKC_APU_DATA] as Boolean)
                                    cv.put(
                                        KEY_CHEKC_ADDITIONAL_WORKS,
                                        map[KEY_CHEKC_ADDITIONAL_WORKS] as String
                                    )
                                    val cursor = db.query(CHECK_DB_TABLE,null,KEY_CHEKC_ID + " = '" + map[KEY_CHEKC_ID] + "'",null,null,null,null)
                                    if (cursor.moveToFirst())
                                        db.update(CHECK_DB_TABLE, cv, KEY_CHEKC_ID + " = '" + map[KEY_CHEKC_ID] + "'", null)
                                    else db.insert(CHECK_DB_TABLE, null, cv)
                                    cursor.close()
                                }
                        }
                        db.close()
                    }
                }
            )
        }
    }
}