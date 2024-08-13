package shelter.bot.botshelter.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long chatId;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false, unique = true)
    private String contactNumber;


// на будущее, сразу создал для питомцев.
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Pet> adoptedPets;

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

//    public List<Pet> getAdoptedPets() {
//        return adoptedPets;
//    }
//
//    public void setAdoptedPets(List<Pet> adoptedPets) {
//        this.adoptedPets = adoptedPets;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(chatId, client.chatId) && Objects.equals(clientName, client.clientName) && Objects.equals(contactNumber, client.contactNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, clientName, contactNumber);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", clientName='" + clientName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}
