import play.api.Logger
import play.api.mvc.Handler
import play.GlobalSettings
import play.mvc.Http.RequestHeader


class Global extends GlobalSettings {
  override def onRouteRequest(request: RequestHeader): Handler = {
    Logger.info(request.toString);
    super.onRouteRequest(request)
  }
}
