package com.example.a22housexam2.BindingExam

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "number")
    var number : String,

    @ColumnInfo(name = "initial")
    var initial : Char
){
    constructor() : this(null,"","",'\u0000')
}