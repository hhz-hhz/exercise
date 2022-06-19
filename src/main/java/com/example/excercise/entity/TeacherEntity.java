package com.example.excercise.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "teacher")
  private List<ClassroomEntity> classrooms;
}
