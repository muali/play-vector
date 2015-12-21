package client.tools

import client.Client
import client.primitives.{Line, Point}
import org.scalajs.dom.raw.MouseEvent

/**
 * Created by Moskvitin Maxim.
 */
object LineTool extends Tool {

  var start: Option[Point] = None
  var idStart: String = ""
  var idEnd: String = ""
  var idLine: String = ""

  override def becomeActive() = {
    Client.canvas.element.onmousedown = {e: MouseEvent => onMouseDown((e.pageX, e.pageY))}
    Client.canvas.element.onmousemove = {e: MouseEvent => onMouseMove((e.pageX, e.pageY))}
    Client.canvas.element.onmouseup = {e: MouseEvent => onMouseUp((e.pageX, e.pageY))}
  }

  def onMouseDown(pageCoordinates: (Double, Double)) = {
    val (x, y) = Client.canvas.toFileCoordinates(pageCoordinates)
    val point = Point(x, y)
    start = Some(point)
    idStart = point.id
    Client.tempPrimitiveModel = Client.tempPrimitiveModel.insert(point)
  }

  def onMouseMove(pageCoordinates: (Double, Double)) = start match {
    case Some(startPoint) => {
      val (x, y) = Client.canvas.toFileCoordinates(pageCoordinates)
      val Some(startPoint) = start
      val endPoint = Point(x, y)
      if (idEnd != "") {
        Client.tempPrimitiveModel = Client.tempPrimitiveModel.erase(idEnd)
        Client.tempPrimitiveModel = Client.tempPrimitiveModel.erase(idLine)
      }

      val line = Line(startPoint, endPoint)
      Client.tempPrimitiveModel = Client.tempPrimitiveModel.insert(endPoint)
      Client.tempPrimitiveModel = Client.tempPrimitiveModel.insert(line)

      idEnd = endPoint.id
      idLine = line.id

      Client.canvas.updateModels()
    }
    case _ =>
  }

  def onMouseUp(pageCoordinates: (Double, Double)) = {
    start match {
      case Some(startPoint) => {
        val (x, y) = Client.canvas.toFileCoordinates(pageCoordinates)

        Client.tempPrimitiveModel = Client.tempPrimitiveModel.erase(startPoint.id)
        if (idEnd != "") {
          Client.tempPrimitiveModel = Client.tempPrimitiveModel.erase(idEnd)
          Client.tempPrimitiveModel = Client.tempPrimitiveModel.erase(idLine)
        }

        val endPoint = Point(x, y)
        val line = Line(startPoint, endPoint)

        Client.filePrimitiveModel = Client.filePrimitiveModel.insert(startPoint)
        Client.filePrimitiveModel = Client.filePrimitiveModel.insert(endPoint)
        Client.filePrimitiveModel = Client.filePrimitiveModel.insert(line)

        start = None
        idEnd = ""
        idLine = ""

        Client.canvas.updateModels()
      }
      case _ =>
    }
  }
}
