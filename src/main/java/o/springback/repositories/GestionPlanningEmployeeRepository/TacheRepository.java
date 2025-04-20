package o.springback.repositories.GestionPlanningEmployeeRepository;
import o.springback.entities.GestionPlanningEmployee.StatutTache;
import o.springback.entities.GestionPlanningEmployee.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.table.TableCellEditor;
import java.util.Date;
import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Long>{
    //@Query("SELECT t.employee.idEmployee, COUNT(t) FROM Tache t WHERE t.employee IS NOT NULL GROUP BY t.employee.idEmployee")
    @Query("SELECT t.employee.nom, t.employee.prenom, COUNT(t) FROM Tache t WHERE t.employee.idEmployee= :id GROUP BY t.employee.nom, t.employee.prenom")
    List<Object[]> countTachesByEmployee(@Param("id") Long id);

    @Query("SELECT t.statutTache, COUNT(t) FROM Tache t WHERE t.employee.idEmployee= :id GROUP BY t.statutTache ORDER BY t.statutTache")
    List<Object[]> countTachesByStatutTacheForEmployee(@Param("id") Long employeeId);
    @Query("SELECT t FROM Tache t WHERE t.employee.idEmployee= :id AND t.statutTache = 'TERMINEE' AND t.dateFin BETWEEN :startDate AND :endDate")
    List<Tache> findTachesTermineesParPeriode(@Param("id") Long employeeId,
                                              @Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate);
    @Query("SELECT t FROM Tache t WHERE t.employee.idEmployee= :id AND t.dateFin BETWEEN :startDate AND :endDate")
    List<Tache> findTachesParDate(@Param("id") Long employeeId,
                                              @Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate);
    @Query("SELECT COUNT(t) > 0 FROM Tache t WHERE t.parent.idTache =:id")
    boolean hasChildTaches(@Param("id") Long id);

    List<Tache> findByEmployee_IdEmployee(Long id);
    @Query("SELECT MAX (t.position) FROM Tache t WHERE t.statutTache = :statut")
    Integer findMaxPositionByStatutTache(@Param("statut") StatutTache statut);
    @Query("SELECT t FROM Tache t WHERE t.parent.idTache = :parentId")
    Integer findMaxPositionUnderParent(@Param("parentId") Long parentId);
    @Query("SELECT t FROM Tache t WHERE t.dateFin BETWEEN :start AND :end")
    List<Tache> findByDateFinBetween(@Param("start") Date start, @Param("end") Date end);


}