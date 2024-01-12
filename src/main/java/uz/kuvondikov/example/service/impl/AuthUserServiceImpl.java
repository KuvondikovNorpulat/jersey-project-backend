package uz.kuvondikov.example.service.impl;

import org.jvnet.hk2.annotations.Service;
import uz.kuvondikov.example.dto.AuthUserDTO;
import uz.kuvondikov.example.dto.CreateDTO;
import uz.kuvondikov.example.dto.UpdateDTO;
import uz.kuvondikov.example.repository.AuthUserRepository;
import uz.kuvondikov.example.repository.impl.AuthUserRepositoryImpl;
import uz.kuvondikov.example.service.AuthUserService;

import javax.ws.rs.ext.Provider;
import java.util.List;

@Service
@Provider
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository repository;

    public AuthUserServiceImpl() {
        this.repository = new AuthUserRepositoryImpl();
    }

    @Override
    public Long create(CreateDTO createDTO) {
        return repository.save(createDTO);
    }

    @Override
    public AuthUserDTO getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public List<AuthUserDTO> getAll() {
        return repository.getAll();
    }

    @Override
    public Long update(Long id, UpdateDTO updateDTO) {
        return repository.update(id, updateDTO);
    }

    @Override
    public Long delete(Long id) {
        return repository.delete(id);
    }
}
