package com.xeniva.demo.controllers

import com.xeniva.demo.dtos.{ResponseEntityBuilder, FileDTO}
import com.xeniva.demo.services.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = Array("/v{version}/file"))
class FileController {

  @Autowired
  private val fileService: FileService = null

  @GetMapping(value = Array("{personId}"))
  def retrieveFile(
                    @PathVariable(required = true) version: String,
                    @PathVariable(required = true) personId: Long): ResponseEntity[Map[String, Object]] = {
    val file = fileService.retrieveFileByPersonId(personId)

    FileDTO(file) match
      case Some(fileDto) => ResponseEntityBuilder.create
        .status(HttpStatus.OK)
        .message("Successfully retrieved file.")
        .body(fileDto)
        .toResponseEntity
      case _ => ResponseEntityBuilder.create
        .status(HttpStatus.NOT_FOUND)
        .message("Requested file was not found.")
        .toResponseEntity
  }
}
