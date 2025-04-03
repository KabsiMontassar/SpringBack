package o.springback.services.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.ITacheService;
import o.springback.entities.GestionPlanningEmployee.PeriodeHistorique;
import o.springback.entities.GestionPlanningEmployee.StatutTache;
import o.springback.entities.GestionPlanningEmployee.Tache;
import o.springback.repositories.GestionPlanningEmployeeRepository.TacheRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.*;

import static java.time.Month.*;

@Service
@AllArgsConstructor
public class TacheService implements ITacheService{
    private TacheRepository tacheRepository;
    @Override
    public Tache save(Tache tache) {
        return tacheRepository.save(tache);
    }

    @Override
    public Tache update(Tache tache) {
        return tacheRepository.save(tache);
    }

    @Override
    public void delete(Long id) {
        tacheRepository.deleteById(id);
    }

    @Override
    public Tache findById(Long id) {
        return tacheRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tache> findAll() {
        return tacheRepository.findAll();
    }

    @Override
    public Map<String, Object> getNombreTachesParEmploye(Long employeeId) {
        List<Object[]> results = tacheRepository.countTachesByEmployee(employeeId);
        if (results.isEmpty()) return null;
        Object [] result = results.get(0);
        String nom = (String) result[0];
        String prenom = (String) result[1];
        Long count = (Long) result[2];
        Map<String, Object> map = new HashMap<>();
        map.put("Nom", nom);
        map.put("Prénom ", prenom);
        map.put("Nombre de taches ", count);

        return map;
    }

    @Override
    public Map<String, Long> getNombreTachesParStatut(Long employeeId) {
        List<Object[]> result = tacheRepository.countTachesByStatutTacheForEmployee(employeeId);
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] statutCount : result ) {
            StatutTache statut = (StatutTache) statutCount[0];
            long count = (Long) statutCount[1];
            map.put(statut.name(), count);
        }
        return map;
    }

    @Override
    public Long getTachesParStatut(Long employeeId, StatutTache statut) {
        List<Object[]> status = tacheRepository.countTachesByStatutTacheForEmployee(employeeId);
        for (Object[] stat: status) {
            StatutTache currentStatut = (StatutTache) stat[0];
            Long count = (Long) stat[1];
            if (currentStatut.equals(statut)) {
                return count;
            }
        }
        return 0L; //aucune tâche trouvée
    }

    @Override
    public Map<String, Object> getHistoriqueTachesParPeriode(Long employeeId, PeriodeHistorique periode) {
        LocalDate today = LocalDate.now();
        LocalDate start;

        switch (periode) {
            case JOUR -> start = today;
            case SEMAINE -> start = today.minusWeeks(1);
            case MOIS -> start = today.minusMonths(1);
            case TRIMESTRE -> start = today.minusMonths(3);
            case SEMESTRE -> start = today.minusMonths(6);
            case ANNEE -> start = today.minusYears(1);
            default -> throw new IllegalArgumentException("Période inconnue : "+periode);
        }
        Date startDate = java.sql.Date.valueOf(start); //conversion de LocalDate en java.sql.Date
        Date endDate = java.sql.Date.valueOf(today);

        List<Tache> taches = tacheRepository.findTachesTermineesParPeriode(employeeId, startDate, endDate);
        List<Map<String, Object>> tachesInfo = taches.stream().map(t -> {
            Map<String, Object> info = new HashMap<>();
            info.put("titre", t.getTitre());
            info.put("dateDebut", t.getDateDebut());
            info.put("dateFin", t.getDateFin());
            return info;
        }).toList(); //crée la liste finale
        Map<String, Object> result = new HashMap<>();
        result.put("période", periode);
        result.put("total", taches.size());
        result.put("taches", tachesInfo);

        return result;
    }

    @Override
    public Map<String, Object> getHistoriqueTachesParDate(Long employeeId, String periode) {
        LocalDate today = LocalDate.now();
        LocalDate start;
        LocalDate end;
        String p = periode.toLowerCase().trim();

        try {
            // Cas : date exacte (format yyyy-MM-dd)
            start = LocalDate.parse(p);
            end = start;
        } catch (DateTimeParseException e) {
            if (p.matches("\\d{4}")) {
                // Cas : année (ex. "2023")
                int year = Integer.parseInt(p);
                start = LocalDate.of(year, 1, 1);
                end = LocalDate.of(year, 12, 31);
            } else if (p.matches("\\d{1,2}")) {
                // Cas : mois numérique (ex. "5" pour mai)
                int month = Integer.parseInt(p);
                start = LocalDate.of(today.getYear(), month, 1);
                end = start.withDayOfMonth(start.lengthOfMonth());
            } else if (p.equals("aujourdhui") || p.equals("jour")) {
                start = end = today;
            } else if (p.equals("semaine")) {
                start = today.minusWeeks(1);
                end = today;
            } else if (p.equals("mois")) {
                start = today.minusMonths(1);
                end = today;
            } else if (p.equals("trimestre")) {
                start = today.minusMonths(3);
                end = today;
            } else if (p.equals("semestre")) {
                start = today.minusMonths(6);
                end = today;
            } else if (p.equals("annee")) {
                start = today.minusYears(1);
                end = today;
            } else {
                // Cas : mois écrit (fr/en)
                Month month = mapMoisMultilingue(p);
                if (month == null) throw new IllegalArgumentException("Période inconnue : " + periode);
                start = LocalDate.of(today.getYear(), month, 1);
                end = start.withDayOfMonth(start.lengthOfMonth());
            }
        }

        Date startDate = java.sql.Date.valueOf(start);
        Date endDate = java.sql.Date.valueOf(end);

        List<Tache> taches = tacheRepository.findTachesTermineesParPeriode(employeeId, startDate, endDate);
        List<Map<String, Object>> tachesInfo = taches.stream().map(t -> {
            Map<String, Object> info = new HashMap<>();
            info.put("titre", t.getTitre());
            info.put("dateDebut", t.getDateDebut());
            info.put("dateFin", t.getDateFin());
            return info;
        }).toList();

        Map<String, Object> result = new HashMap<>();
        result.put("période", periode);
        result.put("total", taches.size());
        result.put("taches", tachesInfo);
        return result;
    }
    private Month mapMoisMultilingue(String moisTexte) {
        return switch (moisTexte.toLowerCase()) {
            case "janvier", "january" -> Month.JANUARY;
            case "février", "fevrier", "february" -> Month.FEBRUARY;
            case "mars", "march" -> Month.MARCH;
            case "avril", "april" -> Month.APRIL;
            case "mai", "may" -> Month.MAY;
            case "juin", "june" -> Month.JUNE;
            case "juillet", "july" -> Month.JULY;
            case "août", "aout", "august" -> Month.AUGUST;
            case "septembre", "september" -> Month.SEPTEMBER;
            case "octobre", "october" -> Month.OCTOBER;
            case "novembre", "november" -> Month.NOVEMBER;
            case "décembre", "decembre", "december" -> Month.DECEMBER;
            default -> null;
        };
    }


}