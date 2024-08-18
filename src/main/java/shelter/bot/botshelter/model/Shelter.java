package shelter.bot.botshelter.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;
    private String clientName;
    private String contactNumber;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL)
    private List<Animal> adoptedAnimals;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<Animal> getAdoptedAnimals() {
        return adoptedAnimals;
    }

    public void setAdoptedAnimals(List<Animal> adoptedAnimals) {
        this.adoptedAnimals = adoptedAnimals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(id, shelter.id) && Objects.equals(chatId, shelter.chatId) && Objects.equals(clientName, shelter.clientName) && Objects.equals(contactNumber, shelter.contactNumber) && Objects.equals(adoptedAnimals, shelter.adoptedAnimals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, clientName, contactNumber, adoptedAnimals);
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", clientName='" + clientName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", adoptedAnimals=" + adoptedAnimals +
                '}';
    }
}
