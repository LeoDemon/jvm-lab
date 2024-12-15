package jvm.tech.demonlee.tmp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Favorites {

    private Map<Class<?>, Object> favorites = new HashMap<>();

    public <T> void putFavorite(Class<T> type, T instance) {
        favorites.put(Objects.requireNonNull(type), instance);
    }

    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
    }

    public static void main(String[] args) {
        Favorites t3 = new Favorites();
        Class clazz = Long.class;
        t3.putFavorite(String.class, "Hello, World!");
        t3.putFavorite(Integer.class, 0xcafebabe);
        t3.putFavorite(clazz, "abc");
        System.out.println("String: " + t3.getFavorite(String.class));
        System.out.println("Integer: " + t3.getFavorite(Integer.class));
        System.out.println("Long: " + t3.getFavorite(Long.class));
    }
}