package com.example.excercise.service;

import com.example.excercise.dto.request.CreateHomeworkRequest;
import com.example.excercise.dto.responce.StudentsHomeworkResponse;
import com.example.excercise.entity.ClassroomEntity;
import com.example.excercise.entity.HomeworkEntity;
import com.example.excercise.entity.TeacherEntity;
import com.example.excercise.exception.ClassroomNotFoundException;
import com.example.excercise.exception.HomeworkNotFoundException;
import com.example.excercise.exception.TeacherNotFoundException;
import com.example.excercise.mapper.StudentHomeworkMapper;
import com.example.excercise.repository.TeachersRepository;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

  private final TeachersRepository teachersRepository;
  private final StudentHomeworkMapper studentHomeworkMapper = Mappers.getMapper(StudentHomeworkMapper.class);
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
            .teacher(teacher)
            .classroom(classroom)
        .build());
    List<HomeworkEntity> homework = teachersRepository.save(teacher).getHomework();
    return homework.get(homework.size()-1).getId();
  }

  public StudentsHomeworkResponse getStudentHomework(Integer id, Integer grade, Integer clazz, Date create_at) {
    TeacherEntity teacher = teachersRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(id));
    ClassroomEntity classroom = teacher.getClassrooms().stream()
        .filter(i -> i.getGrade().equals(grade) && i.getClassNumber().equals(clazz))
        .findFirst()
        .orElseThrow(() -> new ClassroomNotFoundException(grade, clazz));
    HomeworkEntity homework = teacher.getHomework().stream()
        .filter(i -> i.getClassroom().equals(classroom) && i.getCreatedAt().equals(create_at))
        .findFirst()
        .orElseThrow(HomeworkNotFoundException::new);
    return StudentsHomeworkResponse.builder()
        .homework(homework.getStudentHomework().stream()
            .map(studentHomeworkMapper::toHomeworkResponse)
            .collect(Collectors.toList()))
        .build();
  }
}
