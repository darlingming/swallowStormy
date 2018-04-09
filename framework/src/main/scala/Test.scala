class Test {

}

class Calculator(brand: String) {
  /** * A constructor.   */
  val color: String = if (brand == "TI") {
    "blue"
  } else if (brand == "HP") {
    "black"
  } else {
    "white"
  }
}

// An instance method.  def add(m: Int, n: Int): Int = m + n}


trait Car {
  var brand: String
}

trait Shiny {
  val shineRefraction: Int
}


trait Cache[K, V] {
  def get(key: K): V

  def put(key: K, value: V)
  def delete(key: K)
}

class ApplyTest{
  def apply: ApplyTest = new ApplyTest()
}

object ApplyTest{
  def apply: ApplyTest = new ApplyTest()

  def unapply(arg: ApplyTest): Option[Int] = null

}

