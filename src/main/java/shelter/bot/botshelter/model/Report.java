package shelter.bot.botshelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @JsonIgnore
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adoption_id")
    private Adoptions adoption;

    public Report() {


    }

    public Report(Long id, LocalDateTime date, byte[] photo, String diet, String wellbeing, String behaviorChanges, boolean isReviewed, Adoptions adoption) {
        this.id = id;
        this.date = date;
        this.photo = photo;
        this.diet = diet;
        this.wellbeing = wellbeing;
        this.behaviorChanges = behaviorChanges;
        this.isReviewed = isReviewed;
        this.adoption = adoption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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

    public Adoptions getAdoption() {
        return adoption;
    }

    public void setAdoption(Adoptions adoption) {
        this.adoption = adoption;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", date=" + date +
                ", photo=" + Arrays.toString(photo) +
                ", diet='" + diet + '\'' +
                ", wellbeing='" + wellbeing + '\'' +
                ", behaviorChanges='" + behaviorChanges + '\'' +
                ", isReviewed=" + isReviewed +
                ", adoption=" + adoption +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return isReviewed == report.isReviewed && Objects.equals(id, report.id) && Objects.equals(date, report.date) && Objects.deepEquals(photo, report.photo) && Objects.equals(diet, report.diet) && Objects.equals(wellbeing, report.wellbeing) && Objects.equals(behaviorChanges, report.behaviorChanges) && Objects.equals(adoption, report.adoption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, Arrays.hashCode(photo), diet, wellbeing, behaviorChanges, isReviewed, adoption);
    }
}
