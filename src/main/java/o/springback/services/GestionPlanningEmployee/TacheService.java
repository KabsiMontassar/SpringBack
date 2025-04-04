package o.springback.services.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import o.springback.Interfaces.GestionPlanningEmployee.ITacheService;
import o.springback.entities.GestionPlanningEmployee.*;
import o.springback.repositories.GestionPlanningEmployeeRepository.EmployeeRepository;
import o.springback.repositories.GestionPlanningEmployeeRepository.TacheRepository;
import o.springback.repositories.GestionPlanningEmployeeRepository.PlanningRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.time.Month.*;

@Service
@AllArgsConstructor
public class TacheService implements ITacheService{
    private TacheRepository tacheRepository;
    private PlanningRepository planningRepository;
    private EmployeeRepository employeeRepository;
    @Override
    public Tache add(Tache tache) {
        if (tache.getSousTaches() != null){
            for (Tache sousT : tache.getSousTaches()) {
                sousT.setParent(tache);
            }
        }
        return tacheRepository.save(tache);
    }

    @Override
    public Tache ajouterSousTache(Long parentId, Tache sousTache) {
        Tache parent = tacheRepository.findById(parentId).orElse(null);
        if (parent == null) return null;
        sousTache.setParent(parent);
        return tacheRepository.save(sousTache);
    }

    @Override
    public Tache update(Long id,Tache tache) {
        Tache exTache = tacheRepository.findById(id).orElse(null);
        if ( exTache == null ) return null;
        exTache.setTitre(tache.getTitre());
        exTache.setDescription(tache.getDescription());
        exTache.setDateDebut(tache.getDateDebut());
        exTache.setDateFin(tache.getDateFin());
        exTache.setStatutTache(tache.getStatutTache());
        if (tache.getSousTaches() != null) {
            for (Tache sousT : tache.getSousTaches()) {
                sousT.setParent(exTache);
            }
            exTache.setSousTaches(tache.getSousTaches());
        }
        return tacheRepository.save(exTache);
    }

    @Override
    public Tache updateSousTache(Long id, Tache sousTache) {
        Tache exStache = tacheRepository.findById(id).orElse(null);
        if ( exStache == null ) return null;
        exStache.setTitre(sousTache.getTitre());
        exStache.setDescription(sousTache.getDescription());
        exStache.setDateDebut(sousTache.getDateDebut());
        exStache.setDateFin(sousTache.getDateFin());
        exStache.setStatutTache(sousTache.getStatutTache());
        return tacheRepository.save(exStache);
    }

    @Override
    public void delete(Long id) {
        Tache tache = tacheRepository.findById(id).orElse(null);
        if (tache != null) {
            tacheRepository.delete(tache);
        }
    }

    @Override
    public void deletesoustache(Long id) {
        Tache sousTache = tacheRepository.findById(id).orElse(null);
        if (sousTache != null && sousTache.getParent() != null){
            Tache parent = sousTache.getParent();
            parent.getSousTaches().remove(sousTache);
            tacheRepository.deleteById(id);
        }
    }

    @Override
    public Boolean hasSousTaches(Long idTache) {
        return tacheRepository.hasChildTaches(idTache);
    }

    @Override
    public List<Tache> getAllDescendants(Tache tache) {
        List<Tache> all = new ArrayList<>();
        if (tache.getSousTaches() != null){
            for (Tache child : tache.getSousTaches()) {
                all.add(child);
                all.addAll(getAllDescendants(child));
            }
        }
        return all;
    }

    @Override
    public int countAllDescendants(Tache tache) {
        return getAllDescendants(tache).size();
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
            //yfalter bel date exacte
            start = LocalDate.parse(p);
            end = start;
        } catch (DateTimeParseException e) {
            if (p.matches("\\d{4}")) {
                // année (ex. "2025")
                int year = Integer.parseInt(p);
                start = LocalDate.of(year, 1, 1);
                end = LocalDate.of(year, 12, 31);
            } else if (p.matches("\\d{1,2}")) { // tekteb 4 wala 04 ykharrej avril
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
                //mois en lettres (français wala anglais)
                Month month = mapMoisMultilingue(p);
                if (month == null) throw new IllegalArgumentException("Période inconnue : " + periode);
                start = LocalDate.of(today.getYear(), month, 1);
                end = start.withDayOfMonth(start.lengthOfMonth());
            }
        }

        Date startDate = java.sql.Date.valueOf(start);
        Date endDate = java.sql.Date.valueOf(end);

        List<Tache> taches = tacheRepository.findTachesParDate(employeeId, startDate, endDate);
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

    @Override
    public Map<String, Object> replanifierTache(Long employeeId, String strategie) {
        List<TypePlanning> bloque = List.of(
                TypePlanning.CONGES,
                TypePlanning.MALADIE,
                TypePlanning.DEPLACEMENT,
                TypePlanning.VACANCES);
        List<Tache> taches = tacheRepository.findAll().stream()
                .filter(t -> t.getEmployee().getIdEmployee().equals(employeeId))
                .toList(); //tâches de l'employe durant la période bloquée
        int replanifiees = 0, supprimees = 0, ignorees = 0;

        for (Tache tache : taches) {
            List<Planning> conflits = planningRepository.findPlanningByTypePlanning(
                    employeeId, bloque, tache.getDateDebut(), tache.getDateFin());
            if (!conflits.isEmpty()) {
                switch (strategie.toUpperCase()) {
                    case "REPLANIFIER" -> {
                        Optional<Planning> conflitLePlusLong = conflits.stream() //optional maaneha l fonction tnajem ma traja3 chay blech exception
                                .max(Comparator.comparing(p -> p.getDateFin().getTime() - p.getDateDebut().getTime()));
                        if (conflitLePlusLong.isPresent()) {
                            Planning conflit = conflitLePlusLong.get();
                            long joursDeBlocage = ChronoUnit.DAYS.between(
                                    conflit.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                    conflit.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                            ) +1;
                            LocalDate newDebut = tache.getDateFin().toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                    .plusDays(joursDeBlocage);
                            LocalDate newFin = tache.getDateFin().toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                    .plusDays(joursDeBlocage);
                            tache.setDateDebut(Date.from(newDebut.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                            tache.setDateFin(Date.from(newFin.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                            tacheRepository.save(tache);
                            replanifiees++;
                        }

                    } case "SUPPRIMER" -> {
                        tacheRepository.delete(tache);
                        supprimees++;
                    }
                    default -> ignorees++;
                }
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("replanifiees", replanifiees);
        result.put("supprimees", supprimees);
        result.put("ignorees", ignorees);
        return result;
    }



}