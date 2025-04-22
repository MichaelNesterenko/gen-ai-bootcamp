package com.epam.training.gen.ai.util;

import java.util.function.Consumer;
import java.util.function.Function;

public class Util {

    public static <T> T tap(T value, Consumer<T> tapper) {
        tapper.accept(value);
        return value;
    }

    public static <K, V> Function<K, V> asUnchecked(CheckedFunction<K, V> action) {
        return k -> {
            try {
                return action.apply(k);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        };
    }

    public static interface CheckedFunction<K, V> {
        V apply(K t) throws Exception;
    }

}
