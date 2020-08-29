package uj.jwzp2020.veterinaryclinic.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uj.jwzp2020.veterinaryclinic.model.client.Client;
import uj.jwzp2020.veterinaryclinic.repository.ClientRepository;

@Repository
//@Profile("jpa")
public interface ClientRepositoryJpa extends JpaRepository<Client, Long>, ClientRepository {
}
