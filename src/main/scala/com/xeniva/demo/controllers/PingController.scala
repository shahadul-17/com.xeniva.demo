package com.xeniva.demo.controllers

import com.xeniva.demo.dtos.{ResponseEntityBuilder, PingRequestDTO, PingResponseDTO}
import com.xeniva.demo.services.PingService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = Array("/v{version}/ping"))
class PingController {

  @Autowired
  private val pingService: PingService = null

  @GetMapping
  def ping(@PathVariable(required = false) version: String,
           @RequestHeader(required = false) forwardedFor: String,
           request: HttpServletRequest): ResponseEntity[Map[String, Object]] = {
    val ipAddress = Option(forwardedFor) match
      case Some(ipAddress) if !ipAddress.isBlank => ipAddress
      case _ => request.getRemoteHost
    val requestDTO = PingRequestDTO(version, ipAddress.trim)
    val responseDTO = pingService.ping(requestDTO)

    ResponseEntityBuilder.create
      .status(HttpStatus.OK)
      .message("Successfully processed ping request.")
      .body(responseDTO)
      .toResponseEntity
  }
}
