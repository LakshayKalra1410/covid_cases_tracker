package com.example.covid_cases_tracker

data class StateModel (
    val state:String,
    val recovered:Int,
    val death:Int,
    val cases:Int
)