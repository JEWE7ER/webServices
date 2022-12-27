package ru.psu.techjava.application.repositories

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.psu.techjava.application.model.CEmployee
import java.util.UUID

@Repository
interface IRepositoryEmployee : CrudRepository<CEmployee, UUID> {
    /****************************************************************************************************
     * Список сотрудников с максимальным коэффициентом в компании.                                      *
     * @return список отфильтрованных сотрудников.                                                      *
     ****************************************************************************************************/
    @Query("""
        select e.*--name as name, e1.max_coefficient as coefficient
        from (select max(coefficient) as max_coefficient
              from employee
             ) as e1
             , employee as e
        where e.coefficient=e1.max_coefficient;
        """,
        nativeQuery = true
    )
    fun getEmployeeWithMaxCoefficient(): List<CEmployee>

}