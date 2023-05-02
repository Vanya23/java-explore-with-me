package ru.practicum.ewm.controller.pub.compilation;


import ru.practicum.ewm.controller.pub.compilation.dto.CompilationDto;
import ru.practicum.ewm.controller.pub.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.controller.pub.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {


    List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    CompilationDto getCompilationsById(long compId);

    CompilationDto addCompilations(NewCompilationDto newCompilationDto);

    void deleteCompilations(long compId);

    CompilationDto patchCompilations(long compId, UpdateCompilationRequest updateCompilationRequest);
}
