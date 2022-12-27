package ru.psu.techjava.application.services

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.psu.techjava.application.model.CEmployee
import ru.psu.techjava.application.repositories.IRepositoryEmployee
import java.util.*

/********************************************************************************************************
 * Сервис с бизнес-логикой для работы со списком сотрудников.                                           *
 * @author Панин А.Р. 2022 1107.                                                                        *
 ********************************************************************************************************/

@Service
/********************************************************************************************************
 * Конструктор.                                                                                         *
 * @param repositoryEmployee - репозиторий с запросами к таблице сотрудника в СУБД                      *
 * (устанавливается Spring-ом).                                                                         *
 ********************************************************************************************************/
class CServicesEmployee (val repositoryEmployee: IRepositoryEmployee): IServiceEmployee{

    /****************************************************************************************************
     * Список всех сотрудников.                                                                         *
     ****************************************************************************************************/
    override fun getAll(): Iterable<CEmployee>
    {
        return repositoryEmployee.findAll()
    }
    /****************************************************************************************************
     * Поиск сотрудника по идентификатору.                                                              *
     * @param id - идентификатор сотрудника для поиска в формате UUID.                                  *
     ****************************************************************************************************/
    override fun getById(id: UUID): ResponseEntity<CEmployee>
    {
        return repositoryEmployee.findById(id)
            .map { emp -> ResponseEntity.ok(emp) }
            .orElse(ResponseEntity.notFound().build())
    }

    override fun getWithMaxCoefficient() : List<CEmployee> {
        return repositoryEmployee.getEmployeeWithMaxCoefficient()
    }
    /****************************************************************************************************
     * Создание/изменение сотрудника.                                                                   *
     * @param employee - данные сотрудника.                                                             *
     ****************************************************************************************************/
    override fun save(employee: CEmployee) {
        repositoryEmployee.save(employee)
    }
    /****************************************************************************************************
     * Удаление сотрудника.                                                                             *
     * @param employee - данные сотрудника.                                                             *
     ****************************************************************************************************/
    override fun delete(employee: CEmployee) {
        repositoryEmployee.delete(employee)
    }
    /****************************************************************************************************
     * Удаление сотрудника по идентификатору.                                                           *
     * @param id - идентификатор сотрудника для удаления.                                               *
     ****************************************************************************************************/
    override fun deleteById(id: UUID): String {
        return if (repositoryEmployee.existsById(id)) {
            repositoryEmployee.deleteById(id)
            "Элемент удалён"
        }
        else "Элемент не найден"
    }
}