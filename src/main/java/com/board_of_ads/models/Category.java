package com.board_of_ads.models;

import com.board_of_ads.models.posting.Posting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private boolean isActive;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category", referencedColumnName = "id")
    private Category category;

    @OneToMany
    private List<Posting> posts;

    @Column
    private int layer;

    @Column//(unique = true)
    private String frontName;

    public Category(String name, Category category) {
        this.isActive = true;
        this.name = name;
        this.category = category;
    }

    public Category(Long id, String name, Category category) {
        this.isActive = true;
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Category(String name, Category category, int layer) {
        this.isActive = true;
        this.name = name;
        this.category = category;
        this.layer = layer;
    }

    public Category(String name, Category category, int layer, String frontName) {
        this.isActive = true;
        this.name = name;
        this.category = category;
        this.layer = layer;
        this.frontName = frontName;
    }

    public Category(Long id, String name, Category category, int layer) {
        this.isActive = true;
        this.id = id;
        this.name = name;
        this.category = category;
        this.layer = layer;
    }
}
