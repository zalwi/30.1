package pl.zalwi.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zalwi.global.repository.SystemUserRepository;
import pl.zalwi.login.model.SystemUser;

import java.util.List;
import java.util.Optional;

@Service
public class SystemUserService {

    SystemUserRepository systemUserRepository;

    @Autowired
    public SystemUserService(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

    public List<SystemUser> getAllEmployees() {
        return systemUserRepository.findAll();
    }

    public Optional<SystemUser> getSystemUserById(Long id){
        return systemUserRepository.findById(id);
    }
}
