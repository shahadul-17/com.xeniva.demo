package com.xeniva.demo.dtos

case class PingResponseDTO(
  version: String,
  virtualThread: Boolean,
  threadName: String,
  ipAddress: String,
)
