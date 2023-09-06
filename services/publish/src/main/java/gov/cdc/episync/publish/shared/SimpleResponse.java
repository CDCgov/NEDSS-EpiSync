package gov.cdc.episync.publish.shared;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Getter @Setter @EqualsAndHashCode @ToString
public class SimpleResponse<S, T> {
    S code;
    T message;

    public static <S, T> SimpleResponse<S, T> of(S first, T second) {
        return new SimpleResponse<>(first, second);
    }

    private SimpleResponse(S code, T message) {
        this.code = code;
        this.message = message;
    }

    public static <S, T> Collector<SimpleResponse<S, T>, ?, Map<S, T>> toMap() {
        return Collectors.toMap(SimpleResponse::getCode, SimpleResponse::getMessage);
    }
}
