package com.example.excercise.service;


import com.example.excercise.dto.request.CreateStudentHomeworkRequest;
import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.dto.request.UpdateHomeworkRequest;
import com.example.excercise.dto.responce.StudentIdResponse;
import com.example.excercise.dto.responce.StudentsResponse;
import com.example.excercise.entity.ClassroomEntity;
import com.example.excercise.entity.HomeworkEntity;
import com.example.excercise.entity.StudentEntity;
import com.example.excercise.entity.StudentHomeworkEntity;
import com.example.excercise.exception.ClassroomNotFoundException;
import com.example.excercise.exception.HomeworkNotFoundException;
import com.example.excercise.exception.StudentNotFoundException;
import com.example.excercise.mapper.StudentMapper;
import com.example.excercise.repository.ClassroomsRepository;
import com.example.excercise.repository.HomeworkRepository;
import com.example.excercise.repository.StudentsRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import com.example.excercise.util.ValidationUtil;

@Service
@RequiredArgsConstructor
public class StudentService {


  private final StudentsRepository studentsRepository;

  private final ClassroomsRepository classroomsRepository;

  private final HomeworkRepository homeworkRepository;

  private final StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);

  public StudentIdResponse createStudent(CreateStudentRequest createStudentRequest){
    ValidationUtil.validateStudentRequest(createStudentRequest);
    Integer grade = createStudentRequest.getGrade();
    Integer classNumber = createStudentRequest.getClassNumber();
    ClassroomEntity classroom = classroomsRepository.findByGradeAndClassNumber(grade,
        classNumber).orElseThrow(() -> new ClassroomNotFoundException(grade, classNumber));

    StudentEntity student = StudentEntity.builder()
        .name(createStudentRequest.getName())
        .classroom(classroom)
        .build();
    return studentMapper.toStudentIdResponse(studentsRepository.save(student));
  }


  public StudentsResponse findStudentById(Integer id) {
    StudentEntity studentEntity = studentsRepository.findById(id)
        .orElseThrow(() -> new StudentNotFoundException(id));
    return StudentsResponse.builder()
        .data(List.of(studentMapper.toStudentResponseInStudentsResponse(studentEntity)))
        .build();
  }

  public StudentsResponse findAllStudents(){
    List<StudentEntity> allStudents = studentsRepository.findAll();
    return StudentsResponse.builder()
        .data(allStudents.stream()
            .map(studentMapper::toStudentResponseInStudentsResponse)
            .collect(Collectors.toList()))
        .build();
  }

  public StudentsResponse findStudentsByName(String name) {
    List<StudentEntity> requiredStudents = studentsRepository.findAllByName(name);
    return StudentsResponse.builder()
        .data(requiredStudents.stream()
            .map(studentMapper::toStudentResponseInStudentsResponse)
            .collect(Collectors.toList()))
        .build();
  }

  public Integer submitHomework(Integer homeworkId,CreateStudentHomeworkRequest createStudentHomeworkRequest) {
    StudentEntity student = studentsRepository.findById(createStudentHomeworkRequest.getStudent_id())
        .orElseThrow(() -> new StudentNotFoundException(createStudentHomeworkRequest.getStudent_id()));
    HomeworkEntity homework = homeworkRepository.findById(homeworkId)
        .orElseThrow(() -> new HomeworkNotFoundException(homeworkId));
    student.getStudentHomework().add(StudentHomeworkEntity.builder()
            .content(createStudentHomeworkRequest.getContent())
            .student(student)
            .homework(homework)
        .build());
    List<StudentHomeworkEntity> studentHomework = studentsRepository.save(student).getStudentHomework();
    return studentHomework.get(studentHomework.size() - 1).getId();
  }

  public StudentsResponse updateHomework(Integer studentId, UpdateHomeworkRequest updateHomeworkRequest) {
    StudentEntity student = studentsRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));
    Integer homeworkId = updateHomeworkRequest.getId();
    StudentHomeworkEntity studentHomework = student.getStudentHomework().stream()
        .filter(homework -> homework.getHomework().getId().equals(homeworkId))
        .findFirst()
        .orElseThrow(() -> new HomeworkNotFoundException(homeworkId));
    String content = updateHomeworkRequest.getContent();
    if(content != null && !content.isEmpty()){
      studentHomework.setContent(content);
    }
    StudentEntity studentEntity = studentsRepository.save(student);

    return StudentsResponse.builder()
        .data(List.of(studentMapper.toStudentResponseInStudentsResponse(studentEntity)))
        .build();
  }

}
