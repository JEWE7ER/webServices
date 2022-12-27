package ru.psu.techjava.application.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.psu.techjava.application.model.CEmployee
import ru.psu.techjava.application.services.IServiceEmployee
import java.util.UUID

/********************************************************************************************************
 *  онтроллер REST запросов к справочнику сотрудников.                                                  *
 * @author ѕанин ј.–. 2022 1107.                                                                        *
 ********************************************************************************************************/

@RestController
@RequestMapping("/employee")//ќтносительный путь, по которому должны поступать запросы.
/********************************************************************************************************
 *  онструктор.                                                                                         *
 * @param serviceEmployee - сервис с логикой дл€ работы со списком сотрудников                          *
 * (устанавливаетс€ Spring-ом)                                                                          *
 ********************************************************************************************************/
class CControllerEmployee( val serviceEmployee: IServiceEmployee){
    /****************************************************************************************************
     * —писок всех сотрудников.                                                                         *
     ****************************************************************************************************/
    @GetMapping
    fun getAll(): Iterable<CEmployee>
    {
        return serviceEmployee.getAll()
    }
    /****************************************************************************************************
     * ѕоиск сотрудника по идентификатору.                                                              *
     * @param id - идентификатор сотрудника дл€ поиска в формате UUID.                                  *
     ****************************************************************************************************/
    @GetMapping(params = ["id"])//Ётой строкой говорим, что метод вызывать только тогда,
                                   //когда в параметрах запроса есть параметр id.
    fun getById(@RequestParam id: UUID): ResponseEntity<CEmployee> {
        return serviceEmployee.getById(id)
    }

    @GetMapping(path = ["/max_coefficient"])
    fun getWithMaxCoefficient() : Iterable<CEmployee>
    {
        return serviceEmployee.getWithMaxCoefficient()
    }


    /****************************************************************************************************
     * —оздание/изменение сотрудника.                                                                   *
     * @param employee - данные сотрудника.                                                             *
     ****************************************************************************************************/
    @PostMapping
    fun save(@RequestBody employee: CEmployee){ //ƒанные сотрудника должны быть в теле запроса.
        serviceEmployee.save(employee)
    }
    /****************************************************************************************************
     * ”даление сотрудника.                                                                             *
     * @param employee - данные сотрудника.                                                             *
     ****************************************************************************************************/
    @DeleteMapping
    fun delete(@RequestBody employee: CEmployee){
        serviceEmployee.delete(employee)
    }
    /****************************************************************************************************
     * ”даление сотрудника по идентификатору.                                                           *
     * @param id - идентификатор сотрудника дл€ удалени€.                                               *
     ****************************************************************************************************/
    @DeleteMapping(params = ["id"])
    fun deleteById(@RequestParam id: UUID):String {
        return serviceEmployee.deleteById(id)
    }
}