package com.zhugalcf.superkassa.service;

import com.zhugalcf.superkassa.dto.Id;
import com.zhugalcf.superkassa.dto.ModifyRequestDto;
import com.zhugalcf.superkassa.dto.ModifyResponseDto;
import com.zhugalcf.superkassa.dto.RequestDto;
import com.zhugalcf.superkassa.entity.SkExample;
import com.zhugalcf.superkassa.entity.SkExampleRef;
import com.zhugalcf.superkassa.exception.DataNotFoundException;
import com.zhugalcf.superkassa.repository.SkExampleRepository;
import com.zhugalcf.superkassa.util.MapToJsonbMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkExampleService {

    private final SkExampleRepository skExampleRepository;
    private final MapToJsonbMapper mapToJsonbMapper;
    private static final String OBJ_KEY = "current";

    @Transactional
    public Id addSkExample(RequestDto requestDto) {
        Map<String, Object> json = new HashMap<>();
        json.put(OBJ_KEY, requestDto.getValue());
        SkExample skExample = SkExample.builder()
                .obj(mapToJsonbMapper.mapToJson(json))
                .skExampleRef(SkExampleRef.builder().name(requestDto.getName()).build())
                .build();

        SkExample saved = skExampleRepository.save(skExample);
        log.info("SkExample with name: {} was saved", requestDto.getName());
        return Id.builder()
                .id(saved.getId())
                .build();
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public ModifyResponseDto modifySkExample(ModifyRequestDto modifyRequestDto) {
        SkExample skExample = skExampleRepository.findById(modifyRequestDto.getId())
                .orElseThrow(() -> new DataNotFoundException((String
                        .format("SkExample with id:%d doesn't exist", modifyRequestDto.getId()))));
        Map<String, Object> json = mapToJsonbMapper.jsonToMap(skExample.getObj());
        int newCurrent = (int) json.get(OBJ_KEY) + modifyRequestDto.getAdd();
        json.put(OBJ_KEY, newCurrent);
        skExample.setObj(mapToJsonbMapper.mapToJson(json));
        skExampleRepository.save(skExample);
        log.info("SkExample object value with id: {} was incremented on {}",
                modifyRequestDto.getId(), modifyRequestDto.getAdd());
        return ModifyResponseDto.builder()
                .current(newCurrent)
                .build();
    }

    @Transactional
    public void deleteSkExample(Id id) {
        skExampleRepository.deleteById(id.getId());
        log.info("SkExample with id: {} was deleted ", id.getId());
    }
}
