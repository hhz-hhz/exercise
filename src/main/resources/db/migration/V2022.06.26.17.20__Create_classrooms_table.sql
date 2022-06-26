CREATE TABLE classrooms
(
    id           serial PRIMARY KEY,
    grade        integer,
    class_number integer,
    teachers_id  integer
);