package com.doat.ifmis_api.service;

import com.doat.ifmis_api.model.DdoModel;
import org.springframework.stereotype.Service;

@Service
public interface DdoService  {

    DdoModel getDdoDetails();
}
