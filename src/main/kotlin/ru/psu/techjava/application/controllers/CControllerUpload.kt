package ru.psu.techjava.application.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.psu.techjava.application.services.IServiceExcel

@RestController
@RequestMapping("/upload")
class CControllerUpload (val serviceExcel: IServiceExcel){
    @PostMapping("/")
    fun handleFileUpload(@RequestParam("file") file: MultipartFile): String?{
        serviceExcel.uploadExcel(file)
        return "OK"
    }
}
