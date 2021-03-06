package fr.emse.ewall.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
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
    @JsonView({FlatView.class, CategoryDetailView.class, ProductionDetailView.class})
    private Long id;

    @Size(max = 255)
    @NotNull
    @JsonView({FlatView.class, CategoryDetailView.class, ProductionDetailView.class})
    private String name;

    /**
     * The code is used in the QR Code URL. The width can change the size of the QRCode
     */
    @Size(max = 50)
    @NotNull
    @JsonView({FlatView.class, CategoryDetailView.class})
    private String code;
    
    @OneToMany(mappedBy = "category")
    @JsonView(CategoryDetailView.class)
    List<QrCode> qrcodes = new ArrayList<>();

    @Size(max = 5)
    @NotNull
    @JsonView({FlatView.class, CategoryDetailView.class})
    private String shortCode;

    @org.hibernate.annotations.Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    @JsonView(CategoryDetailView.class)
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

    public String getCode() {
        return code;
    }

    public Category setCode(String code) {
        this.code = code;
        return this;
    }

    public String getShortCode() {
        return shortCode;
    }

    public Category setShortCode(String shortCode) {
        this.shortCode = shortCode;
        return this;
    }
}
