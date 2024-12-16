package com.doat.ifmis_api.service;

import com.doat.ifmis_api.model.DdoModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DdoServiceImpl implements DdoService {

    private EntityManager em;

    private QueryService queryService;

    private static final Logger logger = LogManager.getLogger(DdoServiceImpl.class);

    @Override
    public DdoModel getDdoDetails(String ddoCode) {

        try {
            String queryString = queryService.getDdoDetails(ddoCode);
            Query query = em.createNativeQuery(queryString);

            List<Object[]> ddoObjectList = query.getResultList();

            if (!ddoObjectList.isEmpty()) {
                DdoModel ddoModel = getDdoModel(ddoObjectList);
                logger.debug("DB Response == {} ", ddoModel);
                return ddoModel;
            }
        }
        catch (Exception e)
        {
            logger.warn("Exception Occured with {}", e.getMessage());
        }


        return null;
    }

    private static DdoModel getDdoModel(List<Object[]> ddoObjectList) {
        Object[] rows = ddoObjectList.get(0);

        return new DdoModel(String.valueOf(rows[0]),
                String.valueOf(rows[1]),
                String.valueOf(rows[2]),
                String.valueOf(rows[3]),
                String.valueOf(rows[4]),
                String.valueOf(rows[5]),
                String.valueOf(rows[6]),
                String.valueOf(rows[7]),
                String.valueOf(rows[8]),
                String.valueOf(rows[9]));
    }
}
