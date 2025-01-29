package com.doat.ifmis_api.service;

import com.doat.ifmis_api.model.NpsContribution;
import com.doat.ifmis_api.model.NpsModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NpsServiceImpl implements NpsService {

    private EntityManager em;

    private QueryService queryService;

    private static final Logger logger = LogManager.getLogger(NpsServiceImpl.class);

    @Override
    public List<NpsModel> getEmpDetails(String ppan) {

        List<NpsModel> npsModels = new ArrayList<>();

        try {
            String queryString = queryService.getNpsEmpDetails(ppan);

            Query query = em.createNativeQuery(queryString);

            List<Object[]> ddoObjectList = query.getResultList();

            if (!ddoObjectList.isEmpty()) {

                for(int i=0; i<ddoObjectList.size(); i++) {
                    Object[] rows = ddoObjectList.get(i);

                    NpsModel npsModel = new NpsModel(String.valueOf(rows[0]),
                            String.valueOf(rows[1]),
                            String.valueOf(rows[2]),
                            String.valueOf(rows[3]),
                            String.valueOf(rows[4]),
                            String.valueOf(rows[5]),
                            String.valueOf(rows[6]),
                            String.valueOf(rows[7]),
                            String.valueOf(rows[8]),
                            String.valueOf(rows[9]),String.valueOf(rows[10]),"",
                            String.valueOf(rows[12]),
                            String.valueOf(rows[13]),
                            String.valueOf(rows[14]),
                            String.valueOf(rows[15]),
                            String.valueOf(rows[16]),
                            String.valueOf(rows[17]),
                            String.valueOf(rows[11])
                            );
                    npsModels.add(npsModel);

                    System.out.println(npsModel.toString());

                }

            }


        }
        catch (Exception e)
        {
            logger.warn("Exception Occured with {}", e.getMessage());
        }

        return npsModels;
    }

    @Override
    public List<NpsContribution> getConDetails(String ppan) {

        List<NpsContribution> npsContributions = new ArrayList<>();

        try {

            String queryString = queryService.getNpsConDetails(ppan);

            Query query = em.createNativeQuery(queryString);

            List<Object[]> ddoObjectList = query.getResultList();

            if (!ddoObjectList.isEmpty()) {

                for(int i=0; i<ddoObjectList.size(); i++) {

                    Object[] rows = ddoObjectList.get(i);

                    NpsContribution npsContribution = new NpsContribution(
                            String.valueOf(rows[0]).replace("null",""),
                            String.valueOf(rows[1]).replace("null",""),
                            String.valueOf(rows[2]).replace("null",""),
                            String.valueOf(rows[3]),
                            String.valueOf(rows[4]),
                            String.valueOf(rows[5]),
                            String.valueOf(rows[6]),
                            String.valueOf(rows[7]),
                            String.valueOf(rows[8]),
                            String.valueOf(rows[9]),
                            String.valueOf(rows[10]),
                            String.valueOf(rows[11]).replace("null","0")
                    );

                    npsContributions.add(npsContribution);

                }

            }

        }
        catch (Exception e)
        {
            logger.warn("Exception Occured with {}", e.getMessage());
        }

        return npsContributions;
    }
}
