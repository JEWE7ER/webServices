package ru.psu.techjava.application.repositories
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.psu.techjava.application.model.CPosition
import java.util.*

@Repository
interface IRepositoryPosition : CrudRepository<CPosition, UUID>{
}