package ru.psu.techjava.application.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.psu.techjava.application.model.CDepartment
import ru.psu.techjava.application.services.IServiceDepartment
import java.util.*

/********************************************************************************************************
 * ���������� REST �������� � ����������� �����������.                                                  *
 * @author ����� �.�. 2022 1107.                                                                        *
 ********************************************************************************************************/
@RestController
@RequestMapping("/department")
/********************************************************************************************************
 * �����������.                                                                                         *
 * @param serviceDepartment - ������ � ������� ��� ������ �� ������� �������                            *
 * (��������������� Spring-��)                                                                          *
 ********************************************************************************************************/
class CControllerDepartment (val serviceDepartment: IServiceDepartment){

    @GetMapping
    fun getAll(): Iterable<CDepartment>
    {
        return serviceDepartment.getAll()
    }

    @GetMapping(params = ["id"])
    fun getById(@RequestParam id: UUID): ResponseEntity<CDepartment> {
        return serviceDepartment.getById(id)
    }

    @PostMapping
    fun save(@RequestBody department: CDepartment){
        serviceDepartment.save(department)
    }

    @DeleteMapping
    fun delete(@RequestBody department: CDepartment){
        serviceDepartment.delete(department)
    }

    @DeleteMapping(params = ["id"])
    fun deleteById(@RequestParam id: UUID): String {
        return serviceDepartment.deleteById(id)
    }
}