package com.example.excercise.entity;

import java.util.Date;
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
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "homework")
public class HomeworkEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String content;

  @CreationTimestamp
  private Date createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teachers_id")
  private TeacherEntity teacher;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "classrooms_id")
  private ClassroomEntity classroom;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "homework")
  private List<StudentHomeworkEntity> studentHomework;
}
