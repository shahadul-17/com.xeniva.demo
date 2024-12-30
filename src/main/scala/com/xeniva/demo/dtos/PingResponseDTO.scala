package com.xeniva.demo.dtos

case class PingResponseDTO(
  time: String,
  status: Int,
  message: String,
  version: String,
  virtualThread: Boolean,
  threadName: String,
  ipAddress: String)
