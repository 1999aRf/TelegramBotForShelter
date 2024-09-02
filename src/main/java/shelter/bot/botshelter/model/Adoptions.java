package shelter.bot.botshelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Класс сущности усыновлений. Предполагает для клиента иметь несколько усыновлений, однако
 * данная версия программы может обработать только одно усыновление
 */
@Entity
@Table(name = "adoptions")
public class Adoptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "probation_period")
    private LocalDateTime probationPeriod;
    @Column(name = "result")
    private int result;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    @OneToMany(mappedBy = "adoption",fetch = FetchType.EAGER)
    private List<Report> reports;

    public Adoptions( Animal animal, Client client) {
        this.probationPeriod = LocalDateTime.now().plusDays(30L);
        this.result = 0;
        this.animal = animal;
        this.client = client;
    }

    public Adoptions() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getProbationPeriod() {
        return probationPeriod;
    }

    public void setProbationPeriod(LocalDateTime probationPeriod) {
        this.probationPeriod = probationPeriod;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Adoptions{" +
                "id=" + id +
                ", probationPeriod=" + probationPeriod +
                ", result=" + result +
                ", animal=" + animal +
                ", client=" + client +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adoptions adoptions = (Adoptions) o;
        return result == adoptions.result && Objects.equals(id, adoptions.id) && Objects.equals(probationPeriod, adoptions.probationPeriod) && Objects.equals(animal, adoptions.animal) && Objects.equals(client, adoptions.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, probationPeriod, result, animal, client);
    }
}
