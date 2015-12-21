package dao

import javax.inject.{Inject, Singleton}

import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by Moskvitin Maxim.
 */


trait UsersComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  class Users(tag: Tag) extends Table[User](tag, "USER") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def gitHubId = column[String]("GITHUB_ID")
    def * = (id.?, gitHubId) <> (User.tupled, User.unapply)
  }
}

@Singleton
class UsersDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends UsersComponent
with HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  val users = TableQuery[Users]

  /** Insert a new user */
  def insert(user: User): Future[Unit] =
    db.run(users += user).map(_ => ())

  def userByGitHubId(gitHubId: String): Future[Option[User]] = {
    db.run(users.filter(_.gitHubId === gitHubId).result.headOption)
  }

  def getOrCreate(gitHubId: String): Future[User] = {
    userByGitHubId(gitHubId).flatMap {
      case Some(user) => Future(user)
      case None =>
        val user = User(None, gitHubId)
        db.run(users += user).flatMap { r =>
          userByGitHubId(gitHubId).map(_.get)
        }
    }
  }
}
