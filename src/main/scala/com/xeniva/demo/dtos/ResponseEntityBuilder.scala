package com.xeniva.demo.dtos

import scala.jdk.CollectionConverters.*
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}

import java.text.{DateFormat, SimpleDateFormat}
import java.util
import java.util.Date

class ResponseEntityBuilder[Type] {

  private val dateFormatPattern = "yyyy-MM-dd hh:mm:ss a Z"
  private var _status: HttpStatus = HttpStatus.OK
  private var _message: String = ""
  private val _headers: HttpHeaders = new HttpHeaders()
  private var _body: Type = _
  private val objectMapper = new ObjectMapper()
    .registerModules(DefaultScalaModule)
  private val typeReference = new TypeReference[java.util.Map[String, Object]]() { }

  def status(status: HttpStatus): this.type = {
    _status = status

    this
  }

  def message(message: String): this.type = {
    _message = message

    this
  }

  def header(key: String, value: String): this.type = {
    _headers.add(key, value)

    this
  }

  def body(value: Type): this.type = {
    _body = value

    this
  }

  def toResponseEntity: ResponseEntity[Map[String, Object]] = {
    val dateFormat: DateFormat = new SimpleDateFormat(dateFormatPattern)
    val map = if (_body == null)
      new util.HashMap[String, Object]()
    else
      objectMapper.convertValue(_body, typeReference)

    map.put("time", dateFormat.format(new Date()))
    map.put("status", _status.value.asInstanceOf[Object])
    map.put("message", _message)

    ResponseEntity
      .status(_status)
      .headers(_headers)
      .body(map.asScala.toMap)
  }
}

object ResponseEntityBuilder {
  def create[Type] = new ResponseEntityBuilder[Type]
}
