package com.example.transportv2.repository;

import com.example.transportv2.entity.ResultCalcul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface ResultCalculRepository extends JpaRepository<ResultCalcul, Long> {
}