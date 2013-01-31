package models.jajascript

import collection.mutable
import play.api.Logger
import java.util


case class Optimize(flightsIn: Seq[Flight]) {

  def compFlight(flight1: Flight, flight2: Flight) = (flight1.startTime < flight2.startTime)

  val flights: Seq[Flight] = flightsIn.sortWith(compFlight)

  val lastSolutions = mutable.Queue[Solution]()

  def optimize: Solution = {

    flights.foreach(flight => {

      var bestSolution: Option[Solution] = None
      lastSolutions.foreach(solution =>
        if (solution.endTime <= flight.startTime) {
          if (bestSolution.isDefined) {
            if (bestSolution.get.price < solution.price) {
              bestSolution = Option(solution)
            }
          } else {
            bestSolution = Option(solution)
          }
        }
      )

      if (bestSolution.isDefined) {
        lastSolutions.enqueue(Solution(flight.end, bestSolution.get.price + flight.price, bestSolution, flight))
        lastSolutions.dequeueAll(solution => {
          solution.price < bestSolution.get.price
        })
      } else {
        lastSolutions.enqueue(Solution(flight.end, flight.price, None, flight))
      }
    })

    lastSolutions.maxBy(solution => solution.price)
  }

}
