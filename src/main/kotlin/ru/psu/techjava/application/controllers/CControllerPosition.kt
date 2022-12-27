package ru.psu.techjava.application.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.psu.techjava.application.model.CPosition
import ru.psu.techjava.application.services.IServicePosition
import java.util.*

@RestController
@RequestMapping("/position")
class CControllerPosition (var servicePosition: IServicePosition) {
    @GetMapping
    fun getAll(): Iterable<CPosition>
    {
        return servicePosition.getAll()
    }

    @GetMapping(params = ["id"])//Этой строкой говорим, что метод вызывать только тогда,
    //когда в параметрах запроса есть параметр id.
    fun getById(@RequestParam id: UUID): ResponseEntity<CPosition> {
        return servicePosition.getById(id)
    }

    @PostMapping
    fun save(@RequestBody pos: CPosition)
    {
        servicePosition.save(pos)
    }

    @DeleteMapping
    fun delete(@RequestBody position: CPosition){
        servicePosition.delete(position)
    }

    @DeleteMapping(params = ["id"])
    fun deleteById(@RequestParam id: UUID): String {
        return servicePosition.deleteById(id)
    }
}