package client

import client.modeles.PrimitiveModel
import client.tools.Toolbar
import org.scalajs.dom

import scala.scalajs.js._
import scala.scalajs.js.annotation.JSExport

/**
 * Created by Moskvitin Maxim.
 */

object Client extends JSApp{
  val canvas = new Canvas(dom.document.getElementById("DrawingPanel").asInstanceOf[dom.html.Canvas])
  val toolbar = new Toolbar(dom.document.getElementById("Toolbar").asInstanceOf[dom.html.Div])
  var filePrimitiveModel = new PrimitiveModel(Map())
  var tempPrimitiveModel = new PrimitiveModel(Map())

  @JSExport
  def main(): Unit = {
  }
}
