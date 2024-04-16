package com.hostly.hostlyapp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hostly.hostlyapp.models.Accommodation;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, UUID> {
}