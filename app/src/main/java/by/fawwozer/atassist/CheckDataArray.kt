package by.fawwozer.atassist

import by.fawwozer.atassist.Global.Companion.CHECK_DB_TABLE
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_ADDITIONAL_WORKS
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_10
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_11
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_2
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_3
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_4
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_5
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_AFML_6
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_APU_DATA
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_COMMERCIAL
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_ID
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_NAME
import by.fawwozer.atassist.Global.Companion.KEY_CHECK_TYPE
import by.fawwozer.atassist.Global.Companion.MAIN_TEXT_SPLITTER

class CheckDataArray {

    var size = 0
    private var checkDataArray = ArrayList<CheckData>()

    //создание массива данных для всех чеков отределённого типа ВС (Type)
    fun createForType(set_type: Int) {
        checkDataArray.clear()
        var i = 0
        val checkDB = CheckDB()
        val db = checkDB.writableDatabase
        val cursor = db.query(CHECK_DB_TABLE, null, null, null, null, null, KEY_CHECK_ID) //получение данных из базы данных чеков
        if (cursor.moveToFirst()) {
            do {
                val types = cursor.getString(cursor.getColumnIndex(KEY_CHECK_TYPE)) //получение строки типов для которых применим этот чек: "1,|,2,|,3"
                val type = types.split(MAIN_TEXT_SPLITTER)          //деление строки
                for (load_type in type) {                                          //перебор всех типов
                    if (load_type.toInt() == set_type) {                                  //если типы совпадают добавляеться запись об этом чеке
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
        size = i        //запись размера массива
    }
    
    //создание массива данных для всех чеков всех типов ВС
    fun createForAll() {
        checkDataArray.clear()
        var i = 0
        val checkDB = CheckDB()
        val db = checkDB.writableDatabase
        val cursor = db.query(CHECK_DB_TABLE, null, null, null, null, null, KEY_CHECK_ID)   //получение данных из базы данных чеков
        if (cursor.moveToFirst()) {                                                                                                     //добавление данных о чеке
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
        size = i        //запись размера массива
    }

    //получение массива имен чеков
    fun getChecksList(): Array<String> {
        var checksList = emptyArray<String>()
        checksList += "Choose"
        for (checkData in checkDataArray) checksList += checkData.name
        return checksList
    }

    //получение ID чека по позиции в Spinner
    fun getCheckID(pos: Int): Int {
        if (checkDataArray.size == 0) return 0
        if (pos == 0) return -1
        return checkDataArray[pos - 1].id
    }
    
    //получение позиции чека в массиве для установки в Spinner
    fun getPosition(id: Int): Int {
        var i = 1
        for (checkData in checkDataArray) {
            if (checkData.id == id) return i
            i++
        }
        return 0
    }
    
    fun getCheckName(id: Int): String {
        var i = 1
        for (checkData in checkDataArray) {
            if (checkData.id == id) return checkData.name
            i++
        }
        return ""
    }
    
    //класс данных о чеке
    class CheckData {
        var id: Int = -1
        var name: String = ""
        var afml_2: Boolean = false
        var afml_3: Boolean = false
        var afml_4: Boolean = false
        var afml_5: Boolean = false
        var afml_6: Boolean = false
        var afml_10: Boolean = false
        var afml_11: Boolean = false
        var commercial: Boolean = false
        var apu: Boolean = false
        var additionalWorks: String = ""
    }
}