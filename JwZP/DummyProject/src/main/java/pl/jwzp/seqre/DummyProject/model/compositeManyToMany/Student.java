package pl.jwzp.seqre.DummyProject.model.compositeManyToMany;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
class Student {

    @Id
    Long id;

    @OneToMany(mappedBy = "student")
    Set<Course> likedCourses;
}
