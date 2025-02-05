package com.doat.ifmis_api.repository;

import com.doat.ifmis_api.model.DdoModel;
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
public class DdoRepository {

    private static final Logger logger = LogManager.getLogger(DdoRepository.class);
    private final EntityManager em;
    private final QueryService queryService;

    public Optional<DdoModel> getDdoDetails(String ddoCode) {
        try {
            String queryString = queryService.getDdoDetails(ddoCode);
            Query query = em.createNativeQuery(queryString)
                    .setParameter("ddoCode", ddoCode);

            @SuppressWarnings("unchecked")
            List<Object[]> ddoObjectList = query.getResultList();

            return ddoObjectList.stream()
                    .findFirst()
                    .map(this::mapToDdoModel);

        } catch (PersistenceException e) {
            logger.error("Database error while fetching DDO details for code: {}", ddoCode, e);
            throw new DatabaseException("Error fetching DDO details", e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching DDO details for code: {}", ddoCode, e);
            throw new DatabaseException("Unexpected error in DDO details retrieval", e);
        }
    }

    private DdoModel mapToDdoModel(Object[] data) {
        return DdoModel.builder()
                .district((String) data[0])
                .treasuryName((String) data[1])
                .treasuryCode((String) data[2])
                .ddoCode((String) data[3])
                .officeName((String) data[4])
                .title((String) data[5])
                .ddoFirstName((String) data[6])
                .ddoLastName((String) data[7])
                .email((String) data[8])
                .mobile((String) data[9])
                .build();
    }
}
