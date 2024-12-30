package com.xeniva.demo.services

import com.xeniva.demo.dtos.{PingRequestDTO, PingResponseDTO}
import org.springframework.stereotype.Service

trait PingService {
  def ping(request: PingRequestDTO): PingResponseDTO = {
    PingResponseDTO(
      request.version,
      Thread.currentThread().isVirtual,
      Thread.currentThread().getName,
      request.ipAddress,
    )
  }
}

@Service
class PingServiceImpl extends PingService
