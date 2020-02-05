select user(), database();

select * from employee e where dno = 2;
select * from department d ;

select * from department  where deptno = 3;
select deptno, deptname, floor from department where deptno=3;


select * from department;

insert into department values(5, '마케팅', 8);
update department set deptname = '마케팅2', floor = 7	 where deptno = 0;
delete from department  where deptno = 5;
delete  from employee  where empno = 1004;
select * from employee;
select * from department;
desc employee;