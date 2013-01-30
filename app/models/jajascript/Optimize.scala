package models.jajascript

import collection.mutable
import play.api.libs.iteratee.Input.Empty


case class Optimize(flightsIn:Seq[Flight]) {

  def compFlight(flight1:Flight, flight2:Flight) = (flight1.startTime < flight2.startTime)

  val flights:Seq[Flight] = flightsIn.sortWith(compFlight)

  val lastSolutions = mutable.Queue[Solution]()

  def optimize:Solution = {
    flights.foreach(flight => {
      val possibleSolutions = lastSolutions.filter(solution => flight.startTime >= solution.endTime)

      if (possibleSolutions.isEmpty) {
        lastSolutions.enqueue(Solution(flight.end, flight.price, Seq(flight)))
      } else {
        val bestSolution = possibleSolutions.maxBy(solution => solution.price)
        lastSolutions.enqueue(Solution(flight.end, bestSolution.price + flight.price, bestSolution.acceptedFlights.union(Seq(flight))))
      }
    })

    lastSolutions.maxBy(solution => solution.price)
  }

}
