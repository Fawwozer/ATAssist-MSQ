package by.fawwozer.atassist

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_FLIGHT_DESTINATION
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_FLIGHT_NUMBER
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_ID
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_PLANE
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_STAND
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_STATUS
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_TIME_EXPECT
import by.fawwozer.atassist.Global.Companion.KEY_KOBRA_TIME_PLANED
import by.fawwozer.atassist.Global.Companion.KOBRA_DB_TABLE_ARRIVAL
import by.fawwozer.atassist.Global.Companion.KOBRA_DB_TABLE_DEPARTURE
import by.fawwozer.atassist.Global.Companion.PREFERENCE_LOGIN_TOKEN
import by.fawwozer.atassist.Global.Companion.SETTING_KOBRA_LOGIN
import by.fawwozer.atassist.Global.Companion.SETTING_KOBRA_PASSWORD
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class GetKobraSPP {
	
	companion object {
		
		fun get() = runBlocking {
			
			val preference: SharedPreferences = Global.appContext.getSharedPreferences(Global.PREFERENCE_FILE, Context.MODE_PRIVATE)
			
			val token = preference.getString(PREFERENCE_LOGIN_TOKEN, "n/a")
			val yesterday = LocalDate.now()
				.plusDays(-1)
				.format(DateTimeFormatter.ISO_LOCAL_DATE)
			val today = LocalDate.now()
				.format(DateTimeFormatter.ISO_LOCAL_DATE)
			val tomorrow = LocalDate.now()
				.plusDays(1)
				.format(DateTimeFormatter.ISO_LOCAL_DATE)
			val flightPlanURL = "https://spp.airport.by/api/flight/plan"
			
			val yesterdayArrivalRequest = Request.Builder()
				.url("$flightPlanURL?date=$yesterday&scheduleType=&direction=arr&locale=ru&withHeaders=true&timezone=false")
				.header("Authorization", "Bearer $token")
				.build()
			val todayArrivalRequest = Request.Builder()
				.url("$flightPlanURL?date=$today&scheduleType=&direction=arr&locale=ru&withHeaders=true&timezone=false")
				.header("Authorization", "Bearer $token")
				.build()
			val tomorrowArrivalRequest = Request.Builder()
				.url("$flightPlanURL?date=$tomorrow&scheduleType=&direction=arr&locale=ru&withHeaders=true&timezone=false")
				.header("Authorization", "Bearer $token")
				.build()
			
			val yesterdayDepartureRequest = Request.Builder()
				.url("$flightPlanURL?date=$yesterday&scheduleType=&direction=dep&locale=ru&withHeaders=true&timezone=false")
				.header("Authorization", "Bearer $token")
				.build()
			val todayDepartureRequest = Request.Builder()
				.url("$flightPlanURL?date=$today&scheduleType=&direction=dep&locale=ru&withHeaders=true&timezone=false")
				.header("Authorization", "Bearer $token")
				.build()
			val tomorrowDepartureRequest = Request.Builder()
				.url("$flightPlanURL?date=$tomorrow&scheduleType=&direction=dep&locale=ru&withHeaders=true&timezone=false")
				.header("Authorization", "Bearer $token")
				.build()
			
			OkHttpClient().newCall(yesterdayArrivalRequest)
				.enqueue(object: Callback {
					override fun onFailure(call: Call, e: IOException) {
					}
					
					override fun onResponse(call: Call, response: Response) {
						if (response.code != 200) {
							launch {login()}
						} else {
							val array = response.body?.string()
								?.let {JSONObject(it).getJSONArray("items")}
							if (array != null) {
								for (i in 0 until array.length()) {
									val cv = ContentValues()
									cv.put(KEY_KOBRA_ID, array.getJSONObject(i)
										.getLong("id_spp"))
									cv.put(KEY_KOBRA_PLANE, array.getJSONObject(i)
										.getString("bort"))
									cv.put(KEY_KOBRA_FLIGHT_NUMBER, array.getJSONObject(i)
										.getString("nr"))
									cv.put(KEY_KOBRA_FLIGHT_DESTINATION, array.getJSONObject(i)
										.getString("ap"))
									cv.put(KEY_KOBRA_STAND, array.getJSONObject(i)
										.getString("nst"))
									cv.put(KEY_KOBRA_STATUS, array.getJSONObject(i)
										.getString("status"))
									
									val calendar = Calendar.getInstance()
									calendar.add(Calendar.DAY_OF_YEAR, -1)
									calendar.set(Calendar.SECOND, 0)
									calendar.set(Calendar.MILLISECOND, 0)
									
									calendar.set(Calendar.HOUR_OF_DAY, array.getJSONObject(i)
										.getString("vpp")
										.split(":")[0].toInt())
									calendar.set(Calendar.MINUTE, array.getJSONObject(i)
										.getString("vpp")
										.split(":")[1].toInt())
									cv.put(KEY_KOBRA_TIME_PLANED, calendar.timeInMillis)
									
									val landed: Long = if (array.getJSONObject(i)
											.getString("vpf") != ""
									) {
										calendar.set(Calendar.HOUR_OF_DAY, array.getJSONObject(i)
											.getString("vpf")
											.split(":")[0].toInt())
										calendar.set(Calendar.MINUTE, array.getJSONObject(i)
											.getString("vpf")
											.split(":")[1].toInt())
										calendar.timeInMillis
									} else 0
									
									val expect: Long = if (array.getJSONObject(i)
											.getString("vpem_loc") != ""
									) {
										val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
										val cal = Calendar.getInstance()
										cal.set(Calendar.SECOND, 0)
										cal.set(Calendar.MILLISECOND, 0)
										cal.time = format.parse(array.getJSONObject(i)
											.getString("vpem_loc")) as Date
										cal.timeInMillis
									} else 0
									
									if (landed != 0.toLong()) cv.put(KEY_KOBRA_TIME_EXPECT, landed)
									else if (expect != 0.toLong()) cv.put(KEY_KOBRA_TIME_EXPECT, expect)
									else cv.put(KEY_KOBRA_TIME_EXPECT, 0)
									
									val db = KobraDB().writableDatabase
									val cur = db.query(KOBRA_DB_TABLE_ARRIVAL, null, "$KEY_KOBRA_ID = " + array.getJSONObject(i)
										.getLong("id_spp"), null, null, null, null)
									if (cur.moveToFirst()) db.update(KOBRA_DB_TABLE_ARRIVAL, cv, "$KEY_KOBRA_ID=" + array.getJSONObject(i)
										.getLong("id_spp"), null)
									else db.insert(KOBRA_DB_TABLE_ARRIVAL, null, cv)
									cur.close()
								}
							}
						}
					}
				})
			delay(500)
			OkHttpClient().newCall(todayArrivalRequest)
				.enqueue(object: Callback {
					override fun onFailure(call: Call, e: IOException) {
					}
					
					override fun onResponse(call: Call, response: Response) {
						if (response.code != 200) {
							launch {login()}
						} else {
							val array = response.body?.string()
								?.let {JSONObject(it).getJSONArray("items")}
							if (array != null) {
								for (i in 0 until array.length()) {
									val cv = ContentValues()
									cv.put(KEY_KOBRA_ID, array.getJSONObject(i)
										.getLong("id_spp"))
									cv.put(KEY_KOBRA_PLANE, array.getJSONObject(i)
										.getString("bort"))
									cv.put(KEY_KOBRA_FLIGHT_NUMBER, array.getJSONObject(i)
										.getString("nr"))
									cv.put(KEY_KOBRA_FLIGHT_DESTINATION, array.getJSONObject(i)
										.getString("ap"))
									cv.put(KEY_KOBRA_STAND, array.getJSONObject(i)
										.getString("nst"))
									cv.put(KEY_KOBRA_STATUS, array.getJSONObject(i)
										.getString("status"))
									
									val calendar = Calendar.getInstance()
									calendar.set(Calendar.SECOND, 0)
									calendar.set(Calendar.MILLISECOND, 0)
									
									calendar.set(Calendar.HOUR_OF_DAY, array.getJSONObject(i)
										.getString("vpp")
										.split(":")[0].toInt())
									calendar.set(Calendar.MINUTE, array.getJSONObject(i)
										.getString("vpp")
										.split(":")[1].toInt())
									cv.put(KEY_KOBRA_TIME_PLANED, calendar.timeInMillis)
									
									val landed: Long = if (array.getJSONObject(i)
											.getString("vpf") != ""
									) {
										calendar.set(Calendar.HOUR_OF_DAY, array.getJSONObject(i)
											.getString("vpf")
											.split(":")[0].toInt())
										calendar.set(Calendar.MINUTE, array.getJSONObject(i)
											.getString("vpf")
											.split(":")[1].toInt())
										calendar.timeInMillis
									} else 0
									
									val expect: Long = if (array.getJSONObject(i)
											.getString("vpem_loc") != ""
									) {
										val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
										val cal = Calendar.getInstance()
										cal.set(Calendar.SECOND, 0)
										cal.set(Calendar.MILLISECOND, 0)
										cal.time = format.parse(array.getJSONObject(i)
											.getString("vpem_loc")) as Date
										cal.timeInMillis
									} else 0
									
									if (landed != 0.toLong()) cv.put(KEY_KOBRA_TIME_EXPECT, landed)
									else if (expect != 0.toLong()) cv.put(KEY_KOBRA_TIME_EXPECT, expect)
									else cv.put(KEY_KOBRA_TIME_EXPECT, 0)
									
									val db = KobraDB().writableDatabase
									val cur = db.query(KOBRA_DB_TABLE_ARRIVAL, null, "$KEY_KOBRA_ID = " + array.getJSONObject(i)
										.getLong("id_spp"), null, null, null, null)
									if (cur.moveToFirst()) db.update(KOBRA_DB_TABLE_ARRIVAL, cv, "$KEY_KOBRA_ID=" + array.getJSONObject(i)
										.getLong("id_spp"), null)
									else db.insert(KOBRA_DB_TABLE_ARRIVAL, null, cv)
									cur.close()
								}
							}
						}
					}
				})
			delay(500)
			OkHttpClient().newCall(tomorrowArrivalRequest)
				.enqueue(object: Callback {
					override fun onFailure(call: Call, e: IOException) {
					}
					
					override fun onResponse(call: Call, response: Response) {
						if (response.code != 200) {
							launch {login()}
						} else {
							val array = response.body?.string()
								?.let {JSONObject(it).getJSONArray("items")}
							if (array != null) {
								for (i in 0 until array.length()) {
									val cv = ContentValues()
									cv.put(KEY_KOBRA_ID, array.getJSONObject(i)
										.getLong("id_spp"))
									cv.put(KEY_KOBRA_PLANE, array.getJSONObject(i)
										.getString("bort"))
									cv.put(KEY_KOBRA_FLIGHT_NUMBER, array.getJSONObject(i)
										.getString("nr"))
									cv.put(KEY_KOBRA_FLIGHT_DESTINATION, array.getJSONObject(i)
										.getString("ap"))
									cv.put(KEY_KOBRA_STAND, array.getJSONObject(i)
										.getString("nst"))
									cv.put(KEY_KOBRA_STATUS, array.getJSONObject(i)
										.getString("status"))
									
									val calendar = Calendar.getInstance()
									calendar.add(Calendar.DAY_OF_YEAR, 1)
									calendar.set(Calendar.SECOND, 0)
									calendar.set(Calendar.MILLISECOND, 0)
									
									calendar.set(Calendar.HOUR_OF_DAY, array.getJSONObject(i)
										.getString("vpp")
										.split(":")[0].toInt())
									calendar.set(Calendar.MINUTE, array.getJSONObject(i)
										.getString("vpp")
										.split(":")[1].toInt())
									cv.put(KEY_KOBRA_TIME_PLANED, calendar.timeInMillis)
									
									val landed: Long = if (array.getJSONObject(i)
											.getString("vpf") != ""
									) {
										calendar.set(Calendar.HOUR_OF_DAY, array.getJSONObject(i)
											.getString("vpf")
											.split(":")[0].toInt())
										calendar.set(Calendar.MINUTE, array.getJSONObject(i)
											.getString("vpf")
											.split(":")[1].toInt())
										calendar.timeInMillis
									} else 0
									
									val expect: Long = if (array.getJSONObject(i)
											.getString("vpem_loc") != ""
									) {
										val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
										val cal = Calendar.getInstance()
										cal.set(Calendar.SECOND, 0)
										cal.set(Calendar.MILLISECOND, 0)
										cal.time = format.parse(array.getJSONObject(i)
											.getString("vpem_loc")) as Date
										cal.timeInMillis
									} else 0
									
									if (landed != 0.toLong()) cv.put(KEY_KOBRA_TIME_EXPECT, landed)
									else if (expect != 0.toLong()) cv.put(KEY_KOBRA_TIME_EXPECT, expect)
									else cv.put(KEY_KOBRA_TIME_EXPECT, 0)
									
									val db = KobraDB().writableDatabase
									val cur = db.query(KOBRA_DB_TABLE_ARRIVAL, null, "$KEY_KOBRA_ID = " + array.getJSONObject(i)
										.getLong("id_spp"), null, null, null, null)
									if (cur.moveToFirst()) db.update(KOBRA_DB_TABLE_ARRIVAL, cv, "$KEY_KOBRA_ID=" + array.getJSONObject(i)
										.getLong("id_spp"), null)
									else db.insert(KOBRA_DB_TABLE_ARRIVAL, null, cv)
									cur.close()
								}
							}
						}
					}
				})
			delay(500)
			
			OkHttpClient().newCall(yesterdayDepartureRequest)
				.enqueue(object: Callback {
					override fun onFailure(call: Call, e: IOException) {
					}
					
					override fun onResponse(call: Call, response: Response) {
						if (response.code != 200) {
							launch {login()}
						} else {
							val array = response.body?.string()
								?.let {JSONObject(it).getJSONArray("items")}
							if (array != null) {
								for (i in 0 until array.length()) {
									val cv = ContentValues()
									cv.put(KEY_KOBRA_ID, array.getJSONObject(i)
										.getLong("id_spp"))
									cv.put(KEY_KOBRA_PLANE, array.getJSONObject(i)
										.getString("bort"))
									cv.put(KEY_KOBRA_FLIGHT_NUMBER, array.getJSONObject(i)
										.getString("nr"))
									cv.put(KEY_KOBRA_FLIGHT_DESTINATION, array.getJSONObject(i)
										.getString("ap"))
									cv.put(KEY_KOBRA_STAND, array.getJSONObject(i)
										.getString("nst"))
									cv.put(KEY_KOBRA_STATUS, array.getJSONObject(i)
										.getString("status"))
									
									val calendar = Calendar.getInstance()
									calendar.add(Calendar.DAY_OF_YEAR, -1)
									calendar.set(Calendar.SECOND, 0)
									calendar.set(Calendar.MILLISECOND, 0)
									
									calendar.set(Calendar.HOUR_OF_DAY, array.getJSONObject(i)
										.getString("vop")
										.split(":")[0].toInt())
									calendar.set(Calendar.MINUTE, array.getJSONObject(i)
										.getString("vop")
										.split(":")[1].toInt())
									cv.put(KEY_KOBRA_TIME_PLANED, calendar.timeInMillis)
									
									if (array.getJSONObject(i)
											.getString("vof") != ""
									) {
										calendar.set(Calendar.HOUR_OF_DAY, array.getJSONObject(i)
											.getString("vof")
											.split(":")[0].toInt())
										calendar.set(Calendar.MINUTE, array.getJSONObject(i)
											.getString("vof")
											.split(":")[1].toInt())
										cv.put(KEY_KOBRA_TIME_EXPECT, calendar.timeInMillis)
									} else cv.put(KEY_KOBRA_TIME_EXPECT, 0)
									
									val db = KobraDB().writableDatabase
									val cur = db.query(KOBRA_DB_TABLE_DEPARTURE, null, "$KEY_KOBRA_ID = " + array.getJSONObject(i)
										.getLong("id_spp"), null, null, null, null)
									if (cur.moveToFirst()) db.update(KOBRA_DB_TABLE_DEPARTURE, cv, "$KEY_KOBRA_ID=" + array.getJSONObject(i)
										.getLong("id_spp"), null)
									else db.insert(KOBRA_DB_TABLE_DEPARTURE, null, cv)
									cur.close()
								}
							}
						}
					}
				})
			delay(500)
			OkHttpClient().newCall(todayDepartureRequest)
				.enqueue(object: Callback {
					override fun onFailure(call: Call, e: IOException) {
					}
					
					override fun onResponse(call: Call, response: Response) {
						if (response.code != 200) {
							launch {login()}
						} else {
							val array = response.body?.string()
								?.let {JSONObject(it).getJSONArray("items")}
							if (array != null) {
								for (i in 0 until array.length()) {
									val cv = ContentValues()
									cv.put(KEY_KOBRA_ID, array.getJSONObject(i)
										.getLong("id_spp"))
									cv.put(KEY_KOBRA_PLANE, array.getJSONObject(i)
										.getString("bort"))
									cv.put(KEY_KOBRA_FLIGHT_NUMBER, array.getJSONObject(i)
										.getString("nr"))
									cv.put(KEY_KOBRA_FLIGHT_DESTINATION, array.getJSONObject(i)
										.getString("ap"))
									cv.put(KEY_KOBRA_STAND, array.getJSONObject(i)
										.getString("nst"))
									cv.put(KEY_KOBRA_STATUS, array.getJSONObject(i)
										.getString("status"))
									
									val calendar = Calendar.getInstance()
									calendar.set(Calendar.SECOND, 0)
									calendar.set(Calendar.MILLISECOND, 0)
									
									calendar.set(Calendar.HOUR_OF_DAY, array.getJSONObject(i)
										.getString("vop")
										.split(":")[0].toInt())
									calendar.set(Calendar.MINUTE, array.getJSONObject(i)
										.getString("vop")
										.split(":")[1].toInt())
									cv.put(KEY_KOBRA_TIME_PLANED, calendar.timeInMillis)
									
									if (array.getJSONObject(i)
											.getString("vof") != ""
									) {
										calendar.set(Calendar.HOUR_OF_DAY, array.getJSONObject(i)
											.getString("vof")
											.split(":")[0].toInt())
										calendar.set(Calendar.MINUTE, array.getJSONObject(i)
											.getString("vof")
											.split(":")[1].toInt())
										cv.put(KEY_KOBRA_TIME_EXPECT, calendar.timeInMillis)
									} else cv.put(KEY_KOBRA_TIME_EXPECT, 0)
									
									val db = KobraDB().writableDatabase
									val cur = db.query(KOBRA_DB_TABLE_DEPARTURE, null, "$KEY_KOBRA_ID = " + array.getJSONObject(i)
										.getLong("id_spp"), null, null, null, null)
									if (cur.moveToFirst()) db.update(KOBRA_DB_TABLE_DEPARTURE, cv, "$KEY_KOBRA_ID=" + array.getJSONObject(i)
										.getLong("id_spp"), null)
									else db.insert(KOBRA_DB_TABLE_DEPARTURE, null, cv)
									cur.close()
								}
							}
						}
					}
				})
			delay(500)
			OkHttpClient().newCall(tomorrowDepartureRequest)
				.enqueue(object: Callback {
					override fun onFailure(call: Call, e: IOException) {
					}
					
					override fun onResponse(call: Call, response: Response) {
						if (response.code != 200) {
							launch {login()}
						} else {
							val array = response.body?.string()
								?.let {JSONObject(it).getJSONArray("items")}
							if (array != null) {
								for (i in 0 until array.length()) {
									val cv = ContentValues()
									cv.put(KEY_KOBRA_ID, array.getJSONObject(i)
										.getLong("id_spp"))
									cv.put(KEY_KOBRA_PLANE, array.getJSONObject(i)
										.getString("bort"))
									cv.put(KEY_KOBRA_FLIGHT_NUMBER, array.getJSONObject(i)
										.getString("nr"))
									cv.put(KEY_KOBRA_FLIGHT_DESTINATION, array.getJSONObject(i)
										.getString("ap"))
									cv.put(KEY_KOBRA_STAND, array.getJSONObject(i)
										.getString("nst"))
									cv.put(KEY_KOBRA_STATUS, array.getJSONObject(i)
										.getString("status"))
									
									val calendar = Calendar.getInstance()
									calendar.add(Calendar.DAY_OF_YEAR, 1)
									calendar.set(Calendar.SECOND, 0)
									calendar.set(Calendar.MILLISECOND, 0)
									
									calendar.set(Calendar.HOUR_OF_DAY, array.getJSONObject(i)
										.getString("vop")
										.split(":")[0].toInt())
									calendar.set(Calendar.MINUTE, array.getJSONObject(i)
										.getString("vop")
										.split(":")[1].toInt())
									cv.put(KEY_KOBRA_TIME_PLANED, calendar.timeInMillis)
									
									if (array.getJSONObject(i)
											.getString("vof") != ""
									) {
										calendar.set(Calendar.HOUR_OF_DAY, array.getJSONObject(i)
											.getString("vof")
											.split(":")[0].toInt())
										calendar.set(Calendar.MINUTE, array.getJSONObject(i)
											.getString("vof")
											.split(":")[1].toInt())
										cv.put(KEY_KOBRA_TIME_EXPECT, calendar.timeInMillis)
									} else cv.put(KEY_KOBRA_TIME_EXPECT, 0)
									
									val db = KobraDB().writableDatabase
									val cur = db.query(KOBRA_DB_TABLE_DEPARTURE, null, "$KEY_KOBRA_ID = " + array.getJSONObject(i)
										.getLong("id_spp"), null, null, null, null)
									if (cur.moveToFirst()) db.update(KOBRA_DB_TABLE_DEPARTURE, cv, "$KEY_KOBRA_ID=" + array.getJSONObject(i)
										.getLong("id_spp"), null)
									else db.insert(KOBRA_DB_TABLE_DEPARTURE, null, cv)
									cur.close()
								}
							}
						}
					}
				})
			delay(500)
		}
		
		private fun login() {
			val preference: SharedPreferences = Global.appContext.getSharedPreferences(Global.PREFERENCE_FILE, Context.MODE_PRIVATE)
			val editor: SharedPreferences.Editor = preference.edit()
			
			val loginURL = "https://spp.airport.by/api/login"
			val login = preference.getString(SETTING_KOBRA_LOGIN, "belavia")
			val password = preference.getString(SETTING_KOBRA_PASSWORD, "b2Land!D$")
			
			val loginRequest: Request = Request.Builder()
				.url(loginURL)
				.header("Content-Type", "application/json;charset=UTF-8")
				.post("{\"login\":\"$login\",\"password\":\"$password\"}".toRequestBody("application/json;charset=utf-8".toMediaTypeOrNull()))
				.build()
			
			OkHttpClient().newCall(loginRequest)
				.enqueue(object: Callback {
					override fun onFailure(call: Call, e: IOException) {
					}
					
					override fun onResponse(call: Call, response: Response) {
						editor.putString(PREFERENCE_LOGIN_TOKEN, response.body?.string()
							?.let {
								JSONObject(it).get("token")
									.toString()
							})
						editor.apply()
						get()
					}
				})
		}
	}
}