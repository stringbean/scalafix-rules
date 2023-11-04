# Scalafix rules for scalafix-rules

[![Build Status](https://img.shields.io/github/actions/workflow/status/stringbean/scalafix-rules/ci.yml?branch=main)](https://github.com/stringbean/scalafix-rules/actions/workflows/ci.yml)
[![Current Version](https://index.scala-lang.org/stringbean/scalafix-rules/scalafix-rules/latest.svg)](https://index.scala-lang.org/stringbean/scalafix-rules/scalafix-rules) 
[![Known Vulnerabilities](https://snyk.io/test/github/stringbean/scalafix-rules/badge.svg?targetFile=build.sbt)](https://snyk.io/test/github/stringbean/scalafix-rules?targetFile=build.sbt)
![Scala 2.12](https://img.shields.io/badge/Scala-2.12-blue)
![Scala 2.13](https://img.shields.io/badge/Scala-2.13-blue)
![Scala 3](https://img.shields.io/badge/Scala-3-blue)

## Installation

```scala
ThisBuild / scalafixDependencies += "software.purpledragon" && "scalafix-rules" % "<version>"
```

## Rules

### IllegalImports

Checks for usage of 'illegal' classes or packages. These could be non-portable code (such as
`com.sun.*`), legacy code from third-party libraries or code that will not work on certain
environments (such as `java.awt.*` on a headless server).

Inspired by the [`IllegalImportsChecker`][scalastyle-illegalimports] from Scalastyle and the
[`IllegalImport`][checkstyle-illegalimport] check from Checkstyle.

Example:

```text
Example.scala:5: error: [IllegalImports] import of class from illegal package
import com.sun.net.httpserver.HttpServer
       ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
```

#### Configutation

| Name      | Type           | Description                                                                                                                    |
|-----------|----------------|--------------------------------------------------------------------------------------------------------------------------------|
| `error`   | `List[String]` | List of imports to make illegal. These can be fully-qualified classes (`java.util.Vector`) or package wildcards (`com.sun.*`). |
| `warning` | `List[String]` | List of imports to warn on usage. These are in the same format as `error` but will not fail the buid.                          |

#### Defaults

```hocon
IllegalImports.imports = [
  "com.sun.*"
]
```

[checkstyle-illegalimport]: https://checkstyle.sourceforge.io/checks/imports/illegalimport.html#IllegalImport
[scalastyle-illegalimports]: http://www.scalastyle.org/rules-1.0.0.html#org_scalastyle_scalariform_IllegalImportsChecker

