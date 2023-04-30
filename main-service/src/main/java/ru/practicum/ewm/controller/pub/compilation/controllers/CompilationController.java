package ru.practicum.ewm.controller.pub.compilation.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.controller.pub.compilation.CompilationService;
import ru.practicum.ewm.controller.pub.compilation.dto.CompilationDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CompilationController {

    CompilationService service;


    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(required = false, defaultValue = "0") int from,
                                                @RequestParam(required = false, defaultValue = "10") int size) {

        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationsById(@PathVariable long compId) {

        return service.getCompilationsById(compId);
    }


}
