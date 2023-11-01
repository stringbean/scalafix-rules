/*
rules = [IllegalImports]
IllegalImports.error = [
  "com.sun.*"
  java.util.Date
  "java.awt.*"
  "org.apache.commons.lang.*"
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

import java.awt._ /* assert: IllegalImports
       ^^^^^^^^^^
import of illegal package
*/

import org.apache.commons.lang.math.NumberUtils /* assert: IllegalImports
       ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
import of class from illegal package
*/

// below should not be matched
import org.apache.commons.lang3.exception.ExceptionUtils

class IllegalImports
