package es.brouse.auctionhouse.loader.serializer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class Reflexion {
    /**
     * Get all the fields on the entity annotated with {@param annotation}
     * @param entity entity from get the fields
     * @param annotation annotation to check on fields
     * @return the found annotated fields
     */
    public static Set<Field> getFields(Class<?> entity, Class<? extends Annotation> annotation) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    /**
     * Check if the given field if instanceof one the given {@param types}
     * @param entity field to check
     * @param type type to look for
     * @return if the field is instanceof the given type
     */
    public static boolean checkType(Class<?> entity, Class<?> type) {
        return entity.isAssignableFrom(type);
    }

    /**
     * Get the annotation parameter from the given field that matches
     * with the given {@param annotation}
     * @param entity to get the annotation from
     * @param annotation annotation to find
     * @return the found annotation
     * @param <A> annotation type
     */
    public static  <A extends Annotation> A getAnnotation(Class<?> entity, Class<A> annotation) {
        if (entity.isAnnotationPresent(annotation))
            return entity.getAnnotation(annotation);
        throw new ReflexionException("Entity is not annotated with "+ annotation.getName());
    }

    /**
     * Get the value of the field
     * @param object object from where get the value
     * @param field field to get the value
     * @return the found value
     */
    public static Object getValue(Object object, Field field) {
        boolean accessible = true;
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
                accessible = false;
            }
            return field.get(object);
        } catch (IllegalAccessException exception) {
            throw new ReflexionException(exception);
        }finally {
            field.setAccessible(accessible);
        }
    }

    /**
     * Create a new instance of the given entity with the given {@param args}.
     * @apiNote This method will invoke a constructor with all the
     * {@link Serializable} fields in desc order.
     * @param entity entity to instantiate
     * @param args args required for the @AllArgsConstructor
     * @return the created entity
     * @param <T> entity to create
     */
    public static <T> T instance(Class<T> entity, Object[] args) {
        try {
            Constructor<T> constructor = entity.getConstructor(Arrays.stream(args)
                    .map(Object::getClass).toArray(Class[]::new));
            return constructor.newInstance(args);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ReflexionException("Unable to instance class "+ entity.getSimpleName(), e);
        }
    }

    /**
     * Invoke a method from a specific class and make sure it will return the given {@param returnType}.
     * If not it will throw a {@link SerializationException}
     * @param entity entity to serialize
     * @param name method name
     * @param returnType return type of the mehtod
     * @param args args for invoking the method
     * @return the method invoking result
     * @param <T> type of the return
     */
    public static <T> T invokeMethod(Class<?> entity, String name, Class<T> returnType, Object[] args) {
        boolean accessible = false;
        Method method =  null;

        try {
            //Try to get the method for the given name
            method = entity.getMethod(name, Arrays.stream(args).map(Object::getClass).toArray(Class[]::new));

            //Conditions to ensure the method is reade to be invoked
            if (!method.isAccessible())
                accessible = true;
            if (checkType(method.getReturnType(), returnType))
                throw new RuntimeException("This method doesn't return " + returnType.getSimpleName());

            return returnType.cast(method.invoke(entity, args));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            throw new SerializationException(exception);
        }finally {
            //Make sure the method return to it original accessor
            if (method != null && accessible)
                method.setAccessible(false);
        }
    }
}
