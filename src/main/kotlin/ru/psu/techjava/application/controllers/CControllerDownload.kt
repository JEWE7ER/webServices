package ru.psu.techjava.application.controllers

import org.springframework.web.bind.annotation.*
import ru.psu.techjava.application.services.IServiceWord
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/download")
class CControllerDownload(val serviceWord: IServiceWord) {
    @GetMapping("/")
    fun getFile(response: HttpServletResponse): String {
        serviceWord.downloadWord(response)
        return "OK"
    }
}