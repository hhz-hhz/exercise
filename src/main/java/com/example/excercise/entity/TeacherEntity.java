package com.example.excercise.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class TeacherEntity extends PeopleEntity{
}
