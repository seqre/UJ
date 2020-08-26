package pl.jwzp.seqre.DummyProject.model.simpleManyToMany;

import javax.persistence.*;
import java.util.Set;

@Entity
public class EntityA {

    // Owner side
    @ManyToMany
    @JoinTable(
            name = "entities_table",
            joinColumns = @JoinColumn(name = "entitya_id"),
            inverseJoinColumns = @JoinColumn(name = "entityb_id")
    )
    Set<EntityB> entitiesB;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
