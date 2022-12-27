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
 * Класс с автоматическими тестами для проверки функционала сервиса сотрудников.                        *
 * @author Панин А.Р. 2022 1107.                                                                        *
 ********************************************************************************************************/
@SpringBootTest
class CTestServiceEmployee {
    //Список пользователей будет играть роль виртуальной БД,
    //чтобы не затрагивать продуктивную БД и методы репозитория,
    //которые тестируются в другом файле.
    private val staff: MutableList<CEmployee> = mutableListOf()

    //Продуктивный репозиторий использовать не будем,
    //его функционал тестируется в отдельном классе,
    //здесь его имитируем с помощью метода mockk()
    private val repositoryEmployee: IRepositoryEmployee = mockk()

    //А вот сервис студентов продуктивный,
    //но будет вызывать методы имитируемого репозитория.
    private val serviceEmployee = CServicesEmployee(repositoryEmployee)

    /****************************************************************************************************
     * Перед каждым тестом чистим список сотруднков, чтобы тесты не влияли друг на друга.               *
     ****************************************************************************************************/
    @BeforeEach
    fun cleanUp() {
        staff.clear()
    }

    /****************************************************************************************************
     * Проверка логики создания сотрудника и получения списка сотрудников.                              *
     ****************************************************************************************************/
    @Test
    fun createSaveAndRetrieveEmployee() {
        //Создаётся тестовый объект-сотрудник в оперативной памяти.
        val id = UUID.fromString("d13a1260-64dc-4256-96eb-3663b5b77f83")
        val name = "Test2"
        val employee = CEmployee(id, name)

        //Говорим, что при каждом вызове метода save репозитория,
        //если в качестве параметра передаётся конкретный тестовый сотрудник,
        //работу метода будем имитировать кодом, который в последних фигурных скобках.
        //Для произвольного сотрудника работать не будет.
        every { repositoryEmployee.save(employee) } answers {
            //Добавляем студента в список.
            staff.add(employee)
            employee
        }
        //При каждой попытке вызова метода findAll имитируем его результат
        //созданным выше списком студентов.
        every { repositoryEmployee.findAll() } returns staff

        //Собственно сам сценарий тестирования.
        //Вызываем логику сохранения сотрудников.
        serviceEmployee.save(employee)
        //Вызываем логику чтения всех сотрудников
        val temp = serviceEmployee.getAll()
        //Проверяем результат.
        Assertions.assertEquals(1, temp.count())
        Assertions.assertEquals(id, temp.first().id)
        Assertions.assertEquals(name, temp.first().name)

    }

    /****************************************************************************************************
     * Проверка логики удаления сотрудника.                                                             *
     ****************************************************************************************************/
    @Test
    fun deleteNotExistedEmployee() {
        //Какой бы идентификатор не передавали,
        //говорим, что его нет в БД.
        every { repositoryEmployee.existsById(any()) } returns false
        //Пытаемся удалить пользователя по идентификатору.
        val id = UUID.fromString("d13a1260-64dc-4256-96eb-3663b5b77f83")
        val result = serviceEmployee.deleteById(id)
        //Проверяем, что метод действитеьно понял, что такого пользователя нет.
        assert(result == "Элемент не найден")
    }

}