package com.urlable.shorturlsvc.restapi.json

import java.net.URL

import com.urlable.shorturlsvc.core.models.{ShortUrlId, ShortUrlView}
import com.urlable.shorturlsvc.core.tdk.testobjects.ATest
import com.urlable.shorturlsvc.core.tdk.BaseFunSpecTest
import spray.json._

class JsonSupportTest
  extends BaseFunSpecTest {

  describe("importing JsonSupport") {

    val jsonSupport = new JsonSupport {}
    import jsonSupport._

    it("should enable round tripping an instance of URL to JSON and back") {

      /** arrange **/
      val expectedUrl =
        new URL("http://google.com")

      /** act **/
      val actualUrl =
        expectedUrl
          .toJson
          .convertTo[URL]

      /** assert **/
      assert(actualUrl == expectedUrl)

    }

    it("should enable round tripping an instance of ShortUrlView to JSON and back") {

      /** arrange **/
      val expectedShortUrlView =
        ATest.shortUrlView.nonNull

      /** act **/
      val actualShortUrlView =
        expectedShortUrlView
          .toJson
          .convertTo[ShortUrlView]

      /** assert **/
      assert(actualShortUrlView == expectedShortUrlView)

    }

    it("should enable round tripping an instance of ShortUrlId to JSON and back") {

      /** arrange **/
      val expectedShortUrlId: ShortUrlId = 12345L

      /** act **/
      val actualShortUrlId =
        expectedShortUrlId
          .toJson
          .convertTo[ShortUrlId]

      /** assert **/
      assert(actualShortUrlId == expectedShortUrlId)

    }

  }

}
