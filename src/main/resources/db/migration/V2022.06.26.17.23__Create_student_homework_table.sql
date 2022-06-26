CREATE TABLE student_homework
(
    id          serial PRIMARY KEY,
    content     varchar(255),
    homework_id integer,
    students_id integer
);