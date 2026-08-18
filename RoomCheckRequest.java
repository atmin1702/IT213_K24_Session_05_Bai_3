package com.rhotels.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record RoomCheckRequest(
        @JsonPropertyDescription("Ngày nhận phòng định dạng yyyy-MM-dd") String checkIn,
        @JsonPropertyDescription("Ngày trả phòng định dạng yyyy-MM-dd") String checkOut,
        @JsonPropertyDescription("Loại phòng (Deluxe, Standard, vv)") String roomType
) {}
