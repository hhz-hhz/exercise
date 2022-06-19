package com.example.excercise.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Setter;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "students")
public class StudentEntity extends PeopleEntity{
  private Integer grade;
  private Integer classNumber;

  @ManyToMany(cascade=CascadeType.ALL)
  @JoinTable(
      name = "students_homework",
      joinColumns = @JoinColumn(name = "students_id"),
      inverseJoinColumns = @JoinColumn(name = "homework_id"))
  private List<HomeworkEntity> homework;
}
