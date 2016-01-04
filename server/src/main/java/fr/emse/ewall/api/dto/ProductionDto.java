package fr.emse.ewall.api.dto;

import java.time.LocalDateTime;

import fr.emse.ewall.model.Production;
import fr.emse.ewall.model.ProductionState;
import fr.emse.ewall.model.User;

/**
 * When we want to display a paginated list we can't used {@link org.springframework.data.domain.PageImpl} and {@link com.fasterxml.jackson.annotation.JsonView}.
 * We need to use specific DTO.
 * See {@link <a href="https://jira.spring.io/browse/DATACMNS-735">https://jira.spring.io/browse/DATACMNS-735</a>}

 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 03/01/16.
 */
public class ProductionDto {
    
    private Long id;
    
    public String user;

    public String category;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String content;

    private ProductionState state;

    public Long getId() {
        return id;
    }

    public ProductionDto setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ProductionDto setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ProductionDto setContent(String content) {
        this.content = content;
        return this;
    }

    public ProductionState getState() {
        return state;
    }

    public ProductionDto setState(ProductionState state) {
        this.state = state;
        return this;
    }

    public ProductionDto setUser(String user) {
        this.user = user;
        return this;
    }

    public String getUser() {
        return user;
    }

    public String getCategory() {
        return category;
    }

    public ProductionDto setCategory(String category) {
        this.category = category;
        return this;
    }

    public static ProductionDto build(Production production){
        return new ProductionDto()
                .setId(production.getId())
                .setContent(production.getContent())
                .setCreatedAt(production.getCreatedAt())
                .setState(production.getState())
                .setUser(production.getUser().getEsmeid())
                .setCategory(production.getQrcode()!=null ? production.getQrcode().getCategory().getName() : null);
    }
}
