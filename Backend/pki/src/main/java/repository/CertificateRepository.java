package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Certificate;
import model.CertificateType;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer>{
	public Certificate findByAlias(String alias);
	public boolean existsByAlias(String alias);
	List<Certificate> findAllByRevokedAndType(boolean revoked,CertificateType type);
}
