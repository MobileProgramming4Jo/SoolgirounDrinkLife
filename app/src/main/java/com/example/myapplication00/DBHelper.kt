package com.example.myapplication00

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

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
        val LOCATION = "LOCATION"
        val START_TIME = "start_time"
        val END_TIME = "end_time"
        val ISALLDAY = "isallday"
        val ALARM = "alarm"
        val MEMO = "memo"
        val ALCOHOL_TYPE = "alcohol_type"
        val ALCOHOL_QUANTITY = "alcohol_quantity"
        val ALCOHOL_DEGREE = "alcohol_degree"
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
                "$ALCOHOL_TYPE text, " +
                "$ALCOHOL_QUANTITY integer, " +
                "$ALCOHOL_DEGREE real, " +
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

    // DB에 값을 넣어주는 함수 생성
    fun insertGoal(date : String, daily_goal : Int, weekly_goal : Int):Boolean{
        val values = ContentValues()
        values.put(DATE, date)
        values.put(DAILY_GOAL, daily_goal)
        values.put(WEEKLY_GOAL, weekly_goal)
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

    // DB에 registerActivity에서 입력한 값을 넣어주는 함수 생성
    fun insertDairy(date : String, daily_goal : Int, weekly_goal : Int):Boolean{
        val values = ContentValues()
        values.put(DATE, date)
        values.put(DAILY_GOAL, daily_goal)
        values.put(WEEKLY_GOAL, weekly_goal)
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

    fun findGoal(date:String) : Boolean{
        val strsql = "select * from $TABLE_NAME where $DATE = '$date';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        cursor.close()
        db.close()
        return flag
    }



}