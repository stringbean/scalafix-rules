lazy val V = _root_.scalafix.sbt.BuildInfo

lazy val rulesCrossVersions = Seq(V.scala213, V.scala212)
lazy val scala3Version      = "3.3.4"

inThisBuild(
  List(
    organization         := "software.purpledragon",
    homepage             := Some(url("https://github.com/stringbean/scalafix-rules")),
    licenses             := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"),
    ),
    developers           := List(
      Developer("stringbean", "Michael Stringer", "@the_stringbean", url("https://github.com/stringbean")),
    ),
    organizationName     := "Michael Stringer",
    organizationHomepage := Some(url("https://purpledragon.software")),
    startYear            := Some(2023),
    scmInfo              := Some(
      ScmInfo(
        url("https://github.com/stringbean/scalafix-rules"),
        "scm:git:git@github.com:stringbean/scalafix-rules.git")),
    semanticdbEnabled    := true,
    semanticdbVersion    := scalafixSemanticdb.revision,
    versionScheme        := Some("semver-spec"),
  ),
)

lazy val `scalafix-rules` = (project in file("."))
  .aggregate(
    (rules.projectRefs ++
      input.projectRefs ++
      output.projectRefs ++
      tests.projectRefs) *,
  )
  .settings(
    publish / skip := true,
  )

lazy val rules = projectMatrix
  .settings(
    moduleName := "scalafix-rules",
    libraryDependencies ++= Seq(
      "ch.epfl.scala" %% "scalafix-core" % V.scalafixVersion,
    ),
    scalacOptions += "-Xsource:3",
    publishTo  := {
      val centralSnapshots = "https://central.sonatype.com/repository/maven-snapshots/"
      if (isSnapshot.value) Some("central-snapshots" at centralSnapshots)
      else localStaging.value
    },
  )
  .defaultAxes(VirtualAxis.jvm)
  .jvmPlatform(rulesCrossVersions)

lazy val input = projectMatrix
  .settings(
    publish / skip := true,
    libraryDependencies ++= Seq(
      "commons-lang"       % "commons-lang"  % "2.6",
      "org.apache.commons" % "commons-lang3" % "3.13.0",
    ),
  )
  .defaultAxes(VirtualAxis.jvm)
  .jvmPlatform(scalaVersions = rulesCrossVersions :+ scala3Version)
  .disablePlugins(HeaderPlugin, ScalafmtPlugin)

lazy val output = projectMatrix
  .settings(
    publish / skip := true,
    libraryDependencies ++= Seq(
      "commons-lang"       % "commons-lang"  % "2.6",
      "org.apache.commons" % "commons-lang3" % "3.13.0",
    ),
  )
  .defaultAxes(VirtualAxis.jvm)
  .jvmPlatform(scalaVersions = rulesCrossVersions :+ scala3Version)
  .disablePlugins(HeaderPlugin, ScalafmtPlugin)

lazy val testsAggregate = Project("tests", file("target/testsAggregate"))
  .aggregate(tests.projectRefs *)
  .settings(
    publish / skip := true,
  )

lazy val tests = projectMatrix
  .settings(
    publish / skip                         := true,
    scalafixTestkitOutputSourceDirectories :=
      TargetAxis
        .resolve(output, Compile / unmanagedSourceDirectories)
        .value,
    scalafixTestkitInputSourceDirectories  :=
      TargetAxis
        .resolve(input, Compile / unmanagedSourceDirectories)
        .value,
    scalafixTestkitInputClasspath          :=
      TargetAxis.resolve(input, Compile / fullClasspath).value,
    scalafixTestkitInputScalacOptions      :=
      TargetAxis.resolve(input, Compile / scalacOptions).value,
    scalafixTestkitInputScalaVersion       :=
      TargetAxis.resolve(input, Compile / scalaVersion).value,
  )
  .defaultAxes(
    (rulesCrossVersions.map(VirtualAxis.scalaABIVersion) :+ VirtualAxis.jvm) *,
  )
  .jvmPlatform(
    scalaVersions = Seq(V.scala212),
    axisValues = Seq(TargetAxis(scala3Version)),
    settings = Seq(),
  )
  .jvmPlatform(
    scalaVersions = Seq(V.scala213),
    axisValues = Seq(TargetAxis(V.scala213)),
    settings = Seq(),
  )
  .jvmPlatform(
    scalaVersions = Seq(V.scala212),
    axisValues = Seq(TargetAxis(V.scala212)),
    settings = Seq(),
  )
  .dependsOn(rules)
  .enablePlugins(ScalafixTestkitPlugin)

import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations.*

ThisBuild / releasePublishArtifactsAction := PgpKeys.publishSigned.value

ThisBuild / releaseCrossBuild := true
ThisBuild / releaseProcess    := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  releaseStepCommand("scalafmtCheckAll"),
  releaseStepCommand("headerCheckAll"),
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  releaseStepCommand("sonaRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges,
)
