package com.emontazysta.service.impl;

import com.emontazysta.mapper.DemandAdHocMapper;
import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.model.dto.DemandAdHocDto;
import com.emontazysta.model.dto.filterDto.DemandAdHocFilterDto;
import com.emontazysta.model.searchcriteria.DemandAdHocSearchCriteria;
import com.emontazysta.repository.DemandAdHocRepository;
import com.emontazysta.repository.criteria.DemandAdHocCriteriaRepository;
import com.emontazysta.service.DemandAdHocService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DemandAdHocServiceImpl implements DemandAdHocService {

    private final DemandAdHocRepository repository;
    private final DemandAdHocMapper demandAdHocMapper;
    private final DemandAdHocCriteriaRepository demandAdHocCriteriaRepository;

    @Override
    public List<DemandAdHocDto> getAll() {
        return repository.findAll().stream()
                .map(demandAdHocMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DemandAdHocDto getById(Long id) {

        DemandAdHoc demandAdHoc = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return demandAdHocMapper.toDto(demandAdHoc);
    }

    @Override
    public DemandAdHocDto add(DemandAdHocDto demandAdHocDto) {

        DemandAdHoc demandAdHoc = demandAdHocMapper.toEntity(demandAdHocDto);
        demandAdHoc.setCreatedAt(LocalDateTime.now());
        return demandAdHocMapper.toDto(repository.save(demandAdHoc));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public DemandAdHocDto update(Long id, DemandAdHocDto demandAdHocDto) {
        DemandAdHoc updatedDemandAdHoc = demandAdHocMapper.toEntity(demandAdHocDto);
        DemandAdHoc demandAdHocDb = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        demandAdHocDb.setDescription(updatedDemandAdHoc.getDescription());
        demandAdHocDb.setReadByWarehousemanTime(updatedDemandAdHoc.getReadByWarehousemanTime());
        demandAdHocDb.setRealisationTime(updatedDemandAdHoc.getRealisationTime());
        demandAdHocDb.setWarehousemanComment(updatedDemandAdHoc.getWarehousemanComment());
        demandAdHocDb.setSpecialistComment(updatedDemandAdHoc.getSpecialistComment());

        return demandAdHocMapper.toDto(demandAdHocDb);
    }

    @Override
    public List<DemandAdHocFilterDto> getFiltered(DemandAdHocSearchCriteria demandAdHocSearchCriteria) {
        return demandAdHocCriteriaRepository.findAllWithFilters(demandAdHocSearchCriteria);
    }
}
