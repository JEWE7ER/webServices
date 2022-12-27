package ru.psu.techjava.application.model

import com.fasterxml.jackson.annotation.JsonIncludeProperties
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "employee")
data class CEmployee(
        /****************************************************************************************************
         * �������������.                                                                                   *
         ****************************************************************************************************/
        @Id
        var id: UUID? = null, //������������ ���������� id �� 32 ��������

        /****************************************************************************************************
         * �.�.�.                                                                                           *
         ****************************************************************************************************/
        @Column
        var name: String? = "",

        /****************************************************************************************************
         * ���� ��������.                                                                                   *
         ****************************************************************************************************/
        @Column
        val birthday: LocalDate? = null,

        /****************************************************************************************************
         * ����                                                                                             *
         ****************************************************************************************************/
        @Column
        var coefficient: Double = 0.0,

        /****************************************************************************************************
         * ���������                                                                                        *
         ****************************************************************************************************/
        @ManyToOne
        @JoinColumn(name="position_id", nullable = false)
        @JsonIncludeProperties(value = ["id"])
        var position: CPosition? = null
)
{
        override fun toString(): String { //������ ������
                return "�����: "+(if(position==null)"�� ������" else position!!.department!!.name)+
                        "; ���������: "+(if (position==null)"�� �������" else position!!.name)+
                        "; ���: "+name+"; �����������: "+coefficient
        }
}