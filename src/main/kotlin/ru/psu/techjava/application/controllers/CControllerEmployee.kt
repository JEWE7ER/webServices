package ru.psu.techjava.application.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.psu.techjava.application.model.CEmployee
import ru.psu.techjava.application.services.IServiceEmployee
import java.util.UUID

/********************************************************************************************************
 * ���������� REST �������� � ����������� �����������.                                                  *
 * @author ����� �.�. 2022 1107.                                                                        *
 ********************************************************************************************************/

@RestController
@RequestMapping("/employee")//������������� ����, �� �������� ������ ��������� �������.
/********************************************************************************************************
 * �����������.                                                                                         *
 * @param serviceEmployee - ������ � ������� ��� ������ �� ������� �����������                          *
 * (��������������� Spring-��)                                                                          *
 ********************************************************************************************************/
class CControllerEmployee( val serviceEmployee: IServiceEmployee){
    /****************************************************************************************************
     * ������ ���� �����������.                                                                         *
     ****************************************************************************************************/
    @GetMapping
    fun getAll(): Iterable<CEmployee>
    {
        return serviceEmployee.getAll()
    }
    /****************************************************************************************************
     * ����� ���������� �� ��������������.                                                              *
     * @param id - ������������� ���������� ��� ������ � ������� UUID.                                  *
     ****************************************************************************************************/
    @GetMapping(params = ["id"])//���� ������� �������, ��� ����� �������� ������ �����,
                                   //����� � ���������� ������� ���� �������� id.
    fun getById(@RequestParam id: UUID): ResponseEntity<CEmployee> {
        return serviceEmployee.getById(id)
    }

    @GetMapping(path = ["/max_coefficient"])
    fun getWithMaxCoefficient() : Iterable<CEmployee>
    {
        return serviceEmployee.getWithMaxCoefficient()
    }


    /****************************************************************************************************
     * ��������/��������� ����������.                                                                   *
     * @param employee - ������ ����������.                                                             *
     ****************************************************************************************************/
    @PostMapping
    fun save(@RequestBody employee: CEmployee){ //������ ���������� ������ ���� � ���� �������.
        serviceEmployee.save(employee)
    }
    /****************************************************************************************************
     * �������� ����������.                                                                             *
     * @param employee - ������ ����������.                                                             *
     ****************************************************************************************************/
    @DeleteMapping
    fun delete(@RequestBody employee: CEmployee){
        serviceEmployee.delete(employee)
    }
    /****************************************************************************************************
     * �������� ���������� �� ��������������.                                                           *
     * @param id - ������������� ���������� ��� ��������.                                               *
     ****************************************************************************************************/
    @DeleteMapping(params = ["id"])
    fun deleteById(@RequestParam id: UUID):String {
        return serviceEmployee.deleteById(id)
    }
}