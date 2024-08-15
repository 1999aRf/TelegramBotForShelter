package shelter.bot.botshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shelter.bot.botshelter.model.Volunteer;
import shelter.bot.botshelter.services.VolunteerService;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/volunteers")
public class VolunteersController {

    private final VolunteerService service;

    public VolunteersController(VolunteerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Volunteer> addVolunteer(@RequestBody Volunteer volunteer) {
        if (volunteer == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.add(volunteer));
    }

    @PutMapping
    public ResponseEntity<Volunteer> putVolunteer(@RequestBody Volunteer volunteer) {
        if (service.findById(volunteer.getId()) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.edit(volunteer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> findById(@PathVariable Long id) {
        Optional<Volunteer> volunteer = service.findById(id);
        return volunteer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Volunteer>> findAll() {
        Optional<List<Volunteer>> optional = service.findAll();
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
