package com.tinie.User.repositories;

import com.tinie.User.models.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    /**
     * Retrieve a {@link UserDetails} matching given {@code phoneNumber}
     * @param phoneNumber Phone number to match on
     * @return An {@link Optional} of {@link UserDetails} or {@link Optional#empty()}
     */
    Optional<UserDetails> findByPhoneNumber(long phoneNumber);
}
