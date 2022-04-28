package com.wp.driveable.driveableapi.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse implements Response {
    private String message;
}
