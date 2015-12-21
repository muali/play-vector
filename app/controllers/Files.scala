package controllers

import com.google.inject.Inject
import dao.{FilesDAO, UsersDAO}
import org.pac4j.core.profile.CommonProfile
import org.pac4j.play.scala.Security
import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by Moskvitin Maxim.
 */
class Files @Inject() (usersDAO: UsersDAO, filesDAO: FilesDAO) extends Controller with Security[CommonProfile]{
  def files = RequiresAuthentication("GitHubClient") { profile =>
    Action.async {request =>
      val gitHubId = profile.getId()
      for {
        user <- usersDAO.getOrCreate(gitHubId)
        Some(userId) = user.id
        files <- filesDAO.getFilesByOwner(userId)
        fileNames = files.map(_.name)
      } yield Ok(Json.toJson(fileNames))
    }
  }

  def createFile(name: String) = RequiresAuthentication("GitHubClient") { profile =>
    Action.async { request =>
      val gitHubId = profile.getId()
      for {
        user <- usersDAO.getOrCreate(gitHubId)
        Some(userId) = user.id
        _ <- filesDAO.getOrCreate(name, userId)
      } yield Ok("")
    }
  }

}
