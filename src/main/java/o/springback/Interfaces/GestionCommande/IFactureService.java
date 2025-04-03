package o.springback.Interfaces.GestionCommande;

public interface IFactureService {
    Facture createFacture(Facture facture);
    Facture updateFacture(Long id, Facture facture);
    void deleteFacture(Long id);
    Facture getFactureById(Long id);
    List<Facture> getAllFactures();

    //BigDecimal calculeTotal(Long idFacture);

}
