package com.hodvidar.miscellaneous.livecoding.analysis;

import org.json.JSONException;

public class JSONExceptionEnvelope extends JSONException {
    public <T extends BasicBean> JSONExceptionEnvelope(final Class<T> type, final Throwable cause) {
        super(getMessageFromClass(type), cause);
    }

    public JSONExceptionEnvelope(final String message) {
        super(message);
    }

    private static String getMessageFromClass(final Class<?> type) {
        final String className = type == null ? "null" : type.getSimpleName();
        return "Error while parsing the JSON from a String for the class '"
                + className
                + "' The JSON is not correct.";
    }
}
