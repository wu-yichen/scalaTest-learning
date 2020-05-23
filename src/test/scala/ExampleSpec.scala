import org.scalatest._
import java.util.UUID.randomUUID

import DatabaseServer.db
import org.scalatest

object DatabaseServer {
  type db = StringBuilder
  private val database: Map[String, db] = Map()

  def start(name: String): db = {
    val newDb = new db
    database.updated(name, newDb)
    newDb
  }

  def clean(name: String) = database.removed(name)
}

class ExampleSpec extends FlatSpec {
  def withDatabase(test: db => Any) = {
    val dbName = randomUUID.toString
    val db = DatabaseServer.start(dbName)
    try {
      test(db.append("good"))
    }
    finally DatabaseServer.clean(dbName)
  }

  "A test with database" should "pass" in withDatabase { db =>
    db.append(" guy")
    assert(db.toString() == "good guy")
  }
}
