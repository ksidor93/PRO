package com.emontazysta.service;

import com.emontazysta.model.dto.ElementsPlannedNumberDto;
import java.util.List;

public interface ElementsPlannedNumberService {

    List<ElementsPlannedNumberDto> getAll();
    ElementsPlannedNumberDto getById(Long id);
    ElementsPlannedNumberDto add(ElementsPlannedNumberDto elementsPlannedNumberDto);
    void delete(Long id);
    ElementsPlannedNumberDto update(Long id, ElementsPlannedNumberDto elementsPlannedNumberDto);
}
