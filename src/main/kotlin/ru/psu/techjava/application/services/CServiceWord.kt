package ru.psu.techjava.application.services

import org.apache.poi.xwpf.usermodel.*
import org.springframework.stereotype.Service
import ru.psu.techjava.application.model.CEmployee
import java.io.*
import javax.servlet.http.HttpServletResponse

/********************************************************************************************************
 * Сервис с для работы создания результирующего файла с информацией об отфильтрованных сотрудниках.     *
 * @author Панин А.Р. 2022 1107.                                                                        *
 ********************************************************************************************************/
@Service
    class CServiceWord (val serviceEmployee: IServiceEmployee) : IServiceWord {
    fun createTitle(document: XWPFDocument, employee: CEmployee){
        //Создание параграфа
        val par: XWPFParagraph = document.createParagraph()
        //Центрирование параграфа
        par.alignment = ParagraphAlignment.CENTER
        //Создание куска текста
        val run: XWPFRun = par.createRun()
        //Установка содержимого текста
        run.setText(employee.name)//department.getName());
        //Жирность
        run.isBold = true
        //Шрифт
        run.fontFamily = "Times New Roman"
        //Размер шрифта
        run.fontSize = 20
    }
    fun createBodyCell(row: XWPFTableRow, pos: Int, text: String, flag: Boolean, fontSize: Int) {
        val par: XWPFParagraph
        val cell: XWPFTableCell = row.getCell(pos)
        par = cell.addParagraph()
        par.alignment = ParagraphAlignment.CENTER
        par.verticalAlignment = TextAlignment.BOTTOM
        val run: XWPFRun = par.createRun()
        //Установка содержимого текста
        run.setText(text)
        //Жирность
        run.isBold = flag
        //Шрифт
        run.fontFamily = "Times New Roman"
        //Размер шрифта
        run.fontSize = fontSize
    }
    /****************************************************************************************************
     * Создание таблицы с оценками в файле-отчёте.                                                      *
     * @param document - заготовка файла-отчёта.                                                        *
     * @param employee - работник, данные которого выводятся в отчёт.                                   *
     ****************************************************************************************************/
    fun createTable(document: XWPFDocument, employee: CEmployee)
    {
        val table: XWPFTable = document.createTable(1, 3)
        table.width = 5 * 1440

        var row: XWPFTableRow = table.getRow(0)
        createBodyCell(row, 0, "Отдел", true, 14)
        createBodyCell(row, 1, "Должность",true, 14)
        createBodyCell(row, 2, "Коэффициент",true, 14)

        //Создание строк с информацией по должности и коэффициента з/п сотрудника в отделе.
        row = table.createRow()
        createBodyCell(row, 0, employee.position!!.department!!.name!!, false, 12)
        createBodyCell(row, 1, employee.position!!.name!!, false, 12)
        createBodyCell(row, 2, String.format("%.1f", employee.coefficient), false, 12)


        //Прокраска границ таблицы. Необходимость надо проверять в MS Word.
        table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
        table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 8, 0, "000000")
    }

    override fun downloadWord(response: HttpServletResponse) {
        try {
            val doc = XWPFDocument()
            val employeeList = serviceEmployee.getWithMaxCoefficient()
            for (employee in employeeList) {
                //Заголовок
                createTitle(doc, employee)
                //Таблица с сотрудниками.
                createTable(doc, employee)
            }
            //Сохранение информации в файл.
            val report = File("output.docx")
            val fos = FileOutputStream(report)
            doc.write(fos)
        } catch ( ex: IOException) {
            println("Не удалось записать информацию в файл!")
            //log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            //throw new RuntimeException("IOError writing file to output stream");
        }
    }
}