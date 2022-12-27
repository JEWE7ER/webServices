package ru.psu.techjava.application.model

import com.fasterxml.jackson.annotation.JsonIncludeProperties
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "position")
data class CPosition (
    /****************************************************************************************************
     * Идентификатор.                                                                                   *
     ****************************************************************************************************/
    @Id
    var id: UUID? = null,


    /****************************************************************************************************
     * Наименование.                                                                                    *
     ****************************************************************************************************/
    @Column
    var name: String? = null,

    /****************************************************************************************************
     * Оклад.                                                                                           *
     ****************************************************************************************************/
    @Column
    var salary: Double = 0.0,

    /****************************************************************************************************
     * Является ли должность руководящей.                                                               *
     ****************************************************************************************************/
    @Column
    var markerDepHead: Int = 0,

    /****************************************************************************************************
     * Отдел.                                                                                           *
     ****************************************************************************************************/
    @ManyToOne
    @JoinColumn(name="department_id", nullable = false)
    @JsonIncludeProperties(value = ["id"])
    var department: CDepartment? = null,

    /****************************************************************************************************
     * Список сотрудников.                                                                              *
     ****************************************************************************************************/
    @OneToMany(mappedBy = "position")
    @JsonIncludeProperties(value = ["id","name","birthday","coefficient"])
    var employee: MutableList<CEmployee> = mutableListOf()
)
{
    override fun toString(): String {
        return "Отдел: "+(if (department==null) "не указан" else department!!.name)+
                "; Должность: "+name+"; Оклад: "+salary
    }
}