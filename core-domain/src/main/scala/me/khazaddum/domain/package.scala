package me.khazaddum

import cats.data.ValidatedNel

package object domain {

  type Validation[A] = ValidatedNel[String, A]

}
