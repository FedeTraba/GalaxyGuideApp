package com.example.myapplication.model

data class Publication (
    var date: String,
    var explanation: String,
    var hdurl: String? = null,
    var title: String,
    var url: String,
    var copyright: String? = null,
)
{


    constructor() : this("","","","","","")
}