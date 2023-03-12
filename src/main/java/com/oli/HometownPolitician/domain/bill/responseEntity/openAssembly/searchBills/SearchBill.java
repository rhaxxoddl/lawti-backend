package com.oli.HometownPolitician.domain.bill.responseEntity.openAssembly.searchBills;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchBill {
    private String bill_id;
    private int bill_no;
    private int age;
    private String bill_name;
    private String proposer;
    private String proposer_kind;
    private LocalDate propose_dt;
    private String curr_committee_id;
    private String curr_committee;
    private LocalDate committee_dt;
    private LocalDate committee_proc_dt;
    private String link_url;
    private String proc_result_cd;
    private LocalDate proc_dt;
}
