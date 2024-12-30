package com.xeniva.demo.repositories

import com.xeniva.demo.models.plain.File
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

import java.sql.ResultSet
import java.util.Date

trait FileRepository {

  @Autowired
  private val jdbcTemplate: JdbcTemplate = null

  def retrieveImageByPersonId(personId: Long): Option[File] = {
    jdbcTemplate.query(
      """
        |SELECT img.entity_id, img.version, img.size, img.mimetype, img.raw_content_filename, person.created, person.modified
        |FROM onp_crm_person person
        |         JOIN origo_image img on person.image_id = img.entity_id
        |WHERE person.entity_id = ?
        |""".stripMargin, (resultSet: ResultSet) => {
        if (resultSet.next()) {
          val fileId = resultSet.getLong("entity_id")
          val version = resultSet.getLong("version")
          val size = resultSet.getLong("size")
          val mimeType = Option(resultSet.getString("mimetype")).filterNot(_ => resultSet.wasNull())
          val fileName = resultSet.getString("raw_content_filename")
          val createdAt = new Date(resultSet.getTimestamp("created").getTime)
          val updatedAt = Option(resultSet.getTimestamp("modified")).map(timestamp => new Date(timestamp.getTime))

          Some(File(fileId, version, size, mimeType, fileName, createdAt, updatedAt))
        } else {
          None
        }
      }, Long.box(personId))
  }
}

@Repository
@Primary
class FileRepositoryImpl extends FileRepository
