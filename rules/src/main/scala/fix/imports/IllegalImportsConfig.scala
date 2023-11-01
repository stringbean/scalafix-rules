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

import metaconfig.generic.Surface
import metaconfig.ConfDecoder

object IllegalImportsConfig {
  def default: IllegalImportsConfig = IllegalImportsConfig()

  implicit val surface: Surface[IllegalImportsConfig] =
    metaconfig.generic.deriveSurface[IllegalImportsConfig]
  implicit val decoder: ConfDecoder[IllegalImportsConfig] =
    metaconfig.generic.deriveDecoder(default)
}

case class IllegalImportsConfig(error: Seq[String] = Seq("com.sun"), warning: Seq[String] = Seq.empty)
