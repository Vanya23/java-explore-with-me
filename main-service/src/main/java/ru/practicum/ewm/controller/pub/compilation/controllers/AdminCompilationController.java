package ru.practicum.ewm.controller.pub.compilation.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.controller.pub.Create;
import ru.practicum.ewm.controller.pub.compilation.CompilationService;
import ru.practicum.ewm.controller.pub.compilation.dto.CompilationDto;
import ru.practicum.ewm.controller.pub.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.controller.pub.compilation.dto.UpdateCompilationRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminCompilationController {

    CompilationService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public CompilationDto addCompilations(@Validated({Create.class}) @RequestBody NewCompilationDto newCompilationDto) {
        return service.addCompilations(newCompilationDto);
    }


    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteCompilations(@PathVariable long compId) {
        service.deleteCompilations(compId);

    }

    @PatchMapping("{compId}")
    public CompilationDto patchCompilations(@PathVariable long compId, @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        return service.patchCompilations(compId, updateCompilationRequest);
    }


}
