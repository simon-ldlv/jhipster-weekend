package istic.weekend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Meteo.
 */
@Entity
@Table(name = "meteo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Meteo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "updated")
    private LocalDate updated;

    @Column(name = "celsius_average")
    private Double celsiusAverage;

    @OneToOne(mappedBy = "meteo")
    @JsonIgnore
    private Ville ville;

    @ManyToOne
    private Weather weather;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public Meteo updated(LocalDate updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    public Double getCelsiusAverage() {
        return celsiusAverage;
    }

    public Meteo celsiusAverage(Double celsiusAverage) {
        this.celsiusAverage = celsiusAverage;
        return this;
    }

    public void setCelsiusAverage(Double celsiusAverage) {
        this.celsiusAverage = celsiusAverage;
    }

    public Ville getVille() {
        return ville;
    }

    public Meteo ville(Ville ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public Weather getWeather() {
        return weather;
    }

    public Meteo weather(Weather weather) {
        this.weather = weather;
        return this;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
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
        Meteo meteo = (Meteo) o;
        if (meteo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meteo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Meteo{" +
            "id=" + getId() +
            ", updated='" + getUpdated() + "'" +
            ", celsiusAverage='" + getCelsiusAverage() + "'" +
            "}";
    }
}
