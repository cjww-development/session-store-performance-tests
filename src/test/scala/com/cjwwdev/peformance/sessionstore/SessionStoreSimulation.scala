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

package com.cjwwdev.peformance.sessionstore

import java.util.UUID

import com.cjwwdev.peformance.helpers.ConnectionSettings
import io.gatling.core.Predef._
import io.gatling.core.session.Expression
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder

import scala.concurrent.duration._

class SessionStoreSimulation extends Simulation with ConnectionSettings {

  def uuid: String = UUID.randomUUID().toString

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(applicationUrl)
    .header("requestId", s"requestId-${UUID.randomUUID()}")
    .header("Content-Type", "text/plain")
    .header("cjww-headers", "mKAPsVVBf4-hJzJiuBe5234wS-xI5onArz3lzIrEHBlNhTH1CHqQhyYZFgYasZHmi0kGL6DsAbaU0BRL81FLLA")

  def createSession(url: Expression[String]): HttpRequestBuilder = {
    http("create-session")
      .post(url)
      .body(StringBody(""))
      .check(status is 201)
  }

  def updateSession(url: Expression[String]): HttpRequestBuilder = {
    http("update-session")
      .patch(url)
      .body(StringBody(
        s"""
          |{
          | "basicValue" : "testValue",
          | "encValue" : "da123d403129e59a4e351242952b38a4683d0a7aaa79d0cc1601e3721849c10642ab6cd966f31376ba797c1ae884f2dcb912e10486f987f66f05f1f36d18bd5c"
          |}
          """.stripMargin))
      .check(status is 200)
  }

  def getSession(id: String, key: Option[String] = None): HttpRequestBuilder = {
    http(key.map(x => s"get-entry-from-session-$x").getOrElse("get-session"))
      .get(key.map(x => s"/session/session-$id/data?key=$x").getOrElse(s"/session/session-$id/data"))
      .check(status is 200)
  }

  def deleteSession(id: String): HttpRequestBuilder = {
    http("delete-session")
      .delete(s"/session/session-$id")
      .check(status is 204)
  }

  def sessionStoreTests(id: Expression[String]): ScenarioBuilder = {
    scenario("Session store performance run")
      .exec(createSession(_ => s"/session/session-$id"))
      .exec(updateSession(_ => s"/session/session-$id"))
//      .exec(getSession(id, Some("basicValue")))
//      .exec(getSession(id, Some("encValue")))
//      .exec(getSession(id))
//      .exec(deleteSession(id))
  }

  setUp(sessionStoreTests(_ => uuid).inject(constantUsersPerSec(jps) during(duration minute)))
    .protocols(httpProtocol)
}
