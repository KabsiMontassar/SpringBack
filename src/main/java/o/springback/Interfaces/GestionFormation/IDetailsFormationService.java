package o.springback.Interfaces.GestionFormation;


import o.springback.entities.GestionFormation.DetailsFormation;

public interface IDetailsFormationService {
    DetailsFormation addDetailFormation(DetailsFormation detailFormation);
    DetailsFormation updateDetailFormation(int id, DetailsFormation detailFormation);
    void deleteDetailFormation(int id);
    DetailsFormation getDetailFormationById(int id);

    DetailsFormation getByFormationId(int idFormation);
}
