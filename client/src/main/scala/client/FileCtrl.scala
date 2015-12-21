package client

import org.scalajs.dom
import scalajs.js

import scala.scalajs.js.JSON

/**
  * Created by Maxim Moskvitin
  */
class FileCtrl(div: dom.html.Div) {

  val inputForm = dom.document.createElement("form").asInstanceOf[dom.html.Form]
  val inputElement = dom.document.createElement("input").asInstanceOf[dom.html.Input]
  inputElement.placeholder = "Enter file name"
  inputForm.appendChild(inputElement)
  inputForm.onsubmit = {e: dom.Event => newFile(inputElement.value); false}

  val fileList = dom.document.createElement("ul").asInstanceOf[dom.html.UList]

  div.appendChild(inputForm)
  div.appendChild(fileList)

  def newFile(name: String): Unit = {
    println(name)
    val request = new dom.XMLHttpRequest
    request.open("GET", "/file/create/" + name, async = true)
    request.onreadystatechange = { _: dom.Event =>
      if (request.readyState == 4 && request.status == 200) {
        updateFileList()
      }
    }
    request.send()
  }

  def updateFileList() = {
    val request = new dom.XMLHttpRequest
    request.open("GET", "/files", async = true)
    request.onload = {_: dom.Event => updateFileListCallback(request.responseText)}
    request.send()
  }

  def updateFileListCallback(data: String) = {
    val files = JSON.parse(data).asInstanceOf[js.Array[String]]
    clear()
    files.foreach(insertFileElement)
  }

  def clear() = {
    while (fileList.hasChildNodes()) {
      fileList.removeChild(fileList.firstChild)
    }
  }

  def insertFileElement(name: String) = {
    val element = dom.document.createElement("li")
    element.innerHTML = "<a href=\"\">" + name + "</a>"
    fileList.appendChild(element)
  }
}
