package pl.jwzp.seqre.DummyProject.model.newEntityManyToMany;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
class CourseRegistration {

    @Id
    Long id;

    @ManyToOne
    @JoinColumn(name = "studentb_id")
    StudentB student;

    @ManyToOne
    @JoinColumn(name = "courseb_id")
    CourseB course;

    LocalDateTime registeredAt;

    int grade;
}
