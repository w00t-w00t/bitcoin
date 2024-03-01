package exchange

trait IExchange {
    def name : String

    def getPrice: Option[BigDecimal]

    def transactionFees: BigDecimal
    def depositFees: BigDecimal
    def withdrawalFees: BigDecimal

    def thresholdProfit: BigDecimal
}
