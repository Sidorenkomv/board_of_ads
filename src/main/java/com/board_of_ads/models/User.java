package com.board_of_ads.models;


import com.board_of_ads.models.dto.order.Order;
import com.board_of_ads.models.dto.review.Review;
import com.board_of_ads.models.posting.Posting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    @NotBlank
    private String email;

    @Column
    @Size(min = 6, max = 60, message = "Пароль должен содержать минимум 6 и максимум 60 символов")
    @Pattern(regexp = "(?=^.{6,60}$)([^\\s]).*$", message = "Пароль не должен содержать пробелов")
    private String password;

    @Column
    private String firsName;

    @Column
    private String lastName;

    @Column
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id")
    private Image avatar;

    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Message> messages;

    @Column
    private LocalDateTime dataRegistration = LocalDateTime.now();

    @Column
    private boolean enable;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Order> orders;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Posting> postings;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
