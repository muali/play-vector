package dao

import javax.inject.Inject

import models.{User, File}
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class FilesDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends UsersComponent
with HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  class Files(tag: Tag) extends Table[File](tag, "FILE") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def owner = column[Long]("OWNER")
    def content = column[String]("CONTENT")

    def * = (id.?, name, owner, content) <> (File.tupled, File.unapply)
  }

  val files = TableQuery[Files]

  def insert(file: File) = {
    db.run(files += file).map(_ => ())
  }

  def update(file: File) = {
    db.run(files.filter(_.id === file.id).map(_.content).update(file.content)).map(_ => ())
  }

  def getByNameAndOwner(name: String, owner: Long): Future[Option[File]] = {
    db.run(files.filter{f: Files => f.name === name && f.owner === owner}.result.headOption)
  }

  def getOrCreate(name: String, userId: Long): Future[File] = {
    getByNameAndOwner(name, userId).flatMap{
      case Some(file) => Future(file)
      case None =>
        val file = File(None, name, userId, "")
        db.run(files += file).flatMap{ r =>
          getByNameAndOwner(name, userId).map(_.get)
        }
    }
  }

  def getFilesByOwner(userId: Long): Future[List[File]] = {
    db.run(files.filter{_.owner === userId}.result).flatMap{f: Seq[File] => Future(f.toList)}
  }

}
