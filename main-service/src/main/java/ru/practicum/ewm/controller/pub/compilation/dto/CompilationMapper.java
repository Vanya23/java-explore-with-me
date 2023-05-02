package ru.practicum.ewm.controller.pub.compilation.dto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.controller.pub.compilation.model.Compilation;
import ru.practicum.ewm.controller.pub.events.EventRepository;
import ru.practicum.ewm.controller.pub.events.dto.EventMapper;
import ru.practicum.ewm.controller.pub.events.model.EventPatternTime;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompilationMapper {

    EventPatternTime eventPatternTime;

    EventMapper eventMapper;

    public CompilationDto fromCompilationToCompilationDto(Compilation compilation) {

        return new CompilationDto(
                compilation.getId(),
                eventMapper.fromListEventToListEventShortDto(compilation.getEvents()),
                compilation.getPinned(),
                compilation.getTitle());
    }

    public List<CompilationDto> fromCompilationListToCompilationDtoList(List<Compilation> compilations) {
        List<CompilationDto> compilationDtos = new ArrayList<>();
        for (Compilation compilation :
                compilations) {
            compilationDtos.add(fromCompilationToCompilationDto(compilation));
        }
        return compilationDtos;
    }

    public Compilation fromNewCompilationDtoToCompilation(NewCompilationDto newCompilationDto, EventRepository eventRepository) {

        Compilation compilation = new Compilation();
        compilation.setTitle(newCompilationDto.getTitle());
        compilation.setPinned(newCompilationDto.getPinned());
        compilation.setEvents(new ArrayList<>());
        for (Long id : newCompilationDto.getEvents()
        ) {
            compilation.getEvents().add(eventRepository.getReferenceById(id));
        }
        return compilation;
    }


}
