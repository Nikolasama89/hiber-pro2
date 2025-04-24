package gr.aueb.cf;

import gr.aueb.cf.core.enums.GenderType;
import gr.aueb.cf.model.Region;
import gr.aueb.cf.model.Teacher;
import gr.aueb.cf.model.TeacherMoreInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;


public class App {
    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("school7PU");
    private final static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        em.getTransaction().begin();

        Teacher teacher = Teacher.builder()
                .firstname("Νίκος")
                .lastname("Μίχος")
                .isActive(true)
                .build();

        TeacherMoreInfo teacherMoreInfo = TeacherMoreInfo.builder()
                .dateOfBirth(LocalDateTime.of(1989, 4, 21, 10, 10, 10))
                .gender(GenderType.MALE)
                .build();

        teacher.setTeacherMoreInfo(teacherMoreInfo);
        Region region = em.find(Region.class, 1);
        teacher.addRegion(region);

        em.persist(teacher);
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
