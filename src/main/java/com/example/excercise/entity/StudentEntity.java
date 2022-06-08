package com.example.excercise.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class StudentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private Integer grade;
  private Integer classNumber;

  @OneToMany(targetEntity = HomeworkEntity.class, cascade = CascadeType.ALL)
  private List<HomeworkEntity> homework;

  public Integer addHomework(HomeworkEntity homeworkEntity){
    homework.add(homeworkEntity);
    return homework.size();
  }
}
