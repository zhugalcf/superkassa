package com.zhugalcf.superkassa.controller;

import com.zhugalcf.superkassa.dto.ModifyRequestDto;
import com.zhugalcf.superkassa.dto.ModifyResponseDto;
import com.zhugalcf.superkassa.dto.RequestDto;
import com.zhugalcf.superkassa.dto.Id;
import com.zhugalcf.superkassa.service.SkExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SkExampleController {

    private final SkExampleService skExampleService;
    @PostMapping("/add")
    public Id addSkExample(@RequestBody RequestDto requestDto) {
        return skExampleService.addSkExample(requestDto);
    }

    @PatchMapping("/modify")
    public ModifyResponseDto modifySkExample(@RequestBody ModifyRequestDto modifyRequestDto) {
        return skExampleService.modifySkExample(modifyRequestDto);
    }

    @DeleteMapping("/delete")
    public void deleteSkExample(@RequestBody Id id) {
        skExampleService.deleteSkExample(id);
    }
}
