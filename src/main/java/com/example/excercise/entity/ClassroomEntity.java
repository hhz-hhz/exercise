package com.example.excercise.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classrooms")
public class ClassroomEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer grade;
  private Integer classNumber;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "classroom")
  private List<StudentEntity> students;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teachers_id")
  private TeacherEntity teacher;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "classroom")
  List<HomeworkEntity> homework;
}
