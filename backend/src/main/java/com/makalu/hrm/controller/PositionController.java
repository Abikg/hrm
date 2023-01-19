package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.model.PositionDTO;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/position")
public class PositionController {

    private final PositionService positionService;

    @GetMapping("/")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String index(){
        return "redirect:/position/list";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String list(ModelMap map){
        try{
            map.put(ParameterConstant.POSITION_LIST,positionService.list());
        }catch (Exception e){
            log.error("Error occurred in position" ,e);
        }

        return "position/list";
    }

    @GetMapping("/api/list")
    @PreAuthorize("hasRole('AUTHENTICATED')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> list(){
        return ResponseEntity.ok(RestResponseDto.INSTANCE().success().detail(positionService.list()));
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> save(@RequestBody PositionDTO positionDto){
        return ResponseEntity.ok(positionService.save(positionDto));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> getById(@PathVariable("id") UUID positionId){
        return ResponseEntity.ok(positionService.getResponseById(positionId));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> update(@RequestBody PositionDTO positionDto, RedirectAttributes redirectAttributes){
        return ResponseEntity.ok(positionService.update(positionDto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseBody
    public ResponseEntity<RestResponseDto> delete(@PathVariable("id") UUID positionId){
        return ResponseEntity.ok(positionService.delete(positionId));
    }

}
