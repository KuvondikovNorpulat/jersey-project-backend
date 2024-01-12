package uz.kuvondikov.example.repository;

import uz.kuvondikov.example.dto.AuthUserDTO;
import uz.kuvondikov.example.dto.CreateDTO;
import uz.kuvondikov.example.dto.UpdateDTO;

import java.util.List;

public interface AuthUserRepository {
    Long save(CreateDTO createDTO);

    AuthUserDTO getById(Long id);

    List<AuthUserDTO> getAll();

    Long update(Long id, UpdateDTO updateDTO);

    Long delete(Long id);
}