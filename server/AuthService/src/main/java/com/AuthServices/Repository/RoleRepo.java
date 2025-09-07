package com.AuthServices.Repository;

import com.AuthServices.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {

    Optional<Role> findByRoleName(String user);
}
