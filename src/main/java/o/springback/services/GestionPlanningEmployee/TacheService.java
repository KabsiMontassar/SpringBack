package o.springback.services.GestionPlanningEmployee;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.springback.Interfaces.GestionPlanningEmployee.ITacheService;
import o.springback.entities.GestionPlanningEmployee.*;
import o.springback.repositories.GestionPlanningEmployeeRepository.EmployeeRepository;
import o.springback.repositories.GestionPlanningEmployeeRepository.TacheRepository;
import o.springback.repositories.GestionPlanningEmployeeRepository.PlanningRepository;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.JobKOctets;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.time.Month.*;

@Service
@AllArgsConstructor
@Slf4j
public class TacheService implements ITacheService{
    private TacheRepository tacheRepository;
    private PlanningRepository planningRepository;
    private EmployeeRepository employeeRepository;
    @Override
    public Tache add(Tache tache) {
        if (tache.getPosition() == null){
            tache.setPosition(getNextPositionForStatut(tache.getStatutTache()));
        }
        //if (tache.getSousTaches() != null){
        //    for (Tache sousT : tache.getSousTaches()) {
        //        sousT.setParent(tache);
        //        if (sousT.getPosition() == null){
        //            sousT.setPosition(getNextPositionForParent(tache));
        //        }
        //    }

        //}
        return tacheRepository.save(tache);
    }
    private int getNextPositionForParent(Tache parent){
        Integer maxPos = tacheRepository.findMaxPositionUnderParent(parent.getIdTache());
        return maxPos != null ? maxPos + 1 : 1;
    }

    @Override
    public Tache ajouterSousTache(Long parentId, Tache sousTache) {
        Tache parent = tacheRepository.findById(parentId).orElse(null);
        if (parent == null) return null;
        sousTache.setParent(parent);
        if(sousTache.getPosition() == null){
            sousTache.setPosition(getNextPositionForParent(parent));
        }
        if (sousTache.getStatutTache() == null){
            sousTache.setStatutTache(parent.getStatutTache());
        }
        return tacheRepository.save(sousTache);
    }
    private int getNextPositionForStatut(StatutTache statut){
        Integer maxPos = tacheRepository.findMaxPositionByStatutTache(statut);
        return maxPos != null ? maxPos + 1 : 1;
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
        exTache.setPosition(tache.getPosition());
        //if (tache.getSousTaches() != null) {
        //    for (Tache sousT : tache.getSousTaches()) {
        //        sousT.setParent(exTache);
        //    }
        //    exTache.setSousTaches(tache.getSousTaches());
        //}
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

    //@Override
    //public void deletesoustache(Long id) {
    //    Tache sousTache = tacheRepository.findById(id).orElse(null);
    //    if (sousTache != null && sousTache.getParent() != null){
    //        Tache parent = sousTache.getParent();
    //        parent.getSousTaches().remove(sousTache);
    //        tacheRepository.deleteById(id);
    //    }
    //}

    //@Override
    //public Boolean hasSousTaches(Long idTache) {
    //    return tacheRepository.hasChildTaches(idTache);
    //}

    //@Override
    //public List<Tache> getAllDescendants(Tache tache) {
    //    List<Tache> all = new ArrayList<>();
    //    if (tache.getSousTaches() != null){
    //        for (Tache child : tache.getSousTaches()) {
    //            all.add(child);
    //            all.addAll(getAllDescendants(child));
    //        }
    //    }
    //    return all;
    //}

    //@Override
    //public int countAllDescendants(Tache tache) {
    //    return getAllDescendants(tache).size();
    //}

    //@Override
    //public Map<String, Object> getProgressionTache(Long tacheId) {
    //    Tache tache = tacheRepository.findById(tacheId).orElse(null);
    //    if (tache == null) return null;
    //    List<Tache> sousTaches = tache.getSousTaches();
    //    long total = sousTaches.size();
    //    long terminees = sousTaches.stream()
    //            .filter(st -> st.getStatutTache() == StatutTache.TERMINEE)
    //            .count();
    //    double progression = total > 0 ? (double) terminees /total * 100:0 ;

    //    Map<String, Object> result = new HashMap<>();
    //    result.put("tache", tache.getIdTache());
    //    result.put("totalSousTaches", total);
    //    result.put("totalSousTachesTerminee", terminees);
    //    result.put("progression", progression);

    //    return result;
    //}

    //@Override
    //public Map<String, Object> getProgressionParEmploye(Long employeeId) {
    //    List<Tache> taches = tacheRepository.findAll();
    //    int total = 0;
    //    int terminees = 0;
    //    String nom = "";
    //    String prenom = "";

    //    for (Tache tache : taches) {
    //        if (tache.getEmployee() != null && tache.getEmployee().getIdEmployee().equals(employeeId)) {
    //            if (nom.isEmpty()){
    //                nom = tache.getEmployee().getNom();
    //                prenom = tache.getEmployee().getPrenom();
    //            }
    //            //List<Tache> descendants = getAllDescendants(tache);
    //            if (descendants.isEmpty()) {
    //                total++;
    //                if (tache.getStatutTache() == StatutTache.TERMINEE) terminees++;
    //            } else {
    //                for (Tache sousT : descendants) {
    //                    total++;
    //                    if (sousT.getStatutTache() == StatutTache.TERMINEE) terminees++;
    //                }
    //            }
    //        }
    //    }
    //    double progression = (total > 0) ? (terminees * 100.0 / total) : 0.0;
    //    Map<String, Object> result = new HashMap<>();
    //    result.put("nom", nom);
    //    result.put("prénom", prenom);
    //        result.put("Total", total);
    //        result.put("terminées", terminees);
    //        result.put("progression", progression);
    //        result.put("plannings", planningRepository.findByEmployeeId(employeeId));
    //        return result;
    //    }



    @Override
    public Tache findById(Long id) {
        return tacheRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tache> findAll() {
        return tacheRepository.findAll();
    }

    @Override
    public List<Tache> getTachesByEmployeeId(Long idEmployee) {
        return tacheRepository.findByEmployee_IdEmployee(idEmployee);
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

    @Override
    public int findMaxPositionByStatut(StatutTache statut) {
        Integer maxPosition = tacheRepository.findMaxPositionByStatutTache(statut);
        return maxPosition != null ? maxPosition : 0;
    }
//by status
    @Override
    public Map<String, Object> getStatsGlobales() {
        List<Tache> allTaches = tacheRepository.findAll();

        long total = allTaches.size();
        long terminees = allTaches.stream().filter(t -> t.getStatutTache() == StatutTache.TERMINEE).count();

        long enencours = allTaches.stream().filter(t -> t.getStatutTache() == StatutTache.EN_COURS ).count();

        long afaire = allTaches.stream().filter(t -> t.getStatutTache() == StatutTache.A_FAIRE).count();


        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTaches", total);
        stats.put("tachesTerminees", terminees);
        stats.put("tachesEnCours", enencours);
        stats.put("tachesAFaire", afaire);

        return stats;
    }

    @Override
    public Map<String, Object> getStatsGlobalesParEmploye(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null){
            throw new IllegalArgumentException("L'employé avec l'ID " + employeeId + " n'existe pas.");
        }
        List<Tache> taches = employee.getTaches();
        long total = taches.size();
        long terminees = taches.stream().filter(t -> t.getStatutTache() == StatutTache.TERMINEE).count();
        long enCours = taches.stream().filter(t-> t.getStatutTache() == StatutTache.EN_COURS).count();
        long aFaire = taches.stream().filter(t -> t.getStatutTache() == StatutTache.A_FAIRE).count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTaches", total);
        stats.put("tachesTerminees", terminees);
        stats.put("tachesEnCours", enCours);
        stats.put("tachesAFaire", aFaire);
        return stats;
    }


    //@Scheduled(cron = "0 0 7 * * MON")
    @Scheduled(cron = "*/15 * * * * *")
    public void notifierTacheDDL() {
        LocalDate today = LocalDate.now();
        List<Tache> taches = tacheRepository.findByDateFinBetween(
                Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())
        );
        for (Tache tache : taches) {
            log.info("Notification pour la tâche: " + tache.getTitre());
        }
    }



    // @Scheduled(cron = "0 0 8 * * MON") // Chaque lundi à 8h du matin
    @Scheduled(cron = "*/20 * * * * *")
    public void notifierTachesEnRetard() {
        log.info("Recherche des tâches en retard");
        LocalDate aujourdhui = LocalDate.now();

        List<Tache> tachesEnRetard = tacheRepository.findAll().stream()
                .filter(t -> t.getDateFin() != null && t.getDateFin().before(Date.from(aujourdhui.atStartOfDay(ZoneId.systemDefault()).toInstant())))
                .toList();
        Map<Long, List<Tache>> tachesParEmploye = new HashMap<>();

        for (Tache t : tachesEnRetard) {
            if (t.getEmployee() != null) {
                Long idEmployee = t.getEmployee().getIdEmployee();
                tachesParEmploye.computeIfAbsent(idEmployee, k -> new ArrayList<>()).add(t);
            }
        }

        for (Map.Entry<Long, List<Tache>> entry : tachesParEmploye.entrySet()) {
            Long idEmp = entry.getKey();
            Employee emp = employeeRepository.findById(idEmp).orElse(null);

            if (emp != null) {
                log.info(" Employé: {} {}, vous avez {} tâche(s) en retard.",
                        emp.getPrenom(), emp.getNom(), entry.getValue().size());

                for (Tache tache : entry.getValue()) {
                    log.info(" Tâche: {} (deadline: {})", tache.getTitre(), tache.getDateFin());
                }
            }
        }
    }


}
    // proposer une autre schedule method other then notifierTacheDDL
    // @Scheduled(cron = "0 0 12 * * ?")
    // p

