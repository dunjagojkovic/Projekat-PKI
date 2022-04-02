package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer>{
	public Certificate findByAlias(String alias);

}
