package com.rhotels.service;

import com.rhotels.dto.RoomCheckRequest;
import com.rhotels.dto.RoomCheckResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class BookingService {
 
    @Tool(description = "Kiểm tra phòng trống khách sạn")
    public RoomCheckResponse getRoomAvailability(RoomCheckRequest request) {
        // 1. Kiểm tra Null
        if (request.checkIn() == null || request.checkOut() == null || request.roomType() == null) {
            return new RoomCheckResponse(false, false, 0.0, 
                "HỆ THỐNG BÁO LỖI: Bị thiếu ngày nhận, ngày trả hoặc loại phòng. Bạn hãy hỏi lại người dùng để bổ sung.");
        }

        LocalDate start;
        LocalDate end;
        
        // 2. Kiểm tra định dạng ngày an toàn
        try {
            start = LocalDate.parse(request.checkIn());
            end = LocalDate.parse(request.checkOut());
        } catch (DateTimeParseException e) {
            return new RoomCheckResponse(false, false, 0.0, 
                "HỆ THỐNG BÁO LỖI: Định dạng ngày tháng không hợp lệ. Phải tuân thủ chuẩn yyyy-MM-dd.");
        }
 
        // 3. Kiểm tra logic nghiệp vụ
        if (start.isAfter(end)) {
            return new RoomCheckResponse(false, false, 0.0, 
                "HỆ THỐNG BÁO LỖI: Ngày nhận phòng không thể nằm sau ngày trả phòng.");
        }
 
        // 4. Logic giả lập truy vấn database
        boolean isAvailable = "Deluxe".equalsIgnoreCase(request.roomType());
        if (isAvailable) {
            return new RoomCheckResponse(true, true, 1500000.0, "Tra cứu thành công: Đang còn phòng.");
        } else {
            return new RoomCheckResponse(true, false, 0.0, "Tra cứu thành công: Đã hết phòng loại này.");
        }
    }
}
