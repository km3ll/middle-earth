import cats.data.ValidatedNel

package object frdm {

  type Validation[A] = ValidatedNel[String, A]

}
