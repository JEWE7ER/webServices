select d.name as department, p.name as position
               ,e.name as name, e1.max_coefficient as coefficient
from (select max(coefficient) as max_coefficient
	  from employee
	 ) as e1
	 , employee as e
left join position as p on e.position_id=p.id
left join department as d on p.department_id = d.id
where e.coefficient=e1.max_coefficient;