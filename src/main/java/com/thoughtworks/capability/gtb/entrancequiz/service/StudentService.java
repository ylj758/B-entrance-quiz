package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.dto.Group;
import com.thoughtworks.capability.gtb.entrancequiz.dto.Student;
import com.thoughtworks.capability.gtb.entrancequiz.exception.BusinessException;
import com.thoughtworks.capability.gtb.entrancequiz.exception.EmBusinessError;
import com.thoughtworks.capability.gtb.entrancequiz.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StudentService {
    final StudentRepository studentRepository;

    private List<Student> studentList;
    private List<Group> groupList;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        studentList = studentRepository.getStudents();
        groupList = studentRepository.getGroups();
    }

    public void addStudent(Student student) {
        student.setId(studentList.get(studentList.size() - 1).getId() + 1);
        studentList.add(student);
    }

    public void deleteStudent(Integer studentId) throws BusinessException {
        Optional<Student> studentOptional = getStudentById(studentId);
        if (!studentOptional.isPresent()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        studentList.remove(studentOptional.get());
    }


    public List<Student> getStudentList() {
        return studentList;
    }

    public List<Student> getStudentListByGender(String gender) {
        return studentList.stream().filter(student -> student.getGender().equals(gender)).collect(Collectors.toList());
    }

    public Student getStudentListById(Integer id) throws BusinessException {
        Optional<Student> studentOptional = getStudentById(id);
        if (!studentOptional.isPresent()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        return studentOptional.get();
    }

    public void updateStudentPartInfo(Student student) {
        Optional<Student> studentOptional = getStudentById(student.getId());
        if (studentOptional.isPresent()) {
            Student stu = studentOptional.get();
            stu.setGender(student.getGender());
            stu.setName(student.getName());
            stu.setNote(student.getNote());
        }

    }

    public List<Group> groupStudents() {
        List<Student> randomStudents = new ArrayList<>();
        studentList.forEach(student -> randomStudents.add(student));
        Collections.shuffle(randomStudents);
        int studentCount = studentList.size();
        int baseCount = studentCount / 6;
        int leftCount = studentCount % 6;

        for (Group group : groupList) {
            List<Student> list = randomStudents.stream().limit(baseCount).collect(Collectors.toList());
            group.setStudents(list);
            list.forEach(student -> randomStudents.remove(student));
        }
        for (int i=0;i<leftCount;i++){
            groupList.get(i).getStudents().add(randomStudents.get(i));
        }
        return groupList;
    }

    public void updateGroupName(Group group) throws BusinessException {
        Optional<Group> groupOptional = getGroupById(group.getId());
        if (!groupOptional.isPresent()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        groupOptional.get().setName(group.getName());
    }

    private Optional<Student> getStudentById(Integer id) {
        List<Student> collect = studentList.stream().filter(student -> student.getId() == id).collect(Collectors.toList());
        return collect.size() == 0 ? null : Optional.of(collect.get(0));
    }

    private Optional<Group> getGroupById(Integer id) {
        List<Group> collect = groupList.stream().filter(group -> group.getId() == id).collect(Collectors.toList());
        return collect.size() == 0 ? null : Optional.of(collect.get(0));
    }

    public List<Group> getGroups() {
        if (groupList.get(0).getStudents()==null){
            groupStudents();
        }
        return groupList;
    }
}
