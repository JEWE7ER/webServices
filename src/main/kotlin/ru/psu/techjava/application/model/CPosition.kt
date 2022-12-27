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
     * �������������.                                                                                   *
     ****************************************************************************************************/
    @Id
    var id: UUID? = null,


    /****************************************************************************************************
     * ������������.                                                                                    *
     ****************************************************************************************************/
    @Column
    var name: String? = null,

    /****************************************************************************************************
     * �����.                                                                                           *
     ****************************************************************************************************/
    @Column
    var salary: Double = 0.0,

    /****************************************************************************************************
     * �������� �� ��������� �����������.                                                               *
     ****************************************************************************************************/
    @Column
    var markerDepHead: Int = 0,

    /****************************************************************************************************
     * �����.                                                                                           *
     ****************************************************************************************************/
    @ManyToOne
    @JoinColumn(name="department_id", nullable = false)
    @JsonIncludeProperties(value = ["id"])
    var department: CDepartment? = null,

    /****************************************************************************************************
     * ������ �����������.                                                                              *
     ****************************************************************************************************/
    @OneToMany(mappedBy = "position")
    @JsonIncludeProperties(value = ["id","name","birthday","coefficient"])
    var employee: MutableList<CEmployee> = mutableListOf()
)
{
    override fun toString(): String {
        return "�����: "+(if (department==null) "�� ������" else department!!.name)+
                "; ���������: "+name+"; �����: "+salary
    }
}