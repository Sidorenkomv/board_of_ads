package com.board_of_ads.models;

import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.repository.CityRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cities")
public class    City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city_form_subject")
    private String regionFormSubject;

    @Column(name = "million_city")
    private boolean millionCity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Posting> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
    private List<User> users;

    public City(String name, Region region, String regionFormSubject) {
        this.regionFormSubject = regionFormSubject;
        this.name = name;
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(id, city.id) &&
                Objects.equals(name, city.name);
    }

    public City(String name, Region region, String regionFormSubject, boolean mil) {
        this.regionFormSubject = regionFormSubject;
        this.name = name;
        this.region = region;
        this.millionCity = mil;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regionFormSubject='" + regionFormSubject + '\'' +
                '}';
    }

    public void setMillionCity(boolean millionCity) {
        this.millionCity = millionCity;
    }

}
