package ru.psu.techjava.application

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.psu.techjava.application.model.CEmployee
import ru.psu.techjava.application.repositories.IRepositoryEmployee
import ru.psu.techjava.application.services.CServicesEmployee
import java.util.*

/********************************************************************************************************
 * ����� � ��������������� ������� ��� �������� ����������� ������� �����������.                        *
 * @author ����� �.�. 2022 1107.                                                                        *
 ********************************************************************************************************/
@SpringBootTest
class CTestServiceEmployee {
    //������ ������������� ����� ������ ���� ����������� ��,
    //����� �� ����������� ������������ �� � ������ �����������,
    //������� ����������� � ������ �����.
    private val staff: MutableList<CEmployee> = mutableListOf()

    //������������ ����������� ������������ �� �����,
    //��� ���������� ����������� � ��������� ������,
    //����� ��� ��������� � ������� ������ mockk()
    private val repositoryEmployee: IRepositoryEmployee = mockk()

    //� ��� ������ ��������� ������������,
    //�� ����� �������� ������ ������������ �����������.
    private val serviceEmployee = CServicesEmployee(repositoryEmployee)

    /****************************************************************************************************
     * ����� ������ ������ ������ ������ ����������, ����� ����� �� ������ ���� �� �����.               *
     ****************************************************************************************************/
    @BeforeEach
    fun cleanUp() {
        staff.clear()
    }

    /****************************************************************************************************
     * �������� ������ �������� ���������� � ��������� ������ �����������.                              *
     ****************************************************************************************************/
    @Test
    fun createSaveAndRetrieveEmployee() {
        //�������� �������� ������-��������� � ����������� ������.
        val id = UUID.fromString("d13a1260-64dc-4256-96eb-3663b5b77f83")
        val name = "Test2"
        val employee = CEmployee(id, name)

        //�������, ��� ��� ������ ������ ������ save �����������,
        //���� � �������� ��������� ��������� ���������� �������� ���������,
        //������ ������ ����� ����������� �����, ������� � ��������� �������� �������.
        //��� ������������� ���������� �������� �� �����.
        every { repositoryEmployee.save(employee) } answers {
            //��������� �������� � ������.
            staff.add(employee)
            employee
        }
        //��� ������ ������� ������ ������ findAll ��������� ��� ���������
        //��������� ���� ������� ���������.
        every { repositoryEmployee.findAll() } returns staff

        //���������� ��� �������� ������������.
        //�������� ������ ���������� �����������.
        serviceEmployee.save(employee)
        //�������� ������ ������ ���� �����������
        val temp = serviceEmployee.getAll()
        //��������� ���������.
        Assertions.assertEquals(1, temp.count())
        Assertions.assertEquals(id, temp.first().id)
        Assertions.assertEquals(name, temp.first().name)

    }

    /****************************************************************************************************
     * �������� ������ �������� ����������.                                                             *
     ****************************************************************************************************/
    @Test
    fun deleteNotExistedEmployee() {
        //����� �� ������������� �� ����������,
        //�������, ��� ��� ��� � ��.
        every { repositoryEmployee.existsById(any()) } returns false
        //�������� ������� ������������ �� ��������������.
        val id = UUID.fromString("d13a1260-64dc-4256-96eb-3663b5b77f83")
        val result = serviceEmployee.deleteById(id)
        //���������, ��� ����� ������������ �����, ��� ������ ������������ ���.
        assert(result == "������� �� ������")
    }

}