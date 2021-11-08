package realize;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MyOptional<T> {
    private T data;
    private static final MyOptional<?> EMPTY = new MyOptional<>(null);

    private MyOptional(T data) {
        this.data = data;
    }

    public boolean isPresent() {
        return (data != null);
    }

     public boolean isEmpty() {
        return (data == null);
    }

    public T get() {
        if (data == null) {
            throw new NoSuchElementException("No such element");
        }
        return data;
    }

    public static <U> MyOptional<U> of(U obj) {
        if (obj == null) {
            throw new NullPointerException("Seated null");
        }
        MyOptional<U> can = new MyOptional<>(obj);
        return can;
    }

    public T orElse(T other) {
        if (data != null) {
            return data;
        } else return other;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (data != null) {
            consumer.accept(data);
        }
    }

    public T orElseGet(Supplier<? extends T> supplier) {
        return data != null ? data : supplier.get();
    }

    public T orElseThrow() {
        if (data == null) {
            throw new NoSuchElementException("No value present");
        }
        return data;
    }

    public static <T> MyOptional<T> empty() {
        @SuppressWarnings("unchecked")
        MyOptional<T> t = (MyOptional<T>) EMPTY;
        return t;
    }

    public MyOptional<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(data) ? this : empty();
        }
    }

    public static <T> MyOptional<T> ofNullable(T data) {
        return data == null ? (MyOptional<T>) EMPTY
                : new MyOptional<>(data);
    }

    public <U> MyOptional<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return MyOptional.ofNullable(mapper.apply(data));
        }
    }

    public <U> MyOptional<U> flatMap(Function<? super T, ? extends MyOptional<? extends U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            @SuppressWarnings("unchecked")
            MyOptional<U> r = (MyOptional<U>) mapper.apply(data);
            return Objects.requireNonNull(r);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyOptional<?> that = (MyOptional<?>) o;

        return data != null ? data.equals(that.data) : that.data == null;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
