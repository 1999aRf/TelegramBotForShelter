package shelter.bot.botshelter.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import shelter.bot.botshelter.model.Volunteer;
import shelter.bot.botshelter.repositories.VolunteerRepository;

import java.util.Optional;

import static org.assertj.core.api.InstanceOfAssertFactories.LONG;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;
import static shelter.bot.botshelter.constants.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {

    @Mock
    private VolunteerRepository repository;

    @InjectMocks
    private VolunteerService service;


    @Test
    void add() {
        when(repository.save(VOLUNTEER1)).thenReturn(VOLUNTEER1);
        Volunteer added = service.add(VOLUNTEER1);
        assertThat(added).isEqualTo(VOLUNTEER1);
    }

    @Test
    void findById() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.ofNullable(VOLUNTEER1));
        Optional<Volunteer> added = service.findById(123L);
        assertThat(added.get()).isEqualTo(VOLUNTEER1);
    }

    @Test
    void edit() {
        when(repository.save(VOLUNTEER1)).thenReturn(VOLUNTEER1);
        Volunteer added = service.edit(VOLUNTEER1);
        assertThat(added).isEqualTo(VOLUNTEER1);
        Mockito.verify(repository, Mockito.times(1)).save(VOLUNTEER1);
    }

    @Test
    void delete() {
        service.delete(1L);
        Mockito.verify(repository,Mockito.times(1)).deleteById(1L);
    }

    @Test
    void findAll() {
        service.findAll();
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }
}