package uz.kuvondikov.example.repository.impl;

import uz.kuvondikov.example.dto.AuthUserDTO;
import uz.kuvondikov.example.dto.CreateDTO;
import uz.kuvondikov.example.dto.UpdateDTO;
import uz.kuvondikov.example.enums.Gender;
import uz.kuvondikov.example.exception.MyCustomException;
import uz.kuvondikov.example.repository.AuthUserRepository;

import javax.ws.rs.ext.Provider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Provider
public class AuthUserRepositoryImpl implements AuthUserRepository {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/furor_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "root123";

    private Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new MyCustomException("OOPS! Problem connecting to the database!");
        }
    }

    @Override
    public Long save(CreateDTO createDTO) {
        Connection connection = getConnection();
        checkingCreateUserAvailability(connection, createDTO);
        String sql = "INSERT INTO auth_user (first_name,last_name,gender, username,phone_number, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, createDTO.getFirstName());
            statement.setString(2, createDTO.getLastName());
            statement.setString(3, createDTO.getGender().name());
            statement.setString(4, createDTO.getUsername());
            statement.setString(5, createDTO.getPhoneNumber());
            statement.setString(6, createDTO.getPassword());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new MyCustomException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new MyCustomException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException | RuntimeException e) {
            throw new MyCustomException(e.getMessage());
        }
    }

    public void checkingCreateUserAvailability(Connection connection, CreateDTO createDTO) {
        String forUsername = "select id from auth_user where username =?";
        String forPhoneNumber = "select id from auth_user where phone_number =?";
        try {
            PreparedStatement statement = connection.prepareStatement(forUsername);
            statement.setString(1, createDTO.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                throw new MyCustomException("AuthUser with this username already exists : " + createDTO.getUsername());
            PreparedStatement preparedStatement = connection.prepareStatement(forPhoneNumber);
            preparedStatement.setString(1, createDTO.getPhoneNumber());
            ResultSet resultSetOther = preparedStatement.executeQuery();
            if (resultSetOther.next())
                throw new MyCustomException("AuthUser with this phone number already exists : " + createDTO.getPhoneNumber());
        } catch (SQLException e) {
            throw new MyCustomException(e.getMessage());
        }
    }

    @Override
    public AuthUserDTO getById(Long id) {
        Connection connection = getConnection();
        String sql = "SELECT * FROM auth_user WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return AuthUserDTO.
                            builder().
                            id(resultSet.getLong("id")).
                            firstName(resultSet.getString("first_name")).
                            lastName(resultSet.getString("last_name")).
                            gender((resultSet.getString("gender").
                                    equals("MALE") ? Gender.MALE : Gender.FEMALE)).
                            username(resultSet.getString("username")).
                            phoneNumber(resultSet.getString("phone_number")).
                            build();
                } else {
                    throw new MyCustomException("OOPS, User not found this id : " + id);
                }
            }
        } catch (SQLException e) {
            throw new MyCustomException(e.getMessage());
        }
    }

    @Override
    public List<AuthUserDTO> getAll() {
        Connection connection = getConnection();
        List<AuthUserDTO> users = new ArrayList<>();
        String sql = "SELECT * FROM auth_user";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                AuthUserDTO user = AuthUserDTO.builder().
                        id(resultSet.getLong("id")).
                        firstName(resultSet.getString("first_name")).
                        lastName(resultSet.getString("last_name")).
                        gender((resultSet.getString("gender").
                                equals("MALE") ? Gender.MALE : Gender.FEMALE)).
                        username(resultSet.getString("username")).
                        phoneNumber(resultSet.getString("phone_number")).
                        build();
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new MyCustomException(ex.getMessage());
        }
        return users;
    }

    @Override
    public Long update(Long id, UpdateDTO updateDTO) {
        Connection connection = getConnection();
        checkingUpdateUserAvailability(id, connection, updateDTO);
        String sql = "UPDATE auth_user SET first_name=?, last_name=?, username = ?, phone_number = ? ,password=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, updateDTO.getFirstName());
            statement.setString(2, updateDTO.getLastName());
            statement.setString(3, updateDTO.getUsername());
            statement.setString(4, updateDTO.getPhoneNumber());
            statement.setString(5, updateDTO.getPassword());
            statement.setLong(6, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return id;
            } else {
                throw new MyCustomException("OOPS, User not found this id : " + id);
            }
        } catch (SQLException ex) {
            throw new MyCustomException(ex.getMessage());
        }
    }

    public void checkingUpdateUserAvailability(Long id, Connection connection, UpdateDTO updateDTO) {
        try {
            String forUsername = "select id from auth_user where username = ? and id <> ?";
            String forPhoneNumber = "select id from auth_user where phone_number = ? and id <> ?";
            PreparedStatement statement = connection.prepareStatement(forUsername);
            statement.setString(1, updateDTO.getUsername());
            statement.setLong(2, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                throw new MyCustomException("AuthUser with this username already exists : " + updateDTO.getUsername());

            PreparedStatement preparedStatement = connection.prepareStatement(forPhoneNumber);
            preparedStatement.setString(1, updateDTO.getPhoneNumber());
            preparedStatement.setLong(2, id);
            ResultSet resultSetOther = preparedStatement.executeQuery();

            if (resultSetOther.next())
                throw new MyCustomException("AuthUser with this phone number already exists : " + updateDTO.getPhoneNumber());
        } catch (SQLException e) {
            throw new MyCustomException(e.getMessage());
        }
    }

    @Override
    public Long delete(Long id) {
        Connection connection = getConnection();
        String sql = "DELETE FROM auth_user WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return id;
            } else {
                throw new MyCustomException("OOPS, User not found with id : " + id);
            }
        } catch (SQLException e) {
            throw new MyCustomException(e.getMessage());
        }
    }
}
