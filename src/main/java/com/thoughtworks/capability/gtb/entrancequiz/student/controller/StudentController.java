package com.thoughtworks.capability.gtb.entrancequiz.student.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capability.gtb.entrancequiz.student.dto.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    //TODO GTB-知识点: - StudentController.java:22 数据存储的相关职责，不是controller的职责
    private List<Student> studentList;

    public StudentController(){
        initStudentList();
    }

    private void initStudentList() {
        studentList = new ArrayList<>();
        studentList.add(new Student(1, "成吉思汗"));
        studentList.add(new Student(2, "鲁班七号"));
        studentList.add(new Student(3, "太乙真人"));
        studentList.add(new Student(4, "钟无艳"));
        studentList.add(new Student(5, "花木兰"));
        studentList.add(new Student(6, "雅典娜"));
        studentList.add(new Student(7, "芈月"));
        studentList.add(new Student(8, "白起"));
        studentList.add(new Student(9, "刘禅"));
        studentList.add(new Student(10, "庄周"));
        studentList.add(new Student(11, "马超"));
        studentList.add(new Student(12, "刘备"));
        studentList.add(new Student(13, "哪吒"));
        studentList.add(new Student(14, "大乔"));
        studentList.add(new Student(15, "蔡文姬"));
    }

    @GetMapping("/students")
    @ResponseStatus(code = HttpStatus.OK)
    //TODO GTB-知识点: - StudentController.java:44 直接返回studentList即可，Spring框架会自动进行序列化
    public String getStudents() throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(studentList);
        return json;
    }

    //TODO GTB-知识点: - StudentController.java:49 不符合restful实践，一般路径中是资源名称，而非动词
    @PostMapping("/student/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String addStudents(@RequestBody String name) throws JsonProcessingException {
        //TODO GTB-工程实践: - StudentController.java:54 这种自增id的方式，线程不安全，试试AtomicInteger
        studentList.add(new Student(studentList.size()+1,name));
        String json = new ObjectMapper().writeValueAsString(studentList);
        return json;
    }

    //TODO GTB-完成度: - StudentController.java:56 缺少分组的接口
    //TODO GTB-完成度: - StudentController.java:57 缺少查看分组的接口


}
