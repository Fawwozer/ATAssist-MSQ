package by.fawwozer.atassist

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import by.fawwozer.atassist.Global.Companion.FIRESTORE_COLLECTION_PLANES
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_ENABLED
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_FUEL
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_HYDRAULIC
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_HYDRAULIC_STEP
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_ID
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_NAME
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_NAME_KOBRA
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_NAME_TYPE
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_OIL
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_OIL_STEP
import by.fawwozer.atassist.Global.Companion.KEY_PLANE_TYPE
import by.fawwozer.atassist.Global.Companion.PLANE_DB_NAME
import by.fawwozer.atassist.Global.Companion.PLANE_DB_TABLE
import by.fawwozer.atassist.Global.Companion.PLANE_DB_VERSION
import com.google.firebase.firestore.FirebaseFirestore

class PlaneDB: SQLiteOpenHelper(Global.appContext, PLANE_DB_NAME, null, PLANE_DB_VERSION) {
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
		onUpgrade(db, 1, PLANE_DB_VERSION)
	}
	
	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
		when (oldVersion) {
			1 -> {
				Log.d("MY", "PlaneDB/onUpgrade/oldVer = 1")
			}
			2 -> {
				Log.d("MY", "PlaneDB/onUpgrade/oldVer = 2")
			}
			else -> {
				Log.d("MY", "PlaneDB/onUpgrade/oldVer else")
			}
		}
	}
	
	companion object {
		fun loadFromFireStore() {
			val firestore = FirebaseFirestore.getInstance()
			firestore.collection(FIRESTORE_COLLECTION_PLANES)
				.get()
				.addOnSuccessListener {documents ->
					if (documents != null) {
						val planeDB = PlaneDB()
						val db = planeDB.writableDatabase
						for (document in documents) {
							if (document.id != "##INFO##") {
								val map: Map<String, Any> = document.data
								val cv = ContentValues()
								cv.put(KEY_PLANE_ID, map[KEY_PLANE_ID] as Long)
								cv.put(KEY_PLANE_NAME, map[KEY_PLANE_NAME] as String)
								cv.put(KEY_PLANE_NAME_KOBRA, map[KEY_PLANE_NAME_KOBRA] as String)
								cv.put(KEY_PLANE_NAME_TYPE, map[KEY_PLANE_NAME_TYPE] as String)
								cv.put(KEY_PLANE_TYPE, map[KEY_PLANE_TYPE] as Long)
								cv.put(KEY_PLANE_FUEL, map[KEY_PLANE_FUEL] as Long)
								cv.put(KEY_PLANE_OIL, map[KEY_PLANE_OIL] as Long)
								try {
									cv.put(KEY_PLANE_OIL_STEP, map[KEY_PLANE_OIL_STEP] as Double)
								} catch (e: ClassCastException) {
									cv.put(KEY_PLANE_OIL_STEP, map[KEY_PLANE_OIL_STEP] as Long)
								}
								cv.put(KEY_PLANE_HYDRAULIC, map[KEY_PLANE_HYDRAULIC] as Long)
								try {
									cv.put(KEY_PLANE_HYDRAULIC_STEP, map[KEY_PLANE_HYDRAULIC_STEP] as Double)
								} catch (e: ClassCastException) {
									cv.put(KEY_PLANE_HYDRAULIC_STEP, map[KEY_PLANE_HYDRAULIC_STEP] as Long)
								}
								cv.put(KEY_PLANE_ENABLED, map[KEY_PLANE_ENABLED] as Boolean)
								val cursor = db.query(PLANE_DB_TABLE, null, KEY_PLANE_ID + " = '" + map[KEY_PLANE_ID] + "'", null, null, null, null)
								if (cursor.moveToFirst()) {
									db.update(PLANE_DB_TABLE, cv, KEY_PLANE_ID + " = '" + map[KEY_PLANE_ID] + "'", null)
								} else {
									db.insert(PLANE_DB_TABLE, null, cv)
								}
								cursor.close()
							}
						}
						db.close()
					}
				}
		}
	}
}