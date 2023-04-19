package com.example.cado.bo

import java.time.LocalDate

data class Article(
    var id:Long,
    var intitule: String,
    var description: String,
    var prix: Double,
    var niveau: Byte,
    var url:String) {
    var achete:Boolean = false
    var dateAchat: LocalDate?=null
}