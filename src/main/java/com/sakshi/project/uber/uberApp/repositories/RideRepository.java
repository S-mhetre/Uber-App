package com.sakshi.project.uber.uberApp.repositories;

import com.sakshi.project.uber.uberApp.entities.Driver;
import com.sakshi.project.uber.uberApp.entities.Ride;
import com.sakshi.project.uber.uberApp.entities.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    Page<Ride> findByRider(Rider rider, PageRequest pageRequest);

    Page<Ride> findByDriver(Driver driver, PageRequest pageRequest);
}
