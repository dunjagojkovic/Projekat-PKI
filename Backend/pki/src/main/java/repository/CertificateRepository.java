package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Certificate;
import model.CertificateType;
import model.User;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer>{
	public Certificate findByAlias(String alias);
	public boolean existsByAlias(String alias);
	public List<Certificate> findByUser(User user);
	List<Certificate> findAllByRevokedAndType(boolean revoked,CertificateType type);
	
	@Query(value = "SELECT * "
			+ "FROM certificate  "
			+ "WHERE type = 0 AND (Now() BETWEEN valid_from AND valid_until)", nativeQuery = true)
    List<Certificate> getValidIssuers();
	
	@Query(value = "SELECT * "
			+ "FROM certificate  "
			+ "WHERE type = 0 AND (Now() BETWEEN valid_from AND valid_until) AND user_id = :userId", nativeQuery = true)
    List<Certificate> getValidIssuers(@Param("userId") Long userId);
}
