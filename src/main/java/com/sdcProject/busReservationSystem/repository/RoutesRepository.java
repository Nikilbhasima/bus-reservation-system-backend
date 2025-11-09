package com.sdcProject.busReservationSystem.repository;

import com.sdcProject.busReservationSystem.modal.Routes;
import com.sdcProject.busReservationSystem.modal.TravelAgency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
public interface RoutesRepository extends CrudRepository<Routes, Integer> {
    List<Routes> findByTravelAgency(TravelAgency travelAgency);

    @Query("""
    SELECT r FROM Routes r
    WHERE 
        (
            (LOWER(r.sourceCity) LIKE LOWER(CONCAT('%', :sourceCity, '%')) 
             OR LOWER(r.sourceCity) LIKE LOWER(CONCAT('%', :destinationCity, '%')))
        )
        AND
        (
            (LOWER(r.destinationCity) LIKE LOWER(CONCAT('%', :destinationCity, '%')) 
             OR LOWER(r.destinationCity) LIKE LOWER(CONCAT('%', :sourceCity, '%')))
        )
""")
    List<Routes> findMatchingRoutes(@Param("sourceCity") String sourceCity,
                                    @Param("destinationCity") String destinationCity);

}
