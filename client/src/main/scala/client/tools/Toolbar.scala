package client.tools

import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport

/**
 * Created by Moskvitin Maxim.
 */
@JSExport
class Toolbar(element: dom.html.Div) {

  println("Init toolbar")

  val buttons = element.getElementsByTagName("button")
  val tools = List.tabulate(buttons.length)(i => DomTool.fromButton(buttons(i).asInstanceOf[dom.html.Button]))

  tools.foreach(domTool => domTool.button.onclick = {_: dom.MouseEvent => changeActiveTool(domTool.tool)})

  var activeTool: Tool = SelectionTool

  def changeActiveTool(newTool: Tool) = {
    println("change active tool")
    activeTool.becomeInactive()
    activeTool = newTool
    activeTool.becomeActive()
  }
}
