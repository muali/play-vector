package controllers

import org.pac4j.core.profile.CommonProfile
import org.pac4j.play.scala.Security
import play.api.mvc.{Action, Controller}

/**
 * Created by Moskvitin Maxim.
 */
class Files extends Controller with Security[CommonProfile]{
//  def files = RequiresAuthentication("GitHubClient") { profile =>
//    Action {request =>
//      Ok()
//    }
//  }
}
