package istic.weekend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Pratique.
 */
@Entity
@Table(name = "pratique")	
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pratique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "activite_id")
    private Activite activite;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activite getActivite() {
        return activite;
    }

    public Pratique activite(Activite activite) {
        this.activite = activite;
        return this;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public User getOwner() {
        return owner;
    }

    public Pratique owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pratique pratique = (Pratique) o;
        if (pratique.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pratique.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pratique{" +
            "id=" + getId() +
            "}";
    }
}
