package com.example.one_to_one_mapping.api_Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class ApiResponse {
    private Object status;
    private String message;
    private Object data;
}
