package com.example.redmineapp.services

import com.example.redmineapp.data.IssueResponse
import com.example.redmineapp.data.ProjectResponse
import java.net.URL


class ApiService(var apiKey: String)
{
    fun getAllProjects(host: String): ProjectResponse?
    {
        val url = host.plus("/projects.json?key = 'd7e318857259ad0854051089517d50038e7dda16'")
        val json = URL(url).readText()

        throw NotImplementedError()
    }

    fun getAllIssues(host:String, projectId: Int): IssueResponse?
    {
        throw NotImplementedError()
    }

    fun setTime(time: Float, issueId: Int): Boolean
    {
        throw NotImplementedError()
    }
}

