package com.stevenp.recipedemo.services;

import com.stevenp.recipedemo.domain.UnitOfMeasure;
import com.stevenp.recipedemo.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public Set<UnitOfMeasure> listAllUoms() {
        Set<UnitOfMeasure> uomSet = new HashSet<>();
        unitOfMeasureRepository.findAll().iterator().forEachRemaining(uomSet::add);
        log.debug("oumSet: " + uomSet);
        return uomSet;
    }
}
