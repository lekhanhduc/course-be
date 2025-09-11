package vn.fpt.courseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.fpt.courseservice.model.TokenInValid;

@Repository
public interface TokenInvalidRepository extends JpaRepository<TokenInValid, String> {
}
