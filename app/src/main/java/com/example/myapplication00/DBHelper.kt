package com.example.projectapp

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import java.time.LocalDate


//context 정보는 멤버로 많이 사용하므로 반드시 생성자에 있어야 하고 나머지는 클래스 내부에 Static 멤버로 만들면 된다.

class DBHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    // 자바에서 Static과 동일한 역할. 멤버 변수 설정
    companion object {
        val DB_NAME = "mydb.db"
        val DB_VERSION = 1
        val TABLE_NAME = "daily"
        val DATE = "date"
        val DAILY_GOAL = "daily_goal"
        val WEEKLY_GOAL = "weekly_goal"
        val CHECKED = "checked"
        val TITLE = "title"
        val LOCATION = "LOCATION"
        val START_TIME = "start_time"
        val END_TIME = "end_time"
        val ISALLDAY = "isallday"
        val ALARM = "alarm"
        val MEMO = "memo"
        val SOJU = "soju"
        val BEER = "beer"
        val MAKGEOLLI = "makeolli"
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun findAlcohol() : ArrayList<Int> {
        val date = LocalDate.now().toString()
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        val array = arrayListOf<Int>()
        if(flag){
            cursor.moveToFirst()
            array.add(cursor.getInt(11))
            array.add(cursor.getInt(12))
            array.add(cursor.getInt(13))
            array.add(cursor.getInt(14))
        }
        cursor.close()
        db.close()
        return array
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

    fun findDailyGoal(date:String) : String{
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        lateinit var daily : String
        if(flag){
            cursor.moveToFirst()
            daily = cursor.getString(1)
        }
        cursor.close()
        db.close()
        return daily;
    }

    fun findWeeklyGoal(date:String) : String{
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        lateinit var weekly : String;
        if(flag){
            cursor.moveToFirst()
            weekly = cursor.getString(2)
        }
        cursor.close()
        db.close()
        return weekly
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

    fun insertDiary(soju: Int, beer: Int, makeolli: Int, wine: Int, diary: String, self_examination: String, tip: String) : Boolean {
        val now = LocalDate.now()
        var yesterday = now.minusDays(1)

        val values = ContentValues()
        values.put(DATE, now.toString())
        values.put(SOJU, soju)
        values.put(BEER, beer)
        values.put(MAKGEOLLI, makeolli)
        values.put(WINE, wine)
        values.put(DIARY, diary)
        values.put(SELF_EXAMINATION ,self_examination)
        values.put(TIP, tip)
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

    fun updateDiary(date: String, soju: Int, beer: Int, makeolli: Int, wine: Int, diary: String, self_examination: String, tip: String):Boolean{
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            val values = ContentValues()
            values.put(SOJU, soju)
            values.put(BEER, beer)
            values.put(MAKGEOLLI, makeolli)
            values.put(WINE, wine)
            values.put(DIARY, diary)
            values.put(SELF_EXAMINATION ,self_examination)
            values.put(TIP, tip)
            db.update(TABLE_NAME, values, "$DATE = ?", arrayOf(date))
        }
        //작업 완료 후 db, cursor를 닫아주는 함수 호출
        cursor.close()
        db.close()
        return flag
    }
}