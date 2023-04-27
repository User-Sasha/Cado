package com.example.cado.bo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Entity(tableName="articles")
@Parcelize
data class Article(
    @PrimaryKey var id:Long,
    var intitule: String,
    var description: String,
    var prix: Double,
    var niveau: Byte,
    var url:String,
    var achete:Boolean = false,
    var dateAchat: LocalDate?=null) : Parcelable { }