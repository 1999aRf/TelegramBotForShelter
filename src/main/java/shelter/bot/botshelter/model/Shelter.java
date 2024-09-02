package shelter.bot.botshelter.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;
/**
 * Сущность, представляющая приют.
 */
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
    @Pattern(regexp = "^\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}$", message = "Неверный формат номера телефона")
    private String contactNumber;
    @Column(name = "business_time")
    private String businessTime;
    @Column(name = "media_type", nullable = false)
    private String mediaType;

    @JsonIgnore
    @Column(name = "data", nullable = true)
    private byte[] data;

    @OneToMany(mappedBy = "shelter",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Animal> availableAnimals;

    @Column(name = "route_map_url", nullable = true)
    private String routeMapUrl; // Поле для хранения URL или пути к схеме проезда

    public Shelter(Long id, Long chatId, String clientName, String contactNumber, String businessTime, String mediaType, byte[] data, List<Animal> availableAnimals, String routeMapUrl) {
        this.id = id;
        this.chatId = chatId;
        this.clientName = clientName;
        this.contactNumber = contactNumber;
        this.businessTime = businessTime;
        this.mediaType = mediaType;
        this.data = data;
        this.availableAnimals = availableAnimals;
        this.routeMapUrl = routeMapUrl;
    }

    public Shelter() {
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

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
    public List<Animal> getAvailableAnimals() {
        return availableAnimals;
    }
    public void setAvailableAnimals(List<Animal> availableAnimals) {
        this.availableAnimals = availableAnimals;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(id, shelter.id) &&
                Objects.equals(chatId, shelter.chatId) &&
                Objects.equals(clientName, shelter.clientName) &&
                Objects.equals(contactNumber, shelter.contactNumber) &&
                Objects.equals(routeMapUrl, shelter.routeMapUrl);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, clientName, contactNumber, routeMapUrl);
    }
    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", clientName='" + clientName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", routeMapUrl='" + routeMapUrl + '\'' +
                '}';
    }
}