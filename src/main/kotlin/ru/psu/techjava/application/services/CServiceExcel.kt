package ru.psu.techjava.application.services

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.psu.techjava.application.model.CDepartment
import ru.psu.techjava.application.model.CEmployee
import ru.psu.techjava.application.model.CPosition
import ru.psu.techjava.application.repositories.IRepositoryEmployee
import ru.psu.techjava.application.repositories.IRepositoryDepartment
import ru.psu.techjava.application.repositories.IRepositoryPosition
import java.time.LocalDate
import java.util.*
/********************************************************************************************************
 * Сервис для работы с данными из электронной таблицы.                                                  *
 * @author Панин А.Р. 2022 1107.                                                                        *
 ********************************************************************************************************/
@Service
    class CServiceExcel(
      val repositoryEmployee: IRepositoryEmployee
    , val repositoryDepartment: IRepositoryDepartment
    , val repositoryPosition: IRepositoryPosition
    ): IServiceExcel
{
    /****************************************************************************************************
     * Загрузка информации об отделах из электронной таблицы в БД.                                      *
     * @param wb - рабочая книга с данными.                                                             *
     ****************************************************************************************************/
    fun loadDepartment(wb : XSSFWorkbook) {
        val sheet = wb.getSheetAt(0)
        var row :Row
        var cell :Cell
        val nRows = sheet.lastRowNum
        var dUUID : String
        var name : String
        var id : UUID
        var dep: CDepartment
        for (i in 0..nRows) {
            row = sheet.getRow(i)
            cell = row.getCell(0)
            dUUID = cell.getStringCellValue()
            id = UUID.fromString(dUUID)
            cell = row.getCell(1)
            name = cell.getStringCellValue()
            dep = CDepartment(id,name)
            repositoryDepartment.save(dep)
        }
    }
    /****************************************************************************************************
     * Загрузка информации о должностях и связи с отделом из электронной таблицы в БД.                  *
     * @param wb - рабочая книга с данными.                                                             *
     ****************************************************************************************************/
    fun loadPosition(wb : XSSFWorkbook) {
        val sheet = wb.getSheetAt(1)
        var row :Row
        var cell :Cell
        val nRows = sheet.lastRowNum
        var dUUID : String
        var name : String
        var departmentID: UUID
        var id : UUID
        var salary : Double
        var markerDepHead: Int
        var pos: CPosition
        var optDep: Optional<CDepartment>
        var dep: CDepartment
        for (i in 0..nRows) {
            row = sheet.getRow(i);
            cell = row.getCell(0)
            dUUID = cell.getStringCellValue()
            id = UUID.fromString(dUUID)
            cell = row.getCell(1)
            name = cell.getStringCellValue()
            cell = row.getCell(2)
            salary = cell.getNumericCellValue()
            cell = row.getCell(3)
            markerDepHead = cell.getNumericCellValue().toInt()
            cell = row.getCell(4)
            dUUID = cell.getStringCellValue()
            departmentID = UUID.fromString(dUUID)
            optDep = repositoryDepartment.findById(departmentID)
            dep = optDep.get()
            pos = CPosition(id,name,salary,markerDepHead,dep)
            repositoryPosition.save(pos)
        }
    }
    /****************************************************************************************************
     * Загрузка информации о сотрудниках и связи с должностью из электронной таблицы в БД.              *
     * @param wb - рабочая книга с данными.                                                             *
     ****************************************************************************************************/
    fun loadEmployee(wb : XSSFWorkbook) {
        val sheet = wb.getSheetAt(2)
        var row: Row
        var cell: Cell
        val nRows = sheet.lastRowNum
        var dUUID: String
        var name: String
        var id: UUID
        var positionID: UUID
        var coefficient: Double
        var birthday: LocalDate
        var emp: CEmployee
        var optPos: Optional<CPosition>
        var pos: CPosition
        for (i in 0..nRows) {
            row = sheet.getRow(i);
            cell = row.getCell(0)
            dUUID = cell.getStringCellValue()
            id = UUID.fromString(dUUID)
            cell = row.getCell(1)
            name = cell.getStringCellValue()
            cell = row.getCell(2)
            birthday = cell.localDateTimeCellValue.toLocalDate()
            cell = row.getCell(3)
            coefficient = cell.getNumericCellValue()
            cell = row.getCell(4)
            dUUID = cell.getStringCellValue()
            positionID = UUID.fromString(dUUID)
            optPos = repositoryPosition.findById(positionID)
            pos = optPos.get()
            emp = CEmployee(id, name, birthday, coefficient,pos)
            repositoryEmployee.save(emp)
        }
    }
    /****************************************************************************************************
     * Обновлении информации об отделах и их руководителях из БД.                                       *
     ****************************************************************************************************/
    fun setRelation(){
        var i = 0
        for (department in repositoryDepartment.findAll()){
            while (i<department.position.size) {
                if (department.position[i].markerDepHead == 1) {
                    val emp=department.position[i].employee[0]
                    department.depHead=emp
                    repositoryDepartment.save(department)
                }
                i++
            }
            i=0
        }
    }
    /****************************************************************************************************
     * Основной метод сервиса.                                                                          *
     ****************************************************************************************************/
    override fun uploadExcel(file: MultipartFile) {
        val wb = XSSFWorkbook(file.inputStream)
        loadDepartment(wb)
        loadPosition(wb)
        loadEmployee(wb)
        setRelation()
    }
}