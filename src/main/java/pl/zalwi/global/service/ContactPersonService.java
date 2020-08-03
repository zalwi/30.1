package pl.zalwi.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.zalwi.global.model.ContactPerson;
import pl.zalwi.global.repository.ContactPersonRepository;

import java.util.List;

@Service
public class ContactPersonService {

    ContactPersonRepository contactPersonRepository;

    @Autowired
    public ContactPersonService(ContactPersonRepository contactPersonRepository) {
        this.contactPersonRepository = contactPersonRepository;
    }

    public Long addNewPersonAndReturnId(ContactPerson contactPerson){
        ContactPerson savedPerson = contactPersonRepository.save(contactPerson);
        return savedPerson.getId();
    }

    public List<ContactPerson> findAllContactPersons() {
        return contactPersonRepository.findAllByOrderByFirstName();
    }


    public ContactPerson getContactPerson(Long personId) {
        return contactPersonRepository.getOne(personId);
    }
}
