package uj.jwzp2020.veterinaryclinic.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uj.jwzp2020.veterinaryclinic.model.pet.Pet;
import uj.jwzp2020.veterinaryclinic.repository.PetRepository;

@Repository
//@Profile("jpa")
public interface PetRepositoryJpa extends JpaRepository<Pet, Long>, PetRepository {
}
