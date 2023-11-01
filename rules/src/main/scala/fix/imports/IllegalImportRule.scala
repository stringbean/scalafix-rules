/*
 * Copyright 2023 Michael Stringer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fix.imports

import scalafix.lint.{Diagnostic, LintSeverity}

import scala.meta.{Importee, Importer}

object IllegalImportRule {
  private val PackageRule = "^([\\p{Alnum}._]+)\\.\\*$".r
  private val ClassRule = "^([\\p{Alnum}._]+)\\.([\\p{Alnum}]+)$".r

  def apply(rule: String, severity: LintSeverity): IllegalImportRule = {
    rule match {
      case PackageRule(packageName) => PackageIllegalImportRule(packageName, severity)
      case ClassRule(packageName, className) => ClassIllegalImportRule(packageName, className, severity)
      case _ => BareClassIllegalImportRule(rule, severity)
    }
  }
}

trait IllegalImportRule {
  def findMatching(importer: Importer, importee: Importee): Option[Diagnostic]
}

case class PackageIllegalImportRule(name: String, severity: LintSeverity) extends IllegalImportRule {
  override def findMatching(importer: Importer, importee: Importee): Option[Diagnostic] = {
    if ((importer.ref.toString() + ".").startsWith(name + ".")) {
      val message = importee match {
        case Importee.Wildcard() =>
          "import of illegal package"
        case _ =>
          "import of class from illegal package"
      }

      Some(
        Diagnostic("", message, importer.pos, s"Package $name has been marked as illegal", severity),
      )
    } else {
      None
    }
  }
}

case class ClassIllegalImportRule(packageName: String, className: String, severity: LintSeverity)
    extends IllegalImportRule {

  override def findMatching(importer: Importer, importee: Importee): Option[Diagnostic] = {
    if (importer.ref.toString() == packageName && importee.toString() == className) {
      Some(
        Diagnostic(
          "",
          "import of illegal class",
          importee.pos,
          s"Class $packageName.$className has been marked as illegal",
          severity),
      )
    } else {
      None
    }
  }
}

case class BareClassIllegalImportRule(className: String, severity: LintSeverity) extends IllegalImportRule {
  override def findMatching(importer: Importer, importee: Importee): Option[Diagnostic] = None
}
