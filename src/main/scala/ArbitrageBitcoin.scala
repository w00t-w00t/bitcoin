import exchange.{Binance, CoinBase, IExchange}
import scala.math.BigDecimal
object ArbitrageBitcoin {

  // L’arbitrage de crypto-monnaie consiste à acheter des crypto-monnaies sur une bourse et à les vendre sur une autre pour en tirer profit. L’objectif principal de la stratégie d’arbitrage est de gagner de l’argent en utilisant les différences de valeur des pièces cryptographiques entre deux échanges.
  //Il s’agit d’une approche simple du trading de crypto-monnaies par rapport à d’autres méthodes nécessitant une analyse technique. De plus, il s’agit de l’une des meilleures stratégies de trading pour les marchés des cryptomonnaies en raison de sa volatilité.
  // https://www.youtube.com/watch?v=GDtT4UDFK2w

  // une opportunité d'arbitrage est présente lorsqu'il y a la possibilité d'acheter instantanément quelque chose à bas prix sur un marché et de le vendre à un prix plus élevé ailleurs :
  // - arbitrage entre échanges
  // - arbitrage au sein d'une bourse unique

  // Arbitrage spatial optimisé Bitcoin
  //  https://www.youtube.com/watch?v=B450SDfdh0c
  //  https://www.youtube.com/watch?v=PRFJiz5UaFw
  //  https://www.youtube.com/watch?v=GDtT4UDFK2w
  // = pas de transfert de Crypto à réaliser
  // = pas de frais de transfert de Crypto à payer
  //
  // Prérequis : avoir la crypto concerné déja en spot sur les deux exchanges
  // Exemple :
  //  - 100 dollars ETH sur Binance (get) et 100 dollars ETH sur CoinBase (x)
  // si je constate un écart de prix significatif :
  //  - 50 dollars ETH vendu sur Binance (la ou le prix sera le plus élevé)
  //  - 50 dollars ETH acheté sur CoinBase (la ou le prix sera le plus bas)
  // => ça m'aura permis de profiter de l'écart de prix en économisant sur les frais d'achat, frais de transactions, et frais de vente

  // https://coinmarketcap.com/fr/currencies/bitcoin/#Markets

  // https://docs.ccxt.com/#/ccxt.pro.manual

  // https://crypto-robot.com/blog/trading-haute-frequence-arbitrage-cryptos-python

  def main(args: Array[String]): Unit = {

    // major exchanges : Binance, CoinBase, Kraken, Gemini, Bitfinex, Bitstamp, Cex.io, Luno, Paxful, eToro, LocalBitcoins
    val exchange : IExchange = new Binance()
    val exchange2 : IExchange = new CoinBase()

    val exchanges = List(exchange, exchange2)

    for (exchange_buy <- exchanges) {
      val price_buy_opt = exchange_buy.getPrice
      for (exchange_sell <- exchanges) {
        if (!exchange_buy.equals(exchange_sell)) {
          val price_sell_opt = exchange_sell.getPrice
          if (price_buy_opt.isDefined && price_sell_opt.isDefined) {

            val price_buy = price_buy_opt.get
            val price_sell = price_sell_opt.get

            val price_delta = price_sell.-(price_buy)
            val profit = price_delta - exchange_buy.transactionFees - exchange_sell.transactionFees

            if (profit >= exchange_buy.thresholdProfit) {
              //val quantite = ... // Déterminer la quantité optimale à acheter/vendre

              // acheterBitcoin(current_exchange_buy, quantite)
              // vendreBitcoin(current_exchange_sell, quantite)

              val quantite = 0

              println(s"Achat de $quantite BTC sur ${exchange_buy.name} à $price_buy")
              println(s"Vente de $quantite BTC sur ${exchange_sell.name} à $price_sell")
              println(s"Profit potentiel : $profit")

            }
          }
        }
      }
    }

  }

}
