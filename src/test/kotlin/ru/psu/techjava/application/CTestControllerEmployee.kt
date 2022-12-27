package ru.psu.techjava.application

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.psu.techjava.application.controllers.CControllerEmployee
import ru.psu.techjava.application.services.CServicesEmployee
import java.util.*

/********************************************************************************************************
 * ����� � ��������������� ������� ��� �������� ����������� ����������� �����������.                    *
 * @author ����� �.�. 2022 1107.                                                                        *
 ********************************************************************************************************/
@SpringBootTest
class CTestControllerEmployee {
    //������������ ������ ������������ �� �����,
    //��� ���������� ����������� � ��������� ������,
    //����� ��� ��������� � ������� ������ mockk()
    private val serviceEmployee: CServicesEmployee = mockk()

    //� ��� ���������� ����������� ������������,
    //�� ����� �������� ������ ������������ �������.
    private val controllerEmployee = CControllerEmployee(serviceEmployee)

    /****************************************************************************************************
     * �������� ��������� ������� �� �������� ���������� �� ��������������.                             *
     ****************************************************************************************************/
    @Test
    fun testDeleteEmployeeById()
    {
        val id = UUID.randomUUID()
        every {serviceEmployee.deleteById(id)} returns "������ �����"

        val result = controllerEmployee.deleteById(id)
        Assertions.assertEquals("������ �����", result)
    }
}