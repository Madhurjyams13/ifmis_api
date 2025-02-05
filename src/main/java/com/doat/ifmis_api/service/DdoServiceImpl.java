package com.doat.ifmis_api.service;

import com.doat.ifmis_api.model.DdoModel;
import com.doat.ifmis_api.repository.DdoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DdoServiceImpl implements DdoService {
    private final DdoRepository ddoRepository;

    public DdoModel getDdoDetails(String ddoCode) {
        return ddoRepository.getDdoDetails(ddoCode)
                .orElseThrow(() -> new EntityNotFoundException("DDO not found with code: " + ddoCode));
    }
}
