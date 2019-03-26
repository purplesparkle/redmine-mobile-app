package com.example.redmineapp.data

data class RedmineResponse<T>(
    var projects: List<T>,
    var offset: Int,
    var limit: Int)