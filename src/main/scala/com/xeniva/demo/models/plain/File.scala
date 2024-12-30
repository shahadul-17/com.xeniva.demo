package com.xeniva.demo.models.plain

import java.util.Date

case class File(
  fileId: Long,
  version: Long,
  size: Long,
  mimeType: Option[String],
  fileName: String,
  createdAt: Date,
  updatedAt: Option[Date],
)
