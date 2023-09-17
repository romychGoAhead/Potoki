-- liquibase formatted sql

-- changeset roman:2023-09-10-std-ind
CREATE INDEX student_name_index ON student (name);

-- changeset roman:2023-09-10-fac-ind
CREATE INDEX faculty_ind ON faculty (name,color);