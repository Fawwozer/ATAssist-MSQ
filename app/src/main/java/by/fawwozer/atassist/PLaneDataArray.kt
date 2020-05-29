package by.fawwozer.atassist

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
import by.fawwozer.atassist.Global.Companion.PLANE_DB_TABLE

class PLaneDataArray {
	
	var size = 0
	private var planeDataArray = ArrayList<PlaneData>()
	
	fun createForEnabled() {
		planeDataArray.clear()
		var i = 0
		val planeDB = PlaneDB()
		val db = planeDB.writableDatabase
		val cursor = db.query(PLANE_DB_TABLE, null, null, null, null, null, null)
		if (cursor.moveToFirst()) {
			do {
				if (cursor.getInt(cursor.getColumnIndex(KEY_PLANE_ENABLED)) != 0) {
					val planeData = PlaneData()
					planeData.id = cursor.getInt(cursor.getColumnIndex(KEY_PLANE_ID))
					planeData.name = cursor.getString(cursor.getColumnIndex(KEY_PLANE_NAME))
					planeData.kobraName = cursor.getString(cursor.getColumnIndex(KEY_PLANE_NAME_KOBRA))
					planeData.nameType = cursor.getString(cursor.getColumnIndex(KEY_PLANE_NAME_TYPE))
					planeData.type = cursor.getInt(cursor.getColumnIndex(KEY_PLANE_TYPE))
					planeData.fuel = cursor.getInt(cursor.getColumnIndex(KEY_PLANE_FUEL))
					planeData.oil = cursor.getInt(cursor.getColumnIndex(KEY_PLANE_OIL))
					planeData.oilStep = cursor.getDouble(cursor.getColumnIndex(KEY_PLANE_OIL_STEP))
					planeData.hydraulic = cursor.getInt(cursor.getColumnIndex(KEY_PLANE_HYDRAULIC))
					planeData.hydraulicStep = cursor.getDouble(cursor.getColumnIndex(KEY_PLANE_HYDRAULIC_STEP))
					i++
				}
			} while (cursor.moveToNext())
		}
		cursor.close()
		db.close()
		size = i
	}
	
	fun createForAll() {
		planeDataArray.clear()
		var i = 0
		val planeDB = PlaneDB()
		val db = planeDB.writableDatabase
		val cursor = db.query(PLANE_DB_TABLE, null, null, null, null, null, null)
		if (cursor.moveToFirst()) {
			do {
				val planeData = PlaneData()
				planeData.id = cursor.getInt(cursor.getColumnIndex(KEY_PLANE_ID))
				planeData.name = cursor.getString(cursor.getColumnIndex(KEY_PLANE_NAME))
				planeData.kobraName = cursor.getString(cursor.getColumnIndex(KEY_PLANE_NAME_KOBRA))
				planeData.nameType = cursor.getString(cursor.getColumnIndex(KEY_PLANE_NAME_TYPE))
				planeData.type = cursor.getInt(cursor.getColumnIndex(KEY_PLANE_TYPE))
				planeData.fuel = cursor.getInt(cursor.getColumnIndex(KEY_PLANE_FUEL))
				planeData.oil = cursor.getInt(cursor.getColumnIndex(KEY_PLANE_OIL))
				planeData.oilStep = cursor.getDouble(cursor.getColumnIndex(KEY_PLANE_OIL_STEP))
				planeData.hydraulic = cursor.getInt(cursor.getColumnIndex(KEY_PLANE_HYDRAULIC))
				planeData.hydraulicStep = cursor.getDouble(cursor.getColumnIndex(KEY_PLANE_HYDRAULIC_STEP))
				i++
			} while (cursor.moveToNext())
		}
		cursor.close()
		db.close()
		size = i
	}
	
	fun getPlaneNameList(): Array<String> {
		var planeList = emptyArray<String>()
		planeList += "Choose"
		for (planeData in planeDataArray) planeList += planeData.name
		return planeList
	}
	
	fun getPlaneNameWithTypeList(): Array<String> {
		var planeList = emptyArray<String>()
		planeList += "Choose"
		for (planeData in planeDataArray) planeList += planeData.nameType
		return planeList
	}
	
	fun getPlaneID(pos: Int): Int {
		if (planeDataArray.size == 0) return 0
		return planeDataArray[pos - 1].id
	}
	
	fun getPlaneID(kobra: String): Int {
		if (planeDataArray.size == 0) return 0
		for (planeData in planeDataArray) {
			if (planeData.kobraName == kobra) {
				return planeData.id
			}
		}
		return 0
	}
	
	fun getPosition(id: Int): Int {
		var i = 1
		for (planeData in planeDataArray) {
			if (planeData.id == id) return i
			i++
		}
		return 0
	}
	
	class PlaneData {
		var id: Int = -1
		var name: String = ""
		var kobraName: String = ""
		var nameType: String = ""
		var type: Int = -1
		var fuel: Int = -1
		var oil: Int = -1
		var oilStep: Double = 0.0
		var hydraulic: Int = -1
		var hydraulicStep: Double = 0.0
	}
}