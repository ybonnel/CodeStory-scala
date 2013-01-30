package models.jajascript

import collection.mutable
import play.api.Logger
import java.util


case class Optimize(flightsIn:Seq[Flight]) {

  def compFlight(flight1:Flight, flight2:Flight) = (flight1.startTime < flight2.startTime)

  val flights:Seq[Flight] = flightsIn.sortWith(compFlight)

  val lastSolutions = mutable.Queue[Solution]()

  def optimize:Solution = {

    var elapsedTimeFilter:Long = 0
    var elapsedTimeMaxBy:Long = 0
    var elapsedTimeEnqueue:Long = 0
    var elapsedTimeDequeue:Long = 0

    flights.zip(0 to flights.size).foreach(flightWithPosition => {
      val startTimeFilter = System.nanoTime()
      val possibleSolutions = lastSolutions.filter(solution => flightWithPosition._1.startTime >= solution.endTime)
      elapsedTimeFilter += System.nanoTime() - startTimeFilter
      if (possibleSolutions.isEmpty) {
        val startTimeEnqueue = System.nanoTime()
        val acceptedFlights = new util.BitSet()
        acceptedFlights.set(flightWithPosition._2)
        lastSolutions.enqueue(Solution(flightWithPosition._1.end, flightWithPosition._1.price, acceptedFlights))
        elapsedTimeEnqueue += System.nanoTime() - startTimeEnqueue
      } else {
        val startTimeMaxBy = System.nanoTime()
        val bestSolution = possibleSolutions.maxBy(solution => solution.price)
        elapsedTimeMaxBy += System.nanoTime() - startTimeMaxBy
        val startTimeEnqueue = System.nanoTime()
        val acceptedFlights = new util.BitSet()
        acceptedFlights.or(bestSolution.acceptedFlights)
        acceptedFlights.set(flightWithPosition._2)
        lastSolutions.enqueue(Solution(flightWithPosition._1.end, bestSolution.price + flightWithPosition._1.price, acceptedFlights))
        elapsedTimeEnqueue += System.nanoTime() - startTimeEnqueue
        val startTimeDequeue = System.nanoTime()
        lastSolutions.dequeueAll(solution => { solution.price < bestSolution.price})
        elapsedTimeDequeue += System.nanoTime() - startTimeDequeue
      }
    })

    Logger.info("Time for filter  : " + elapsedTimeFilter)
    Logger.info("Time for maxBy   : " + elapsedTimeMaxBy)
    Logger.info("Time for enqueue : " + elapsedTimeEnqueue)
    Logger.info("Time for dequeue : " + elapsedTimeDequeue)

    lastSolutions.maxBy(solution => solution.price)
  }

}
