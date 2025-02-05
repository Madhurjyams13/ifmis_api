package com.doat.ifmis_api.repository;

import com.doat.ifmis_api.model.NpsContribution;
import com.doat.ifmis_api.model.NpsModel;
import com.doat.ifmis_api.security.DatabaseException;
import com.doat.ifmis_api.service.QueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class NpsRepository {

    private static final Logger logger = LogManager.getLogger(NpsRepository.class);
    private final EntityManager em;
    private final QueryService queryService;

    public Optional<NpsModel> getEmpDetails(String ppan) {

        try {
            String queryString = queryService.getNpsEmpDetails(ppan);
            Query query = em.createNativeQuery(queryString)
                    .setParameter("ppan", ppan);

            @SuppressWarnings("unchecked")
            List<Object[]> npsObjectList = query.getResultList();

            return npsObjectList.stream()
                    .findFirst()
                    .map(this::mapToNpsModel);

        } catch (PersistenceException e) {
            logger.error("Database error while fetching NPS details for code: {}", ppan, e);
            throw new DatabaseException("Error fetching NPS details", e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching NPS details for code: {}", ppan, e);
            throw new DatabaseException("Unexpected error in NPS details retrieval", e);
        }
    }

    public List<NpsContribution> getConDetails(String ppan) {

        try {
            String queryString = queryService.getNpsConDetails(ppan);
            Query query = em.createNativeQuery(queryString)
                    .setParameter("ppan", ppan);

            @SuppressWarnings("unchecked")
            List<Object[]> npsObjectList = query.getResultList();

            return npsObjectList.stream()
                    .map(this::mapToNpsConModel).toList();

        } catch (PersistenceException e) {
            logger.error("Database error while fetching NPS Contribution details for code: {}", ppan, e);
            throw new DatabaseException("Error fetching NPS Contribution details", e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching NPS Contribution details for code: {}", ppan, e);
            throw new DatabaseException("Unexpected error in NPS Contribution details retrieval", e);
        }
    }

    private NpsModel mapToNpsModel(Object[] data) {
        return NpsModel.builder()
                .ppan(data[0].toString())
                .status(data[1].toString())
                .pran(data[2] == null ? "" : data[2].toString())
                .pran_gen_date(data[3] == null ? "" : data[3].toString())
                .dob(data[4] == null ? "" : data[4].toString())
                .ret_date(data[5] == null ? "" : data[5].toString())
                .name(data[6].toString())
                .treasury_code(data[7].toString())
                .office(data[8].toString())
                .dto(data[9].toString())
                .ddo(data[10].toString())
                .ira_status(data[11] == null ? "" : data[11].toString())
                .first_capture(data[12] == null ? "" : data[12].toString())
                .last_capture(data[13] == null ? "" : data[13].toString())
                .total_capture(data[14] == null ? "" : data[14].toString())
                .first_upload(data[15] == null ? "" : data[15].toString())
                .last_upload(data[16] == null ? "" : data[16].toString())
                .total_upload(data[17] == null ? "" : data[17].toString())
                .treasuryName(data[18].toString())
                .build();
    }

    private NpsContribution mapToNpsConModel(Object[] data) {
        return NpsContribution.builder()
                .accMonth(data[0] == null ? "" : data[0].toString())
                .accYear(data[1] == null ? "" : data[1].toString())
                .uploadDate(data[2] == null ? "" : data[2].toString())
                .empID(data[3] == null ? "" : data[3].toString())
                .PRAN(data[4] == null ? "" : data[4].toString())
                .payYear(data[5] == null ? "" : data[5].toString())
                .payMonth(data[6] == null ? "" : data[6].toString())
                .amount(data[7] == null ? "" : data[7].toString())
                .type(data[8] == null ? "" : data[8].toString())
                .transactionID(data[9] == null ? "" : data[9].toString())
                .uploadStatus(data[10] == null ? "" : data[10].toString())
                .govtContri(data[11] == null ? "" : data[11].toString())
                .build();
    }

}
