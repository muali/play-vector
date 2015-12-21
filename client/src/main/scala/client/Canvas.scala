package client

import client.primitives.Primitive
import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport

/**
 * Created by Moskvitin Maxim.
 */
@JSExport
class Canvas(val element : dom.html.Canvas) {
  val ctx = element.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  def toFileCoordinates(pageCoordinates: (Double, Double)) = {
    val (pageX, pageY) = pageCoordinates
    val bounds = element.getBoundingClientRect()
    (pageX - bounds.left, pageY - bounds.top)
  }

  def updateModels() = {
    println("Update")
    val bounds = element.getBoundingClientRect()
    ctx.clearRect(0, 0, bounds.right, bounds.bottom)
    (Client.filePrimitiveModel.primitives ++ Client.tempPrimitiveModel.primitives).
      foreach{p: Primitive => {println(p.id); p.draw(ctx)}}
  }

}
