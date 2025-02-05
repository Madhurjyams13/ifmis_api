package com.doat.ifmis_api.service;

import com.doat.ifmis_api.model.NpsContribution;
import com.doat.ifmis_api.model.NpsModel;
import com.doat.ifmis_api.repository.NpsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class NpsServiceImpl implements NpsService {

    private final NpsRepository npsRepository;

    @Override
    public List<NpsModel> getEmpDetails(String ppan) {
        return npsRepository.getEmpDetails(ppan)
                .map(Collections::singletonList)
                .orElse(null);
    }

    @Override
    public List<NpsContribution> getConDetails(String ppan) {
        return npsRepository.getConDetails(ppan);
    }
}
