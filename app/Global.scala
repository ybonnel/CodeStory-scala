import play.api.Logger
import play.api.mvc.Handler
import play.GlobalSettings
import play.mvc.Http.RequestHeader
import play.mvc.Result


class Global extends GlobalSettings {
  override def onRouteRequest(request: RequestHeader): Handler = {
    Logger.info(request.toString)
    super.onRouteRequest(request)
  }

  override def onError(request: RequestHeader, t: Throwable): Result = {
    Logger.error("Error", t)
    super.onError(request, t)
  }

  override def onBadRequest(request: RequestHeader, error: String): Result = {
    Logger.warn("Bad request : " + error)
    super.onBadRequest(request, error)
  }
}
