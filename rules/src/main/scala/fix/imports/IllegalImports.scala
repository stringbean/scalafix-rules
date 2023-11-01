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

import metaconfig.Configured
import scalafix.v1.*

import scala.meta.*

class IllegalImports(config: IllegalImportsConfig) extends SemanticRule("IllegalImports") {
  def this() = this(IllegalImportsConfig())

  override val isLinter: Boolean = true
  override val description: String = "Ensures a file does not use certain imports"

  override def withConfiguration(config: Configuration): Configured[Rule] = {
    config.conf
      .getOrElse("IllegalImports")(this.config)
      .map(newConfig => new IllegalImports(newConfig))
  }

  override def fix(implicit doc: SemanticDocument): Patch = {
    val matcher = new ImportMatcher(config)

    doc.tree
      .collect { case i: Import =>
        matcher.findIllegalImports(i)
      }
      .flatten
      .asPatch
  }
}
