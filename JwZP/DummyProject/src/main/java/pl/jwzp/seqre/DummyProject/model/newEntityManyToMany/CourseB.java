package pl.jwzp.seqre.DummyProject.model.newEntityManyToMany;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
class CourseB {

    @Id
    Long id;

    @OneToMany(mappedBy = "course")
    Set<StudentB> likes;
}
