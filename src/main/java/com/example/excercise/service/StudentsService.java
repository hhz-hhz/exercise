package com.example.excercise.service;


import com.example.excercise.dto.request.CreateHomeworkRequest;
import com.example.excercise.dto.request.CreateStudentRequest;
import com.example.excercise.dto.request.UpdateHomeworkRequest;
import com.example.excercise.dto.responce.StudentGroupsResponse;
import com.example.excercise.dto.responce.StudentIdResponse;
import com.example.excercise.dto.responce.StudentsResponse;
import com.example.excercise.entity.HomeworkEntity;
import com.example.excercise.entity.StudentEntity;
import com.example.excercise.exception.ClassNumberNotValidatedException;
import com.example.excercise.exception.GradeNotValidatedException;
import com.example.excercise.exception.HomeworkNotFoundException;
import com.example.excercise.exception.NameIsNullException;
import com.example.excercise.exception.StudentNotFoundException;
import com.example.excercise.mapper.StudentMapper;
import com.example.excercise.repository.StudentsRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentsService {


  private final StudentsRepository studentsRepository;

  private final StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);

  public StudentIdResponse createStudent(CreateStudentRequest createStudentRequest){
    validateStudentRequest(createStudentRequest);
    StudentEntity student = StudentEntity.builder()
        .name(createStudentRequest.getName())
        .grade(createStudentRequest.getGrade())
        .classNumber(createStudentRequest.getClassNumber())
        .build();
    return studentMapper.toStudentIdResponse(studentsRepository.save(student));
  }

  private void validateStudentRequest(CreateStudentRequest createStudentRequest){
    if(createStudentRequest.getName().isBlank()){
      throw new NameIsNullException();
    }
    if(createStudentRequest.getGrade() < 1 || createStudentRequest.getGrade() > 9){
      throw new GradeNotValidatedException();
    }
    if(createStudentRequest.getClassNumber() < 1 || createStudentRequest.getClassNumber() > 20){
      throw new ClassNumberNotValidatedException();
    }
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

  public Integer submitHomework(CreateHomeworkRequest createHomeworkRequest) {
    List<StudentEntity> studentsList = new ArrayList<>();
    createHomeworkRequest.getStudent()
        .forEach(studentId -> studentsList.add(studentsRepository.findById(studentId)
            .orElseThrow(() -> new StudentNotFoundException(studentId))));
    HomeworkEntity homeworkEntity = HomeworkEntity.builder()
        .student(new ArrayList<>(studentsList))
        .topic(createHomeworkRequest.getTopic())
        .content(createHomeworkRequest.getContent())
        .build();
    studentsList.get(0).getHomework().add(homeworkEntity);
    List<HomeworkEntity> homework = studentsRepository.save(studentsList.get(0)).getHomework();
    HomeworkEntity newHomework = homework.get(homework.size() - 1);
    studentsList.stream().skip(1).forEach(student -> {
      student.getHomework().add(newHomework);
      studentsRepository.save(student);
    });
    return newHomework.getId();
  }

  public StudentsResponse updateHomework(Integer studentId, UpdateHomeworkRequest updateHomeworkRequest) {
    StudentEntity student = studentsRepository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));
    Integer homeworkId = updateHomeworkRequest.getId();
    String topic = updateHomeworkRequest.getTopic();
    String content = updateHomeworkRequest.getContent();
    List<HomeworkEntity> homeworkList = student.getHomework();
    HomeworkEntity oldHomework = homeworkList
        .stream()
        .filter(homework -> homework.getId().equals(homeworkId))
        .findFirst()
        .orElseThrow(() -> new HomeworkNotFoundException(homeworkId));
    HomeworkEntity homework = homeworkList.get(homeworkList.indexOf(oldHomework));
    if(topic != null && !topic.isBlank()){
      homework.setTopic(topic);
    }
    if(content != null && !content.isEmpty()){
      homework.setContent(content);
    }
    StudentEntity studentEntity = studentsRepository.save(student);

    return StudentsResponse.builder()
        .data(List.of(studentMapper.toStudentResponseInStudentsResponse(studentEntity)))
        .build();
  }

  public StudentGroupsResponse findStudentGroupsByTopic(String topic) {
    List<StudentEntity> allStudents = studentsRepository.findAll();
    HashSet<HomeworkEntity> groupHomework = new HashSet<>();
    allStudents.stream()
        .filter(student -> student.getHomework()
            .stream()
            .anyMatch(i -> i.getTopic().equals(topic)))
        .forEach(i -> groupHomework.add(i.getHomework().stream()
            .filter(o -> o.getTopic()
                .equals(topic))
            .findFirst()
            .orElse(null)));
    StudentGroupsResponse response = StudentGroupsResponse.builder()
        .groups(new ArrayList<>())
        .build();
    groupHomework
        .forEach(i -> response.getGroups().add(i.getStudent().stream()
            .map(studentMapper::toStudentResponseInStudentGroupsResponse)
            .collect(Collectors.toList())));
    return response;
  }
}
