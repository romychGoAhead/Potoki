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
driver_license boolean default false
)

create table car (
id serial primary key,
make varchar,
model varchar,
price integer
--people_id references people(id)
)

create table people_cars
(
car_id int references car (id),
people_id int references people (id),
primary key(car_id, people_id)
);

insert into people(id, name, age, driver_license) values (1,'Игорь', 18, true);
insert into people(id, name, age, driver_license) values (2,'Марина', 20, true);
insert into people(id, name, age, driver_license) values (3,'Александр', 22, true);


insert into car(id, make, model, price ) values (1,'Тойота', 'ist', 500000);
insert into car(id, make, model, price ) values (2,'Тойота', 'camry', 1500000);
insert into car(id, make, model, price ) values (3,'Тойота', 'rush', 800000);


insert into people_cars(people_id, car_id) values (1, 1);
insert into people_cars(people_id, car_id) values (1, 2);
insert into people_cars(people_id, car_id) values (2, 2);
insert into people_cars(people_id, car_id) values (2, 3);
insert into people_cars(people_id, car_id) values (3, 1);
insert into people_cars(people_id, car_id) values (3, 2);
insert into people_cars(people_id, car_id) values (3, 3);