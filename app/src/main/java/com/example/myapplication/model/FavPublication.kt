package com.example.myapplication.model

data class FavPublication (
    var date: String,
    var explanation: String,
    var hdurl: String? = null,
    var title: String,
    var url: String,
    var copyright: String? = null,
    val usuarioID: String? = null
){
    constructor() : this("","","","","","","")

}