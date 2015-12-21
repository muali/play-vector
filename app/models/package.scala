/**
  * Created by 123 on 21.12.2015.
  */
package object models {
  case class User(id: Option[Long], gitHubId: String)
  case class File(id: Option[Long], name: String, owner: Long, content: String)
}
