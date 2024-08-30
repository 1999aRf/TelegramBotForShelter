package shelter.bot.botshelter.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "volunteers")
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private long chat_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contacts", nullable = true)
    private String contacts;

    public Volunteer(long chat_id, String name, String contacts) {
        this.chat_id = chat_id;
        this.name = name;
        this.contacts = contacts;
    }

    public Volunteer(Long id, long chat_id, String name, String contacts) {
        this.id = id;
        this.chat_id = chat_id;
        this.name = name;
        this.contacts = contacts;
    }

    public Volunteer() {
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getChat_id() {
        return chat_id;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return id == volunteer.id && chat_id == volunteer.chat_id && contacts == volunteer.contacts && Objects.equals(name, volunteer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chat_id, name, contacts);
    }

    @Override
    public String toString() {
        return "Волонтер:" +
                " имя - '" + name + '\'' +
                "\n, номер -" + contacts;
    }
}
