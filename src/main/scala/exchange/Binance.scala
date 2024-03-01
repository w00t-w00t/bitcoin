package exchange
import sttp.client4._

import scala.concurrent.duration.DurationInt

class Binance extends IExchange {
    override def name: String = "Binance"

    override def thresholdProfit: BigDecimal = BigDecimal("0.00")
    override def transactionFees: BigDecimal = BigDecimal("0.02")

  override def depositFees: BigDecimal = BigDecimal("0.00")
  override def withdrawalFees: BigDecimal = BigDecimal("0.00")
   /**
     * Get the current price of Bitcoin from Binance
     * @return The current price of Bitcoin
     */
    override
    def getPrice: Option[BigDecimal] = {
      val url = "https://www.binance.com/en/price/bitcoin"

      val request = basicRequest
        .get(uri"$url")
        .readTimeout(10.seconds)

      val backend = DefaultSyncBackend()
      val response = request.send(backend)

      //println(response.header("Content-Length"))

      val body = response.body

      if(body.isRight){

        val x = body.getOrElse("")

        //println(x)

        val regex = """The\s+live\s+price\s+of\s+BTC\s+is\s+\$([\d,.]+)\s+with""".r

        val matchResult = regex.findFirstMatchIn(x)

        matchResult match {
          case Some(m) =>
            val price = m.group(1).replace(",", "")
            println(price)
            Some(BigDecimal(price))
          case None =>
            None
        }

      } else None

    }

}
