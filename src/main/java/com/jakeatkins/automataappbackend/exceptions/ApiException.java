package com.jakeatkins.automataappbackend.exceptions;

import java.time.*;

public record ApiException(String error, String message, int status, Instant timestamp ) {
    
}
