package client.tools

import org.scalajs.dom

/**
 * Created by Moskvitin Maxim.
 */
class Tool {
  def becomeActive() = {}
  def becomeInactive() = {}
}

case class DomTool(button: dom.html.Button, tool: Tool)

object DomTool {
  def fromButton(button: dom.html.Button): DomTool = button.id match {
    case "LineTool" => DomTool(button, LineTool)
    case _ => DomTool(button, new Tool)
  }
}