package data

data class RedmineResponse<T>(
    var projects: List<T>,
    var offset: Int,
    var limit: Int)