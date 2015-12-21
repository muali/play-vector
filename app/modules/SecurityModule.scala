package modules

import com.google.inject.AbstractModule
import org.pac4j.core.client.Clients
import org.pac4j.core.config.Config
import org.pac4j.oauth.client.GitHubClient
import org.pac4j.play.CallbackController
import org.pac4j.play.http.DefaultHttpActionAdapter
import play.api.{Environment, Configuration}

/**
 * Created by Moskvitin Maxim.
 */
class SecurityModule(environment: Environment, configuration: Configuration) extends AbstractModule {
  override def configure() = {
    val baseUrl = configuration.getString("baseUrl").get

    val gitHubClient = new GitHubClient("43e685e4a00582b9c4e0", "1cc3a02be8f8c44274a3869970914ff7b31a4751")
    gitHubClient.setScope("")

    val clients = new Clients(baseUrl + "/callback", gitHubClient)

    val config = new Config(clients)
    config.setHttpActionAdapter(new DefaultHttpActionAdapter)
    bind(classOf[Config]).toInstance(config)

    val callbackController = new CallbackController
    callbackController.setDefaultUrl("/")
    bind(classOf[CallbackController]).toInstance(callbackController)
  }
}
