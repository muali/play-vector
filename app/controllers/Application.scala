package controllers

import org.pac4j.core.profile.CommonProfile
import org.pac4j.play.scala.Security
import play.api._
import play.api.mvc._

class Application extends Controller with Security[CommonProfile] {

  def index = Action {
    Ok(views.html.index())
  }

  def facebookLogin = Action {
    Ok(views.html.index())
  }

  def gitHubLogin = RequiresAuthentication("GitHubClient") {profile =>
    Action { request =>
      Redirect("/app")
    }
  }

  def app = RequiresAuthentication("GitHubClient") {profile =>
    Action { request =>
      Ok(views.html.protectedIndex())
    }
  }

}
