--Шаг 2. необходимо спроектировать таблицы, связи между ними и корректно определить типы данных.
--Здесь не важно, какой тип вы выберете, например, для данных, представленных в виде строки (varchar или text).
--Важно, что вы выберете один из строковых типов, а не числовых, например.

--Описание структуры: у каждого человека есть машина. Причем несколько человек могут
--пользоваться одной машиной. У каждого человека есть имя, возраст и признак того,
--что у него есть права (или их нет). У каждой машины есть марка, модель и стоимость.
--Также не забудьте добавить таблицам первичные ключи и связать их.
create table people (
id serial primary key,
name varchar,
age integer,
rights boolean default false
)

create table car (
id serial primary key,
make varchar,
model varchar,
price integer
--people_id references people(id)
)

create table peopleAndcars
(
	car_id int foreign key references car (car_id),
	people_id int foreign key references people (people_id),
	primary key(car_id, people_id)
)

insert into people(name, age, rights) values ('Игорь', 18, true);
insert into people(name, age, rights) values ('Марина', 20, true);
insert into people(name, age, rights) values ('Александр', 22, true);


insert into car(people_id, make, model, price ) values (1,'Тойота', 'ist', 500000);
insert into car(people_id, make, model, price ) values (2,'Тойота', 'camry', 1500000);
insert into car(people_id, make, model, price ) values (3,'Тойота', 'rush', 800000);


insert into peopleAndcars(people_id, car_id) values (1, 1);
insert into peopleAndcars(people_id, car_id) values (1, 2);
insert into peopleAndcars(people_id, car_id) values (2, 2);
insert into peopleAndcars(people_id, car_id) values (2, 3);
insert into peopleAndcars(people_id, car_id) values (3, 1);
insert into peopleAndcars(people_id, car_id) values (3, 2);
insert into peopleAndcars(people_id, car_id) values (3, 3);