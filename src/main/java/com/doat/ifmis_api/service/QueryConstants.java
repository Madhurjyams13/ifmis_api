package com.doat.ifmis_api.service;

public final class QueryConstants {
    public static final String DDO_DETAILS_QUERY = """
             SELECT
                 ds.district_Name,
                 t.hierarchy_Name,
                 t.hierarchy_Code,
                 hs.hierarchy_Code as ddo_code,
                 o.office_Name,
                 us.title_Name,
                 us.user_Name,
                 us.last_Name,
                 us.email,
                 us.mobile
             FROM pfmaster.hierarchy_setup AS hs
             JOIN pfmaster.hierarchy_setup o ON hs.parent_hierarchy = o.hierarchy_Id
             JOIN probityfinancials.ddo_setup AS s ON s.ddo_id = hs.hierarchy_Id
             JOIN probityfinancials.district_setup AS ds ON ds.district_Id = s.district_id
             JOIN pfmaster.hierarchy_setup AS t ON t.hierarchy_Id = s.treasury_id
             JOIN pfmaster.seat_user_alloted AS sua ON sua.seat_id = hs.hierarchy_Id
             JOIN pfmaster.user_setup AS us ON us.user_Id = sua.user_Id
             WHERE hs.category = 'S'
             AND sua.active_Status = 'Y'
             AND hs.hierarchy_Code = :ddoCode
            """;
    public static final String NPS_EMP_DETAILS_QUERY = """
                SELECT emp.*,
                       CONCAT(YEAR(con.first_capture), '/', LPAD(MONTH(con.first_capture), 2, 0)),
                       CONCAT(YEAR(con.last_capture), '/', LPAD(MONTH(con.last_capture), 2, 0)),
                       con.total_capture,
                       CONCAT(YEAR(con.first_upload), '/', LPAD(MONTH(con.first_upload), 2, 0)),
                       CONCAT(YEAR(con.last_upload), '/', LPAD(MONTH(con.last_upload), 2, 0)),
                       con.total_upload
                FROM (
                    SELECT
                        a.gpf_or_ppan_no ppan,
                        CASE
                            WHEN a.removed_by_ddo = 'Y' AND a.removal_reason = 'T' THEN 'Transfer'
                            WHEN a.removed_by_ddo = 'Y' AND a.removal_reason = 'R' THEN 'Retired'
                            WHEN a.removed_by_ddo = 'Y' AND a.removal_reason = 'E' THEN 'Expired'
                            WHEN a.removed_by_ddo = 'Y' AND a.removal_reason = 'S' THEN 'Resigned'
                            WHEN a.removed_by_ddo = 'Y' AND a.removal_reason = 'O' THEN 'Duplicate/ Invalid Details'
                            WHEN a.removed_by_ddo = 'Y' AND a.removal_reason = 'SP' THEN 'Suspended'
                            WHEN a.removed_by_ddo = 'N' THEN 'Active'
                            ELSE 'Other'
                        END AS status,
                        a.pran_no,
                        b.pran_issued_on,
                        a.dateofbirth,
                        a.emp_retirement_date,
                        CONCAT(
                            UPPER(a.emp_First_Name), ' ',
                            IFNULL(a.emp_middle_Name, ''), ' ',
                            UPPER(a.emp_Last_Name)
                        ) AS name,
                        SUBSTR(ddo.hierarchy_Code, 1, INSTR(ddo.hierarchy_Code, '/') - 1) AS try,
                        SUBSTR(ddo.hierarchy_Code, INSTR(ddo.hierarchy_Code, '/') + 1, LENGTH(ddo.hierarchy_Code)) AS ddo,
                        tr.dto_reg_no,
                        tr.ddo_reg_no,
                        a.ira_status,
                        tr.treasury_name
                    FROM probityfinancials.eis_data a
                    LEFT JOIN probityfinancials.nps_base b
                        ON a.id = b.eis_id
                    JOIN pfmaster.hierarchy_setup ddo
                        ON a.ddo_Id = ddo.hierarchy_Id
                        AND ddo.category = 'S'
                    JOIN probityfinancials.treasury_setup tr
                        ON a.treasury_Id = tr.treasury_hierarchy
                    WHERE a.gpf_or_ppan_no = :ppan
                ) emp
                LEFT JOIN (
                    SELECT cap.ppan,
                           cap.first_capture, cap.last_capture, cap.total_capture,
                           up.first_upload, up.last_upload, up.total_upload
                    FROM (
                        SELECT m.ppan,
                               COUNT(m.pmonth) AS total_capture,
                               MIN(m.pmonth) AS first_capture,
                               MAX(m.pmonth) AS last_capture
                        FROM (
                            SELECT
                                STR_TO_DATE(
                                    CONCAT(
                                        CAST(b.year AS CHAR),
                                        '-',
                                        LPAD(CAST(b.month AS CHAR), 2, '0'),
                                        '-01'
                                    ), '%Y-%m-%d'
                                ) AS pmonth, b.*
                            FROM ctmis_master.bills_nps_deduction b
                            WHERE b.ppan = :ppan
                        ) m
                        GROUP BY m.ppan
                    ) cap
                    LEFT JOIN (
                        SELECT m.ppan,
                               COUNT(m.pmonth) AS total_upload,
                               MIN(m.pmonth) AS first_upload,
                               MAX(m.pmonth) AS last_upload
                        FROM (
                            SELECT
                                STR_TO_DATE(
                                    CONCAT(
                                        CAST(b.year AS CHAR),
                                        '-',
                                        LPAD(CAST(b.month AS CHAR), 2, '0'),
                                        '-01'
                                    ), '%Y-%m-%d'
                                ) AS pmonth, b.*
                            FROM ctmis_master.bills_nps_deduction b
                            WHERE b.ppan = :ppan
                            AND b.status = 'A'
                        ) m
                        GROUP BY m.ppan
                    ) up
                    ON cap.ppan = up.ppan
                ) con
                ON emp.ppan = con.ppan
            """;
    public static final String NPS_CON_DETAILS_QUERY = """
            SELECT YEAR(c.submitted_nsdl_on),
                   LPAD(MONTH(c.submitted_nsdl_on), 2, 0),
                   DATE(c.submitted_nsdl_on),
                   a.ppan, a.pran, a.year,
                   LPAD(a.month, 2, 0),
                   a.employee_contribution,
                   CASE
                       WHEN a.type = 'R' THEN 'Regular'
                       WHEN a.type = 'A' THEN 'Arrear'
                   END AS ctype,
                   CASE
                       WHEN c.transaction_id IS NULL THEN a.TRANS_ID
                       ELSE c.transaction_id
                   END AS trans_id,
                   'Uploaded',
                   a.employer_contribution
            FROM ctmis_master.bills_nps_deduction a
            JOIN ctmis_master.bills_nps_contribution_details b
                ON a.id = b.bill_nps_deduction_id
            JOIN ctmis_master.bills_nps_contribution_base c
                ON b.bill_nps_contribution_base_id = c.id
            WHERE a.ppan = :ppan
              AND a.status = 'A'
            
            UNION ALL
            
            SELECT '', '', '', a.ppan, a.pran, a.year, LPAD(a.month, 2, 0),
                   a.employee_contribution,
                   CASE
                       WHEN a.type = 'R' THEN 'Regular'
                       WHEN a.type = 'A' THEN 'Arrear'
                   END AS ctype,
                   '',
                   CASE
                       WHEN a.status = 'P' THEN 'Pending'
                       WHEN a.status = 'N' THEN 'No PRAN'
                       ELSE 'Others'
                   END AS cstatus,
                   a.employer_contribution
            FROM ctmis_master.bills_nps_deduction a
            WHERE a.ppan = :ppan
              AND a.status <> 'A'
            ORDER BY 6, 7
            """;

    private QueryConstants() {
    }
}
