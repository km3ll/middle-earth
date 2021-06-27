import cats.data.{ EitherT, Kleisli }
import scala.concurrent.Future

package object cqrs {

  type Response[D] = EitherT[Future, String, D]

  type CommandK[C, D] = Kleisli[Response, C, D]

  type QueryK[C, D] = Kleisli[Response, C, D]

}
