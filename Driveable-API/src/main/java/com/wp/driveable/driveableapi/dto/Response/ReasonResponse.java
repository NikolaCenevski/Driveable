package com.wp.driveable.driveableapi.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReasonResponse {
    private String name;
    private String surname;
    private String description;
}
