package com.xeniva.demo.controllers

import com.xeniva.demo.dtos.PingResponseDTO
import com.xeniva.demo.dtos.PingRequestDTO
import com.xeniva.demo.services.PingService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, RequestHeader, RequestMapping, RestController}

import java.text.{DateFormat, SimpleDateFormat}
import java.util.Date

@RestController
@RequestMapping(value = Array("/v{version}/ping"))
class PingController(pingService: PingService) {

  @GetMapping
  def ping(@PathVariable(required = false) version: String,
           @RequestHeader(required = false) forwardedFor: String,
           request: HttpServletRequest): PingResponseDTO = {
    val ipAddress = Option(forwardedFor) match
      case Some(ipAddress) if !ipAddress.isBlank => ipAddress
      case _ => request.getRemoteHost
    val requestDTO = PingRequestDTO(version, ipAddress.trim)

    pingService.ping(requestDTO)
  }
}
