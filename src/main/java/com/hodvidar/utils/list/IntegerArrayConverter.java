package com.hodvidar.utils.list;

import com.hodvidar.utils.regex.NumberExtractor;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class IntegerArrayConverter extends SimpleArgumentConverter {
    @Override
    protected Object convert(final Object source, final Class<?> targetType) throws ArgumentConversionException {
        if (source instanceof String) {
            return NumberExtractor.getArray((String) source);
        }
        throw new IllegalArgumentException("Invalid input format");
    }
}
