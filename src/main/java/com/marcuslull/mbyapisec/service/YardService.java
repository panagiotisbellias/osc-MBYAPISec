package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.YardDto;
import com.marcuslull.mbyapisec.model.entity.Yard;
import com.marcuslull.mbyapisec.repository.YardRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class YardService {
    private final YardRepository yardRepository;
    private final MapperService mapperService;

    public YardService(YardRepository yardRepository, MapperService mapperService) {
        this.yardRepository = yardRepository;
        this.mapperService = mapperService;
    }

    public List<YardDto> getYards() {
        List<YardDto> yardDtos = new ArrayList<>();
        yardRepository.findYardsByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .forEach(yard -> {
                    YardDto yardDto = mapperService.map(yard);
                    yardDtos.add(yardDto);
                });
        return yardDtos;
    }

    public YardDto getYard(Long id) {
        Yard yard = yardRepository.findYardByIdAndUserEmail(id, SecurityContextHolder.getContext().getAuthentication().getName());
        if (yard != null) {
            return mapperService.map(yard);
        }
        return null;
    }

    public YardDto postYard(YardDto yardDto) {
        yardDto.setUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return mapperService.map(yardRepository.save(mapperService.map(yardDto)));
    }

    public YardDto putYard(String id, YardDto yardDto) {
        yardDto.setId(Long.valueOf(id));
        yardDto.setUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Yard yard = yardRepository.findYardByIdAndUserEmail(yardDto.getId(), yardDto.getUserEmail());
        if (yard != null) {
            yardDto.setCreated(yard.getCreated());
            return mapperService.map(yardRepository.save(mapperService.map(yardDto)));
        }
        return null;
    }

    @Transactional
    public void deleteYard(Long id) {
        yardRepository.deleteYardByIdAndUserEmail(id, SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
