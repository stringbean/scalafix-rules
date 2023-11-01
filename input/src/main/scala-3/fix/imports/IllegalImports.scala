/*
rules = [IllegalImports]
IllegalImports.imports = [
  "com.sun.*"
  java.util.Date
  "java.awt.*"
]
*/
package fix.imports

import com.sun.net.httpserver.HttpServer /* assert: IllegalImports
       ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
import of class from illegal package
*/

import java.util.{Date, Calendar} /* assert: IllegalImports
                  ^^^^
import of illegal class
*/

import java.awt.* /* assert: IllegalImports
       ^^^^^^^^^^
import of illegal package
*/

class IllegalImports
