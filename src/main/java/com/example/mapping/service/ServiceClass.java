package com.example.mapping.service;

import com.example.mapping.entity.Address;
import com.example.mapping.entity.Course;
import com.example.mapping.entity.Enrollment;
import com.example.mapping.entity.Student;
import com.example.mapping.repository.AddressRepository;
import com.example.mapping.repository.CourseRepository;
import com.example.mapping.repository.EnrollmentRepository;
import com.example.mapping.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceClass {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private AddressRepository addressRepository;

    //constructor for final(if used)
  /*  @Autowired
    public ServiceClass(StudentRepository studentRepository, CourseRepository courseRepository,
                             EnrollmentRepository enrollmentRepository, AddressRepository addressRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.addressRepository = addressRepository;
    }*/
    //only the data type which is not mapped to any other is inserted here.
    @Transactional                 //for inserting into database automatically
    public void insertData() {
        // Insert initial data for Student
        Student student1 = new Student();
        student1.setName("Neha KM");

        Student student2 = new Student();
        student2.setName("Pooja KM");

        //  studentRepository.saveAll(List.of(student1,student2));
        //using this method instead of saveAll coz of joining column in enrollment
        List<Student> savedStudents = studentRepository.saveAll(List.of(student1, student2));
        // Retrieve students by their IDs
        List<Long> studentIds = List.of(savedStudents.get(0).getId(), savedStudents.get(1).getId());
        List<Student> retrievedStudents = studentRepository.findAllById(studentIds);

        // Insert initial data for Course
        Course course1 = new Course();
        course1.setName("Mathematics");
        course1.setDescription("Introduction to Mathematics");

        Course course2 = new Course();
        course2.setName("Biology");
        course2.setDescription("Introduction to Biology");

        List<Course> savedCourses = courseRepository.saveAll(List.of(course1, course2));
        // Retrieve students by their IDs
        List<Long> courseIds = List.of(savedCourses.get(0).getId(), savedCourses.get(1).getId());
        List<Course> retrievedCourses = courseRepository.findAllById(courseIds);

        // Insert initial data for Enrollment

        /*Student student = studentRepository.findAllById().orElse(null);
                .orElseThrow(() -> new RuntimeException("Student with ID 1 not found"));
        student.getEnrollments().size(); // Trigger lazy loading within the transaction
        Course course = courseRepository.findById((1L)).orElse(null);*/

        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        enrollment1.setSemester("First Semester");
        enrollmentRepository.save(enrollment1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course2);
        enrollment2.setSemester("Second Semester");
        enrollmentRepository.save(enrollment2);

        // Associate enrollment with student
        for (Student student : retrievedStudents) {
            student.getEnrollments().addAll(List.of(enrollment1, enrollment2));
        }
        // Associate enrollment with course
        for (Course course : retrievedCourses) {
            course.getEnrollments().addAll(List.of(enrollment1, enrollment2));
        }


        // Insert initial data for Address
        Address address1 = new Address();
        address1.setStreet("Gokula");
        address1.setCity("Bhadravathi");
        address1.setCountry("India");
        address1.setStudent(student1); // Associate address with student
        // Associate address with student
        student1.setAddress(address1);
   //     studentRepository.save(student1);

        Address address2 = new Address();
        address2.setStreet("Kundalahalli");
        address2.setCity("Bengaluru");
        address2.setCountry("India");
        address2.setStudent(student2); // Associate address with student
        // Associate address with student
        student2.setAddress(address2);
      //  studentRepository.save(student2);
        studentRepository.saveAll(List.of(student1,student2));

        addressRepository.saveAll(List.of(address1,address2));
    }


}

