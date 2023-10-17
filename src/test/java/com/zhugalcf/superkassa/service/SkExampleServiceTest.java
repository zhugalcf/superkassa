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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SkExampleServiceTest {

    @InjectMocks
    SkExampleService skExampleService;
    @Mock
    SkExampleRepository skExampleRepository;
    @Spy
    MapToJsonbMapper mapToJsonbMapper = new MapToJsonbMapper();
    SkExample skExample;
    Map<String, Object> json = new HashMap<>();
    static final String OBJ_KEY = "current";
    ModifyRequestDto modifyRequestDto;

    @BeforeEach
    void setUp() {
        SkExampleRef skExampleRef = SkExampleRef.builder()
                .name("counter")
                .id(1L)
                .build();
        json.put(OBJ_KEY, 0);
        skExample = SkExample.builder()
                .id(1L)
                .skExampleRef(skExampleRef)
                .obj(mapToJsonbMapper.mapToJson(json))
                .build();
        modifyRequestDto = ModifyRequestDto.builder()
                .add(2)
                .id(1L)
                .build();
    }

    @Test
    void testSkExampleIsModified() {
        Mockito.when(skExampleRepository.findById(1L)).thenReturn(Optional.of(skExample));
        ModifyResponseDto modifyResponseDto = skExampleService.modifySkExample(modifyRequestDto);
        json.put(OBJ_KEY, (int) json.get(OBJ_KEY) + 2);
        skExample.setObj(mapToJsonbMapper.mapToJson(json));
        Mockito.verify(skExampleRepository).save(skExample);
        assertEquals(ModifyResponseDto.builder().current(2).build(), modifyResponseDto);
    }

    @Test
    void testModifySkExampleThrowsDataNotFoundException() {
        Mockito.when(skExampleRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> skExampleService.modifySkExample(modifyRequestDto));
    }

    @Test
    void testSkExampleIsCreated() {
        Mockito.when(skExampleRepository.save(any())).thenReturn(skExample);
        Id counter = skExampleService.addSkExample(RequestDto.builder()
                .value(0)
                .name("counter")
                .build());
        assertEquals(Id.builder().id(1L).build(),counter);
    }
}