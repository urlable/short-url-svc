package com.urlable.shorturlsvc.core

import java.net.URL
import com.urlable.shorturlsvc.core.tdk.testobjects.ATest
import com.urlable.shorturlsvc.core.tdk.BaseFunSpecTest
import org.mockito.Mockito._

import scala.concurrent.Future

class CoreTest extends BaseFunSpecTest {

  describe("createShortUrl") {
    it("should invoke createShortUrlUc.execute and return result") {

      /** arrange **/
      val providedTarget = new URL("http://dummyurl.co")
      val expectedReturnedFutureShortUrlView = Future.successful(ATest.shortUrlView.nonNull)

      val mockCompositionRoot = mock[CompositionRoot](RETURNS_DEEP_STUBS)
      when(mockCompositionRoot.createShortUrlUc.execute(providedTarget))
        .thenReturn(expectedReturnedFutureShortUrlView)

      val objectUnderTest = new Core(mockCompositionRoot)

      /** act **/
      val actualReturnedFutureShortUrlView =
        objectUnderTest
          .createShortUrl(providedTarget)

      /** assert **/
      assert(actualReturnedFutureShortUrlView == expectedReturnedFutureShortUrlView)

    }
  }
  describe("getShortUrlWithId") {
    it("should invoke getShortUrlWithId.execute and return result") {

      /** arrange **/
      val providedShortUrlId = ATest.string.nonNull
      val expectedReturnedFutureShortUrlView = Future.successful(ATest.shortUrlView.nonNull)

      val mockCompositionRoot = mock[CompositionRoot](RETURNS_DEEP_STUBS)
      when(mockCompositionRoot.getShortUrlWithIdUc.execute(providedShortUrlId))
        .thenReturn(expectedReturnedFutureShortUrlView)

      val objectUnderTest = new Core(mockCompositionRoot)

      /** act **/
      val actualReturnedFutureShortUrlView =
        objectUnderTest
          .getShortUrlWithId(providedShortUrlId)

      /** assert **/
      assert(actualReturnedFutureShortUrlView == expectedReturnedFutureShortUrlView)

    }
  }

}
