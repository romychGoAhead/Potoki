-- Шаг 1. В корне проекта нужно создать файл scripts421.sql (что значит 4-й курс, 2-й урок, 1-е задание)
--и поместить в него запросы для создания ограничений.

--Возраст студента не может быть меньше 16 лет.
alter table student
add constraint age check (age >=16);
--Имена студентов должны быть уникальными и не равны нулю.
alter table student
add constraint uniq_name unique (name);

alter table student
alter column name set not null;

--Пара “значение названия” - “цвет факультета” должна быть уникальной.
alter table faculty
add constraint uniq_name_color unique (name,color);
--При создании студента без возраста ему автоматически должно присваиваться 20 лет.
alter table student
alter column age set default 20;

