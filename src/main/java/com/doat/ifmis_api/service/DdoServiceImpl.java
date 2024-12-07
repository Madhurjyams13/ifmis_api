package com.doat.ifmis_api.service;

import com.doat.ifmis_api.model.DdoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class DdoServiceImpl implements DdoService {

    @Autowired
    private EntityManager em;

    @Override
    public DdoModel getDdoDetails(String ddoCode) {

        String queryString = "SELECT \n" +
                "    ds.district_Name,\n" +
                "    t.hierarchy_Name ,\n" +
                "    t.hierarchy_Code ,\n" +
                "    hs.hierarchy_Code ddo_code,\n" +
                "    o.office_Name,\n" +
                "    us.title_Name,\n" +
                "    us.user_Name,\n" +
                "    us.last_Name,\n" +
                "    us.email,\n" +
                "    us.mobile\n" +
                "FROM\n" +
                "    pfmaster.hierarchy_setup AS hs\n" +
                "    \t  JOIN pfmaster.hierarchy_setup o\n" +
                "    ON hs.parent_hierarchy = o.hierarchy_Id\n" +
                "        JOIN\n" +
                "    probityfinancials.ddo_setup AS s ON s.ddo_id = hs.hierarchy_Id\n" +
                "        JOIN\n" +
                "    probityfinancials.district_setup AS ds ON ds.district_Id = s.district_id\n" +
                "        JOIN\n" +
                "    pfmaster.hierarchy_setup AS t ON t.hierarchy_Id = s.treasury_id\n" +
                "        JOIN\n" +
                "    pfmaster.seat_user_alloted AS sua ON sua.seat_id = hs.hierarchy_Id\n" +
                "        JOIN\n" +
                "    pfmaster.user_setup AS us ON us.user_Id = sua.user_Id\n" +
                "WHERE\n" +
                "    hs.category = 'S'\n" +
                "    AND sua.active_Status = 'Y'\n" +
                "    AND hs.hierarchy_Code = '"+ddoCode+"'";

        Query query = em.createNativeQuery(queryString);

        List<Object[]> ddoObjectList= query.getResultList();

        if (ddoObjectList.size()>=1) {

            Object rows[] = ddoObjectList.get(0);
            //System.out.println(String.valueOf(rows[0]));

            DdoModel ddoModel =new  DdoModel(String.valueOf(rows[0]),
                    String.valueOf(rows[1]),
                    String.valueOf(rows[2]),
                    String.valueOf(rows[3]),
                    String.valueOf(rows[4]),
                    String.valueOf(rows[5]),
                    String.valueOf(rows[6]),
                    String.valueOf(rows[7]),
                    String.valueOf(rows[8]),
                    String.valueOf(rows[9]));

            System.out.println(ddoModel.toString());

            return ddoModel;
        }

        return null;
    }
}
