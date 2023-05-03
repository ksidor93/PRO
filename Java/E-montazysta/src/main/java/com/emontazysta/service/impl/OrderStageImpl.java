package com.emontazysta.service.impl;

import com.emontazysta.enums.OrderStageStatus;
import com.emontazysta.enums.Role;
import com.emontazysta.mapper.ElementsPlannedNumberMapper;
import com.emontazysta.mapper.OrderStageMapper;
import com.emontazysta.mapper.ToolMapper;
import com.emontazysta.mapper.ToolsPlannedNumberMapper;
import com.emontazysta.model.*;
import com.emontazysta.model.dto.*;
import com.emontazysta.model.searchcriteria.OrdersStageSearchCriteria;
import com.emontazysta.repository.*;
import com.emontazysta.repository.criteria.OrdersStageCriteriaRepository;
import com.emontazysta.service.OrderStageService;
import com.emontazysta.service.ToolService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderStageImpl implements OrderStageService {

    private final OrderStageRepository repository;
    private final OrderStageMapper orderStageMapper;
    private final OrdersStageCriteriaRepository ordersStageCriteriaRepository;
    private final ToolsPlannedNumberRepository toolsPlannedNumberRepository;
    private final ToolsPlannedNumberMapper toolsPlannedNumberMapper;
    private final ElementsPlannedNumberRepository elementsPlannedNumberRepository;
    private final ElementsPlannedNumberMapper elementsPlannedNumberMapper;
    private final ToolReleaseRepository toolReleaseRepository;
    private final ElementReturnReleaseRepository elementReturnReleaseRepository;
    private final ToolService toolService;
    private final ToolMapper toolMapper;
    private final AuthUtils authUtils;

    @Override
    public List<OrderStageDto> getAll() {
        return repository.findAll().stream()
                .map(orderStageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderStageDto getById(Long id) {
        OrderStage orderStage = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return orderStageMapper.toDto(orderStage);
    }

    @Override
    public OrderStageDto add(OrderStageDto orderStageDto) {
            orderStageDto.setFitters(new ArrayList<>());
            orderStageDto.setComments(new ArrayList<>());
            orderStageDto.setToolReleases(new ArrayList<>());
            orderStageDto.setElementReturnReleases(new ArrayList<>());
            orderStageDto.setAttachments(new ArrayList<>());
            orderStageDto.setNotifications(new ArrayList<>());
            orderStageDto.setDemandAdHocs(new ArrayList<>());
            orderStageDto.setListOfToolsPlannedNumber(new ArrayList<>());
            orderStageDto.setListOfElementsPlannedNumber(new ArrayList<>());
            orderStageDto.setPlannedDurationTime(ChronoUnit.HOURS.between(orderStageDto.getPlannedStartDate(),orderStageDto.getPlannedEndDate()));
            orderStageDto.setStatus(OrderStageStatus.PLANNING);

            OrderStage orderStage = orderStageMapper.toEntity(orderStageDto);
            return orderStageMapper.toDto(repository.save(orderStage));
    }


    @Override
    @Transactional
    public OrderStageDto addWithToolsAndElements(OrderStageWithToolsAndElementsDto orderStageDto) {
        orderStageDto.setStatus(OrderStageStatus.PLANNING);

        OrderStageWithToolsAndElementsDto modiffiedOrderStageDto = completeEmptyAttributes(orderStageDto);

        OrderStage orderStage = orderStageMapper.toEntity(modiffiedOrderStageDto);
        OrderStageDto savedOrderStageDto = orderStageMapper.toDto(repository.save(orderStage));


        if(!modiffiedOrderStageDto.getListOfToolsPlannedNumber().isEmpty()) {
            for (ToolsPlannedNumberDto toolsPlannedNumberDto : modiffiedOrderStageDto.getListOfToolsPlannedNumber()) {
                toolsPlannedNumberDto.setOrderStageId(savedOrderStageDto.getId());
                ToolsPlannedNumber toolsPlannedNumber = toolsPlannedNumberMapper.toEntity(toolsPlannedNumberDto);
                toolsPlannedNumberRepository.save(toolsPlannedNumber);
            }
        }

        if(!modiffiedOrderStageDto.getListOfElementsPlannedNumber().isEmpty()) {
            for (ElementsPlannedNumberDto elementsPlannedNumberDto : modiffiedOrderStageDto.getListOfElementsPlannedNumber()) {
                elementsPlannedNumberDto.setOrderStageId(savedOrderStageDto.getId());
                ElementsPlannedNumber elementsPlannedNumber = elementsPlannedNumberMapper.toEntity(elementsPlannedNumberDto);
                elementsPlannedNumberRepository.save(elementsPlannedNumber);
            }
        }

        return getById(savedOrderStageDto.getId());
    }

    private OrderStageWithToolsAndElementsDto completeEmptyAttributes(OrderStageWithToolsAndElementsDto orderStageDto) {
        orderStageDto.setFitters(orderStageDto.getFitters() == null ? new ArrayList<>() : orderStageDto.getFitters());
        orderStageDto.setComments(orderStageDto.getComments() == null ? new ArrayList<>() : orderStageDto.getComments());
        orderStageDto.setToolReleases(orderStageDto.getToolReleases() == null ? new ArrayList<>() : orderStageDto.getToolReleases());
        orderStageDto.setElementReturnReleases(orderStageDto.getElementReturnReleases() == null ? new ArrayList<>() : orderStageDto.getElementReturnReleases());
        orderStageDto.setAttachments(orderStageDto.getAttachments() == null ? new ArrayList<>() : orderStageDto.getAttachments());
        orderStageDto.setNotifications(orderStageDto.getNotifications() == null ? new ArrayList<>() : orderStageDto.getNotifications());
        orderStageDto.setDemandAdHocs(orderStageDto.getDemandAdHocs() == null ? new ArrayList<>() : orderStageDto.getDemandAdHocs());
        orderStageDto.setListOfToolsPlannedNumber(orderStageDto.getListOfToolsPlannedNumber() == null ? new ArrayList<>() : orderStageDto.getListOfToolsPlannedNumber());
        orderStageDto.setListOfElementsPlannedNumber(orderStageDto.getListOfElementsPlannedNumber() == null ? new ArrayList<>() : orderStageDto.getListOfElementsPlannedNumber());
        orderStageDto.setPlannedDurationTime(ChronoUnit.HOURS.between(orderStageDto.getPlannedStartDate(), orderStageDto.getPlannedEndDate()));

        return orderStageDto;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderStageDto update(Long id, OrderStageWithToolsAndElementsDto orderStageDto) {

        OrderStageWithToolsAndElementsDto modiffiedOrderStageDto = completeEmptyAttributes(orderStageDto);

        OrderStage updatedOrderStage = orderStageMapper.toEntity(modiffiedOrderStageDto);

        OrderStage orderStageDb = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        List<ToolsPlannedNumber> updatedToolsList = new ArrayList<>();
        List<ElementsPlannedNumber> updatedElementsList = new ArrayList<>();
        if (authUtils.getLoggedUser().getRoles().contains(Role.SPECIALIST)) {
            if(!updatedOrderStage.getListOfToolsPlannedNumber().isEmpty()) {
                orderStageDb.getListOfToolsPlannedNumber().stream()
                        .forEach(toolsPlannedNumber -> toolsPlannedNumberRepository.delete(toolsPlannedNumber));

                for (ToolsPlannedNumberDto toolsPlannedNumberDto : modiffiedOrderStageDto.getListOfToolsPlannedNumber()) {
                    toolsPlannedNumberDto.setOrderStageId(orderStageDb.getId());
                    ToolsPlannedNumber toolsPlannedNumber = toolsPlannedNumberMapper.toEntity(toolsPlannedNumberDto);
                    toolsPlannedNumberRepository.save(toolsPlannedNumber);
                    updatedToolsList.add(toolsPlannedNumber);
                }
            }

            if(!updatedOrderStage.getListOfElementsPlannedNumber().isEmpty()) {
                orderStageDb.getListOfElementsPlannedNumber().stream()
                        .forEach(elementsPlannedNumber -> elementsPlannedNumberRepository.delete(elementsPlannedNumber));

                for (ElementsPlannedNumberDto elementsPlannedNumberDto : modiffiedOrderStageDto.getListOfElementsPlannedNumber()) {
                    elementsPlannedNumberDto.setOrderStageId(orderStageDto.getId());
                    ElementsPlannedNumber elementsPlannedNumber = elementsPlannedNumberMapper.toEntity(elementsPlannedNumberDto);
                    elementsPlannedNumberRepository.save(elementsPlannedNumber);
                    updatedElementsList.add(elementsPlannedNumber);

                }
            }

        } else{
            updatedToolsList = updatedOrderStage.getListOfToolsPlannedNumber();
            updatedElementsList = updatedOrderStage.getListOfElementsPlannedNumber();
        }

        //As updated set old lists
        List<ToolRelease> updatedToolReleaseList = orderStageDb.getToolReleases();
        List<ElementReturnRelease> updatedElementReturnReleaseList = orderStageDb.getElementReturnReleases();
        if(authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MAN) ||
                authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MANAGER)) {
            //Tool and Element release
            //As updated set new lists
            updatedToolReleaseList = updatedOrderStage.getToolReleases();
            updatedElementReturnReleaseList = updatedOrderStage.getElementReturnReleases();

            //Check if toolRelease exists, if not add it
            for (ToolRelease toolRelease : updatedOrderStage.getToolReleases()) {
                if(!orderStageDb.getToolReleases().contains(toolRelease)) {
                    toolRelease.setOrderStage(orderStageDb);
                    toolRelease.setReleasedBy((Warehouseman) authUtils.getLoggedUser());
                    toolReleaseRepository.save(toolRelease);
                }
            }

            //Check if elementReturnRelease exists, if not add it
            for (ElementReturnRelease elementReturnRelease : updatedOrderStage.getElementReturnReleases()) {
                if(!orderStageDb.getElementReturnReleases().contains(elementReturnRelease)) {
                    elementReturnRelease.setOrderStage(orderStageDb);
                    elementReturnRelease.setServedBy((Warehouseman) authUtils.getLoggedUser());
                    elementReturnReleaseRepository.save(elementReturnRelease);
                }
            }

            //Tool return
            if(Optional.ofNullable(orderStageDto.getReturningTools()).isPresent()) {
                for (String toolCode : orderStageDto.getReturningTools()) {
                    Optional<ToolRelease> toolReleaseOptional = orderStageDb.getToolReleases().stream()
                            .filter(o -> o.getTool().getCode().equals(toolCode))
                            .filter(o -> o.getReturnTime() == null)
                            .findFirst();
                    if (toolReleaseOptional.isEmpty()) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nie można zwrócić narzędzia o kodzie: " + toolCode);
                    }else {
                        toolReleaseOptional.get().setReturnTime(LocalDateTime.now());
                    }
                }
            }
        }


        orderStageDb.setName(updatedOrderStage.getName());
        orderStageDb.setStatus(updatedOrderStage.getStatus());
        orderStageDb.setPrice(updatedOrderStage.getPrice());
        orderStageDb.setPlannedStartDate(updatedOrderStage.getPlannedStartDate());
        orderStageDb.setPlannedEndDate(updatedOrderStage.getPlannedEndDate());
        orderStageDb.setStartDate(updatedOrderStage.getStartDate());
        orderStageDb.setEndDate(updatedOrderStage.getEndDate());
        orderStageDb.setPlannedDurationTime(ChronoUnit.HOURS.between(updatedOrderStage.getPlannedStartDate(),updatedOrderStage.getPlannedEndDate()));
        orderStageDb.setPlannedFittersNumber(updatedOrderStage.getPlannedFittersNumber());
        orderStageDb.setMinimumImagesNumber(updatedOrderStage.getMinimumImagesNumber());
        orderStageDb.setAssignedTo(updatedOrderStage.getAssignedTo());
        orderStageDb.setManagedBy(updatedOrderStage.getManagedBy());
        orderStageDb.setComments(updatedOrderStage.getComments());
        orderStageDb.setAttachments(updatedOrderStage.getAttachments());
        orderStageDb.setNotifications(updatedOrderStage.getNotifications());
        orderStageDb.setDemandsAdHoc(updatedOrderStage.getDemandsAdHoc());
        orderStageDb.setListOfToolsPlannedNumber(updatedToolsList);
        orderStageDb.setListOfElementsPlannedNumber(updatedElementsList);
        orderStageDb.setToolReleases(updatedToolReleaseList);
        orderStageDb.setElementReturnReleases(updatedElementReturnReleaseList);

        return orderStageMapper.toDto(repository.save(orderStageDb));
    }

    @Override
    public List<OrderStageDto> getFilteredOrders(OrdersStageSearchCriteria ordersStageSearchCriteria, Principal principal) {
        return  ordersStageCriteriaRepository.findAllWithFilters(ordersStageSearchCriteria, principal);
    }
}
