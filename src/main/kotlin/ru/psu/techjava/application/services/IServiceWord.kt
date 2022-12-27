package ru.psu.techjava.application.services

import javax.servlet.http.HttpServletResponse

interface IServiceWord {
    fun downloadWord(response : HttpServletResponse)
}