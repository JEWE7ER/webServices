package ru.psu.techjava.application.services

import org.springframework.http.ResponseEntity
import ru.psu.techjava.application.model.CDepartment
import java.util.*

interface IServiceDepartment {
    fun getAll(): Iterable<CDepartment>
    fun getById(id: UUID): ResponseEntity<CDepartment>
    fun save(department: CDepartment)
    fun delete(department: CDepartment)
    fun deleteById(id: UUID): String
}