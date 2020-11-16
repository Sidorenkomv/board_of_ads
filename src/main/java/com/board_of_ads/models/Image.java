package com.board_of_ads.models;

import com.board_of_ads.models.posting.Posting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Класс-сущность всех изображений сайта
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "images_link_db")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 1024)
    private String pathURL;

    @ManyToMany
    @JoinTable(name="posting_images",
            joinColumns=@JoinColumn (name="posting_id"),
            inverseJoinColumns=@JoinColumn(name="image_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Posting> postings;

    public Image(String pathURL) {
        this.pathURL = pathURL;
    }

    public Image(Long id, String pathURL) {
        this.id = id;
        this.pathURL = pathURL;
    }
}
