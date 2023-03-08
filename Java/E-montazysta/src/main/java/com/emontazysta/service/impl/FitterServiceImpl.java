package com.emontazysta.service.impl;

import com.emontazysta.mapper.FitterMapper;
import com.emontazysta.model.Fitter;
import com.emontazysta.model.dto.FitterDto;
import com.emontazysta.repository.FitterRepository;
import com.emontazysta.service.FitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FitterServiceImpl implements FitterService {

    private final FitterRepository repository;
    private final FitterMapper fitterMapper;

    @Override
    public List<FitterDto> getAll() {
        return repository.findAll().stream()
                .map(fitterMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public FitterDto getById(Long id) {
        Fitter fitter = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return fitterMapper.toDto(fitter);
    }

    @Override
    public FitterDto add(FitterDto fitterDto) {
        Fitter fitter = fitterMapper.toEntity(fitterDto);
        return fitterMapper.toDto(repository.save(fitter));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public FitterDto update(Long id, FitterDto fitterDto) {
        Fitter updatedFitter = fitterMapper.toEntity(fitterDto);
        Fitter fitter = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        fitter.setFirstName(updatedFitter.getFirstName());
        fitter.setLastName(updatedFitter.getLastName());
        fitter.setUsername(updatedFitter.getUsername());
        fitter.setEmail(updatedFitter.getEmail());
        fitter.setPhone(updatedFitter.getPhone());
        fitter.setPesel(updatedFitter.getPesel());
        fitter.setUnavailabilities(updatedFitter.getUnavailabilities());
        fitter.setNotifications(updatedFitter.getNotifications());
        fitter.setEmployeeComments(updatedFitter.getEmployeeComments());
        fitter.setElementEvents(updatedFitter.getElementEvents());
        fitter.setEmployments(updatedFitter.getEmployments());
        fitter.setAttachments(updatedFitter.getAttachments());
        fitter.setToolEvents(updatedFitter.getToolEvents());
        return fitterMapper.toDto(repository.save(fitter));
    }
}
