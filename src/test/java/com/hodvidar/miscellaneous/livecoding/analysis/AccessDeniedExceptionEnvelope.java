package com.hodvidar.miscellaneous.livecoding.analysis;

public class AccessDeniedExceptionEnvelope extends AccessDeniedException {


    public <T extends BasicBean> AccessDeniedExceptionEnvelope(final Class<T> type, final Throwable cause) {
        super(getMessageFromClass(type), cause);
    }

    private static String getMessageFromClass(final Class<?> type) {
        final String className = type == null ? "null" : type.getSimpleName();
        return "Error while populating class '"
                + className
                + "' from JSON. The JSON is not correct for this Beans.";
    }

}