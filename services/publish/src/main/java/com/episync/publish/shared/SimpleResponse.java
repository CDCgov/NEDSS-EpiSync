package com.episync.publish.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class SimpleResponse {
    int code;
    String message;
}
