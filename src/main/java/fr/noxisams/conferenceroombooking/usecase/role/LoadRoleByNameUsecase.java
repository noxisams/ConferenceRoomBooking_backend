package fr.noxisams.conferenceroombooking.usecase.role;

import fr.noxisams.conferenceroombooking.enumeration.RoleEnum;
import fr.noxisams.conferenceroombooking.exception.RoleNotFoundException;
import fr.noxisams.conferenceroombooking.model.Role;
import fr.noxisams.conferenceroombooking.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Component
public class LoadRoleByNameUsecase {

    private final RoleRepository roleRepository;

    @Autowired
    public LoadRoleByNameUsecase(RoleRepository roleRepository) {
        Assert.notNull(roleRepository, "roleRepository must not be null");
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public Role execute(RoleEnum name) {
        return roleRepository.findByName(name).orElseThrow(
                () -> new RoleNotFoundException("Role Not Found with name: " + name)
        );
    }
}
