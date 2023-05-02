package ru.practicum.ewm.controller.pub.compilation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.controller.pub.GeneratePageableObj;
import ru.practicum.ewm.controller.pub.compilation.dto.CompilationDto;
import ru.practicum.ewm.controller.pub.compilation.dto.CompilationMapper;
import ru.practicum.ewm.controller.pub.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.controller.pub.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.controller.pub.compilation.model.Compilation;
import ru.practicum.ewm.controller.pub.error.exception.BadRequestException;
import ru.practicum.ewm.controller.pub.error.exception.NotFoundException;
import ru.practicum.ewm.controller.pub.events.EventRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompilationServiceImpl implements CompilationService {
    CompilationRepository repository;
    EventRepository eventRepository;
    CompilationMapper compilationMapper;
    GeneratePageableObj myServicePage;

    Sort sortIdAsc = Sort.by(Sort.Direction.ASC, "id"); // по возрастанию


    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {

        Pageable pageable = myServicePage.checkAndCreatePageable(from, size, sortIdAsc);

        Page<Compilation> page;

        page = repository.findAllByPinned(pinned, pageable);

        List<Compilation> compilations = page.getContent();

        return compilationMapper.fromCompilationListToCompilationDtoList(compilations);
    }

    @Override
    public CompilationDto getCompilationsById(long compId) {
        if (!repository.existsById(compId)) throw new NotFoundException("");
        Compilation compilation = repository.getReferenceById(compId);
        return compilationMapper.fromCompilationToCompilationDto(compilation);
    }

    @Override
    @Transactional
    public CompilationDto addCompilations(NewCompilationDto newCompilationDto) {
        if (Strings.isBlank(newCompilationDto.getTitle())) throw new BadRequestException("");
        Compilation compilation = compilationMapper.fromNewCompilationDtoToCompilation(newCompilationDto, eventRepository);
        compilation = repository.save(compilation);
        return compilationMapper.fromCompilationToCompilationDto(compilation);
    }

    @Override
    @Transactional
    public void deleteCompilations(long compId) {
        if (!repository.existsById(compId)) throw new NotFoundException("");
        repository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto patchCompilations(long compId, UpdateCompilationRequest updateCompilationRequest) {
        if (!repository.existsById(compId)) throw new NotFoundException("");
        Compilation compilation = repository.getReferenceById(compId);
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        if (Strings.isNotBlank(updateCompilationRequest.getTitle())) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        if (updateCompilationRequest.getEvents() != null && updateCompilationRequest.getEvents().size() != 0) {
            compilation.setEvents(new ArrayList<>());
            for (Long id :
                    updateCompilationRequest.getEvents()) {
                compilation.getEvents().add(eventRepository.getReferenceById(id));
            }
        }
        compilation = repository.save(compilation);
        return compilationMapper.fromCompilationToCompilationDto(compilation);
    }


}
