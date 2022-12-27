package ru.psu.techjava.application.services

import org.springframework.web.multipart.MultipartFile

interface IServiceExcel {
    fun uploadExcel(file : MultipartFile)
}