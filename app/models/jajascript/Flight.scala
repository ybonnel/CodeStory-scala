package models.jajascript


case class Flight(name:String, startTime:Int, duration:Int, price:Int) {

  def end=startTime+duration
}
