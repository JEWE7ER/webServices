package ru.psu.techjava.application.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.psu.techjava.application.model.CDepartment
import java.util.*

@Repository
interface IRepositoryDepartment : CrudRepository<CDepartment, UUID> {
}