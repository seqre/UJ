package pl.jwzp.seqre.DummyProject.model.newEntityManyToMany;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
class StudentB {

    @Id
    Long id;

    @OneToMany(mappedBy = "student")
    Set<CourseB> likedCours;
}
