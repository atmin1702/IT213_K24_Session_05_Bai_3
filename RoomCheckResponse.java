package com.rhotels.dto;

public record RoomCheckResponse(
        boolean isSuccess,
        boolean isAvailable,
        double pricePerNight,
        String message
) {}
