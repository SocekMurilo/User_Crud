package com.murilo.crud_user.repositories;

import com.murilo.crud_user.models.UserModel;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID>{
    // List<UserModel> findByAutor(String autor);
    // List<UserModel> findByTitulo(String titulo);
}