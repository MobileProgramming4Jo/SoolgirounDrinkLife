package com.example.myapplication00

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.example.myapplication00.DiaryData
import java.time.LocalDate


//context 정보는 멤버로 많이 사용하므로 반드시 생성자에 있어야 하고 나머지는 클래스 내부에 Static 멤버로 만들면 된다.

class DBHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    // 자바에서 Static과 동일한 역할. 멤버 변수 설정
    companion object {
        val DB_NAME = "project.db"
        val DB_VERSION = 1
        val TABLE_NAME = "daily"
        val DATE = "date"
        val DAILY_GOAL = "daily_goal"
        val WEEKLY_GOAL = "weekly_goal"
        val CHECKED = "checked"
        val TITLE = "title"
        val LOCATION = "location"
        val START_TIME = "start_time"
        val END_TIME = "end_time"
        val ISALLDAY = "isallday"
        val ALARM = "alarm"
        val MEMO = "memo"
        val SOJU = "soju"
        val BEER = "beer"
        val MAKGEOLLI = "makgeolli"
        val WINE = "wine"
        val DIARY = "diary"
        val SELF_EXAMINATION = "self_examination"
        val TIP = "tip"
    }

    //DB가 생성될 때 실행되는 함수
    override fun onCreate(db: SQLiteDatabase?) {
        //table 생성 sql문 생성
        val create_table = "create table if not exists $TABLE_NAME(" +
                "$DATE text primary key," +
                "$DAILY_GOAL integer," +
                "$WEEKLY_GOAL integer, " +
                "$CHECKED boolean, " +
                "$TITLE text, " +
                "$LOCATION text, " +
                "$START_TIME text, " +
                "$END_TIME text, " +
                "$ISALLDAY boolean, " +
                "$ALARM text, " +
                "$MEMO text, " +
                "$SOJU integer, " +
                "$BEER integer, " +
                "$MAKGEOLLI integer, " +
                "$WINE integer," +
                "$DIARY text, " +
                "$SELF_EXAMINATION text, " +
                "$TIP text);"

        //생성한 sql문을 DB 객체를 이용해 전송
        db!!.execSQL(create_table)
    }

    //데이터베이스의 버전이 바뀐 경우 호출하는 함수
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists $TABLE_NAME;"

        db!!.execSQL(drop_table)
        onCreate(db)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insert():Boolean{
        val now = LocalDate.now()
        var yesterday = now.minusDays(1)
        val values = ContentValues()
        values.put(DATE, now.toString())
        values.put(DAILY_GOAL, findDailyGoal(yesterday.toString())!!)
        values.put(WEEKLY_GOAL, findWeeklyGoal(yesterday.toString()))
        val db = writableDatabase
        if(db.insert(TABLE_NAME, null, values)>0){
            db.close()
            return true
        }
        else{
            db.close()
            return false
        }
    }

    fun findWeeklyGoal(date: String): String {
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        var weekly = ""
        if(flag){
            cursor.moveToFirst()
            weekly = cursor.getString(2)
        }
        cursor.close()
        db.close()
        return weekly
    }

    fun findDailyGoal(date: String): String {
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        var daily = ""
        if(flag){
            cursor.moveToFirst()
            daily = cursor.getString(1)
        }
        cursor.close()
        db.close()
        return daily
    }

    // DB에 registerActivity에서 입력한 값을 넣어주는 함수
    fun insertDairy(title : String, location: String,
                    isallday : Boolean, start_time : String, end_time : String,
                    alarm : String, memo : String ) : Boolean{
        val values = ContentValues()
        values.put(TITLE, title)
        values.put(LOCATION, location)
        values.put(ISALLDAY, isallday)
        values.put(START_TIME, start_time)
        values.put(END_TIME, end_time)
        values.put(ALARM, alarm)
        values.put(MEMO, memo)
        val db = writableDatabase
        if(db.insert(TABLE_NAME, null, values)>0){
            db.close()
            return true
        }
        else{
            db.close()
            return false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findAlcohol(date: String) : Int {
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        var num = 0

        if(flag){
            cursor.moveToFirst()
            num = cursor.getInt(11) + cursor.getInt(12)+cursor.getInt(13)+cursor.getInt(14)
        }
        cursor.close()
        db.close()
        return num
    }

    fun getAllAlcohol() : ArrayList<Int> {
        var strsql="select * from $TABLE_NAME;"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val result = arrayListOf<Int>(0,0,0,0)
        if(cursor != null && cursor.count >0) {
            cursor.moveToFirst()
            do {
                result[0] = result[0] + cursor.getInt(11)
                result[1] = result[1] + cursor.getInt(12)
                result[2] = result[2] + cursor.getInt(13)
                result[3] = result[3] + cursor.getInt(14)
            } while (cursor.moveToNext())
            //작업 완료 후 db, cursor를 닫아주는 함수 호출
        }
        cursor.close()
        db.close()
        return  result
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun updateAlcohol(array : ArrayList<Int>) : Boolean{
        val date = LocalDate.now().toString()
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            val values = ContentValues()
            values.put(SOJU ,array[0])
            values.put(BEER, array[1])
            values.put(MAKGEOLLI, array[2])
            values.put(WINE, array[3])
            db.update(TABLE_NAME, values, "$DATE = ?", arrayOf(date))
        }
        //작업 완료 후 db, cursor를 닫아주는 함수 호출
        cursor.close()
        db.close()
        return flag
    }

    fun updateGoal(date : String, daily_goal: Int, weekly_goal: Int):Boolean{
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            val values = ContentValues()
            values.put(DAILY_GOAL ,daily_goal)
            values.put(WEEKLY_GOAL, weekly_goal)
            db.update(TABLE_NAME, values, "$DATE = ?", arrayOf(date))
        }
        //작업 완료 후 db, cursor를 닫아주는 함수 호출
        cursor.close()
        db.close()
        return flag
    }

/*
    fun find(date : String, key :String) : String{
        val keyColumn = when(key){
            "daily_goal" -> 1
            "weekly_goal" -> 2
            "checked" -> 3
            "title" -> 4
            "location" -> 5
            "start_time" -> 6
            "end_time" -> 7
            "isallday" -> 8
            "alarm" -> 9
            "memo" -> 10
            "alcohol_type" -> 11
            "alcohol_quantity" -> 12
            "alcohol_degree" -> 13
            "diary" -> 14
            "self_examination" -> 15
            "tip" -> 16
            else -> -1
        }
        if (keyColumn == -1){
            return "invalid key"
        }
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        lateinit var value : String;
        if(flag){
            cursor.moveToFirst()
            value = cursor.getString(keyColumn)
        }
        cursor.close()
        db.close()
        return value
    }*/



    //해당 date의 일일 목표 달성 여부
    fun isDailyGoalAchieved(date:String) : Int{
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        var daily : Int = -1
        var drunk : Int = -1
        if(flag){
            cursor.moveToFirst()
            daily = cursor.getInt(1)
            drunk = cursor.getInt(12)
        }
        cursor.close()
        db.close()
        return if (daily < 0 || drunk < 0){
            -1
        } else if (daily <= drunk){
            1
        } else {
            0
        }
    }

    fun checkData(date:String):Boolean{
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        cursor.close()
        db.close()
        return flag;
    }

    fun insertDiary(diaryData: DiaryData) : Boolean {
        val now = LocalDate.now()
        var yesterday = now.minusDays(1)

        val values = ContentValues()
        values.put(DATE, now.toString())
        values.put(SOJU, diaryData.soju)
        values.put(BEER, diaryData.beer)
        values.put(MAKGEOLLI, diaryData.makeolli)
        values.put(WINE, diaryData.wine)
        values.put(DIARY, diaryData.diary)
        values.put(SELF_EXAMINATION , diaryData.self_examination)
        values.put(TIP, diaryData.tip)
        values.put(DAILY_GOAL, findDailyGoal(yesterday.toString()))
        values.put(WEEKLY_GOAL, findWeeklyGoal(yesterday.toString()))

        val db = writableDatabase
        if(db.insert(TABLE_NAME, null, values)>0){
            db.close()
            return true
        }
        else{
            db.close()
            return false
        }
    }

    fun updateDiary(date: String, diaryData: DiaryData):Boolean{
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            val values = ContentValues()
            values.put(SOJU, diaryData.soju)
            values.put(BEER, diaryData.beer)
            values.put(MAKGEOLLI, diaryData.makeolli)
            values.put(WINE, diaryData.wine)
            values.put(DIARY, diaryData.diary)
            values.put(SELF_EXAMINATION, diaryData.self_examination)
            values.put(TIP, diaryData.tip)
            db.update(TABLE_NAME, values, "$DATE = ?", arrayOf(date))
        }
        //작업 완료 후 db, cursor를 닫아주는 함수 호출
        cursor.close()
        db.close()
        return flag
    }

    fun getDiary(date: String): DiaryData {
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        lateinit var diaryData : DiaryData

        if(flag){
            cursor.moveToFirst()
            diaryData = DiaryData(
                isExist = true,
                soju = cursor.getIntOrNull(11) ?: 0,
                beer = cursor.getIntOrNull(12) ?: 0,
                makeolli = cursor.getIntOrNull(13) ?: 0,
                wine = cursor.getIntOrNull(14) ?: 0,
                diary = cursor.getStringOrNull( 15) ?: "",
                self_examination = cursor.getStringOrNull( 16) ?: "",
                tip = cursor.getStringOrNull( 17) ?: ""
            )
        } else {
            diaryData = DiaryData(false, 0,0,0,0,"","","")
        }

        cursor.close()
        db.close()
        return diaryData
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAlcohol(cmonth: Int): ArrayList<Int> {
        var dayAlchol = ArrayList<Int>()
        lateinit var date: LocalDate
        var days = 0
        val now = LocalDate.now().toString()
        var parse = now.split("-")
        when (cmonth) {
            1 -> {
                date = LocalDate.of(parse[0].toInt(), 1, 1)
                days = 31
            }
            2 -> {
                date = LocalDate.of(parse[0].toInt(), 2, 1)
                days = 30
            }
            3 -> {
                date = LocalDate.of(parse[0].toInt(), 3, 1)
                days = 31
            }
            4 -> {
                date = LocalDate.of(parse[0].toInt(), 4, 1)
                days = 30
            }
            5 -> {
                date = LocalDate.of(parse[0].toInt(), 5, 1)
                days = 31
            }
            6 -> {
                date = LocalDate.of(parse[0].toInt(), 6, 1)
                days = 30
            }
            7 -> {
                date = LocalDate.of(parse[0].toInt(), 7, 1)
                days = 31
            }
            8 -> {
                date = LocalDate.of(parse[0].toInt(), 8, 1)
                days = 31
            }
            9 -> {
                date = LocalDate.of(parse[0].toInt(), 9, 1)
                days = 30
            }
            10 -> {
                date = LocalDate.of(parse[0].toInt(), 10, 1)
                days = 31
            }
            11 -> {
                date = LocalDate.of(parse[0].toInt(), 11, 1)
                days = 30
            }
            12 -> {
                date = LocalDate.of(parse[0].toInt(), 12, 1)
                days = 31
            }
        }


        for (i in 0..days - 1) {
            var changeDate = date.plusDays(i.toLong()).toString()
            dayAlchol.add(findAlcohol(changeDate)) // 날마다 종류별 1개씩 마신 양 가져오기
            //해야 할 작업 작성부분

        }

        return dayAlchol

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDailyGoal(cmonth: Int): ArrayList<Int> {
        var daily_goal = ArrayList<Int>()
        lateinit var date: LocalDate
        var days = 0
        val now = LocalDate.now().toString()
        var parse = now.split("-")
        when (cmonth) {
            1 -> {
                date = LocalDate.of(parse[0].toInt(), 1, 1)
                days = 31
            }
            2 -> {
                date = LocalDate.of(parse[0].toInt(), 2, 1)
                days = 30
            }
            3 -> {
                date = LocalDate.of(parse[0].toInt(), 3, 1)
                days = 31
            }
            4 -> {
                date = LocalDate.of(parse[0].toInt(), 4, 1)
                days = 30
            }
            5 -> {
                date = LocalDate.of(parse[0].toInt(), 5, 1)
                days = 31
            }
            6 -> {
                date = LocalDate.of(parse[0].toInt(), 6, 1)
                days = 30
            }
            7 -> {
                date = LocalDate.of(parse[0].toInt(), 7, 1)
                days = 31
            }
            8 -> {
                date = LocalDate.of(parse[0].toInt(), 8, 1)
                days = 31
            }
            9 -> {
                date = LocalDate.of(parse[0].toInt(), 9, 1)
                days = 30
            }
            10 -> {
                date = LocalDate.of(parse[0].toInt(), 10, 1)
                days = 31
            }
            11 -> {
                date = LocalDate.of(parse[0].toInt(), 11, 1)
                days = 30
            }
            12 -> {
                date = LocalDate.of(parse[0].toInt(), 12, 1)
                days = 31
            }
        }


        for (i in 0..days - 1) {
            var changeDate = date.plusDays(i.toLong()).toString()
            daily_goal.add(findDailyGoal(changeDate).toInt()) // 날마다 종류별 1개씩 마신 양 가져오기
            //해야 할 작업 작성부분

        }

        return daily_goal

    }

}