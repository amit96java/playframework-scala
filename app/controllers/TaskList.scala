package controllers

import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class TaskList @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def taskList = Action {
    Ok("This works!")
  }
}
