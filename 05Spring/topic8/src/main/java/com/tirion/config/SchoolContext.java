package com.tirion.config;

import com.tirion.demo.Klass;
import com.tirion.demo.School;
import com.tirion.demo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;


@Configuration
@ConditionalOnBean(SchoolBaseAutoConfiguration.class)
public class SchoolContext extends SchoolBaseAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SchoolContext.class);

    private School school;

    @PostConstruct
    private void init() {
        school = school();
        System.out.println("----------" + school.getClass1().getStudents().get(0).getName());
    }

    @Bean
    public School school() {
        School school = new School();
        school.setClass1(klass());
        return school;
    }

    @Bean
    public Klass klass() {
        Klass klass = new Klass();
        klass.setStudents(new ArrayList<>());
        klass.getStudents().add(student100());
        return klass;
    }

    @Bean
    public Student student100() {
        Student student = new Student();
        student.setName("xxx");
        return student;
    }

    @PreDestroy
    public void destroy() {

    }
}
