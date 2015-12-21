package client

import java.util.UUID

import org.scalajs.dom

/**
 * Created by Moskvitin Maxim.
 */
package object primitives {

  trait Primitive {
    val id = UUID.randomUUID().toString
    def draw(ctx: dom.CanvasRenderingContext2D): Unit
  }

  case class Point(x: Double, y: Double) extends Primitive {
    val radii = 2
    val style = "#0F0FFF"
    def draw(ctx: dom.CanvasRenderingContext2D): Unit = {
      ctx.beginPath()
      ctx.arc(x, y, radii, 0, Math.PI * 2)
      ctx.fillStyle = style
      ctx.fill()
    }
  }

  case class Line(begin: Point, end: Point) extends Primitive {
    def draw(ctx: dom.CanvasRenderingContext2D): Unit = {
      ctx.beginPath()
      ctx.moveTo(begin.x, begin.y)
      ctx.lineTo(end.x, end.y)
      ctx.stroke()
    }
  }


}
