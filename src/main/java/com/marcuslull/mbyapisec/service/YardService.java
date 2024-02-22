package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.YardDto;
import com.marcuslull.mbyapisec.repository.UserRepository;
import com.marcuslull.mbyapisec.repository.YardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class YardService {
    private final UserRepository userRepository;
    private final YardRepository yardRepository;
    private final MapperService mapperService;

    public YardService(UserRepository userRepository, YardRepository yardRepository, MapperService mapperService) {
        this.userRepository = userRepository;
        this.yardRepository = yardRepository;
        this.mapperService = mapperService;
    }

    public List<YardDto> getYards() {
        List<YardDto> yardDtos = new ArrayList<>();
        yardRepository.findAll().forEach(yard -> {
            YardDto yardDto = mapperService.map(yard);
            yardDtos.add(yardDto);
        });
        return yardDtos;
    }

    public YardDto postYard(YardDto yardDto) {
        return mapperService.map(yardRepository.save(mapperService.map(yardDto)));
    }
}
