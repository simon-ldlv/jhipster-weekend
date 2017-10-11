package istic.weekend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ville.
 */
@Entity
@Table(name = "ville")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ville implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ville_activites",
               joinColumns = @JoinColumn(name="villes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="activites_id", referencedColumnName="id"))
    private Set<Activite> activites = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Ville name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Activite> getActivites() {
        return activites;
    }

    public Ville activites(Set<Activite> activites) {
        this.activites = activites;
        return this;
    }

    public Ville addActivites(Activite activite) {
        this.activites.add(activite);
        activite.getVilles().add(this);
        return this;
    }

    public Ville removeActivites(Activite activite) {
        this.activites.remove(activite);
        activite.getVilles().remove(this);
        return this;
    }

    public void setActivites(Set<Activite> activites) {
        this.activites = activites;
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
        Ville ville = (Ville) o;
        if (ville.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ville.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ville{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
