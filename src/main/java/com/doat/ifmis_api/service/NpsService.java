package com.doat.ifmis_api.service;

import com.doat.ifmis_api.model.NpsContribution;
import com.doat.ifmis_api.model.NpsModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NpsService {

    List<NpsModel> getEmpDetails(String ppan);

    List<NpsContribution> getConDetails(String ppan);

}
