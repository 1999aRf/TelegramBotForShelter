package shelter.bot.botshelter.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import shelter.bot.botshelter.model.Shelter;
import shelter.bot.botshelter.repositories.ShelterRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShelterServiceTest {
    @InjectMocks
    private ShelterService shelterService;

    @Mock
    private ShelterRepository shelterRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getShelterById() {
        Shelter shelter = new Shelter();
        shelter.setId(1L);
        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));

        Optional<Shelter> foundShelter = shelterService.getShelterById(1L);

        assertTrue(foundShelter.isPresent());
        assertEquals(shelter.getId(), foundShelter.get().getId());
    }

    @Test
    void createShelter() {
        Shelter shelter = new Shelter();
        when(shelterRepository.save(shelter)).thenReturn(shelter);

        Shelter createdShelter = shelterService.createShelter(shelter);

        assertNotNull(createdShelter);
        verify(shelterRepository, times(1)).save(shelter);
    }

    @Test
    void updateShelter() {
        Shelter shelter = new Shelter();
        shelter.setId(1L);
        byte[] imageData = "dummyData".getBytes();

        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));
        when(shelterRepository.save(shelter)).thenReturn(shelter);

        Shelter updatedShelter = shelterService.updateShelter(1L, shelter);

        assertNotNull(updatedShelter);
        verify(shelterRepository, times(1)).save(shelter);
    }

    @Test
    void deleteShelter() {
        Shelter shelter = new Shelter();
        shelter.setId(1L);
        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));

        shelterService.deleteShelter(1L);

        verify(shelterRepository, times(1)).deleteById(1L);
    }
}