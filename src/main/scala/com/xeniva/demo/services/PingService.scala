package com.xeniva.demo.services

import com.xeniva.demo.dtos.{PingRequestDTO, PingResponseDTO}
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

import java.text.{DateFormat, SimpleDateFormat}
import java.util.Date

trait PingService {

  private val dateFormatPattern = "yyyy-MM-dd HH:mm:ss a Z"

  def ping(request: PingRequestDTO): PingResponseDTO = {
    val currentTime = new Date()
    val dateFormat: DateFormat = new SimpleDateFormat(dateFormatPattern)

    PingResponseDTO(
      dateFormat.format(currentTime),
      HttpStatus.OK.value(),
      "Successfully processed ping request.",
      request.version,
      Thread.currentThread().isVirtual,
      Thread.currentThread().getName,
      request.ipAddress,
    )
  }
}

@Service
class PingServiceImpl extends PingService
