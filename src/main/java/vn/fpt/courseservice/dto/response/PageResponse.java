package vn.fpt.courseservice.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private int currentPages;
    private int sizes;
    private int totalPages;
    private long totalElements;
    private List<T> data;
}
