package com.xeniva.demo.services

import com.xeniva.demo.models.plain.File
import com.xeniva.demo.repositories.FileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

trait FileService {

  @Autowired
  private val fileRepository: FileRepository = null

  def retrieveFileByPersonId(personId: Long,
                             eTagHeaderValue: Option[String] = None): Option[File] = {
    fileRepository.retrieveImageByPersonId(personId)
  }
}

@Service
class FileServiceImpl extends FileService
