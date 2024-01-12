package uz.kuvondikov.example.config;

import uz.kuvondikov.example.exception.MyCustomException;
import uz.kuvondikov.example.filters.CorsFilter;
import uz.kuvondikov.example.resources.AuthUserController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class MyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(AuthUserController.class);
        classes.add(MyCustomException.class);
        return classes;
    }
}
