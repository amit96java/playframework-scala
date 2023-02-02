package controllers

import auth.AuthAction
import play.api.libs.json.Json
import play.api.mvc._
import repositories.DataRepository

import javax.inject._
import scala.collection.mutable

case class TodoListItem(id: Long, description: String, isItDone: Boolean)

case class NewTodoListItem(description: String)
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents,
                               dataRepository: DataRepository,
                               authAction: AuthAction) extends AbstractController(cc) {
  private val todoList = new mutable.ListBuffer[TodoListItem]()
  todoList += TodoListItem(1, "test", true)
  todoList += TodoListItem(2, "some other value", false)
  implicit val todoListJson = Json.format[TodoListItem]
  implicit val newTodoListJson = Json.format[NewTodoListItem]
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def explore() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.explore())
  }

  def tutorial() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.tutorial())
  }


  def hello() = Action {
    Ok(Json.toJson(todoList))
  }

  def getById(id: String) = Action {
    id match {
      case "amit" => Ok(id)
      case _ => BadRequest
    }
  }

  def addNewItem() = Action { implicit request =>
    val content = request.body
    val headers = request.headers.get("Authorization")
//    println(s"headers is ${headers}")
    println(s"content is ${content}")
    val jsonObject = content.asJson
    val todoListItem: Option[NewTodoListItem] =
      jsonObject.flatMap(
        Json.fromJson[NewTodoListItem](_).asOpt
      )

    todoListItem match {
      case Some(newItem) =>
        val nextId = todoList.map(_.id).max + 1
        val toBeAdded = TodoListItem(nextId, newItem.description, false)
        todoList += toBeAdded
        Created(Json.toJson(toBeAdded))
      case None =>
        BadRequest
    }
  }

  def ping = authAction {
    Ok("Hello, Scala!")
  }

  // NEW - Get a single post
  def getPost(postId: Int) = authAction { implicit request =>
    dataRepository.getPost(postId) map { post =>
      // If the post was found, return a 200 with the post data as JSON
      Ok(Json.toJson(post))
    } getOrElse NotFound    // otherwise, return Not Found
  }

  // NEW - Get comments for a post
  def getComments(postId: Int) = authAction { implicit request =>
    // Simply return 200 OK with the comment data as JSON.
    Ok(Json.toJson(dataRepository.getComments(postId)))
  }
  
}
