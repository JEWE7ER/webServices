package ru.psu.techjava.application.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.psu.techjava.application.model.CPosition
import ru.psu.techjava.application.repositories.IRepositoryPosition
import java.util.*

@Service
    class CServicePosition : IServicePosition{
    @Autowired
    lateinit var repositoryPosition: IRepositoryPosition

    override fun getAll(): Iterable<CPosition>
    {
        return repositoryPosition.findAll()
    }
    override fun getById(id: UUID): ResponseEntity<CPosition>
    {
        return repositoryPosition.findById(id)
            .map { position -> ResponseEntity.ok(position) }
            .orElse(ResponseEntity.notFound().build())
    }

    override fun save(position: CPosition) {
            repositoryPosition.save(position)
    }

    override fun delete(position: CPosition) {
        repositoryPosition.delete(position)
    }

    override fun deleteById(id: UUID): String {
        return if (repositoryPosition.existsById(id)) {
            repositoryPosition.deleteById(id)
            "Ёлемент удалЄн"
        }
        else "Ёлемент не найден"
    }
}