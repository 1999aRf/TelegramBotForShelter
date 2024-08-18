package shelter.bot.botshelter.services;

import org.jvnet.hk2.annotations.Service;
import shelter.bot.botshelter.model.Shelter;
import shelter.bot.botshelter.repositories.ShelterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }
    public List<Shelter> getAllShelters() {
        return shelterRepository.findAll();
    }

    public Optional<Shelter> getShelterById(Long id) {
        return shelterRepository.findById(id);
    }

    public Shelter createShelter(Shelter shelter) {
        return shelterRepository.save(shelter);
    }

    public Shelter updateShelter(Long id, Shelter shelterDetails) {
        Shelter shelter = shelterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shelter not found"));

        shelter.setChatId(shelterDetails.getChatId());
        shelter.setClientName(shelterDetails.getClientName());
        shelter.setContactNumber(shelterDetails.getContactNumber());
        shelter.setAdoptedAnimals(shelterDetails.getAdoptedAnimals());

        return shelterRepository.save(shelter);
    }

    public void deleteShelter(Long id) {
        shelterRepository.deleteById(id);
    }
}
