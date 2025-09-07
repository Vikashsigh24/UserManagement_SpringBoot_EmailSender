package com.myapp.restapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.restapi.Entities.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>{


}
