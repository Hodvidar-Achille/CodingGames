package com.hodvidar.miscellaneous.livecoding.analysis;

// Maybe it would be better to rename this Exception class
// As it is only linked to the Bean method, it should be named something like BeanException
// AccessDeniedException gives the impression that it is related to a security issue.
// PopulateBeanException would be a better name.
public class AccessDeniedException extends Exception {

    public AccessDeniedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
