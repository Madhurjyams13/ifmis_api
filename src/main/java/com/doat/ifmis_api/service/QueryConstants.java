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

    public static final String AA_DETAILS_QUERY = """
                SELECT CONCAT(aa.approval_id,'H',ah.id),
                dep.hierarchy_Code,
                case\s
                	when aa.parent_id IS NULL\s
                		then 'Original'
                	when aa.aa_type IS NOT NULL\s
                		then 'Revision'
                	when aa.aa_type IS NULL\s
                		then 'Revalidation'\t
                	ELSE 'Other'
                END AS aa_type ,\s
                case\s
                	when h.plan_status_migration IS NOT NULL \s
                		then h.plan_status_migration
                	when pc.abbreviation IS NOT NULL\s
                		then pc.abbreviation
                	ELSE 'EE'
                END AS scheme,
                aa.aa_issue_no,\s
                date(aa.approved_on),
                CONCAT(
                	h.head,'-',
                	case\s
                	when h.plan_status_migration IS NOT NULL \s
                		then h.plan_status_migration
                	when pc.abbreviation IS NOT NULL\s
                		then pc.abbreviation
                	ELSE 'EE'
                	END, '-',
                	h.ga_ssa_status,'-', h.voted_charged_status) head,
                CONCAT(SUBSTR(aa.fin_year,1,5),'20',SUBSTR(aa.fin_year,6,8)) finYear,
                aa.project_name,
                aa.amount,
                ah.amount head_amount,
                aa.project_aim,\s
                'filePath',
                ddo.hierarchy_Code,
                ofc.office_Name
                #,h.*
                #aa.*\s
                FROM probityfinancials.administrative_approval aa
                LEFT JOIN probityfinancials.administrative_approval_heads ah
                	ON aa.approval_id = ah.approval_id
                LEFT JOIN probityfinancials.heads h
                	ON ah.head_id = h.head_id \s
                JOIN pfmaster.hierarchy_setup dep
                	ON aa.dept_id = dep.hierarchy_Id
                	AND dep.category = 'D'
                LEFT JOIN probityfinancials.plan_category_head_mapping pchm
                	ON h.head_id = pchm.head_id
                LEFT JOIN probityfinancials.plan_category pc\s
                	ON pchm.pc_id = pc.pc_id
                LEFT JOIN probityfinancials.administrative_approval_ddo_mapping map
                	ON aa.approval_id = map.approval_id
                JOIN pfmaster.hierarchy_setup ddo
                	ON map.mapped_ddo_id = ddo.hierarchy_Id
                	AND ddo.category = 'S'
                JOIN pfmaster.hierarchy_setup ofc
                	ON ddo.parent_hierarchy = ofc.hierarchy_Id
                	AND ofc.category = 'O'
                WHERE
                dep.hierarchy_Code = :deptCode
                AND DATE(aa.issued_on) BETWEEN :fromDate AND :toDate
                AND CONCAT(SUBSTR(aa.fin_year,1,5),'20',SUBSTR(aa.fin_year,6,8)) = :fy
           \s""";

    public static final String BA_DETAILS_QUERY = """
                SELECT pc.full_meaning scheme_desc , m.* FROM\s
                (
                SELECT\s
                CONCAT(SUBSTR(a.year,1,5),'20',SUBSTR(a.year,6,8)) year,
                gr.grant_no,\s
                gr.grant_desc,
                gr.grant_assamese,
                dep.hierarchy_Code dept_code,
                dep.hierarchy_Name department,
                dir.hierarchy_Code dir_code,\s
                dir.hierarchy_Name directorate,
                CONCAT(
                	h.head, '-',
                	case\s
                	when h.plan_status_migration IS NOT NULL \s
                		then h.plan_status_migration
                	when pc.abbreviation IS NOT NULL\s
                		then pc.abbreviation
                	ELSE 'EE'
                	END, '-',
                	h.ga_ssa_status, '-',
                	h.voted_charged_status) head ,\s
                concat(mjh.head_code,'-',mjh.head_name) major_desc,
                mjh.head_name_unicode major_unicode,
                concat(smh.head_code,'-',smh.head_name) sub_major_desc,
                smh.head_name_unicode sub_major_unicode,
                concat(mnh.head_code,'-',mnh.head_name) minor_desc,
                mnh.head_name_unicode minor_unicode,
                concat(sh.head_code,'-',sh.head_name) sub_desc,
                sh.head_name_unicode sub_unicode,
                concat(ssh.head_code,'-',ssh.head_name) sub_sub_desc,
                ssh.head_name_unicode sub_sub_unicode,
                concat(dh.head_code,'-',dh.head_name) detail_desc,
                dh.head_name_unicode detail_unicode,
                concat(sdh.head_code,'-',sdh.head_name) sub_detail_desc,
                sdh.head_name_unicode sub_detail_unicode,
                case\s
                	when h.plan_status_migration IS NOT NULL \s
                		then h.plan_status_migration
                	when pc.abbreviation IS NOT NULL\s
                		then pc.abbreviation
                	ELSE 'EE'
                END AS scheme,
                SUM(b.allotted_amount) bud
                FROM probityfinancials.budget_allocation_base a
                JOIN probityfinancials.budget_allocation_details b
                	ON a.id = b.base_id
                JOIN pfmaster.hierarchy_setup dir
                	ON a.office_id = dir.hierarchy_Id
                	AND dir.category = 'R'
                JOIN pfmaster.hierarchy_setup dep
                	ON dir.parent_hierarchy = dep.hierarchy_Id
                	AND dep.category = 'D'
                JOIN probityfinancials.grant_setup gr
                	ON b.grant_id = gr.id
                JOIN probityfinancials.heads h
                	ON b.head_id = h.head_id
                LEFT JOIN probityfinancials.plan_category_head_mapping pchm
                	ON h.head_id = pchm.head_id
                LEFT JOIN probityfinancials.plan_category pc\s
                	ON pchm.pc_id = pc.pc_id
                JOIN probityfinancials.head_setup mjh
                	ON h.major_head = mjh.head_setup_id
                	AND mjh.type = 'MJH'
                JOIN probityfinancials.head_setup smh
                	ON h.sub_major_head = smh.head_setup_id
                	AND smh.type = 'SMH'
                JOIN probityfinancials.head_setup mnh
                	ON h.minor_head = mnh.head_setup_id
                	AND mnh.type = 'MNH'
                JOIN probityfinancials.head_setup sh
                	ON h.sub_head = sh.head_setup_id
                	AND sh.type = 'SH'
                JOIN probityfinancials.head_setup ssh
                	ON h.sub_sub_head = ssh.head_setup_id
                	AND ssh.type = 'SSH'
                JOIN probityfinancials.head_setup dh
                	ON h.detailed_head = dh.head_setup_id
                	AND dh.type = 'DH'
                JOIN probityfinancials.head_setup sdh
                	ON h.sub_detailed_head = sdh.head_setup_id
                	AND sdh.type = 'SDH'
                WHERE
                CONCAT(SUBSTR(a.year,1,5),'20',SUBSTR(a.year,6,8)) = :finYear
                GROUP BY\s
                gr.grant_no,\s
                gr.grant_desc,
                gr.grant_assamese,
                dep.hierarchy_Code ,
                dep.hierarchy_Name ,
                dir.hierarchy_Code ,\s
                dir.hierarchy_Name ,
                CONCAT(
                	h.head, '-',
                	case\s
                	when h.plan_status_migration IS NOT NULL \s
                		then h.plan_status_migration
                	when pc.abbreviation IS NOT NULL\s
                		then pc.abbreviation
                	ELSE 'EE'
                	END, '-',
                	h.ga_ssa_status, '-',
                	h.voted_charged_status),\s
                concat(mjh.head_code,'-',mjh.head_name),
                mjh.head_name_unicode,
                concat(smh.head_code,'-',smh.head_name),
                smh.head_name_unicode,
                concat(mnh.head_code,'-',mnh.head_name),
                mnh.head_name_unicode,
                concat(sh.head_code,'-',sh.head_name),
                sh.head_name_unicode,
                concat(ssh.head_code,'-',ssh.head_name),
                ssh.head_name_unicode,
                concat(dh.head_code,'-',dh.head_name),
                dh.head_name_unicode,
                concat(sdh.head_code,'-',sdh.head_name),
                sdh.head_name_unicode,
                case\s
                	when h.plan_status_migration IS NOT NULL \s
                		then h.plan_status_migration
                	when pc.abbreviation IS NOT NULL\s
                		then pc.abbreviation
                	ELSE 'EE'
                END
                ) m
                JOIN probityfinancials.plan_category pc
                	ON m.scheme = pc.abbreviation
                WHERE\s
                m.year = :finYear
            
                LIMIT 100
            """;

    private QueryConstants() {
    }
}
