INSERT INTO DEPARTMENT values
(1, '영업', 8),
(2, '기획', 10),
(3, '개발', 9),
(4, '총무', 7);

INSERT INTO EMPLOYEE(empno, empname, title, manager, salary, dno) values
(4377, '이성래', '사장', NULL, 5000000, 2),
(3426, '박영권', '과장', 4377, 3000000, 1),
(1003, '조민희', '과장', 4377, 3000000, 2),
(3011, '이수민', '부장', 4377, 4000000, 3),
(2106, '김창섭', '대리', 1003, 2500000, 2),
(3427, '최종철', '사원', 3011, 1500000, 3);


drop procedure if exists native_jdbc_study.procedure_01;

delimiter $$
$$
create procedure native_jdbc_study.procedure_01(
	in in_dno int
)
begin
	select empno, empname, title, manager, salary, dno
	  from employee 
	 where dno = in_dno;
end$$
delimiter ;