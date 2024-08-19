package shelter.bot.botshelter.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="shelters")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id",nullable = false, unique = true)
    private Long chatId;
    @Column(name = "client_name",nullable = true)
    private String clientName;
    @Column(name = "contact_number",nullable = true, unique = true)
    private String contactNumber;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL)
    private List<Animal> adoptedAnimals;

    private String routeMapUrl; // Поле для хранения URL или пути к схеме проезда

    public String getRouteMapUrl() {
        return routeMapUrl;
    }

    public void setRouteMapUrl(String routeMapUrl) {
        this.routeMapUrl = routeMapUrl;
    }

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
