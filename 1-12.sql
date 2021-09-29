-- 1
create table student
(
	id 			bigserial primary key,
	name 		varchar(30) not null,
	birthday 	timestamp 	not null,
	grade 		int 		not null
);

create table subject
(
	id 			bigserial primary key,
	name 		varchar(30) not null,
	description varchar(300),
	grade 		int 		not null
);

create table payment_type
(
	id 			bigserial primary key,
	name 		varchar(30) not null
);

create table payment
(
	id 			bigserial primary key,
	type_id		bigint		not null references payment_type (id),
	amount		decimal 	not null,
	student_id	bigint		not null references student (id),
	date 		timestamp 	not null
);

create table mark
(
	id 			bigserial primary key,
	student_id	bigint	not null references student (id),
	subject_id	bigint	not null references subject (id),
	mark 		int 	not null
);

-- 2
insert into student (name, birthday, grade)
values 	('John', '1999-05-04', 1),
		('Chris', '1999-12-11', 1),
		('Carl', '2000-05-15', 1),
		('Oliver', '1998-10-23', 2),
		('James', '1999-04-01', 2),
		('Lucas', '2000-01-30', 2),
		('Henry', '1999-07-02', 2),
		('Jacob', '1999-02-14', 3),
		('Logan', '1998-12-25', 3),
		('Robert', '2000-02-21', 4),
		('Din', '1999-11-12', 4),
		('Carla', '2000-03-09', 4),
		('Jack', '2001-12-19', 5),
		('Molly', '1999-09-28', 5);
		
insert into subject (name, description, grade)
values	('art', 'aaaa', 1),
		('music', null, 1),
		('geography', null, 2),
		('history', 'bbbbb', 2),
		('PE', null, 3),
		('math', 'cccccc', 3),
		('science', null, 4),
		('IT', null, 4),
		('physics', 'ddd', 5),
		('chemistry', null, 5);

insert into payment_type (name)
values	('daily'), ('weekly'), ('monthly');

insert into payment (type_id, amount, student_id, date)
values	(2, 61.5, 1, '2021-09-29'),
		(3, 132.2, 4, '2021-08-21'),
		(2, 56.5, 7, '2021-09-22'),
		(1, 6.9, 5, '2021-09-29'),
		(1, 7.1, 10, '2021-09-28'),
		(3, 127.8, 8, '2021-08-05');
		
insert into mark (student_id, subject_id, mark)
values	(2, 1, 8), 
		(4, 4, 5),
		(5, 3, 9),
		(8, 6, 4),
		(9, 5, 9),
		(10, 7, 7),
		(1, 8, 10);
		
-- 3
alter table mark 
	add constraint mark_check check (mark >= 1 and mark <= 10);

alter table subject
	add constraint grade_check check (grade >=1 and grade <= 5);

alter table payment_type
	add constraint mda unique (name);

-- 4
select * from student;
select * from student limit 50;
select name from student;
select concat(name, ' ', grade) as name from student;
select distinct amount from payment;
select distinct grade from student;

-- 5
select * from payment where amount >= 100;
select * from student where birthday < current_date - interval '20 years';
select * from student where grade = 5 and birthday >= current_date - interval '20 years';
select * from student where name = 'Mike' or grade in (2, 3, 5);
select * from payment where date > current_date - interval '1 month';
select * from student where name like 'J%';
select * from student where name like '%y';
select * from subject where description like '%a%';

-- 6
select * from payment p
join payment_type pt 
on p.type_id = pt.id
where pt.name = 'weekly';

select * from mark m
join subject s
on m.subject_id = s.id
where s.name = 'art';

select s.* from student s
join (
payment p
join payment_type pt 
on p.type_id = pt.id
) on s.id = p.student_id
where pt.name = 'weekly';

select s.* from student s
join (
mark m
join subject sj
on m.subject_id = sj.id
) on s.id = m.student_id
where sj.name = 'math';

-- 7
update subject
set grade = 5 
where name = 'math';

update student
set birthday = '1994-12-25'
where name = 'Robert';

update payment
set amount = 150
where date = '2021-08-21';

update mark m
set mark = 2
where m.id in (select m.id from mark m
	join student s on m.student_id = s.id
	join subject sj on m.subject_id = sj.id
	where s.name = 'Jacob' and sj.name = 'math')
	
-- 8
alter table payment
add constraint payment_student_id_fkey_for_delete
	foreign key (student_id)
	references student (id)
	on delete cascade;
	
alter table mark
add constraint mark_student_id_fkey_for_delete
	foreign key (student_id)
	references student (id)
	on delete cascade;
	
delete from student 
where grade > 4;

delete from student s
where s.id in (select s.id from student
			  join mark m
			  on s.id = m.student_id
			  where m.mark < 4);

alter table payment
add constraint payment_type_id_fkey_for_delete
	foreign key (type_id)
	references payment_type (id)
	on delete cascade;
	
delete from payment_type pt
where pt.name = 'daily';

delete from mark
where mark < 4;

-- 9
select * from mark 
where mark > 6
order by mark desc;

select * from payment
where amount < 100
order by amount;

select * from payment_type
order by name;

select * from student
order by name desc;

select * from student
where grade > 3
order by name;

select * from student s
	join payment p
	on p.student_id = s.id
where p.amount > 100
order by birthday;

-- 10
select * from student
where birthday = (select min(birthday) from student);

select * from payment
where date = (select min(date) from payment);

select avg(mark) from mark;

select min(amount) from payment p
	join payment_type pt
	on p.type_id = pt.id
where pt.name = 'weekly';

-- 11
select s.grade, avg(m.mark) from student s
			join mark m
			on m.student_id = s.id
group by s.grade
having avg(m.mark) > 6;

select s.id, max(s.name), avg(m.mark) from student s
			join mark m
			on m.student_id = s.id
group by s.id
having avg(m.mark) = (select avg(mark) from mark
					group by student_id
					order by avg(mark) desc limit 1);

select s.id, max(s.name), count(p.student_id) from student s
			join payment p
			on p.student_id = s.id
group by s.id
having count(p.student_id) < 2;

-- 12
select * from student s
where s.id in (
	select s.id from student
		join mark m
		on m.student_id = s.id
		group by s.id
		having avg(m.mark) > 4)

select * from student s
where s.id in (
	select s.id from student
		join payment p
		on p.student_id = s.id
		group by s.id
		having sum(p.amount) > 100)

select * from subject s
where s.id in (
	select s.id from subject
		join mark m
		on m.subject_id = s.id
		group by s.id
		having avg(m.mark) = (select avg(mark) from mark
			group by subject_id
			order by avg(mark) desc limit 1));
