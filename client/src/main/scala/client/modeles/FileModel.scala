package client.modeles

import client.primitives.Primitive

/**
 * Created by Moskvitin Maxim.
 */

class PrimitiveModel(val primitiveMap: Map[String, Primitive]) {
  def primitives = primitiveMap.values.toList
  def insert(primitive: Primitive): PrimitiveModel = new PrimitiveModel(primitiveMap + ((primitive.id, primitive)))
  def erase(id: String): PrimitiveModel = new PrimitiveModel(primitiveMap - id)
}