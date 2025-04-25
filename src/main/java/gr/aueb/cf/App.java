package gr.aueb.cf;

import gr.aueb.cf.core.enums.GenderType;
import gr.aueb.cf.model.Region;
import gr.aueb.cf.model.Teacher;
import gr.aueb.cf.model.TeacherMoreInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.List;


public class App {
    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("school7PU");
    private final static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        em.getTransaction().begin();

//        Teacher teacher = Teacher.builder()
//                .firstname("Νίκος")
//                .lastname("Μίχος")
//                .isActive(true)
//                .build();
//
//        TeacherMoreInfo teacherMoreInfo = TeacherMoreInfo.builder()
//                .dateOfBirth(LocalDateTime.of(1989, 4, 21, 10, 10, 10))
//                .gender(GenderType.MALE)
//                .build();
//
//        teacher.setTeacherMoreInfo(teacherMoreInfo);
//        Region region = em.find(Region.class, 1);
//        teacher.addRegion(region);
//
//        em.persist(teacher);
//        em.getTransaction().commit();

        // merge (update)
//        String sql = "SELECT t from Teacher t WHERE t.lastname = :lastname";
//        TypedQuery<Teacher> query = em.createQuery(sql, Teacher.class);
//        query.setParameter("lastname", "Ανδρούτσος");
//        Teacher teacher = query.getSingleResult();
//        teacher.setFirstname("Athanasios");
//        em.merge(teacher);

//        String sql = "SELECT t from Teacher t WHERE t.lastname = :lastname";
//        TypedQuery<Teacher> query = em.createQuery(sql, Teacher.class);
//        query.setParameter("lastname", "Ανδρούτσος");
//        Teacher teacher = query.getSingleResult();
//        teacher.setIsActive(false);
//        em.merge(teacher);

        // SELECT τους active με region Θεσσαλονικη
//        String sql = "SELECT t from Teacher t WHERE t.isActive = true AND t.region.title = :regionTitle";
//        TypedQuery<Teacher> query = em.createQuery(sql, Teacher.class);
//        query.setParameter("regionTitle", "Θεσσαλονίκη");
//        List<Teacher> teachers = query.getResultList();
//        teachers.forEach(System.out::println);

        // count των teachers ανα περιοχη
//        String sql = "SELECT r.title, COUNT(t) FROM Region r LEFT JOIN r.teachers t WHERE t.isActive = false OR t.isActive IS NULL GROUP BY r.title";
//        TypedQuery<Object[]> query = em.createQuery(sql, Object[].class);
//        List<Object[]> teachersPerRegion = query.getResultList();
//        for (Object[] row : teachersPerRegion) {
//            for (Object item: row) {
//                System.out.print(item + " ");
//            }
//            System.out.println();
//        }

        // Find all active teachers in a region CRITERIA API
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = cb.createQuery(Teacher.class);
        Root<Teacher> teacher = query.from(Teacher.class);
        Join<Teacher, Region> region = teacher.join("region");

        query.select(teacher).where(
                cb.and(
                        cb.isTrue(teacher.get("isActive")),
                        cb.equal(region.get("title"), "Θεσσαλονίκη")
                )
        );
        List<Teacher> teachers = em.createQuery(query).getResultList();
        teachers.forEach(System.out::println);




        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
