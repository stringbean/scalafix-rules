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

import scalafix.lint.LintSeverity
import scalafix.v1.*

import scala.meta.*

class ImportMatcher(config: IllegalImportsConfig) {
  private val rules = config.error.map(rule => IllegalImportRule.apply(rule, LintSeverity.Error)) ++
    config.warning.map(rule => IllegalImportRule.apply(rule, LintSeverity.Warning))

  def findIllegalImports(i: Import): Seq[Patch] = {
    i.importers flatMap { importer =>
      importer.importees flatMap { importee =>
        rules.view
          .flatMap(rule => rule.findMatching(importer, importee))
          .headOption
          .map(failure => Patch.lint(failure))
      }
    }
  }
}
