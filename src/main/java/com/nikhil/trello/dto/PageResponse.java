package com.nikhil.trello.dto;

import java.util.List;

public record PageResponse<T>(
        long total,
        int page,
        int pageSize,
        int totalPages,
        List<T> data
) {
}
