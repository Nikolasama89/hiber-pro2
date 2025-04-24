package gr.aueb.cf.model;

import gr.aueb.cf.core.enums.LessonType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

//    @Column(length = 1000000)     for all databases
    @Column(columnDefinition = "TEXT")  //Database Specific
    private String comments;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "lesson_type", columnDefinition = "TINYINT COMMENT 'ΕΙΔΟΣ ΜΑΘΗΜΑΤΟΣ (1. Θεωρία, 2. Εργαστήριο, 3. Μικτό)'")
    private LessonType lessonType;

    @Getter(AccessLevel.PROTECTED)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "courses_teachers")   // table-name + _id
//    @JoinTable(name = "courses_teachers", joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
//        inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id")
//    )
    private Set<Teacher> teachers = new HashSet<>();

    public Set<Teacher> getAllTeachers() {
        return Collections.unmodifiableSet(teachers);
    }

    public void addTeacher(Teacher teacher) {
        if (teachers == null) teachers = new HashSet<>();
        teachers.add(teacher);
        teacher.getCourses().add(this);
    }

    public void removeTeacher(Teacher teacher) {
        if (teachers == null) return;
        teachers.remove(teacher);
        teacher.getCourses().remove(this);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", comments='" + comments + '\'' +
                ", lessonType=" + lessonType +
                '}';
    }
}
