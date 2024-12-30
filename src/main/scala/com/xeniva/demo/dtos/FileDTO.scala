package com.xeniva.demo.dtos

import com.xeniva.demo.models.plain.File

import java.util.Date

case class FileDTO(
  fileId: Long,
  version: Long,
  size: Long,
  mimeType: Option[String],
  fileName: String,
  createdAt: Date,
  updatedAt: Option[Date],
)

object FileDTO {

  def apply(file: File): FileDTO = {
    FileDTO(
      file.fileId,
      file.version,
      file.size,
      file.mimeType,
      file.fileName,
      file.createdAt,
      file.updatedAt)
  }

  def apply(fileOption: Option[File]): Option[FileDTO] = {
    fileOption.map(FileDTO(_))
  }
}
