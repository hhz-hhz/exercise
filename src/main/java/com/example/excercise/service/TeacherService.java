package com.example.excercise.service;

import com.example.excercise.dto.request.CreateHomeworkRequest;
import com.example.excercise.entity.ClassroomEntity;
import com.example.excercise.entity.HomeworkEntity;
import com.example.excercise.entity.TeacherEntity;
import com.example.excercise.exception.ClassroomNotFoundException;
import com.example.excercise.exception.TeacherNotFoundException;
import com.example.excercise.repository.TeachersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

  private final TeachersRepository teachersRepository;
  public Integer createHomework(CreateHomeworkRequest createHomeworkRequest) {
    Integer teacher_id = createHomeworkRequest.getTeacher_id();
    TeacherEntity teacher = teachersRepository.findById(teacher_id)
        .orElseThrow(() -> new TeacherNotFoundException(teacher_id));
    ClassroomEntity classroom = teacher.getClassrooms().stream()
        .filter(i -> i.getId().equals(createHomeworkRequest.getClassroom_id()))
        .findFirst()
        .orElseThrow(() -> new ClassroomNotFoundException(createHomeworkRequest.getClassroom_id()));
    teacher.getHomework().add(HomeworkEntity.builder()
            .content(createHomeworkRequest.getContent())
            .created_at(teacher.getName())
            .teacher(teacher)
            .classroom(classroom)
        .build());
    List<HomeworkEntity> homework = teachersRepository.save(teacher).getHomework();
    return homework.get(homework.size()-1).getId();
  }
}
