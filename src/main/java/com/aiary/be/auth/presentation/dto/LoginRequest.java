package com.aiary.be.auth.presentation.dto;

public record LoginRequest(
    String email,
    String password
){};
