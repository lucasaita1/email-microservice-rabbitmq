package dev.lucas.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {

    private UUID userId;
    private String to;
    private String subject;
    private String body;
}