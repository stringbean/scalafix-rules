addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.12.1")

// code style
addSbtPlugin("de.heikoseeberger" % "sbt-header"   % "5.10.0")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt" % "2.5.2")

// publishing/building
addSbtPlugin("com.eed3si9n"   % "sbt-projectmatrix" % "0.10.0")
addSbtPlugin("com.github.sbt" % "sbt-pgp"           % "2.2.1")
addSbtPlugin("com.github.sbt" % "sbt-release"       % "1.4.0")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype"      % "3.10.0")
