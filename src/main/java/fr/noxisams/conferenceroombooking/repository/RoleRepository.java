package fr.noxisams.conferenceroombooking.repository;

import fr.noxisams.conferenceroombooking.enumeration.RoleEnum;
import fr.noxisams.conferenceroombooking.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(RoleEnum name);
}
