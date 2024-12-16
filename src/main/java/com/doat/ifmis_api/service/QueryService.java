package com.doat.ifmis_api.service;

import org.springframework.stereotype.Service;

@Service
public class QueryService {

    public String getDdoDetails(String ddoCode) {

        return "SELECT \n" +
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
                "    AND hs.hierarchy_Code = '" + ddoCode + "'";

    }
}

