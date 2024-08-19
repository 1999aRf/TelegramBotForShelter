package shelter.bot.botshelter.services;

import org.jvnet.hk2.annotations.Service;
import org.springframework.web.multipart.MultipartFile;
import shelter.bot.botshelter.model.Shelter;
import shelter.bot.botshelter.repositories.ShelterRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    public String saveRouteMap(Long shelterId, MultipartFile file) throws IOException {
        Optional<Shelter> shelterOpt = shelterRepository.findById(shelterId);
        if (shelterOpt.isPresent()) {
            Shelter shelter = shelterOpt.get();
            String fileName = file.getOriginalFilename();
            String uploadDir = "uploads/maps/";

            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            Path filePath = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            shelter.setRouteMapUrl(filePath.toString());
            shelterRepository.save(shelter);

            return filePath.toString();
        } else {
            throw new RuntimeException("Shelter not found");
        }
    }
}
