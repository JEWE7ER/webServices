package ru.psu.techjava.application.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.psu.techjava.application.model.CDepartment
import ru.psu.techjava.application.repositories.IRepositoryDepartment
import java.util.*

@Service
class CServiceDepartment : IServiceDepartment {
    @Autowired
    lateinit var repositoryDepartment: IRepositoryDepartment

    override fun getAll(): Iterable<CDepartment>
    {
        return repositoryDepartment.findAll()
    }
    override fun getById(id: UUID): ResponseEntity<CDepartment>
    {
        return repositoryDepartment.findById(id)
            .map { department -> ResponseEntity.ok(department) }
            .orElse(ResponseEntity.notFound().build())
    }

    override fun save(department: CDepartment) {
        repositoryDepartment.save(department)
    }

    override fun delete(department: CDepartment) {
        repositoryDepartment.delete(department)
    }

    override fun deleteById(id: UUID): String {
        return if (repositoryDepartment.existsById(id)) {
            repositoryDepartment.deleteById(id)
            "Ёлемент удалЄн"
        }
        else "Ёлемент не найден"
    }
}