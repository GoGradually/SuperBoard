package jdbc.productmanagementsystem.infra.persistence;

import jdbc.productmanagementsystem.domain.repository.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class RepositoryJDBCUtils {
    static void fillId(Object object, long idValue) {
        try {
            Optional<Field> idField = findIdField(object.getClass());
            if (idField.isEmpty()) return;
            Field id = idField.get();
            id.setAccessible(true);
            id.set(object, idValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<Field> findIdField(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        return Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst();
    }
}
