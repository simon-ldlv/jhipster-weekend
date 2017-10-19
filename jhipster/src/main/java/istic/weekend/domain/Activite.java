package istic.weekend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Activite.
 */
@Entity
@Table(name = "activite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "collectif")
    private Boolean collectif;

    @Column(name = "celsius_min")
    private Double celsiusMin;

    @Column(name = "celsius_max")
    private Double celsiusMax;

    @ManyToMany(mappedBy = "activites")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ville> villes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "activite_weathers",
               joinColumns = @JoinColumn(name="activites_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="weathers_id", referencedColumnName="id"))
    private Set<Weather> weathers = new HashSet<>();

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

    public Activite name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isCollectif() {
        return collectif;
    }

    public Activite collectif(Boolean collectif) {
        this.collectif = collectif;
        return this;
    }

    public void setCollectif(Boolean collectif) {
        this.collectif = collectif;
    }

    public Double getCelsiusMin() {
        return celsiusMin;
    }

    public Activite celsiusMin(Double celsiusMin) {
        this.celsiusMin = celsiusMin;
        return this;
    }

    public void setCelsiusMin(Double celsiusMin) {
        this.celsiusMin = celsiusMin;
    }

    public Double getCelsiusMax() {
        return celsiusMax;
    }

    public Activite celsiusMax(Double celsiusMax) {
        this.celsiusMax = celsiusMax;
        return this;
    }

    public void setCelsiusMax(Double celsiusMax) {
        this.celsiusMax = celsiusMax;
    }

    public Set<Ville> getVilles() {
        return villes;
    }

    public Activite villes(Set<Ville> villes) {
        this.villes = villes;
        return this;
    }

    public Activite addVilles(Ville ville) {
        this.villes.add(ville);
        ville.getActivites().add(this);
        return this;
    }

    public Activite removeVilles(Ville ville) {
        this.villes.remove(ville);
        ville.getActivites().remove(this);
        return this;
    }

    public void setVilles(Set<Ville> villes) {
        this.villes = villes;
    }

    public Set<Weather> getWeathers() {
        return weathers;
    }

    public Activite weathers(Set<Weather> weathers) {
        this.weathers = weathers;
        return this;
    }

    public Activite addWeathers(Weather weather) {
        this.weathers.add(weather);
        weather.getActivites().add(this);
        return this;
    }

    public Activite removeWeathers(Weather weather) {
        this.weathers.remove(weather);
        weather.getActivites().remove(this);
        return this;
    }

    public void setWeathers(Set<Weather> weathers) {
        this.weathers = weathers;
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
        Activite activite = (Activite) o;
        if (activite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activite{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", collectif='" + isCollectif() + "'" +
            ", celsiusMin='" + getCelsiusMin() + "'" +
            ", celsiusMax='" + getCelsiusMax() + "'" +
            "}";
    }
}
