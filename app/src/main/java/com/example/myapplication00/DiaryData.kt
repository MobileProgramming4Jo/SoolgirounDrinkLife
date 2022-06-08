package com.example.myapplication00

import java.io.Serializable

data class DiaryData(var isExist: Boolean, var soju: Int, var beer: Int, var makeolli: Int, var wine: Int, var diary: String, var self_examination: String, var tip: String): Serializable {
}