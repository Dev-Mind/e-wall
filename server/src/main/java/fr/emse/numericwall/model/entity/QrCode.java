package fr.emse.numericwall.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * QRcode are linked with a {@link Category}. The QRCodes are generated by admin users on the category screen
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/12/15.
 */
@Entity
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    public Category category;

    @ManyToOne
    Work work;

    @org.hibernate.annotations.Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime generatedAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public QrCode setId(Long id) {
        this.id = id;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public QrCode setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Work getWork() {
        return work;
    }

    public QrCode setWork(Work work) {
        this.work = work;
        return this;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public QrCode setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
        return this;
    }
}
