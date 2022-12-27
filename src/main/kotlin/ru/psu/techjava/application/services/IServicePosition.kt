package ru.psu.techjava.application.services

import org.springframework.http.ResponseEntity
import ru.psu.techjava.application.model.CPosition
import java.util.*

interface IServicePosition {
    fun getAll(): Iterable<CPosition>
    fun getById(id: UUID): ResponseEntity<CPosition>
    fun save(position: CPosition)
    fun delete(position: CPosition)
    fun deleteById(id: UUID): String
}