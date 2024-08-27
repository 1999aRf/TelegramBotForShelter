package shelter.bot.botshelter.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "diet")
    private String diet;

    @Column(name = "wellbeing")
    private String wellbeing;

    @Column(name = "behavior_changes")
    private String behaviorChanges;

    @Column(name = "is_reviewed", nullable = false)
    private boolean isReviewed;

    public Report() {
    }

    public Report(Long id, User user, Animal animal, LocalDate date, byte[] photo, String diet, String wellbeing, String behaviorChanges, boolean isReviewed) {
        this.id = id;
        this.user = user;
        this.animal = animal;
        this.date = date;
        this.photo = photo;
        this.diet = diet;
        this.wellbeing = wellbeing;
        this.behaviorChanges = behaviorChanges;
        this.isReviewed = isReviewed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getWellbeing() {
        return wellbeing;
    }

    public void setWellbeing(String wellbeing) {
        this.wellbeing = wellbeing;
    }

    public String getBehaviorChanges() {
        return behaviorChanges;
    }

    public void setBehaviorChanges(String behaviorChanges) {
        this.behaviorChanges = behaviorChanges;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return isReviewed == report.isReviewed && Objects.equals(id, report.id) && Objects.equals(user, report.user) && Objects.equals(animal, report.animal) && Objects.equals(date, report.date) && Objects.deepEquals(photo, report.photo) && Objects.equals(diet, report.diet) && Objects.equals(wellbeing, report.wellbeing) && Objects.equals(behaviorChanges, report.behaviorChanges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, animal, date, Arrays.hashCode(photo), diet, wellbeing, behaviorChanges, isReviewed);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", user=" + user +
                ", animal=" + animal +
                ", date=" + date +
                ", photo=" + Arrays.toString(photo) +
                ", diet='" + diet + '\'' +
                ", wellbeing='" + wellbeing + '\'' +
                ", behaviorChanges='" + behaviorChanges + '\'' +
                ", isReviewed=" + isReviewed +
                '}';
    }
}
