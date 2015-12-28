package fr.emse.ewall.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * A work is created by a user (role {@link Role#WRITER}). Texts is the most common way to create a work
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/12/15.
 */
@Entity
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({FlatView.class, CompleteView.class})
    private Long id;

    /**
     * In the reality this relation is a one-to-one relationship but we have a lot of problem in Hibernate to manage
     * these relations.
     */
    @OneToMany(mappedBy = "production")
    List<QrCode> qrcodes = new ArrayList<>();

    @ManyToOne(optional = false)
    @JsonView(CompleteView.class)
    public User user;

    @org.hibernate.annotations.Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    @JsonView(CompleteView.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Lob
    @JsonView({FlatView.class, CompleteView.class})
    private String content;

    @JsonView({FlatView.class, CompleteView.class})
    @Enumerated(EnumType.STRING)
    private ProductionState state = ProductionState.PENDING;

    public Long getId() {
        return id;
    }

    public Production setId(Long id) {
        this.id = id;
        return this;
    }

    public QrCode getQrcode() {
        return qrcodes.isEmpty() ? null : qrcodes.get(0);
    }

    public Production setQrcode(QrCode qrcode) {
        this.qrcodes.clear();
        this.qrcodes.add(qrcode);
        return this;
    }

    public User getUser() {
        return user;
    }

    public Production setUser(User user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Production setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Production setContent(String content) {
        this.content = content;
        return this;
    }

    public ProductionState getState() {
        return state;
    }

    public Production setState(ProductionState state) {
        this.state = state;
        return this;
    }
}
