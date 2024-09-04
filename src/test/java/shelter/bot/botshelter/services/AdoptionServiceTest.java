package shelter.bot.botshelter.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shelter.bot.botshelter.model.Adoptions;
import shelter.bot.botshelter.model.Report;
import shelter.bot.botshelter.repositories.AdoptionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdoptionServiceTest {
    @Mock
    private AdoptionRepository repository;

    @InjectMocks
    private AdoptionService service;

    @Test
    void testAddAdoptions() {
        Adoptions adoption = new Adoptions();
        when(repository.save(any(Adoptions.class))).thenReturn(adoption);

        Adoptions savedAdoption = service.add(adoption);

        assertNotNull(savedAdoption);
        verify(repository, times(1)).save(adoption);
    }

    @Test
    void testFindByIdSuccess() {
        Adoptions adoption = new Adoptions();
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(adoption));

        Optional<Adoptions> foundAdoption = service.findById(1L);

        assertTrue(foundAdoption.isPresent());
        assertEquals(adoption, foundAdoption.get());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        Optional<Adoptions> foundAdoption = service.findById(1L);

        assertFalse(foundAdoption.isPresent());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testEditAdoptions() {
        Adoptions adoption = new Adoptions();

        when(repository.save(any(Adoptions.class))).thenReturn(adoption);

        Adoptions updatedAdoption = service.edit(adoption);

        assertNotNull(updatedAdoption);
        verify(repository, times(1)).save(adoption);
    }

    @Test
    void testDeleteAdoptions() {
        doNothing().when(repository).deleteById(any(Long.class));

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testFindAllSuccess() {
        Adoptions adoption = new Adoptions();
        when(repository.findAll()).thenReturn(List.of(adoption));

        Optional<List<Adoptions>> allAdoptions = service.findAll();

        assertTrue(allAdoptions.isPresent());
        assertEquals(1, allAdoptions.get().size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindAllNotFound() {
        when(repository.findAll()).thenReturn(null);

        Optional<List<Adoptions>> allAdoptions = service.findAll();

        assertFalse(allAdoptions.isPresent());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindByProbationPeriodLessNow() {
        Adoptions adoption = new Adoptions();
        when(repository.findByProbationPeriodLessNow()).thenReturn(Optional.of(List.of(adoption)));

        Optional<List<Adoptions>> adoptionsList = service.findByProbationPeriodLessNow();

        assertTrue(adoptionsList.isPresent());
        assertEquals(1, adoptionsList.get().size());
        verify(repository, times(1)).findByProbationPeriodLessNow();
    }

    @Test
    void testFindReportsByAdoptionIdSuccess() {
        Report report = new Report();
        when(repository.findReportsByAdoptionId(any(Long.class))).thenReturn(Optional.of(List.of(report)));

        Optional<List<Report>> reportsList = service.findReportsByAdoptionId(1L);

        assertTrue(reportsList.isPresent());
        assertEquals(1, reportsList.get().size());
        verify(repository, times(1)).findReportsByAdoptionId(1L);
    }

    @Test
    void testFindReportsByAdoptionIdNotFound() {
        when(repository.findReportsByAdoptionId(any(Long.class))).thenReturn(Optional.empty());

        Optional<List<Report>> reportsList = service.findReportsByAdoptionId(1L);

        assertFalse(reportsList.isPresent());
        verify(repository, times(1)).findReportsByAdoptionId(1L);
    }
}