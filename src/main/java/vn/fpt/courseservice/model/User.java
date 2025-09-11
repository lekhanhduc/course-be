package vn.fpt.courseservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private String status;

    @OneToMany(mappedBy = "user", fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    public List<UserHasRole> userHasRoles = new ArrayList<>();

    public void addRole(Role role){
        this.userHasRoles.add(UserHasRole.builder()
                .user(this)
                .role(role)
                .build());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userHasRoles.stream()
                .map(userHasRole -> new SimpleGrantedAuthority(userHasRole.getRole().getName()))
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
