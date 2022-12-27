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
 * Класс с автоматическими тестами для проверки функционала контроллера сотрудников.                    *
 * @author Панин А.Р. 2022 1107.                                                                        *
 ********************************************************************************************************/
@SpringBootTest
class CTestControllerEmployee {
    //Продуктивный сервис использовать не будем,
    //его функционал тестируется в отдельном классе,
    //здесь его имитируем с помощью метода mockk()
    private val serviceEmployee: CServicesEmployee = mockk()

    //А вот контроллер сотрудников продуктивный,
    //но будет вызывать методы имитируемого сервиса.
    private val controllerEmployee = CControllerEmployee(serviceEmployee)

    /****************************************************************************************************
     * Проверка обработки запроса на удаление сотрудника по идентификатору.                             *
     ****************************************************************************************************/
    @Test
    fun testDeleteEmployeeById()
    {
        val id = UUID.randomUUID()
        every {serviceEmployee.deleteById(id)} returns "Объект удалён"

        val result = controllerEmployee.deleteById(id)
        Assertions.assertEquals("Объект удалён", result)
    }
}