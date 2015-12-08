package fr.emse.numericwall.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * A work is created by a user (role {@link Role#WRITER}). Texts is the most common way to create a work
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/12/15.
 */
@Entity
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * In the reality this relation is a one-to-one relationship but we have a lot of problem in Hibernate to manage
     * these relations.
     */
    @OneToMany(mappedBy = "work")
    List<QrCode> qrcodes = new ArrayList<>();


    @ManyToOne(optional = false)
    public User user;

    @org.hibernate.annotations.Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Lob
    private String content;

    public Long getId() {
        return id;
    }

    public Work setId(Long id) {
        this.id = id;
        return this;
    }

    public QrCode getQrcode() {
        return qrcodes.isEmpty() ? null : qrcodes.get(0);
    }

    public Work setQrcode(QrCode qrcode) {
        this.qrcodes.clear();
        this.qrcodes.add(qrcode);
        return this;
    }

    public User getUser() {
        return user;
    }

    public Work setUser(User user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Work setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Work setContent(String content) {
        this.content = content;
        return this;
    }
}
