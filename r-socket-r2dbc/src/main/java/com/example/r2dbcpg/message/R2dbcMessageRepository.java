package com.example.r2dbcpg.message;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface R2dbcMessageRepository extends R2dbcRepository<Message, UUID> { }
