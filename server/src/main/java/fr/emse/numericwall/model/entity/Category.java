package fr.emse.numericwall.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Text can be organized by catagories. For each catagory we will have a set of {@link QrCode}
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/12/15.
 */
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 255)
    private String name;

    @OneToMany(mappedBy = "category")
    List<QrCode> qrcodes = new ArrayList<>();

    @org.hibernate.annotations.Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public Category setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public List<QrCode> getQrcodes() {
        return qrcodes;
    }

    public Category addQrcode(QrCode qrCode) {
        qrcodes.add(qrCode);
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Category setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
