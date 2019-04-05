package com.example.wafflesale.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.wafflesale.domain.*

class MyDBAdapter(_context: Context) {
    private val DATABASE_NAME = "Waffles"
    private var mContext: Context? = null
    private var mDbHelper: MyDBHelper? = null
    private var mSqLiteDatabase: SQLiteDatabase? = null
    private val DATABASE_VERSION = 1

    init {
        this.mContext = _context
        mDbHelper = MyDBHelper(_context, DATABASE_NAME, null, DATABASE_VERSION)
    }

    fun open() {
        mSqLiteDatabase = mDbHelper?.writableDatabase
    }

    fun addMember(firstName: String, lastName: String, image: Int) {
        val contentValues = ContentValues()
        contentValues.put("firstName", firstName)
        contentValues.put("lastName", lastName)
        contentValues.put("image", image)
        mSqLiteDatabase?.insert("members", null, contentValues)
    }

    fun addClient(firstName: String, lastName: String, street: String, number: String, city: String, postCode: String) {
        val contentValues = ContentValues()
            contentValues.put("firstName", firstName)
            contentValues.put("lastName", lastName)
            contentValues.put("street", street)
            contentValues.put("number", number)
            contentValues.put("city", city)
            contentValues.put("postCode", postCode)
            mSqLiteDatabase?.insert("clients", null, contentValues)
    }

    fun addOrder(memberId: Int, clientId: Int) {
        val contentValues = ContentValues()
        contentValues.put("memberId", memberId)
        contentValues.put("clientId", clientId)
        mSqLiteDatabase?.insert("orders", null, contentValues)
    }

    fun addOrderLine(orderId: Int, product: Product, amount: Int) {
        val contentValues = ContentValues()
        contentValues.put("orderId", orderId)
        contentValues.put("product", product.pName)
        contentValues.put("amount", amount)
        mSqLiteDatabase?.insert("order_lines", null, contentValues)
    }

    fun findAllMembers(): ArrayList<Member> {
        var allMembers: ArrayList<Member> = ArrayList()
        var cursor: Cursor = mSqLiteDatabase?.query(
            "members", null, null,
            null, null, null, null
        )!!
        if (cursor.moveToFirst()) {
            do {
                var member = Member()
                member.id = cursor.getInt(0)
                member.firstName = cursor.getString(1)
                member.lastName = cursor.getString(2)
                member.imageUrl = cursor.getInt(3)
                allMembers.add(member)
            } while (cursor.moveToNext())
        }
        return allMembers
    }

    fun findAllClients(): ArrayList<Client> {
        var allClients: ArrayList<Client> = ArrayList()
            var cursor: Cursor = mSqLiteDatabase?.query(
                "clients", null, null,
                null, null, null, null
            )!!
            if (cursor.moveToFirst()) {
                do {
                    var client = Client()
                    client.id = cursor.getInt(0)
                    client.firstName = cursor.getString(1)
                    client.lastName = cursor.getString(2)
                    client.street = cursor.getString(3)
                    client.number = cursor.getString(4)
                    client.city = cursor.getString(5)
                    client.postCode = cursor.getString(6)
                    allClients.add(client)
                } while (cursor.moveToNext())
            }
        return allClients
    }

    fun findAllOrders(): ArrayList<Order> {
        var allOrders: ArrayList<Order> = ArrayList()
        var cursor: Cursor = mSqLiteDatabase?.query(
            "orders", null, null,
            null, null, null, null
        )!!
        if (cursor.moveToFirst()) {
            do {
                var order = Order()
                order.id = cursor.getInt(0)
                order.memberId = cursor.getInt(1)
                order.clientId = cursor.getInt(2)
                allOrders.add(order)
            } while (cursor.moveToNext())
        }
        return allOrders
    }

    fun findAllOrderLinesByOrder(id: Int): ArrayList<OrderLine> {
        var allOrderLines: ArrayList<OrderLine> = ArrayList()
        var cursor: Cursor = mSqLiteDatabase?.query(
            "orderLines", null, "orderId = $id",
            null, null, null, null
        )!!
        if (cursor.moveToFirst()) {
            do {
                var orderLine = OrderLine()
                orderLine.id = cursor.getInt(0)
                orderLine.orderId = cursor.getInt(1)
                when (cursor.getString(2)) {
                    Product.CHOCO_WAFFLE.pName -> orderLine.product = Product.CHOCO_WAFFLE
                    Product.VANILLE_WAFFLE.pName -> orderLine.product = Product.VANILLE_WAFFLE
                    Product.FRANCHIPAN.pName -> orderLine.product = Product.FRANCHIPAN
                    Product.SQUAREJAM.pName -> orderLine.product = Product.SQUAREJAM
                    Product.MIX.pName -> orderLine.product = Product.MIX
                }
                orderLine.amount = cursor.getInt(3)
                allOrderLines.add(orderLine)
            } while (cursor.moveToNext())
        }
        return allOrderLines
    }

    fun deleteMembers() {
        mSqLiteDatabase?.delete("members", null, null)
    }

    fun deleteMemberById(id: Int) {
        mSqLiteDatabase?.delete("members", "id = $id", null)
    }

    fun deleteClients() {
        mSqLiteDatabase?.delete("clients", null, null)
    }

    fun deleteClientById(id: Int) {
        mSqLiteDatabase?.delete("clients", "id = $id", null)
    }

    fun deleteOrders() {
        mSqLiteDatabase?.delete("orders", null, null)
    }

    fun deleteOrderById(id: Int) {
        mSqLiteDatabase?.delete("orders", "id = $id", null)
    }

    fun deleteOrderLinesOfOrder(id : Int) {
        mSqLiteDatabase?.delete("orderLines", "orderId = $id", null)
    }

    fun dropTables() {
        mDbHelper?.onUpgrade(mSqLiteDatabase, 1, 1)
    }


    inner class MyDBHelper(
        context: Context?, name: String?,
        factory: SQLiteDatabase.CursorFactory?, version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            val queryMembers =
                "CREATE TABLE members(id integer primary key autoincrement, firstName text, lastName text, image integer);"
            val queryClients =
                "CREATE TABLE clients(id integer primary key autoincrement, firstName text, lastName text, street text, number, text, city text, postCode text);"
            val queryOrders =
                "CREATE TABLE orders(id integer primary key autoincrement, memberId integer, clientId integer, FOREIGN KEY(memberId) REFERENCES members(id), FOREIGN KEY(clientId) REFERENCES clients(id));"
            val queryOrderLines =
                "CREATE TABLE order_lines(id integer primary key autoincrement, orderId int, product text, amount integer, FOREIGN KEY(orderId) REFERENCES orders(id));"
            db?.execSQL(queryMembers)
            db?.execSQL(queryClients)
            db?.execSQL(queryOrders)
            db?.execSQL(queryOrderLines)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            val dropMembers = "DROP TABLE IF EXISTS members;"
            val dropClients = "DROP TABLE IF EXISTS clients;"
            val dropOrders = "DROP TABLE IF EXISTS orders;"
            val dropOrderLines = "DROP TABLE IF EXISTS order_lines;"
            db?.execSQL(dropMembers)
            db?.execSQL(dropClients)
            db?.execSQL(dropOrders)
            db?.execSQL(dropOrderLines)
            onCreate(db)
        }

    }
}