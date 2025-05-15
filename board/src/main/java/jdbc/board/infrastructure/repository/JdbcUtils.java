package jdbc.board.infrastructure.repository;

import jdbc.board.domain.shared.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class JdbcUtils {
    public static void fillId(Object object, Object id) {
        if (object == null || id == null) {
            throw new IllegalArgumentException("object is null");
        }
        Optional<Field> field = findIdField(object.getClass());
        if (field.isEmpty()) return;
        Field idField = field.get();
        idField.setAccessible(true);
        try {
            idField.set(object, id);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<Field> findIdField(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(idField -> idField.isAnnotationPresent(Id.class))
                .findFirst();
    }
}
