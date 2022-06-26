CREATE TABLE homework
(
    id           serial PRIMARY KEY,
    content      varchar(255),
    created_at   timestamptz,
    classrooms_id integer,
    teachers_id  integer
);