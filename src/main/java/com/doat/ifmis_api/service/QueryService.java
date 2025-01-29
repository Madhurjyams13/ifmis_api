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

    public String getNpsEmpDetails(String ppan) {

        return "SELECT emp.*, \n" +
                "concat(YEAR(con.first_capture),'/',lpad(MONTH(con.first_capture),2,0)), \n" +
                "concat(YEAR(con.last_capture),'/',lpad(MONTH(con.last_capture),2,0)),\n" +
                "con.total_capture,\n" +
                "concat(YEAR(con.first_upload),'/',lpad(MONTH(con.first_upload),2,0)),\n" +
                "concat(YEAR(con.last_upload),'/',lpad(MONTH(con.last_upload),2,0)),\n" +
                "con.total_upload from \n" +
                "(\n" +
                "\tSELECT \n" +
                "\ta.gpf_or_ppan_no ppan,\n" +
                "\tcase \n" +
                "\t\twhen a.removed_by_ddo = 'Y' AND a.removal_reason = 'T' then 'Transfer'\n" +
                "\t\twhen a.removed_by_ddo = 'Y' AND a.removal_reason = 'R' then 'Retired'\n" +
                "\t\twhen a.removed_by_ddo = 'Y' AND a.removal_reason = 'E' then 'Expired'\n" +
                "\t\twhen a.removed_by_ddo = 'Y' AND a.removal_reason = 'S' then 'Resigned'\n" +
                "\t\twhen a.removed_by_ddo = 'Y' AND a.removal_reason = 'O' then 'Duplicate/ Invalid Details'\n" +
                "\t\twhen a.removed_by_ddo = 'Y' AND a.removal_reason = 'SP' then 'Suspended'\n" +
                "\t\twhen a.removed_by_ddo = 'N' then 'Active'\n" +
                "\t\tELSE 'Other'\n" +
                "\tEND AS status, a.pran_no, b.pran_issued_on,\n" +
                "\ta.dateofbirth, a.emp_retirement_date,\n" +
                "\tCONCAT(\n" +
                "\t\tUPPER(a.emp_First_Name),' ', \n" +
                "\t\tIFNULL(a.emp_middle_Name,''), ' ', \n" +
                "\t\tupper(a.emp_Last_Name)\n" +
                "\t\t) name,\n" +
                "\t\tsubstr(ddo.hierarchy_Code,1,INSTR(ddo.hierarchy_Code,'/')-1) try,\n" +
                "\t\tSUBSTR(ddo.hierarchy_Code,INSTR(ddo.hierarchy_Code,'/')+1, LENGTH(ddo.hierarchy_Code)) ddo,\n" +
                "\t\ttr.dto_reg_no, tr.ddo_reg_no,\n" +
                "\ttr.treasury_name\n" +
                "\t#,a.*\t \n" +
                "\tFROM probityfinancials.eis_data a\n" +
                "\tLEFT JOIN probityfinancials.nps_base b\n" +
                "\t\tON a.id = b.eis_id\n" +
                "\tJOIN pfmaster.hierarchy_setup ddo\n" +
                "\t\tON a.ddo_Id = ddo.hierarchy_Id\n" +
                "\t\tAND ddo.category = 'S'\n" +
                "\tJOIN probityfinancials.treasury_setup tr\n" +
                "\t\tON a.treasury_Id = tr.treasury_hierarchy\n" +
                "\tWHERE\n" +
                "\ta.gpf_or_ppan_no = '"+ppan +"'\n" +
                ") emp\n" +
                "\n" +
                "LEFT JOIN \n" +
                "(\n" +
                "\tSELECT cap.ppan, \n" +
                "\tcap.first_capture, cap.last_capture, cap.total_capture,\n" +
                "\tup.first_upload, up.last_upload, up.total_upload\n" +
                "\tFROM \n" +
                "\t(\n" +
                "\tSELECT m.ppan, \n" +
                "\tCOUNT(m.pmonth) total_capture, MIN(m.pmonth) first_capture,  \n" +
                "\tMAX(m.pmonth) last_capture\n" +
                "\tFROM \n" +
                "\t(\n" +
                "\t\tSELECT \n" +
                "\t\tSTR_TO_DATE(\n" +
                "\t\t\tCONCAT(\n" +
                "\t\t\t\tCAST(b.year AS CHAR), \n" +
                "\t\t\t\t'-', \n" +
                "\t\t\t\tLPAD(CAST(b.month AS CHAR), 2, '0'), \n" +
                "\t\t\t\t'-01'\n" +
                "\t\t\t\t)\n" +
                "\t\t, '%Y-%m-%d') pmonth, b.*\n" +
                "\t\tFROM ctmis_master.bills_nps_deduction b\n" +
                "\t\tWHERE\n" +
                "\t\tb.ppan = '"+ppan +"' \n" +
                "\t) m\n" +
                "\tGROUP BY m.ppan\n" +
                "\t) cap \n" +
                "\t\n" +
                "\tLEFT JOIN \n" +
                "\t(\n" +
                "\tSELECT m.ppan, \n" +
                "\tCOUNT(m.pmonth) total_upload, MIN(m.pmonth) first_upload,  \n" +
                "\tMAX(m.pmonth) last_upload\n" +
                "\tFROM \n" +
                "\t(\n" +
                "\t\tSELECT \n" +
                "\t\tSTR_TO_DATE(\n" +
                "\t\t\tCONCAT(\n" +
                "\t\t\t\tCAST(b.year AS CHAR), \n" +
                "\t\t\t\t'-', \n" +
                "\t\t\t\tLPAD(CAST(b.month AS CHAR), 2, '0'), \n" +
                "\t\t\t\t'-01'\n" +
                "\t\t\t\t)\n" +
                "\t\t, '%Y-%m-%d') pmonth, b.*\n" +
                "\t\tFROM ctmis_master.bills_nps_deduction b\n" +
                "\t\tWHERE\n" +
                "\t\tb.ppan = '"+ppan +"'\n" +
                "\t\tAND b.status = 'A'\n" +
                "\t) m\n" +
                "\tGROUP BY m.ppan\n" +
                "\t) up \n" +
                "\tON cap.ppan = up.ppan\n" +
                ") con\n" +
                "ON emp.ppan = con.ppan";

    }

    public String getNpsConDetails(String ppan) {

        return  "SELECT YEAR(c.submitted_nsdl_on), \n" +
                "lpad(MONTH(c.submitted_nsdl_on),2,0), \n" +
                "date(c.submitted_nsdl_on),\n" +
                "a.ppan, a.pran, a.year,\n" +
                "LPAD(a.month,2,0),\n" +
                "a.employee_contribution,\n" +
                "case\n" +
                "\twhen a.type = 'R'\n" +
                "\t\tthen 'Regular'\n" +
                "\twhen a.type = 'A'\n" +
                "\t\tthen 'Arrear'\n" +
                "END AS ctype, \n" +
                "case \n" +
                "\twhen c.transaction_id IS NULL \n" +
                "\t\tthen a.TRANS_ID\n" +
                "\tELSE c.transaction_id\n" +
                "END AS trans_id,\n" +
                "'Uploaded',\n" +
                "a.employer_contribution\n" +
                "FROM ctmis_master.bills_nps_deduction a\n" +
                "JOIN ctmis_master.bills_nps_contribution_details b\n" +
                "\tON a.id = b.bill_nps_deduction_id\n" +
                "JOIN ctmis_master.bills_nps_contribution_base c\n" +
                "\tON b.bill_nps_contribution_base_id = c.id\n" +
                "WHERE\n" +
                "a.ppan = '"+ppan +"'\n" +
                "AND a.status = 'A'\n" +
                "\n" +
                "UNION ALL\n" +
                "\n" +
                "SELECT \n" +
                "'','', '', a.ppan, a.pran, a.year, LPAD(a.month,2,0),\n" +
                "a.employee_contribution,\n" +
                "case\n" +
                "\twhen a.type = 'R'\n" +
                "\t\tthen 'Regular'\n" +
                "\twhen a.type = 'A'\n" +
                "\t\tthen 'Arrear'\n" +
                "END AS ctype, \n" +
                "'', \n" +
                "case \n" +
                "\twhen a.status = 'P'\n" +
                "\t\tthen 'Pending'\n" +
                "\twhen a.status = 'N'\n" +
                "\t\tthen 'No PRAN'\n" +
                "\tELSE \n" +
                "\t\t'Others'\n" +
                "END AS cstatus,\n" +
                "a.employer_contribution\t \n" +
                "FROM ctmis_master.bills_nps_deduction a\n" +
                "WHERE\n" +
                "a.ppan = '"+ppan +"'\n" +
                "AND a.status <> 'A'  order by 6,7";

    }
}

