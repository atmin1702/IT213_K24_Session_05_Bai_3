# BÀI 3: Đọc hiểu & Dò lỗi - Lập trình phòng thủ chống ảo tưởng tham số

## 1. Phân tích các lỗi logic và điểm yếu:
1. **Lỗi NullPointerException:** Việc nhận 3 biến String thô tạo lỗ hổng lớn. Nếu AI trả về JSON rỗng hoặc null, khi gọi hàm `start.isAfter(end)` hoặc `"Deluxe".equalsIgnoreCase(roomType)` sẽ lập tức gây crash `NullPointerException`.
2. **Lỗi DateTimeParseException:** Không hề có bước xác thực độ hợp lệ (Validation) hoặc khối `try-catch` bảo vệ hàm `LocalDate.parse()`.
3. **Lập trình Fail-fast sai chỗ (Phá vỡ luồng hội thoại):** Ném `IllegalArgumentException` ở bên trong lõi phương thức @Tool sẽ khiến Spring AI Engine bị Exception chặn đứng (Ngắt luồng). Khi đó, người dùng sẽ nhận được mã lỗi HTTP 500. Đúng ra, AI cần nhận được **thông báo lỗi bằng Text** để nó "đọc, tự nhận ra mình sai hoặc thiếu thông tin, và quay lại chat hỏi người dùng".

## 2. Giải trình giải pháp validate dữ liệu phòng thủ:
- **Đóng gói DTO (Record):** Sử dụng `RoomCheckRequest` và `RoomCheckResponse`. Điều này giúp Spring AI tạo JSON Schema mô tả cấu trúc cho LLM chặt chẽ hơn thay vì truyền String thô.
- **Bắt lỗi an toàn (Fail-safe):** Dùng `try-catch` cho việc parse ngày tháng. Thay vì ném Exception, ta return về một Object Response có cờ `isSuccess = false` và trường `message` mô tả lỗi (VD: "Thiếu ngày, định dạng sai"). LLM được huấn luyện sẵn để xử lý kết quả Tool, khi thấy lỗi này, nó sẽ tự xin lỗi và hỏi lại khách hàng.

## 3. Mã nguồn Java sau khi refactor thành công:
Xem các file Java đính kèm: `RoomCheckRequest.java`, `RoomCheckResponse.java`, `BookingService.java`.
