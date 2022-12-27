package ru.psu.techjava.application

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.psu.techjava.application.model.CEmployee
import ru.psu.techjava.application.repositories.IRepositoryEmployee
import java.util.*

/********************************************************************************************************
 * Класс с автоматическими тестами функционала репозитория сотрудников.                                   *
 * @author Панин А.Р. 2022 1107.                                                                        *
 ********************************************************************************************************/
@SpringBootTest
class CTestRepositoryEmployee {
    @Autowired
    private lateinit var repositoryEmployee: IRepositoryEmployee

    /****************************************************************************************************
     * На всякий случай перед каждыйм тестом из этого файла очищаем таблицу сотрудников в виртуальной   *
     * СУБД, чтобы предыдущий тест не влиял на следующий.                                               *
     ****************************************************************************************************/
    @BeforeEach
    fun cleanUp() {
        repositoryEmployee.deleteAll()
    }

    /****************************************************************************************************
     * Проверка создания пользователя в СУБД и чтения всех пользователей.                               *
     ****************************************************************************************************/
    @Test
    fun createAndRetrieveEmployee(){
        val id = UUID.randomUUID()
        val name = "Test"
        val employee = CEmployee(id, name)
        repositoryEmployee.save(employee)

        val staff = repositoryEmployee.findAll()
        Assertions.assertEquals(1, staff.count())

        Assertions.assertEquals(id, staff.first().id)
        Assertions.assertEquals(name, staff.first().name)
    }
}