addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.14.5")

// code style
addSbtPlugin("com.github.sbt" % "sbt-header"   % "5.11.0")
addSbtPlugin("org.scalameta"  % "sbt-scalafmt" % "2.5.6")

// publishing/building
addSbtPlugin("com.eed3si9n"   % "sbt-projectmatrix" % "0.11.0")
addSbtPlugin("com.github.sbt" % "sbt-pgp"           % "2.3.1")
addSbtPlugin("com.github.sbt" % "sbt-release"       % "1.4.0")
