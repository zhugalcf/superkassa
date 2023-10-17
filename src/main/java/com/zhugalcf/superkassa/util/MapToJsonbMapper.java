package com.zhugalcf.superkassa.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PGobject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MapToJsonbMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> jsonToMap(PGobject json) {
        if (json == null) {
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(json.getValue(), new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public PGobject mapToJson(Map<String, Object> map) {
        try {
            String json = objectMapper.writeValueAsString(map);
            PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");
            pgObject.setValue(json);
            return pgObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
