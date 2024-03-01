package exchange

import sttp.client4._
import scala.concurrent.duration.DurationInt
import org.json4s._
import org.json4s.native.JsonMethods._
class CoinBase extends IExchange {

  override def name: String = "CoinBase"

  override def thresholdProfit: BigDecimal = BigDecimal("0.00")

  override def transactionFees: BigDecimal = BigDecimal("0.04")

  override def depositFees: BigDecimal = BigDecimal("0.00")
  override def withdrawalFees: BigDecimal = BigDecimal("0.00")
    /**
    * Get the current price of Bitcoin from CoinBase
    * @return The current price of Bitcoin
    */
  override
  def getPrice: Option[BigDecimal] = {
    val url = "https://api.coinbase.com/v2/prices/spot?currency=USD"
    val request = basicRequest.get(uri"$url").readTimeout(10.seconds)
    val backend = DefaultSyncBackend()
    val response = request.send(backend)
    val body = response.body

    if(body.isRight) {

      val x = body.getOrElse("")

      val p = parse(x)

      // get data amount
      val data = p \ "data" \ "amount"
      val price = compact(render(data)).replace("\"", "")

      Some(BigDecimal(price))

    } else {
      None
    }

  }
}
