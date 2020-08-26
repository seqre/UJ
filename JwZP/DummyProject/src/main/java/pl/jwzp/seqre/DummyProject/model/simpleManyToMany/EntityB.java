package pl.jwzp.seqre.DummyProject.model.simpleManyToMany;

import javax.persistence.*;
import java.util.Set;

@Entity
public class EntityB {

    @ManyToMany(mappedBy = "entitiesB")
    Set<EntityA> entitiesA;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
