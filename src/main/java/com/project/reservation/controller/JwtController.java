package com.project.reservation.controller;

import com.project.reservation.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class JwtController {

    private final JwtUtil jwtUtil;

    @GetMapping("/token")
    public ResponseEntity<String> getToken(@RequestParam Long userId) {
        return ResponseEntity.ok()
                .body(jwtUtil.generateToken(userId));
    }
}
