package playground.cetera.inheritance

/** I have a specific functionality in mind here.  I want an abstract class, and
  * I want the companion objects of the abstract class to extend a common trait.
  * However, I also want the abstract class to access member of that trait.
  */

/** Here is the abstract class.  I want variableCol to be in scope, even if it
  * isn't defined
  */
object Companions {
  sealed trait CommonColumns {
    val commonCol1: String
    val commonCol2: String
  }

  case class ExtensionColumns(
      commonCol1: String,
      commonCol2: String,
      newColumn: String
  ) extends CommonColumns

  abstract class AbstractClass[ColumnType <: CommonColumns] {

    val companionOb: AbsClassCompanion[ColumnType]
    lazy val cols: ColumnType = companionOb.columnObject

    def doOperationWithCommonCols: Unit = println(
      cols.commonCol1 + "," + cols.commonCol2
    )
  }

  trait AbsClassCompanion[ColumnType <: CommonColumns] {
    val columnObject: ColumnType
  }

// Extension of AbstractClass
  class ClassExtension extends AbstractClass[ExtensionColumns] {

    val companionOb: AbsClassCompanion[ExtensionColumns] = ClassExtension

    import ClassExtension._

    def doOperationWithNewCols: Unit = println(cols.newColumn)

    def prodOfList: Int = someVal.reduce(_ * _)
  }

  object ClassExtension extends AbsClassCompanion[ExtensionColumns] {

    val columnObject: ExtensionColumns =
      ExtensionColumns(
        commonCol1 = "foo",
        commonCol2 = "bar",
        newColumn = "baz"
      )

    val someVal: List[Int] = List(1, 2, 3)

  }
}
