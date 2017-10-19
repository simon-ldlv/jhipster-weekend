package istic.weekend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Weather.
 */
@Entity
@Table(name = "weather")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Weather implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "weather")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Meteo> meteos = new HashSet<>();

    @ManyToMany(mappedBy = "weathers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    public Weather name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Meteo> getMeteos() {
        return meteos;
    }

    public Weather meteos(Set<Meteo> meteos) {
        this.meteos = meteos;
        return this;
    }

    public Weather addMeteos(Meteo meteo) {
        this.meteos.add(meteo);
        meteo.setWeather(this);
        return this;
    }

    public Weather removeMeteos(Meteo meteo) {
        this.meteos.remove(meteo);
        meteo.setWeather(null);
        return this;
    }

    public void setMeteos(Set<Meteo> meteos) {
        this.meteos = meteos;
    }

    public Set<Activite> getActivites() {
        return activites;
    }

    public Weather activites(Set<Activite> activites) {
        this.activites = activites;
        return this;
    }

    public Weather addActivites(Activite activite) {
        this.activites.add(activite);
        activite.getWeathers().add(this);
        return this;
    }

    public Weather removeActivites(Activite activite) {
        this.activites.remove(activite);
        activite.getWeathers().remove(this);
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
        Weather weather = (Weather) o;
        if (weather.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weather.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Weather{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
