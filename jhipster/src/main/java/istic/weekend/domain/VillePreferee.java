package istic.weekend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A VillePreferee.
 */
@Entity
@Table(name = "ville_preferee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VillePreferee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ville ville;

    @ManyToOne
    private User owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ville getVille() {
        return ville;
    }

    public VillePreferee ville(Ville ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public User getOwner() {
        return owner;
    }

    public VillePreferee owner(User user) {
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
        VillePreferee villePreferee = (VillePreferee) o;
        if (villePreferee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), villePreferee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VillePreferee{" +
            "id=" + getId() +
            "}";
    }
}
