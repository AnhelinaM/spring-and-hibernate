create table punishment
(
  id 	serial primary key,
  name 	varchar(30) not null unique
);

create table violation
(
  id 				serial primary key,
  type 				varchar(200) not null unique,
  min_punishment 	int references punishment (id),
  max_punishment 	int references punishment (id)
);

create table employees
(
  id 			serial primary key,
  name 			varchar(250) 	not null,
  position 		varchar(100) 	not null,
  experience 	int 			not null,
  salary 		int 			not null
);

create table fact_of_violation
(
  id 						serial primary key,
  date_of_the_violation 	timestamp	not null,
  violation_id 				int 		not null references violation (id),
  violation_description 	text 		not null,
  end_date_of_punishment 	date 		not null
);

create table status
(
  id 	serial primary key,
  name 	varchar(30) not null unique
);

create table violators
(
  fact_of_violation_id 	int not null references fact_of_violation (id),
  employee_id 			int not null references employees (id),
  punishment_id 		int not null references punishment (id),
  status_id				int not null references status(id),
  primary key (fact_of_violation_id, employee_id, punishment_id)
);

insert into employees (name, position, experience, salary)
values 	('Андреев Андрей Ильич', 'доцент', 15, 1200),
		('Зуб Никита Александрович', 'лаборант', 3, 500),
		('Королёв Илья Дмитриевич', 'ассистент', 1, 400),
		('Кривицкая Анна Петровна', 'секретарь', 10, 900),
		('Морозов Владимир Петрович', 'профессор', 25, 2000),
		('Морозова Галина Леонтьевна', 'профессор', 23, 1900),
		('Авдеева Ирина Константиновна', 'ассистент', 2, 550),
		('Гордей Лариса Ивановна', 'старший преподаватель', 5, 700),
		('Борисов Николай Васильевич', 'старший преподаватель', 4, 650),
		('Романова Мария Максимовна', 'доцент', 10, 1000);

insert into punishment (name)
values	('замечание'),
		('выговор'),
		('дисциплинарное взыскание'),
		('штраф'),
		('не учет в табеле рабочего дня'),
		('лишение премии'),
		('понижение в должности'),
		('увольнение');
		
insert into status (name)
values	('назначено'),
		('аннулировано');

insert into violation (type, min_punishment, max_punishment)
values	('отсутствие работника без уважительной причины на работе', 5, 8),
		('нахождение без уважительных причин не на своем рабочем месте, а в других помещениях организации', 1, 5),
		('преждевременный уход с работы', null, 5),
		('отказ без уважительных причин от поездки в командировку', null, null),
		('невыход на работу в выходной и праздничный день, если работник привлекается к работе на законных основаниях', 5, null),
		('отказ или уклонение без уважительных причин от прохождения в установленном порядке в рабочее время медицинских осмотров работниками некоторых категорий', null, 8),
		('отказ от инструктажа, обучения и проверки знаний по вопросам охраны труда', 1, 3);

insert into fact_of_violation (date_of_the_violation, violation_id, violation_description, end_date_of_punishment)
values 	('2020-10-25 08:30:00', 1, 'опоздание на 30 минут', '2020-10-25'), -- замечание
		('2020-12-08 16:20:35', 3, 'уход на час раньше без предупреждения', '2020-12-09'), -- выговор
		('2021-01-13 14:05:06', 2, 'уход с рабочего места без предупреждения на 2 часа', '2021-02-13'), -- лишение премии
		('2021-01-18 04:05:18', 6, 'отказ проходить медосмотр', '2021-01-18'), -- не учёт в табеле
		('2021-01-21 04:05:03', 5, 'отказ выйти на работу в выходной на законных обстоятельствах', '2021-02-21'), -- штраф
		('2021-02-02 04:05:14', 4, 'отказ от командировки', '2021-04-02'), -- дисциплинарное
		('2021-02-28 08:00:00', 1, 'неявка на работу', '2021-03-14'), -- увольнение
		('2021-05-31 04:05:25', 2, 'отказ от выполнения рабочих обязанностей', '2021-05-31'), -- понижение в должности
		('2021-08-15 10:05:00', 7, 'отказ от инструктажа', '2021-08-15'); -- замечание

insert into violators (fact_of_violation_id, employee_id, punishment_id, status_id)
values 	(1, 5, 1, 1),
	(1, 6, 1, 1),
	(2, 1, 2, 1),
	(3, 7, 6, 1),
	(5, 10, 5, 1),
	(6, 4, 4, 1),
	(7, 10, 3, 1),
	(8, 7, 8, 1),
	(9, 10, 7, 1),
	(9, 9, 1, 1),
	(1, 10, 1, 1),
	(3, 1, 1, 1),
	(3, 3, 2, 1),
	(3, 5, 1, 1),
	(3, 6, 2, 1);

-- найти сведения обо всех нарушениях, допущенных конкретным работником (имя нарушителя – параметр запроса), 
-- а также о наказаниях, понесенных им;
select 	e.name as violator_name, 
		vn.type as violation,
		f.date_of_the_violation,
		p.name as punishment 
from employees e
join violators v on v.employee_id = e.id
left join punishment p on v.punishment_id = p.id
left join fact_of_violation f on v.fact_of_violation_id = f.id
left join violation vn on f.violation_id = vn.id
where e.name like 'Романова%';

-- найти сведения о фактах групповых нарушений (количество нарушителей по каждому факту – не менее трех);
select f.id as fact_of_violation_id, 
		max(vn.type) as violation,
		count(e.id) as num_of_violators
from employees e
join violators v on v.employee_id = e.id
left join punishment p on v.punishment_id = p.id
left join fact_of_violation f on v.fact_of_violation_id = f.id
left join violation vn on f.violation_id = vn.id
group by f.id
having count(e.id) >= 3;

-- найти сведения о пяти самых старых снятых наказаниях.
select 	p.name as punishment,
		f.date_of_the_violation,
		f.end_date_of_punishment
from violators v
left join punishment p on v.punishment_id = p.id
left join fact_of_violation f on v.fact_of_violation_id = f.id
where f.end_date_of_punishment <= current_date
order by f.date_of_the_violation
limit 5;

-- Создать триггеры.
-- Триггер должен отслеживать, чтобы нарушитель не получил более одного наказания за одно нарушение;
create or replace function check_punishment()
returns trigger as
$$
declare
  select_punishment_id  integer;
begin
 select_punishment_id = (select punishment_id from violators
  where (fact_of_violation_id = new.fact_of_violation_id
  and employee_id = new.employee_id 
     and status_id = (select s.id from status s where s.name = 'назначено')));
 if select_punishment_id is not null 
 	and select_punishment_id != new.punishment_id then 
--   значит, наказание уже есть, оно не аннулировано и оно другое 
--   (предполагается, что можно аннулировать старое и назначить новое наказание)
   raise exception 'Наказание уже назначено!';
 end if;
 return new;
end;
$$ language plpgsql;

create trigger check_punishment_trigger before insert or update on violators
for each row 
execute procedure check_punishment();

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
select * from violators
order by fact_of_violation_id;

delete from violators
where fact_of_violation_id = 1 and employee_id = 5 and punishment_id = 2;

insert into violators (fact_of_violation_id, employee_id, punishment_id, status_id)
values 	(1, 5, 2, 1);
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

-- Триггер должен отслеживать, чтобы нарушение не аннулировалось более одного раза;
create or replace function cancel_punishment()
returns trigger as
$$
begin
 if old.status_id = (select s.id from status s where s.name = 'аннулировано')
 	and new.status_id = (select s.id from status s where s.name = 'аннулировано') then
  raise exception 'Наказание уже аннулировано!';
 end if;
 
 return new;
end;
$$ language plpgsql;

create trigger cancel_punishment_trigger before update on violators
for each row 
execute procedure cancel_punishment();

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
select * from violators
order by fact_of_violation_id;

update violators
set status_id = (select s.id from status s where s.name = 'назначено')
where fact_of_violation_id = 1 and employee_id = 5 and punishment_id = 1;

update violators
set status_id = (select s.id from status s where s.name = 'аннулировано')
where fact_of_violation_id = 1 and employee_id = 5 and punishment_id = 1;
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

-- Триггер должен отслеживать, чтобы за одно нарушение, если участников несколько, все получили одинаковые наказания.
create or replace function same_punishment()
returns trigger as
$$
begin
	if new.punishment_id not in (select punishment_id from violators
 					where fact_of_violation_id = new.fact_of_violation_id) then
  		raise exception 'Наказание отличается от остальных!';
 end if;
 
 return new;
end;
$$ language plpgsql;

create trigger same_punishment_trigger before insert or update on violators
for each row 
execute procedure same_punishment();

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
select * from violators
order by fact_of_violation_id;

-- update violators
-- set status_id = 2, punishment_id = 1
-- where fact_of_violation_id = 1

-- update violators
-- set status_id = 2
-- where fact_of_violation_id = 1

delete from violators
where fact_of_violation_id = 1 and employee_id = 9 and punishment_id = 1;

insert into violators
values (1, 9, 2, 1);
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --


