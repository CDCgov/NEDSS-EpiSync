package gov.cdc.episync.publish.config;

import gov.cdc.episync.framework.EpisyncDocumentType.Type;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TypeEnumConverter implements Converter<String, Type> {
    @Override
    public final Type convert(String value) {
        try {
            return Type.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return Type.NONE;
        }
    }
}