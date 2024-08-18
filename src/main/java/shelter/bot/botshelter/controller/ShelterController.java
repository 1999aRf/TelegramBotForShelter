package shelter.bot.botshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shelter.bot.botshelter.model.Shelter;
import shelter.bot.botshelter.services.ShelterService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shelters")
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @GetMapping
    public List<Shelter> getAllShelters() {
        return shelterService.getAllShelters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shelter> getShelterById(@PathVariable Long id) {
        Optional<Shelter> shelter = shelterService.getShelterById(id);
        return shelter.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Shelter createShelter(@RequestBody Shelter shelter) {
        return shelterService.createShelter(shelter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shelter> updateShelter(@PathVariable Long id, @RequestBody Shelter shelterDetails) {
        Shelter updatedShelter = shelterService.updateShelter(id, shelterDetails);
        return ResponseEntity.ok(updatedShelter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShelter(@PathVariable Long id) {
        shelterService.deleteShelter(id);
        return ResponseEntity.noContent().build();
    }
}
