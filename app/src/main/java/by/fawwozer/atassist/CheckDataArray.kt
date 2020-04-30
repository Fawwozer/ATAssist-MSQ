package by.fawwozer.atassist

import android.util.Log
import by.fawwozer.atassist.Global.Companion.CHECK_DB_TABLE
import by.fawwozer.atassist.Global.Companion.GENERAL_FIRST_STRING_SPLITTER
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_ADDITIONAL_WORKS
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_2
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_ID
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_NAME
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_TYPE
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_3
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_4
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_5
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_6
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_10
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_11
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_APU_DATA
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_COMMERCIAL

class CheckDataArray {

    var size = 0
    private var checkDataArray = ArrayList<CheckData>()

    fun createForType(set_type: Int) {
        checkDataArray.clear()
        var i = 0
        val context = Global.appContext
        val checkDB = CheckDB()
        val db = checkDB.writableDatabase
        val cursor = db.query(CHECK_DB_TABLE, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val TYPES = cursor.getString(cursor.getColumnIndex(KEY_CHECK_TYPE))
                val types = TYPES.split(GENERAL_FIRST_STRING_SPLITTER)
                for (load_type in types) {
                    if (load_type.toInt() == set_type) {
                        val checkData = CheckData()
                        checkData.id = cursor.getInt(cursor.getColumnIndex(KEY_CHECK_ID))
                        checkData.name = cursor.getString(cursor.getColumnIndex(KEY_CHECK_NAME))
                        checkData.afml_2 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_2)) != 0)
                        checkData.afml_3 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_3)) != 0)
                        checkData.afml_4 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_4)) != 0)
                        checkData.afml_5 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_5)) != 0)
                        checkData.afml_6 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_6)) != 0)
                        checkData.afml_10 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_10)) != 0)
                        checkData.afml_11 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_11)) != 0)
                        checkData.apu = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_APU_DATA)) != 0)
                        checkData.commercial = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_COMMERCIAL)) != 0)
                        checkData.additionalWorks = cursor.getString(cursor.getColumnIndex(KEY_CHECK_ADDITIONAL_WORKS))
                        checkDataArray.add(checkData)
                        i++
                        break
                    }
                }

            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        size = i
    }

    fun createForAll() {
        checkDataArray.clear()
        var i = 0
        val checkDB = CheckDB()
        val db = checkDB.writableDatabase
        val cursor = db.query(CHECK_DB_TABLE, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val checkData = CheckData()
                checkData.id = cursor.getInt(cursor.getColumnIndex(KEY_CHECK_ID))
                checkData.name = cursor.getString(cursor.getColumnIndex(KEY_CHECK_NAME))
                checkData.afml_2 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_2)) != 0)
                checkData.afml_3 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_3)) != 0)
                checkData.afml_4 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_4)) != 0)
                checkData.afml_5 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_5)) != 0)
                checkData.afml_6 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_6)) != 0)
                checkData.afml_10 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_10)) != 0)
                checkData.afml_11 = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_AFML_11)) != 0)
                checkData.apu = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_APU_DATA)) != 0)
                checkData.commercial = (cursor.getInt(cursor.getColumnIndex(KEY_CHECK_COMMERCIAL)) != 0)
                checkData.additionalWorks = cursor.getString(cursor.getColumnIndex(KEY_CHECK_ADDITIONAL_WORKS))
                checkDataArray.add(checkData)
                i++
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        size = i
    }

    fun getChecksList(): Array<String> {
        var checksList = emptyArray<String>()
        checksList += "Choose"
        for (checkData in checkDataArray) checksList += checkData.name
        return checksList
    }

    fun getCheckID(pos: Int): Int {
        if (checkDataArray.size != 0) return checkDataArray[pos].id
        else return -1
    }

    class CheckData {
        var id: Int = -1
            get() = field
            set(value) {
                field = value
            }
        var name: String = ""
            get() = field
            set(value) {
                field = value
            }
        var afml_2: Boolean = false
            get() = field
            set(value) {
                field = value
            }
        var afml_3: Boolean = false
            get() = field
            set(value) {
                field = value
            }
        var afml_4: Boolean = false
            get() = field
            set(value) {
                field = value
            }
        var afml_5: Boolean = false
            get() = field
            set(value) {
                field = value
            }
        var afml_6: Boolean = false
            get() = field
            set(value) {
                field = value
            }
        var afml_10: Boolean = false
            get() = field
            set(value) {
                field = value
            }
        var afml_11: Boolean = false
            get() = field
            set(value) {
                field = value
            }
        var commercial: Boolean = false
            get() = field
            set(value) {
                field = value
            }
        var apu: Boolean = false
            get() = field
            set(value) {
                field = value
            }
        var additionalWorks: String = ""
            get() = field
            set(value) {
                field = value
            }
    }
}