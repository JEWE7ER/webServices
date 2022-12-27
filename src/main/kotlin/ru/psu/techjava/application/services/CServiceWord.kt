package ru.psu.techjava.application.services

import org.apache.poi.xwpf.usermodel.*
import org.springframework.stereotype.Service
import ru.psu.techjava.application.model.CEmployee
import java.io.*
import javax.servlet.http.HttpServletResponse

/********************************************************************************************************
 * ������ � ��� ������ �������� ��������������� ����� � ����������� �� ��������������� �����������.     *
 * @author ����� �.�. 2022 1107.                                                                        *
 ********************************************************************************************************/
@Service
    class CServiceWord (val serviceEmployee: IServiceEmployee) : IServiceWord {
    fun createTitle(document: XWPFDocument, employee: CEmployee){
        //�������� ���������
        val par: XWPFParagraph = document.createParagraph()
        //������������� ���������
        par.alignment = ParagraphAlignment.CENTER
        //�������� ����� ������
        val run: XWPFRun = par.createRun()
        //��������� ����������� ������
        run.setText(employee.name)//department.getName());
        //��������
        run.isBold = true
        //�����
        run.fontFamily = "Times New Roman"
        //������ ������
        run.fontSize = 20
    }
    fun createBodyCell(row: XWPFTableRow, pos: Int, text: String, flag: Boolean, fontSize: Int) {
        val par: XWPFParagraph
        val cell: XWPFTableCell = row.getCell(pos)
        par = cell.addParagraph()
        par.alignment = ParagraphAlignment.CENTER
        par.verticalAlignment = TextAlignment.BOTTOM
        val run: XWPFRun = par.createRun()
        //��������� ����������� ������
        run.setText(text)
        //��������
        run.isBold = flag
        //�����
        run.fontFamily = "Times New Roman"
        //������ ������
        run.fontSize = fontSize
    }
    /****************************************************************************************************
     * �������� ������� � �������� � �����-������.                                                      *
     * @param document - ��������� �����-������.                                                        *
     * @param employee - ��������, ������ �������� ��������� � �����.                                   *
     ****************************************************************************************************/
    fun createTable(document: XWPFDocument, employee: CEmployee)
    {
        val table: XWPFTable = document.createTable(1, 3)
        table.width = 5 * 1440

        var row: XWPFTableRow = table.getRow(0)
        createBodyCell(row, 0, "�����", true, 14)
        createBodyCell(row, 1, "���������",true, 14)
        createBodyCell(row, 2, "�����������",true, 14)

        //�������� ����� � ����������� �� ��������� � ������������ �/� ���������� � ������.
        row = table.createRow()
        createBodyCell(row, 0, employee.position!!.department!!.name!!, false, 12)
        createBodyCell(row, 1, employee.position!!.name!!, false, 12)
        createBodyCell(row, 2, String.format("%.1f", employee.coefficient), false, 12)


        //��������� ������ �������. ������������� ���� ��������� � MS Word.
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
                //���������
                createTitle(doc, employee)
                //������� � ������������.
                createTable(doc, employee)
            }
            //���������� ���������� � ����.
            val report = File("output.docx")
            val fos = FileOutputStream(report)
            doc.write(fos)
        } catch ( ex: IOException) {
            println("�� ������� �������� ���������� � ����!")
            //log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            //throw new RuntimeException("IOError writing file to output stream");
        }
    }
}