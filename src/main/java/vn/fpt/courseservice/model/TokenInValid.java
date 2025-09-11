package vn.fpt.courseservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tokens_invalid")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenInValid {

    @Id
    private String jwtId;
}
