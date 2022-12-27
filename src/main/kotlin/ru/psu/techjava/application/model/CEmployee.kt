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
         * Идентификатор.                                                                                   *
         ****************************************************************************************************/
        @Id
        var id: UUID? = null, //инициалируем уникальный id из 32 символов

        /****************************************************************************************************
         * Ф.И.О.                                                                                           *
         ****************************************************************************************************/
        @Column
        var name: String? = "",

        /****************************************************************************************************
         * Дата рождения.                                                                                   *
         ****************************************************************************************************/
        @Column
        val birthday: LocalDate? = null,

        /****************************************************************************************************
         * Стаж                                                                                             *
         ****************************************************************************************************/
        @Column
        var coefficient: Double = 0.0,

        /****************************************************************************************************
         * Должность                                                                                        *
         ****************************************************************************************************/
        @ManyToOne
        @JoinColumn(name="position_id", nullable = false)
        @JsonIncludeProperties(value = ["id"])
        var position: CPosition? = null
)
{
        override fun toString(): String { //формат вывода
                return "Отдел: "+(if(position==null)"не указан" else position!!.department!!.name)+
                        "; Должность: "+(if (position==null)"не указана" else position!!.name)+
                        "; ФИО: "+name+"; Коэффициент: "+coefficient
        }
}