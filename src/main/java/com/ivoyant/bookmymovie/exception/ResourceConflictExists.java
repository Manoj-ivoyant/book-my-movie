package com.ivoyant.bookmymovie.exception;

public class ResourceConflictExists extends BookMyMovieGlobalException {

    private static final long serialVersionUID = 1L;

    public ResourceConflictExists() {
        super("Resource Conflict Exists", GlobalErrorCode.ERROR_RESOURCE_CONFLICT_EXISTS);
    }

    public ResourceConflictExists(String message) {
        super(message, GlobalErrorCode.ERROR_RESOURCE_CONFLICT_EXISTS);
    }
}
