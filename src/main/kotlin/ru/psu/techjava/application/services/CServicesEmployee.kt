package ru.psu.techjava.application.services

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.psu.techjava.application.model.CEmployee
import ru.psu.techjava.application.repositories.IRepositoryEmployee
import java.util.*

/********************************************************************************************************
 * ������ � ������-������� ��� ������ �� ������� �����������.                                           *
 * @author ����� �.�. 2022 1107.                                                                        *
 ********************************************************************************************************/

@Service
/********************************************************************************************************
 * �����������.                                                                                         *
 * @param repositoryEmployee - ����������� � ��������� � ������� ���������� � ����                      *
 * (��������������� Spring-��).                                                                         *
 ********************************************************************************************************/
class CServicesEmployee (val repositoryEmployee: IRepositoryEmployee): IServiceEmployee{

    /****************************************************************************************************
     * ������ ���� �����������.                                                                         *
     ****************************************************************************************************/
    override fun getAll(): Iterable<CEmployee>
    {
        return repositoryEmployee.findAll()
    }
    /****************************************************************************************************
     * ����� ���������� �� ��������������.                                                              *
     * @param id - ������������� ���������� ��� ������ � ������� UUID.                                  *
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
     * ��������/��������� ����������.                                                                   *
     * @param employee - ������ ����������.                                                             *
     ****************************************************************************************************/
    override fun save(employee: CEmployee) {
        repositoryEmployee.save(employee)
    }
    /****************************************************************************************************
     * �������� ����������.                                                                             *
     * @param employee - ������ ����������.                                                             *
     ****************************************************************************************************/
    override fun delete(employee: CEmployee) {
        repositoryEmployee.delete(employee)
    }
    /****************************************************************************************************
     * �������� ���������� �� ��������������.                                                           *
     * @param id - ������������� ���������� ��� ��������.                                               *
     ****************************************************************************************************/
    override fun deleteById(id: UUID): String {
        return if (repositoryEmployee.existsById(id)) {
            repositoryEmployee.deleteById(id)
            "������� �����"
        }
        else "������� �� ������"
    }
}