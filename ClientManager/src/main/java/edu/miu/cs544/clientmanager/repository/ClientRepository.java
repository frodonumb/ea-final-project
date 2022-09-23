package edu.miu.cs544.clientmanager.repository;

import edu.miu.cs544.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ClientRepository extends JpaRepository<Client, UUID> {
}
