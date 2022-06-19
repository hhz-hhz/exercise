package com.example.excercise.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "classrooms_id", nullable = false)
  private ClassroomEntity classroom;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "student")
  private List<StudentHomeworkEntity> studentHomework;

  @ManyToMany(cascade=CascadeType.ALL)
  @JoinTable(
      name = "students_homework",
      joinColumns = @JoinColumn(name = "students_id"),
      inverseJoinColumns = @JoinColumn(name = "homework_id"))
  private List<HomeworkEntity> homework;
}
