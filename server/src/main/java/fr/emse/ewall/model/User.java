package fr.emse.ewall.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({FlatView.class, ProductionDetailView.class})
    private Long id;

    @Size(max = 255)
    @JsonView({FlatView.class, ProductionDetailView.class})
    private String esmeid;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Authority> authorities = new HashSet<>();

    @JsonView(FlatView.class)
    @Transient
    private List<String> roles;

    @org.hibernate.annotations.Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime firstConnectionAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }


    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public User setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public User addAuthority(Authority authority) {
        this.authorities.add(authority);
        return this;
    }

    public List<String> getRoles() {
        return authorities
                .stream()
                .map(a -> a.getName().toString())
                .collect(Collectors.toList());
    }

    public String getEsmeid() {
        return esmeid;
    }

    public User setEsmeid(String esmeid) {
        this.esmeid = esmeid;
        return this;
    }

    public LocalDateTime getFirstConnectionAt() {
        return firstConnectionAt;
    }

    public User setFirstConnectionAt(LocalDateTime firstConnectionAt) {
        this.firstConnectionAt = firstConnectionAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User account = (User) o;
        return Objects.equals(esmeid, account.esmeid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(esmeid);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", esmeid='" + esmeid + '\'' +
                '}';
    }
}
