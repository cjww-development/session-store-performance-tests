/*
 * Copyright 2018 CJWW Development
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

val testPackName = "session-store-performance-tests"

val dependencies: Seq[ModuleID] = Seq(
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.0.2" % "test, it",
  "io.gatling"            % "gatling-test-framework"    % "3.0.2" % "test, it",
  "com.typesafe"          % "config"                    % "1.3.3",
  "org.apache.commons"    % "commons-lang3"             % "3.4"
)

val options: Seq[String] = Seq(
  "-encoding",
  "UTF-8",
  "-target:jvm-1.8",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:implicitConversions",
  "-language:postfixOps"
)

lazy val testPack = Project(testPackName, file("."))
  .enablePlugins(GatlingPlugin)
  .settings(
    version             :=  "0.1.0",
    scalaVersion        :=  "2.12.8",
    libraryDependencies ++= dependencies,
    scalacOptions       :=  options
  )