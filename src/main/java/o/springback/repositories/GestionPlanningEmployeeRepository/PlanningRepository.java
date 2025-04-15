package o.springback.repositories.GestionPlanningEmployeeRepository;
import o.springback.entities.GestionPlanningEmployee.Planning;
import o.springback.entities.GestionPlanningEmployee.TypePlanning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long>{
    @Query("SELECT p FROM Planning p WHERE p.tache.employee.idEmployee= :employeeId AND p.typePlanning IN :types AND " +
            "((p.dateDebut BETWEEN :start AND :end) OR (p.dateFin BETWEEN :start AND :end) OR (p.dateDebut <= :start AND p.dateFin >= :end))")
    List<Planning> findPlanningByTypePlanning(@Param("employeeId") Long employeeId,
                                              @Param("types")List<TypePlanning> types,
                                              @Param("start") Date start,
                                              @Param("end") Date end);

    @Query("SELECT p FROM Planning p WHERE p.employee.idEmployee = :id")
    List<Planning> findByEmployeeId(@Param("id") Long id);
}