package pl.zalwi.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.zalwi.global.model.ContactPerson;

import java.util.List;

@Repository
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long>, JpaSpecificationExecutor<ContactPerson> {
    List<ContactPerson> findAllByOrderByFirstName();
}
