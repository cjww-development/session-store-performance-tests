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

package com.cjwwdev.peformance.helpers

import com.typesafe.config.ConfigFactory

trait ConnectionSettings {

  private val config = ConfigFactory.load()

  //Local | Sandbox | Heroku
  private val env: String = Option(System.getProperty("env")).getOrElse("local")

  val applicationUrl: String = config.getString(s"baseUrls.$env")

  val jps: Int      = config.getInt("runConfig.jps")
  val duration: Int = config.getInt("runConfig.duration")
}
